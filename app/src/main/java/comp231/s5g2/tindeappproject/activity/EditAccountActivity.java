package comp231.s5g2.tindeappproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import comp231.s5g2.tindeappproject.R;

public class EditAccountActivity extends AppCompatActivity {

    private EditText etName, etEmail, etMobile;
    private TextView tvUserName;
    private Button btnSave, btnCancel;

    //instantiate Firebase
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        //getting the intent data from ViewProfileActivity
        Intent data = getIntent();
        String editUserName = data.getStringExtra("mUserName");
        String editName = data.getStringExtra("mName");
        String editEmail = data.getStringExtra("mEmail");
        String editMobile = data.getStringExtra("mMobileTV");

        //initialize layout
        tvUserName = findViewById(R.id.tvUserName);
        etName = findViewById(R.id.etEditName);
        etEmail = findViewById(R.id.etEditEmail);
        etMobile = findViewById(R.id.etEditMobile);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        //initialize firebase
        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = firebaseAuth.getCurrentUser();

        //setting back the value
        tvUserName.setText(editUserName);
        etName.setText(editName);
        etEmail.setText(editEmail);
        etMobile.setText(editMobile);

        //button cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ViewProfileActivity.class));
            }
        });

        //saving edit
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strName = etName.getText().toString().trim();
                String strEmail = etEmail.getText().toString().trim();
                String strMobile = etMobile.getText().toString().trim();

                user.updateEmail(strEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                        //Mapping the users
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("email",strEmail);
                        edited.put("name",strName);
                        edited.put("userPhoneNumber",strMobile);
                        reference.updateChildren(edited);

                        Toast.makeText(EditAccountActivity.this,"Email is updated!", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditAccountActivity.this, "Email already exist!", Toast.LENGTH_LONG).show();
                    }
                });



            }
        });

    }
}