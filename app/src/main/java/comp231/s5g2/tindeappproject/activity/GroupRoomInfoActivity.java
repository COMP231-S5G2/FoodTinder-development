package comp231.s5g2.tindeappproject.activity;

import android.content.Context;
import android.content.SharedPreferences;
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

    GroupRoom groupRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_room_info);

        roomCode = findViewById(R.id.tvRoomCode);
        roomAdmin = findViewById(R.id.tvRoomAdminUser);
        location = findViewById(R.id.tvLocation);
        btnStart = findViewById(R.id.btnStartFoodMatch);

        SharedPreferences result = getSharedPreferences("SaveData", Context.MODE_PRIVATE);
        String edit1 = result.getString("Value1", roomAdmin.getText().toString());
        String edit2 = result.getString("Value2", roomCode.getText().toString());
        String edit3 = result.getString("Value3",location.getText().toString());

        roomCode.setText(edit2);
        roomAdmin.setText(edit1);
        location.setText(edit3);

    }


}