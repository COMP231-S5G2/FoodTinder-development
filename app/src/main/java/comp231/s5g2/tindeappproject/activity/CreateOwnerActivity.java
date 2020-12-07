package comp231.s5g2.tindeappproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.models.Owner;
import comp231.s5g2.tindeappproject.models.User;

public class CreateOwnerActivity extends AppCompatActivity {
    private EditText editOwnerName, editPassword, editPhoneNum, editGmail;
    private Button btnRegister;

    private FirebaseAuth mAuth;
    private User ownerCreation;
    private FirebaseAuth.AuthStateListener fireBaseAuthStateListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_owner);

        mAuth = FirebaseAuth.getInstance();
        fireBaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser restaurantOwner = FirebaseAuth.getInstance().getCurrentUser();
                if(restaurantOwner != null){
                    startActivity(new Intent(CreateOwnerActivity.this, CreateRestaurantActivity.class));
                    finish();
                    return;
                }
            }
        };


        editOwnerName = findViewById(R.id.editTxtOwnerName);
        editPassword = findViewById(R.id.editTxtOwnerPassword);
        editPhoneNum = findViewById(R.id.editTxtOwnerPhone);
        editGmail = findViewById(R.id.editTxtOwnerGmail);
        btnRegister = findViewById(R.id.btnOwnerRegister);

        ownerCreation = new User();

        btnRegister.setOnClickListener(v -> {
            final String ownerName = editOwnerName.getText().toString();
            final String password = editPassword.getText().toString();
            final String gmail = editGmail.getText().toString();
            final String phoneNum = editPhoneNum.getText().toString();
            if (!(gmail.isEmpty() || password.isEmpty())) {
                if(isValidEmail(gmail)) {
                    mAuth.createUserWithEmailAndPassword(gmail, password)
                            .addOnCompleteListener(CreateOwnerActivity.this, task -> {

                                if (task.isSuccessful()) {
                                    Log.i("user", "User Created Successfully");
                                    ownerCreation.setEmail(gmail);
                                    ownerCreation.setName(ownerName);
                                    ownerCreation.setUserId("2");
                                    ownerCreation.setUserPhoneNumber(phoneNum);
                                    myRef.child("Owner").child(ownerCreation.getUserId()).setValue(ownerName).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            startActivity(new Intent(CreateOwnerActivity.this, CreateRestaurantActivity.class));
                                        }
                                    });

                                } else {
                                    Log.i("Owner", "Error, owner not created");
                                }

                            });
                } else
                 {
                     Toast.makeText(CreateOwnerActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                 }
            } else {
                Toast.makeText(CreateOwnerActivity.this, "Please enter your gmail and password", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(fireBaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(fireBaseAuthStateListener);
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}