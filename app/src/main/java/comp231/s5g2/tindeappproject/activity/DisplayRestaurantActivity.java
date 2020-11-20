package comp231.s5g2.tindeappproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.adapter.AdapterListDishes;
import comp231.s5g2.tindeappproject.models.Dish;
import comp231.s5g2.tindeappproject.models.Restaurant;

public class DisplayRestaurantActivity extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    //Restaurant restaurant = new Restaurant();

    private TextView restaurantPhone;
    private TextView restaurantName;
    private RecyclerView listDishes;
    AdapterListDishes adapter = new AdapterListDishes();

    public List<Dish> dishes = new ArrayList<Dish>();
    //public List<String> dishesName = new ArrayList<>();


    private String matchedRestaurantID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matchedRestaurantID = "-MMDriFDJXNS9dSmrusC";

        restaurantName = findViewById(R.id.restaurantName);
        restaurantPhone = findViewById(R.id.restaurantPhone);

        DatabaseReference nameRef = myRef.child(matchedRestaurantID).child("restaurantName");

        nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                restaurantName.setText(dataSnapshot.getValue(String.class));
                Log.d("restName", "Value is: " + restaurantName.getText());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("restName", "Failed to read value.", error.toException());
            }
        });

        DatabaseReference phoneRef = myRef.child(matchedRestaurantID).child("restaurantPhone");

        phoneRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                restaurantPhone.setText(dataSnapshot.getValue(String.class));
                Log.d("restName", "Value is: " + restaurantPhone.getText());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("restName", "Failed to read value.", error.toException());
            }
        });


        Log.e("Dishes size outside ", " " + dishes.size());


        ///DISPLAYING THE DISHES

        listDishes = findViewById(R.id.RecyclerViewDishes);

        DatabaseReference dishesRef = myRef.child(matchedRestaurantID).child("dishes");
        dishesRef.addValueEventListener(new ValueEventListener() {

            Dish dish;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dishes.clear();

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                    Dish dish = childDataSnapshot.getValue(Dish.class);
                    dishes.add(dish);

                }

                adapter = new AdapterListDishes(dishes);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                listDishes.setHasFixedSize(true);
                listDishes.setLayoutManager(layoutManager);
                listDishes.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }
}







/*

       Dish dish1 = new Dish("Fried Egg", 123,"very good");
        Dish dish2 = new Dish("Fries", 32.1,"vgreat");
        Dish dish3 = new Dish("omelets", 23.3,"mediocre");
        Dish dish5 = new Dish("omelets", 23.3,"mediocre");
        Dish dish6 = new Dish("omelets", 23.3,"mediocre");
        Dish dish7 = new Dish("omelets", 23.3,"mediocre");

        dishes.add(dish1);
        dishes.add(dish2);
        dishes.add(dish3);
        dishes.add(dish5);
        dishes.add(dish6);
        dishes.add(dish7);


        List<Dish> dishes = new ArrayList<>();
        dishes.add(dish1);
        dishes.add(dish2);
        dishes.add(dish3);

        restaurant.setDishes(dishes);
        restaurant.setRestaurantAddress("999 Centennial Avenue");
        restaurant.setRestaurantName("Tim Hortons");
        restaurant.setRestaurantPhone("3133224");

        myRef.push().setValue(restaurant);*/
