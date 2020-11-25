package comp231.s5g2.tindeappproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import comp231.s5g2.tindeappproject.R;

public class MainGroupRoomActivity extends AppCompatActivity {
    private Button btnCreateGrp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //layout for Group Room
        setContentView(R.layout.main_group_room);

        btnCreateGrp = findViewById(R.id.btnCreateGrp);
        btnCreateGrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainGroupRoomActivity.this, GroupRoomInfoActivity.class));
            }
        });

    }


}