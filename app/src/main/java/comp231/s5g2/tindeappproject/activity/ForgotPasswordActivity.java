package comp231.s5g2.tindeappproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import comp231.s5g2.tindeappproject.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText inputEmail;
    private Button resetBtn;
    private LinearLayout layout;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Toolbar - id can be found at custom_appbar layout
        toolbar = findViewById(R.id.AppBar);
        setSupportActionBar(toolbar);

        //initialize layout
        inputEmail = findViewById(R.id.etEmail);
        resetBtn = findViewById(R.id.btnReset);
        layout = findViewById(R.id.forgotPasswordLayout);

        //initialize firebase
        auth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });


    }//end of onCreate

    private void resetPassword() {
        String email = inputEmail.getText().toString().trim();

        //validation
        if(email.isEmpty()){
            inputEmail.setError("Name is required");
            inputEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputEmail.setError("Please provide valid email");
            inputEmail.requestFocus();
            return;
        }

        //call firebase auth
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    //hide keyboard
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(layout.getWindowToken(), 0);

                    Toast.makeText(getApplicationContext(), "Check email to reset your password.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}