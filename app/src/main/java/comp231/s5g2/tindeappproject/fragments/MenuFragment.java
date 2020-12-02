package comp231.s5g2.tindeappproject.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.activity.AddDishesActivity;
import comp231.s5g2.tindeappproject.adapter.AdapterListDishes;
import comp231.s5g2.tindeappproject.models.Dish;
import comp231.s5g2.tindeappproject.models.Owner;
import comp231.s5g2.tindeappproject.models.Restaurant;

public class MenuFragment extends Fragment {


    private Owner owner = new Owner();
    private Restaurant restaurant = new Restaurant();
    private List<Dish> listDish = new ArrayList<>();

    private RecyclerView recyclerViewDishes;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Restaurants");
    private AdapterListDishes adapter;

    private Button addDishActivity;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

    public MenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        owner.setOwnerID("2");
        DatabaseReference refDishes = myRef.child(owner.getOwnerID());


        addDishActivity = view.findViewById(R.id.buttonAddDish);
        recyclerViewDishes = view.findViewById(R.id.RecyclerViewDishes);

        refDishes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    owner = snapshot.getValue(Owner.class);

                    restaurant = owner.getRestaurant();
                    listDish = restaurant.getDishes();
                    if (restaurant == null){
                        addDishActivity.setVisibility(View.INVISIBLE);
                    }
                    Log.e("Size ", ""+listDish.size());
                    if (listDish.size() > 0) {
                        addDishActivity.setVisibility(View.INVISIBLE);
                        adapter = new AdapterListDishes(listDish,false);
                        recyclerViewDishes.setAdapter(adapter);

                    }
                    recyclerViewDishes.setHasFixedSize(true);
                    recyclerViewDishes.setLayoutManager(layoutManager);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        addDishActivity.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), AddDishesActivity.class);
            startActivity(intent);
        });

        return view;

    }
}