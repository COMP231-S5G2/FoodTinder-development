package comp231.s5g2.tindeappproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comp231.s5g2.tindeappproject.R;

public class HomeActivity extends AppCompatActivity {

    private Button btnAddDishes, btnFindingMatch, btnGroupRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //layout controls
        init();

        //button methods
        btnAddDishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CreateRestaurantActivity.class));
            }
        });

        btnFindingMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),FindingMatchHomeActivity.class));
            }
        });

        btnGroupRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainGroupRoomActivity.class));
            }
        });

    }

    private void init() {
        btnAddDishes = findViewById(R.id.btnAddDishesActivity);
        btnFindingMatch = findViewById(R.id.btnFindingMatchActivity);
        btnGroupRoom = findViewById(R.id.btnCreateGroupRoom);
    }
}