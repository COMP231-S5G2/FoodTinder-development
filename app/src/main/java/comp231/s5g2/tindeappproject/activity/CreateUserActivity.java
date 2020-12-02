package comp231.s5g2.tindeappproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.models.User;


public class CreateUserActivity extends AppCompatActivity {

    private FirebaseAuth user = FirebaseAuth.getInstance();

    private EditText userName;
    private EditText userEmail;
    private EditText userPhone;
    private EditText userPassword;
    private Button save;
    String inputEmail, inputPassword;
    private User userCreation = new User();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile_edit);

        Intent intent = new Intent(getApplicationContext(), FindingMatchActivity.class);
        userEmail = (EditText)findViewById(R.id.editTextEmailUser);
        userName =(EditText) findViewById(R.id.editTextPersonName);
        userPhone = (EditText)findViewById(R.id.editTextNumber);
        userPassword = findViewById(R.id.editTextPasswordCreation);
        save = (Button)findViewById(R.id.saveUser);

        save.setOnClickListener(v -> {
            inputEmail = userEmail.getText().toString();
            inputPassword = userEmail.getText().toString();
            if (!(inputEmail.isEmpty() || inputPassword.isEmpty())) {

                user.createUserWithEmailAndPassword(inputEmail, inputPassword)
                        .addOnCompleteListener(CreateUserActivity.this, task -> {

                            if (task.isSuccessful()) {
                                Log.i("user", "User Created Successfully");
                                userCreation.setEmail(inputEmail);
                                userCreation.setName(userName.getText().toString().trim());
                                userCreation.setUserID("1");
                                userCreation.setUserPhoneNumber(Integer.parseInt(String.valueOf(userPhone)));
                                myRef.child("Users").child(userCreation.getUserID()).setValue(userCreation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(intent);
                                    }
                                });

                            } else {
                                Log.i("user", "Error, user not created");

                            }

                        });
            } else {
                Toast.makeText(CreateUserActivity.this, "Something is not right =/", Toast.LENGTH_SHORT).show();
            }
        });
    }
}