package marykristine.pacleb.findingmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class FindingMatchActivity extends AppCompatActivity {
    Activity activity;
    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finding_match_activity);

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
                if (x1 < x2) {
                    Intent myIntent = new Intent(FindingMatchActivity.this, FindingMatchHomeActivity.class);
                    startActivity(myIntent);
                }
                break;
        }
        return false;
    }
}