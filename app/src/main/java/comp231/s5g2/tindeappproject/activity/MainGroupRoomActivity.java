package comp231.s5g2.tindeappproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.models.GroupRoom;

public class MainGroupRoomActivity extends AppCompatActivity {
    private EditText roomName, roomCode, roomLocation;
    private Button btnCreateGrp, btnStartMatch;
    private DatabaseReference groupDbRef;
    SharedPreferences sharedPreferences;
    int groupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_group_room);

        //initialize layout content
        roomName = findViewById(R.id.etRoomName);
        roomCode = findViewById(R.id.etRoomCode);
        roomLocation = findViewById(R.id.etLocation);
        btnCreateGrp = findViewById(R.id.btnCreateGrp);
        btnStartMatch = findViewById(R.id.btnStartFoodMatch);

        String strRoomName = roomName.getText().toString();
        String strRoomCode = roomCode.getText().toString();
        String strRoomLocation = roomLocation.getText().toString();

        if(TextUtils.isEmpty(strRoomName) && (TextUtils.isEmpty(strRoomCode)) && (TextUtils.isEmpty(strRoomLocation))) {
            roomName.setError("Cannot be empty");
            roomCode.setError("Cannot be empty");
            roomLocation.setError("Cannot be empty");
        }


        //initialize DB table
        groupDbRef = FirebaseDatabase.getInstance().getReference().child("GroupRoom");


        btnCreateGrp.setOnClickListener(view -> {
            insertData();
            startActivity(new Intent(getApplicationContext(),GroupRoomInfoActivity.class));

            sharedPreferences = getSharedPreferences("SaveData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Value1",roomName.getText().toString());
            editor.putString("Value2",roomCode.getText().toString());
            editor.putString("Value3",roomLocation.getText().toString());
            editor.commit();
        });

        btnStartMatch.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), FindingMatchActivity.class));
        });

    }

    private void insertData() {

        String admin = roomName.getText().toString();
        String code = roomCode.getText().toString();
        String loc = roomLocation.getText().toString();

        groupID++;
        GroupRoom groupRoom = new GroupRoom(admin, loc, code);
        groupDbRef.child(String.valueOf(groupID)).setValue(groupRoom);

    }








}