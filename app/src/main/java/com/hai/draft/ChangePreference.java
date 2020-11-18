package com.hai.draft;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

public class ChangePreference extends AppCompatActivity {
     SaveDialogFragment saveDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_preference);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        CheckBox chkBoxCheese = (CheckBox) findViewById(R.id.checkBox);
        CheckBox chkBoxPeanut = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox chkBoxVegan = (CheckBox) findViewById(R.id.checkBox3) ;
        CheckBox chkBoxVeget = (CheckBox) findViewById(R.id.checkBox4);
        saveDialogFragment = new SaveDialogFragment();

        final Button btnSave = (Button) findViewById(R.id.button);
        Button btnBack = (Button) findViewById(R.id.button1);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.radius_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                //TODO Implement into db
                switch (position){
                    case 0:
                        Toast.makeText(getApplicationContext(), "You switched to 500m", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "You switched to 1km", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "You switched to 3km", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "You switched to 5km", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePreference.this);
        builder.setMessage("Would you like to save ?")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    //TODO Implement into db
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Saved", Toast.LENGTH_SHORT).show();
                        ChangePreference.super.onBackPressed();
                    }
                })
                .setNegativeButton("Cancel",null);
        AlertDialog alert = builder.create();
        alert.show();
    }

}