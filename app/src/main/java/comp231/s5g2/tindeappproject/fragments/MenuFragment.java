package comp231.s5g2.tindeappproject.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.List;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.activity.AddDishesActivity;
import comp231.s5g2.tindeappproject.activity.CreateRestaurantActivity;
import comp231.s5g2.tindeappproject.adapter.AdapterListDishes;
import comp231.s5g2.tindeappproject.models.Dish;
import comp231.s5g2.tindeappproject.models.Owner;
import comp231.s5g2.tindeappproject.models.Restaurant;

public class MenuFragment extends Fragment {

    private List<Dish> dishList = new ArrayList<>();

    public Uri imguri;
    StorageReference storageRef;
    private StorageTask uploadTask;

    private EditText dishName, dishDescription;
    private ImageView dishImage;
    private RadioButton petSafe, vegan, vegetarian, nutsFree, halal;
    private Button createDish;

    private Owner owner = new Owner();
    private Restaurant restaurant = new Restaurant();
    private Dish dish = new Dish();
    private List<Dish> listDish = new ArrayList();

    private RecyclerView recyclerViewDishes;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Restaurants");

    private StorageReference refDishImage;
    private AdapterListDishes adapter;

    private Button addDishActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            owner.setOwnerID("3");


        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DatabaseReference refDishes = myRef.child(owner.getOwnerID()).child("restaurants");
            View view = inflater.inflate(R.layout.fragment_menu, container, false);

            addDishActivity = view.findViewById(R.id.buttonAddDish);
            recyclerViewDishes = view.findViewById(R.id.RecyclerViewDishes);

        refDishes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    restaurant = snapshot.getValue(Restaurant.class);
                    listDish = restaurant.getDishes();
                    addDishActivity.setVisibility(View.INVISIBLE);
                    if (listDish == null){
                        addDishActivity.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        adapter = new AdapterListDishes(getContext(), listDish);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerViewDishes.setHasFixedSize(true);
        recyclerViewDishes.setLayoutManager(layoutManager);
        recyclerViewDishes.setAdapter(adapter);

        addDishActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), AddDishesActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }
}