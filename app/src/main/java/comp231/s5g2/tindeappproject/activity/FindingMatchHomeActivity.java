package comp231.s5g2.tindeappproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp231.s5g2.tindeappproject.R;

public class FindingMatchHomeActivity extends AppCompatActivity {
    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finding_match_home);

        Toast.makeText(this, "Swipe left to go back home.", Toast.LENGTH_SHORT).show();
    }

    //Method for swiping right to take user in next activity
    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                //swipe right
                if (x1 > x2) {
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));

                } else // swipe left to the main menu
                {
                    startActivity(new Intent(getApplicationContext(),FindingMatchActivity.class));
                }
                break;

        }
        return false;
    }
}