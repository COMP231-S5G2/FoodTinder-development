package com.hai.joininggrouproom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoiningGroupRoomActivity extends AppCompatActivity {
    private String text ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joining_group_room);
        Button joiningBtn = (Button) findViewById(R.id.button);
        joiningBtn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(JoiningGroupRoomActivity.this);
            final EditText txtBox = new EditText(this);
            //TODO Implement into db
            builder.setMessage("Please enter the 4-digit code")
                    .setView(txtBox)
                    .setPositiveButton("Confirm", (dialog, which) -> {
                        text = txtBox.getText().toString();
                        Toast.makeText(getApplicationContext(),"Move to the room", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel",null);
            AlertDialog alert = builder.create();
            alert.show();

        });
    }
}