package comp231.s5g2.tindeappproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import comp231.s5g2.tindeappproject.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText inputEmail;
    private Button resetBtn;

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
    }


}