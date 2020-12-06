package comp231.s5g2.tindeappproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.models.User;

public class EditAccountActivity extends AppCompatActivity {

    private EditText etName, etEmail, etMobile;
    private TextView tvUserName;
    private Button btnSave, btnCancel;
    private LinearLayout layout;

    //instantiate Firebase
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;

    //get userID from the firebase
    String userID;

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
        layout = findViewById(R.id.editUserLayout);
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

                String srtUserName = tvUserName.getText().toString().trim();
                String strName = etName.getText().toString().trim();
                String strEmail = etEmail.getText().toString().trim();
                String strMobile = etMobile.getText().toString().trim();

                user = firebaseAuth.getCurrentUser();
                userID = user.getUid();

                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                        .setDisplayName(userID).build();

                user.updateProfile(userProfileChangeRequest)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Mapping the users
                            Map<String, Object> edited = new HashMap<>();
                            edited.put("userName",srtUserName);
                            edited.put("email",strEmail);
                            edited.put("name",strName);
                            edited.put("userPhoneNumber",strMobile);

                            //updating the fields in User table
                            reference.updateChildren(edited);

                            //hide keyboard
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(layout.getWindowToken(), 0);

                            Toast.makeText(EditAccountActivity.this,"Account is updated!", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditAccountActivity.this, "Email already exist!", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

    }
}