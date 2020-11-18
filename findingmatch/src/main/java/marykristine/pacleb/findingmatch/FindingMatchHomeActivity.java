package marykristine.pacleb.findingmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class FindingMatchHomeActivity extends AppCompatActivity {
    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finding_match_home);
        Toast.makeText(this, "Swipe right to find food match.", Toast.LENGTH_LONG).show();
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
                    Intent myIntent = new Intent(FindingMatchHomeActivity.this, FindingMatchActivity.class);
                    startActivity(myIntent);
                } else //swipe left
                {
                    Intent myIntent = new Intent(FindingMatchHomeActivity.this, FindingMatchHomeActivity.class);
                    startActivity(myIntent);
                }
                break;

        }
        return false;
    }
}