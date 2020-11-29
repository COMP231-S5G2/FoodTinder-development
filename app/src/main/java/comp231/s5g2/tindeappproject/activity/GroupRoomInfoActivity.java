package comp231.s5g2.tindeappproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.models.GroupRoom;


public class GroupRoomInfoActivity extends AppCompatActivity {

    private TextView roomCode, roomAdmin, location;
    private Button btnStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_room_info);

        roomCode = findViewById(R.id.tvRoomCode);
        roomAdmin = findViewById(R.id.tvRoomAdminUser);
        location = findViewById(R.id.tvLocation);

        btnStart = findViewById(R.id.btnStartFoodMatch);



    }


}