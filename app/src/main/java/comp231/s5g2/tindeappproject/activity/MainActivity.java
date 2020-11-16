package comp231.s5g2.tindeappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp231.s5g2.tindeappproject.models.Dish;
import comp231.s5g2.tindeappproject.models.Restaurant;

public class MainActivity extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    Restaurant restaurant = new Restaurant();

    private TextView restaurantPhone;
    private TextView restaurantName;
    private ImageView restaurantImage;
    private RecyclerView listDishes;
    private double dishPrice;
    private List<Dish> dishes = new ArrayList<Dish>();


    private String matchedRestaurantID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matchedRestaurantID = "-MMDriFDJXNS9dSmrusC";



        restaurantName = findViewById(R.id.restaurantName);
        restaurantPhone = findViewById(R.id.restaurantPhone);
        restaurantImage = findViewById(R.id.imageView);

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


        listDishes = findViewById(R.id.DishesList);

        DatabaseReference dishesRef = myRef.child(matchedRestaurantID).child("dishes");


        dishesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Dish dish = new Dish();

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren())
                    //Log.v("key node", "" + childDataSnapshot.getValue()); //displays the key for the node
                    dish = childDataSnapshot.getValue(Dish.class);
                    dishes.add(dish);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       List<String> dishesNames = new ArrayList<String>();

        for (Dish dish: dishes){

            String dishName = dish.getNome();
            dishesNames.add(dishName);
        }

      ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_2, android.R.id.text1, dishesNames);


        listDishes.setAdapter(adapter);



/*      Dish dish1 = new Dish("Fried Egg", 123,"very good");
        Dish dish2 = new Dish("Fries", 32.1,"vgreat");
        Dish dish3 = new Dish("omelets", 23.3,"mediocre");


        List<Dish> dishes = new ArrayList<>();
        dishes.add(dish1);
        dishes.add(dish2);
        dishes.add(dish3);

        restaurant.setDishes(dishes);
        restaurant.setRestaurantAddress("999 Centennial Avenue");
        restaurant.setRestaurantName("Tim Hortons");
        restaurant.setRestaurantPhone("3133224");

        myRef.push().setValue(restaurant);*/
    }
}
