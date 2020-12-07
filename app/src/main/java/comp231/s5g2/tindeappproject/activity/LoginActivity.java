package comp231.s5g2.tindeappproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import comp231.s5g2.tindeappproject.R;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth user = FirebaseAuth.getInstance();

    private EditText userPassword, userEmail;
    private Button logIn, signUp, loginGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Singing up an user

        userEmail = findViewById(R.id.editTextEmail);
        userPassword = findViewById(R.id.editTextPassword);
        logIn = findViewById(R.id.buttonLogIn);
        signUp = findViewById(R.id.buttonSignUp);
        loginGuest = findViewById(R.id.buttonLoginAsGuest);

        Intent intent = new Intent(getApplicationContext(), FindingMatchActivity.class);

        if (user.getCurrentUser() != null) {
            Log.i("User", "User already logged in!");
            startActivity(intent);
        } else {

            String inputEmail = userEmail.getText().toString().trim();
            String inputPassword = userPassword.getText().toString().trim();

            logIn.setOnClickListener(v -> {

                if (inputEmail.isEmpty() || inputPassword.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Something is not right =/", Toast.LENGTH_SHORT).show();
                }else{
                    user.signInWithEmailAndPassword(inputEmail, inputPassword)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i("User", "User logged in!");
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Something is not right =/", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });


        }
        signUp.setOnClickListener(V -> {

            Intent intentCreate = new Intent(getApplicationContext(), CreateUserActivity.class);
            startActivity(intentCreate);
        });

        loginGuest.setOnClickListener(V -> {
            startActivity(intent);
        });

    }
}