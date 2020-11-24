package comp231.s5g2.tindeappproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    //Restaurant restaurant = new Restaurant();

    private TextView restaurantPhone, restaurantName;
    private ImageView profilePic;
    private RecyclerView listDishes;
    AdapterListDishes adapter = new AdapterListDishes();
    Owner owner = new Owner();

    String restaurantImg;

    public List<Dish> dishes = new ArrayList<Dish>();
    //public List<String> dishesName = new ArrayList<>();


    private String matchedRestaurantID;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matchedRestaurantID = "4";
        //matchedPhotoID = "1";

        Button nextActivity = findViewById(R.id.buttonNextActivity);

        nextActivity.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), CreateRestaurantActivity.class);
            startActivity(intent);
        });

        profilePic = findViewById(R.id.profileImage);
        listDishes = findViewById(R.id.RecyclerViewDishes);
        restaurantName = findViewById(R.id.restaurantName);
        restaurantPhone = findViewById(R.id.restaurantPhone);

        DatabaseReference ref = myRef.child(matchedRestaurantID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                owner = dataSnapshot.getValue(Owner.class);
               Restaurant restaurantDB = owner.getRestaurant();
                restaurantName.setText(restaurantDB.getRestaurantName());
                restaurantPhone.setText(restaurantDB.getRestaurantPhone());
                dishes = restaurantDB.getDishes();
                restaurantImg = restaurantDB.getPictureToken();
                Log.e("Token",""+restaurantImg);
                 ImageViewLoader();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("restName", "Failed to read value.", error.toException());
            }
        });


        adapter = new AdapterListDishes(dishes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        listDishes.setHasFixedSize(true);
        listDishes.setLayoutManager(layoutManager);
        listDishes.setAdapter(adapter);


    }


    private void ImageViewLoader() {


        StorageReference strPicRef =  FirebaseStorage.getInstance().getReference().child(restaurantImg);

        Log.e("Sucess", "" + strPicRef.toString());

        strPicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("Sucess", "Rest View");
                Glide.with(DisplayRestaurantActivity.this)
                        .load(uri)
                        .into(profilePic);

            }
        });

    }
}

