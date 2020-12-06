package comp231.s5g2.tindeappproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.models.User;

public class ViewProfileActivity extends AppCompatActivity {

    private TextView userName, name, email, mobile;
    private Button edit, logout;

    //Firebase
    private FirebaseUser user;
    private DatabaseReference reference;

    //get userID from the firebase
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        edit = findViewById(R.id.btnEdit);
        logout = findViewById(R.id.btnLogOut);

        //user log out from the app
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        //initialize firebase db
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        //casting TextView to a class
        final TextView  userNameTextView = findViewById(R.id.tvUserName);
        final TextView  nameTextView = findViewById(R.id.tvName);
        final TextView  emailTextView = findViewById(R.id.tvEmail);
        final TextView  mobileTextView = findViewById(R.id.tvMobile);

        //get user from realtime db
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                //checking if user exist
                if(userProfile !=null){
                    String userName = userProfile.userName;
                    String name = userProfile.name;
                    String email = userProfile.email;
                    String mobile = userProfile.userPhoneNumber;

                    //adding strings to the layout
                    userNameTextView.setText(userName);
                    nameTextView.setText(name);
                    emailTextView.setText(email);
                    mobileTextView.setText(mobile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Something is wrong with this.", Toast.LENGTH_LONG).show();

            }
        });

    }
}