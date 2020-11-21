package comp231.s5g2.tindeappproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
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

public class CreateRestaurantActivity extends AppCompatActivity {

    Animation rotateOpen, toBottom, rotateClose, fromBottom;

    FloatingActionButton edit, editMenu, editRestaurant;

    TextView labelEditRestaurant, labelEditMenu;

    private boolean clicked = false;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);


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

            clicked = false;
            Intent intent = new Intent(getApplicationContext(), addDishesActivity.class);
            startActivity(intent);


        });

        editMenu.setOnClickListener(v -> clicked = false);
    }


    private void editButtonClicked() {

        setVisibility(clicked);
        setClickable(clicked);
        setAnimation(clicked);
        clicked = !clicked;

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
        if (clicked) {
            editRestaurant.setClickable(false);
            editMenu.setClickable(false);
        } else {
            editRestaurant.setClickable(true);
            editMenu.setClickable(true);

        }
    }

    private void setAnimation(boolean clicked) {
        if (!clicked) {
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



