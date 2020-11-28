package comp231.s5g2.tindeappproject.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import comp231.s5g2.tindeappproject.models.Dish;
import comp231.s5g2.tindeappproject.models.Owner;
import comp231.s5g2.tindeappproject.models.Restaurant;

public class
DisplayRestaurantActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Restaurants");
    private TextView restaurantPhone, restaurantName, restaurantWebsite;
    private ImageView profilePic;
    Owner owner = new Owner();
    String restaurantImg;
    public List<Dish> dishes = new ArrayList<>();
    private AdapterListDishes adapter;
    private Intent intent;
    StorageReference restRef = FirebaseStorage.getInstance().getReference("Restaurants");
    StorageReference dishesRef;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    private RecyclerView recyclerViewDishes;

    Restaurant restaurant = new Restaurant();


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
        dishesRef = restRef.child(matchedRestaurantID);

        profilePic = findViewById(R.id.profileImage);
       //listDishes = findViewById(R.id.RecyclerViewDishes);
        restaurantName = findViewById(R.id.restaurantName);
        restaurantPhone = findViewById(R.id.restaurantPhone);
        restaurantWebsite = findViewById(R.id.restaurantWebsite);
        recyclerViewDishes = findViewById(R.id.recyclerView);


        DatabaseReference ref = myRef.child(matchedRestaurantID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                if (dataSnapshot !=null) {
                    owner = dataSnapshot.getValue(Owner.class);
                    restaurant = owner.getRestaurant();
                    restaurantName.setText(restaurant.getRestaurantName());
                    restaurantPhone.setText(restaurant.getRestaurantPhone());
                    restaurantWebsite.setText(restaurant.getWebSite());
                    dishes = restaurant.getDishes();
                    restaurantImg = restaurant.getPictureToken();
                    if (dishes.size() > 0) {
                        dishes = restaurant.getDishes();
                        adapter = new AdapterListDishes(dishes);
                        recyclerViewDishes.setAdapter(adapter);
                        recyclerViewDishes.setHasFixedSize(true);
                        recyclerViewDishes.setLayoutManager(layoutManager);
                    }
                    ImageViewLoader();

                   /* adapter = new RestDisplayAdapter(getApplicationContext(), dishes );
                    listView.setAdapter(adapter);*/
                }

                else {
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

        restaurantPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectPhone(restaurantPhone.getText().toString());
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

    private void redirectPhone(String text){
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel: " + text));
        startActivity(i);
    }
}

