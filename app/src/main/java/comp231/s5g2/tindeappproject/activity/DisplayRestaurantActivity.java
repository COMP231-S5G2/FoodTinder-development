package comp231.s5g2.tindeappproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.adapter.AdapterListDishes;
import comp231.s5g2.tindeappproject.adapter.RestDisplayAdapter;
import comp231.s5g2.tindeappproject.models.Dish;
import comp231.s5g2.tindeappproject.models.Owner;
import comp231.s5g2.tindeappproject.models.Restaurant;

public class
DisplayRestaurantActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Restaurants");
    //Restaurant restaurant = new Restaurant();

    private TextView restaurantPhone, restaurantName;
    private ImageView profilePic;
    //AdapterListDishes adapter = new AdapterListDishes();
    Owner owner = new Owner();
    //RecyclerView listDishes;
    private ArrayList<Dish> listDishes;
    private ListView listView;
    String restaurantImg;
    public List<Dish> dishes = new ArrayList<>();
    private ArrayAdapter<Dish> adapter;
    private Intent intent;
    StorageReference restRef = FirebaseStorage.getInstance().getReference("Restaurants");
    StorageReference dishesRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("Display", "Loading display Activity");

        String matchedRestaurantID = "3";
        //matchedPhotoID = "1";

        Button nextActivity = findViewById(R.id.buttonNextActivity);
        nextActivity.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), CreateRestaurantActivity.class);
            startActivity(intent);
        });

       // getDishImages();


        dishes = new ArrayList<>();
        listView = (ListView)findViewById(R.id.listView) ;


        dishesRef = restRef.child(matchedRestaurantID);

        profilePic = findViewById(R.id.profileImage);
       //listDishes = findViewById(R.id.RecyclerViewDishes);
        restaurantName = findViewById(R.id.restaurantName);
        restaurantPhone = findViewById(R.id.restaurantPhone);


        DatabaseReference ref = myRef.child(matchedRestaurantID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                if (dataSnapshot !=null) {
                    owner = dataSnapshot.getValue(Owner.class);
                    Restaurant restaurantDB = owner.getRestaurant();
                    restaurantName.setText(restaurantDB.getRestaurantName());
                    restaurantPhone.setText(restaurantDB.getRestaurantPhone());
                    dishes = restaurantDB.getDishes();
                    restaurantImg = restaurantDB.getPictureToken();
                    dishes = restaurantDB.getDishes();
                    ImageViewLoader();

                   /* adapter = new RestDisplayAdapter(getApplicationContext(), dishes );
                    listView.setAdapter(adapter);*/
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Restaurant not Found", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                // Failed to read value
                Log.w("restName", "Failed to read value.", error.toException());
            }
        });






        /*adapter = new AdapterListDishes(getApplicationContext(), dishes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        listDishes.setHasFixedSize(true);
        listDishes.setLayoutManager(layoutManager);
        listDishes.setAdapter(adapter);
*/
    }

    public void getDishImages(){

        //getting images

        dishesRef.getDownloadUrl().addOnSuccessListener(uri -> {
            Log.e("picDish", "success");
            if (uri != null) {
                Glide.with(getApplication())
                        .load(uri)
                        .into(profilePic);
            }

        });
    }



    private void ImageViewLoader() {


        StorageReference strPicRef = FirebaseStorage.getInstance().getReference().child(restaurantImg);

        Log.e("Sucess", "" + strPicRef.toString());

        strPicRef.getDownloadUrl().addOnSuccessListener(uri -> {
            Log.e("Sucess", "Rest View");
            Glide.with(getApplication())
                    .load(uri)
                    .into(profilePic);
        });

    }
}

