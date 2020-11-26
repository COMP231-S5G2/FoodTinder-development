package comp231.s5g2.tindeappproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.fragments.MenuFragment;
import comp231.s5g2.tindeappproject.fragments.RestaurantFragment;
import comp231.s5g2.tindeappproject.interfaces.IEditRestaurant;

public class CreateRestaurantActivity extends AppCompatActivity {

    Animation rotateOpen, toBottom, rotateClose, fromBottom;

    public FloatingActionButton edit, editMenu, editRestaurant;

    TextView labelEditRestaurant, labelEditMenu;

    private boolean clicked = false;
    private IEditRestaurant listener;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);

        Log.e("Restaurant", "Loading restaurant Activity");


     /*   public void setListener(IEditRestaurant listener)
        {
            this.listener = listener ;
        }*/


        labelEditMenu = findViewById(R.id.editRestaurantLabel);
        labelEditRestaurant = findViewById(R.id.editMenuLabel);

        edit = findViewById(R.id.editFAB);
        editMenu = findViewById(R.id.menuEditFAB);
        editRestaurant = findViewById(R.id.restaurantEditFAB);

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_botton);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_botton);


        SmartTabLayout smartTabLayout = findViewById(R.id.ViewPagerTab);

        ViewPager viewPager = findViewById(R.id.ViewPager);

        //Adapter Config
        FragmentPagerAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("Menu", MenuFragment.class)
                        .add("Restaurant", RestaurantFragment.class)
                        .create());
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);

        View root = viewPager.getRootView();

        edit.setOnClickListener(v ->
                editButtonClicked());


        editMenu.setOnClickListener(v -> {
            setClickable(clicked);
            setVisibility(!clicked);
            Intent intent = new Intent(getApplicationContext(), AddDishesActivity.class);
            startActivity(intent);



        });

        editRestaurant.setOnClickListener(v -> {
                    setClickable(clicked);
                    RestaurantFragment fragment = new RestaurantFragment();
                    ((RestaurantFragment) fragment).Clickable();
                    setVisibility(!clicked);



                }

        );
    }


    private void editButtonClicked() {

        setVisibility(clicked);
        setClickable(clicked);
        setAnimation(clicked);
    }

    private void setVisibility(boolean clicked) {

        if (clicked) {
            editMenu.setVisibility(View.VISIBLE);
            labelEditMenu.setVisibility(View.VISIBLE);
            editRestaurant.setVisibility(View.VISIBLE);
            labelEditRestaurant.setVisibility(View.VISIBLE);
        } else {
            editMenu.setVisibility(View.INVISIBLE);
            labelEditMenu.setVisibility(View.INVISIBLE);
            editRestaurant.setVisibility(View.INVISIBLE);
            labelEditRestaurant.setVisibility(View.INVISIBLE);


        }

    }

    private void setClickable(boolean clicked) {

        editRestaurant.setEnabled(clicked);
        editMenu.setEnabled(clicked);

        this.clicked = !clicked;

    }


    private void setAnimation(boolean clicked) {
        if (clicked) {
            editMenu.startAnimation(fromBottom);
            labelEditMenu.startAnimation(fromBottom);
            editRestaurant.startAnimation(fromBottom);
            labelEditRestaurant.startAnimation(fromBottom);
            edit.startAnimation(rotateOpen);

        } else {
            editMenu.startAnimation(toBottom);
            labelEditMenu.startAnimation(toBottom);
            editRestaurant.startAnimation(toBottom);
            labelEditRestaurant.startAnimation(toBottom);
            edit.startAnimation(rotateClose);


        }
    }
}



