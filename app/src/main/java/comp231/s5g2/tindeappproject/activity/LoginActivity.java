package comp231.s5g2.tindeappproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import comp231.s5g2.tindeappproject.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email, password;
    private Button logIn;
    private TextView signUp, guestLogIn;

    String inputEmail, inputPassword;

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize
        email = findViewById(R.id.etEmailAddress);
        password = findViewById(R.id.etPassword);
        logIn = findViewById(R.id.btnLogIn);
        signUp = findViewById(R.id.tvSignUp);
        guestLogIn = findViewById(R.id.tvGuestLogIn);

        //initialize Firebase
        mFirebaseAuth = FirebaseAuth.getInstance();

        logIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
        guestLogIn.setOnClickListener(this);

    }//end of onCreate

    //onClick for buttons
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogIn:
                userLogIn();
                break;

            case R.id.tvSignUp:
                startActivity(new Intent(getApplicationContext(),CreateUserActivity.class));
                break;

            case R.id.tvGuestLogIn:
                startActivity(new Intent(getApplicationContext(),FindingMatchActivity.class));
                break;

        }

    }

    //method for user login
    private void userLogIn() {
        inputEmail = email.getText().toString().trim();
        inputPassword = password.getText().toString().trim();

        if(inputEmail.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()){
            email.setError("Email is invalid");
            email.requestFocus();
            return;
        }

        if(inputPassword.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }

        if(password.length() < 6 ){
            password.setError("Minimum password is 6 characters");
            password.requestFocus();
            return;
        }

        mFirebaseAuth.signInWithEmailAndPassword(inputEmail, inputPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //redirect to user profile
                            startActivity(new Intent(LoginActivity.this, FindingMatchActivity.class));

                        }else{
                            Toast.makeText(LoginActivity.this, "Failed to sign in. " +
                                    "Please check your credentials", Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }//end userLogIn
}