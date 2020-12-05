package comp231.s5g2.tindeappproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.fragments.SaveDialogFragment;

public class ChangePreferenceActivity extends AppCompatActivity {
    SaveDialogFragment saveDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_preference);
        CheckBox chkBoxCheese = (CheckBox) findViewById(R.id.chkBoxCheese);
        CheckBox chkBoxPeanut = (CheckBox) findViewById(R.id.chkBoxPeanut);
        CheckBox chkBoxVegan = (CheckBox) findViewById(R.id.chkBoxVegan) ;
        CheckBox chkBoxVeget = (CheckBox) findViewById(R.id.chkBoxVegetarian);
        saveDialogFragment = new SaveDialogFragment();
        final Button btnSave = (Button) findViewById(R.id.buttonSave);
        Button btnBack = (Button) findViewById(R.id.buttonBack);

        chkBoxCheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                //TODO Implement into db
                if(checked)
                    Toast.makeText(getApplicationContext(), "Cheese allergic selected", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Cheese allergic unselected", Toast.LENGTH_LONG).show();

            }
        });
        chkBoxPeanut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                //TODO Implement into db
                if(checked)
                    Toast.makeText(getApplicationContext(),"Peanut allergic selected", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"Peanut allergic unselected", Toast.LENGTH_LONG).show();
            }
        });
        chkBoxVegan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                //TODO Implement into db
                if(checked)
                    Toast.makeText(getApplicationContext(), "Vegan dish restriction activate", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Vegan dish restriction deactivate", Toast.LENGTH_LONG).show();

            }
        });
        chkBoxVeget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                //TODO Implement into db
                if(checked)
                    Toast.makeText(getApplicationContext(),"Vegetarian dish restriction activate",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"Vegetarian dish restriction deactivate", Toast.LENGTH_LONG).show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            //TODO Implement into db
            public void onClick(View v) {
                boolean selected = ((Button) btnSave).isSelected();
                if (!selected)
                    saveDialogFragment.show(getSupportFragmentManager(),null);
                else
                    Toast.makeText(getApplicationContext(),"Return main activity", Toast.LENGTH_SHORT).show();

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            //TODO Implement into db
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}