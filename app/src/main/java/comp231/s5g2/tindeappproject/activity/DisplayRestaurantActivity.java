package comp231.s5g2.tindeappproject.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
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
import java.util.Collections;
import java.util.List;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.adapter.AdapterListDishes;
import comp231.s5g2.tindeappproject.models.Dish;
import comp231.s5g2.tindeappproject.models.Owner;
import comp231.s5g2.tindeappproject.models.Restaurant;

public class DisplayRestaurantActivity extends AppCompatActivity {

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
    private int matchedDishID;

    Restaurant restaurant = new Restaurant();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("Display", "Loading display Activity");


        Button nextActivity = findViewById(R.id.buttonNextActivity);
        nextActivity.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), CreateRestaurantActivity.class);
            startActivity(intent);
        });


        //getting data from finding match activity
        Bundle data = getIntent().getExtras();
        matchedDishID = data.getInt("SelectedDish");
        String matchedOwnerID = data.getString("SelectedOwner");

        dishesRef = restRef.child(matchedOwnerID);
        profilePic = findViewById(R.id.profileImage);
        restaurantName = findViewById(R.id.restaurantName);
        restaurantPhone = findViewById(R.id.restaurantPhone);
        restaurantWebsite = findViewById(R.id.restaurantWebsite);
        recyclerViewDishes = findViewById(R.id.recyclerView);


        DatabaseReference ref = myRef.child(matchedOwnerID);

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
                        adapter = new AdapterListDishes(dishesControl(dishes,matchedDishID),true);
                        recyclerViewDishes.setAdapter(adapter);
                        recyclerViewDishes.setHasFixedSize(true);
                        recyclerViewDishes.setLayoutManager(layoutManager);
                    }
                    ImageViewLoader();

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

        restaurantPhone.setOnClickListener(view -> redirectPhone(restaurantPhone.getText().toString()));

        restaurantWebsite.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private List<Dish> dishesControl(List<Dish> dishes, int matchedDishID) {

       if (matchedDishID > 0){
        Collections.swap(dishes, matchedDishID, 0);
       }
        return dishes;
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

    /*private void redirectWebsite(String text){
        Intent i = new Intent("android.intent.action.VIEW", Uri.parse(text));
        startActivity(i);
    }*/
}

