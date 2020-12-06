package comp231.s5g2.tindeappproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.models.User;


public class CreateUserActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText userName, name, email, mobile, password, confirmPassword;
    private Button createAccount;
    private TextView logIn;
    private ProgressDialog mProgressDialog;
    private Context mContext;
    private Activity mActivity;

    String inputUserName, inputName, inputEmail, inputPassword, inputMobile, inputConfirmPw;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile_edit);

        //initialize layout
        userName = findViewById(R.id.etUserName);
        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        mobile = findViewById(R.id.etMobile);
        password = findViewById(R.id.etPassword);
        confirmPassword = findViewById(R.id.etConfirmPassword);
        createAccount = findViewById(R.id.btnCreateAccount);
        logIn = findViewById(R.id.tvLogIn);

        //initialize Firebase
        mFirebaseAuth = FirebaseAuth.getInstance();

        // Get the application context
        mContext = getApplicationContext();
        mActivity = CreateUserActivity.this;

        // Initialize the progress dialog
        mProgressDialog = new ProgressDialog(mActivity);

        createAccount.setOnClickListener(this::onClick);
        logIn.setOnClickListener(this::onClick);

    }//end of onCreate

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnCreateAccount:
                createAccount();
                break;

            case R.id.tvLogIn:
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

    }//end onClick

    //method when user click Create account button
    private void createAccount() {

        inputUserName = userName.getText().toString().trim();
        inputName = name.getText().toString().trim();
        inputEmail = email.getText().toString().trim();
        inputPassword = password.getText().toString().trim();
        inputMobile = mobile.getText().toString().trim();
        inputConfirmPw = confirmPassword.getText().toString().trim();

        if(inputUserName.isEmpty()){
            userName.setError("username is required");
            userName.requestFocus();
            return;
        }

        if(inputName.isEmpty()){
            name.setError("Name is required");
            name.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()){
            email.setError("Please provide valid email");
            email.requestFocus();
            return;
        }

        if(inputMobile.isEmpty()){
            mobile.setError("Mobile is required");
            mobile.requestFocus();
            return;
        }

        if(inputPassword.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(password.length() < 6 ){
            password.setError("Minimum password is 6 characters");
            password.requestFocus();
            return;
        }

        if(inputConfirmPw.isEmpty()){
            mobile.setError("Password is required");
            mobile.requestFocus();
            return;
        }

        mFirebaseAuth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            User user = new User(inputUserName, inputName, inputEmail, inputEmail);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(CreateUserActivity.this, "Account created", Toast.LENGTH_LONG).show();

                                    }else {
                                        Toast.makeText(CreateUserActivity.this, "Failed to register", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(CreateUserActivity.this, "Failed to register", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }// end of createAccount

}