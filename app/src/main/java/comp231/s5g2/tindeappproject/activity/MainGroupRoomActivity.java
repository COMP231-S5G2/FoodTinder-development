package comp231.s5g2.tindeappproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.models.GroupRoom;

public class MainGroupRoomActivity extends AppCompatActivity {
    private EditText roomName, roomCode, roomLocation;
    private Button btnCreateGrp;
    private DatabaseReference groupDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_group_room);

        //initialize layout content
        roomName = findViewById(R.id.etRoomName);
        roomCode = findViewById(R.id.etRoomCode);
        roomLocation = findViewById(R.id.etLocation);

        btnCreateGrp = findViewById(R.id.btnCreateGrp);
        btnCreateGrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                //startActivity(new Intent(MainGroupRoomActivity.this, GroupRoomInfoActivity.class));
            }
        });

        groupDbRef = FirebaseDatabase.getInstance().getReference().child("GroupRoom");

    }

    private void insertData() {

        String admin = roomName.getText().toString();
        String code = roomCode.getText().toString();
        String loc = roomLocation.getText().toString();

        GroupRoom groupRoom = new GroupRoom(admin, loc, code);
        groupDbRef.push().child("GroupRoom").setValue(groupRoom);
    }


}