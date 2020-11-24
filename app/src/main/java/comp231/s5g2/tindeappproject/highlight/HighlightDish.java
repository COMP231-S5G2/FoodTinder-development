package comp231.s5g2.tindeappproject.highlight;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import comp231.s5g2.tindeappproject.R;
//This activity is only for testing the highlight code. Once it is implemented on swipe's code, it can be removed
public class HighlightDish extends AppCompatActivity {

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highlight_dish);
        image = findViewById(R.id.imageTest);
        highlight();
    }
    public void highlight(){
        Drawable highlight = getResources().getDrawable(R.drawable.highlight_dish); //This code will be added on swipe's activity
        image.setBackground(highlight);//his code will be added on swipe's activity
    }
}