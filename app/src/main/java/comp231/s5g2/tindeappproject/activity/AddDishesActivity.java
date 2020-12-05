package comp231.s5g2.tindeappproject.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.models.Dish;
import comp231.s5g2.tindeappproject.models.Owner;
import comp231.s5g2.tindeappproject.models.Restaurant;

public class AddDishesActivity extends AppCompatActivity {


    private final List<Dish> dishList = new ArrayList<>();


    public Uri imgUri;
    StorageReference storageRef;
    private StorageTask uploadTask;

    private EditText dishName, dishDescription, dishPrice, dishRestriction;
    private ImageView dishImage;

    private Owner owner = new Owner();
    private Restaurant restaurant = new Restaurant();
    private final Dish dish = new Dish();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Restaurants");
    Animation shake;

    private HashMap<String, String> values = new HashMap<String, String>();

    int listSize = 0;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //adding dish before exit
        values.put("dishName","");
        values.put("dishPrice","");
        values.put("dishDescription","");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dishes);
        Log.e("Dishes", "Loading dishes Activity");

        owner.setOwnerID("2");

        dishImage = findViewById(R.id.imageViewDish);
        Button createDish = findViewById(R.id.createDishButton);
        dishPrice = findViewById(R.id.editTextPrice);
        dishRestriction = findViewById(R.id.editRectriction);
        dishDescription = findViewById(R.id.editTextDishDescription);
        dishName = findViewById(R.id.editTextDishName);
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);


        DatabaseReference ownerRef = myRef.child(owner.getOwnerID());

        dishImage.setOnClickListener(v -> fileChooser()); //getting the file from phone

        ownerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                        Owner ownerDB = snapshot.getValue(Owner.class);
                        if (ownerDB != null) {
                            if (ownerDB.getRestaurant() != null) {
                                restaurant = ownerDB.getRestaurant();
                                if (ownerDB.getOwnerID().equals(owner.getOwnerID())){
                                    owner = ownerDB;
                                }
                                if (restaurant.getDishes().size() > 0) {
                                    dishList.addAll(restaurant.getDishes());
                                    listSize = dishList.size();
                                    dish.setDishID(listSize);
                                }
                            }
                        }
                    }
                    Log.e("Snp", "Size :" + listSize);
                    }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        createDish.setOnClickListener(v -> {
            if (imgUri == null) {
                Toast.makeText(this,
                        "select an Image first", Toast.LENGTH_SHORT).show();
                dishImage.setAnimation(shake);
            }else {

                dish.setDescription(dishDescription.getText().toString().trim());
                dish.setName(dishName.getText().toString().trim());
                dish.setPrice(Double.parseDouble(dishPrice.getText().toString().trim()));
                dish.setRestriction(dishRestriction.getText().toString().trim());
                dish.setOwnerID(owner.getOwnerID());

                Uploader(owner);

            }if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(this,
                        "Uploading, please, wait", Toast.LENGTH_SHORT).show();
            }
        });
    }//end of onCreate

    private String getExtension(Uri uri) {
        ContentResolver cr = Objects.requireNonNull(this.getContentResolver());
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }


    private void Uploader(Owner owner) {

        storageRef = FirebaseStorage.getInstance().getReference().child("Restaurants")
                .child(owner.getOwnerID()).child("Dish_" + dish.getDishID() + "." + getExtension(imgUri));
        dish.setImageAccessToken(storageRef.getPath());
        List<Dish> dishesToDB = new ArrayList<>();
        dishesToDB.add(dish);
        dishesToDB.addAll(owner.getRestaurant().getDishes());
        owner.getRestaurant().setDishes(dishesToDB);

        Log.e("Upload", "" + storageRef.toString());

        uploadTask =

                storageRef.putFile(imgUri)
                        .addOnSuccessListener(taskSnapshot -> {

                            Toast.makeText(this,
                                    "Dish Created Successfully", Toast.LENGTH_SHORT).show();
                            imgUri = null;
                            myRef.child(owner.getOwnerID()).setValue(owner);

                            Toast.makeText(this,
                                    "Dish Created Successfully", Toast.LENGTH_SHORT).show();
                            imgUri = null;

                        });

        //adding dish info
        values.put("dishName",dishName.getText().toString());
        values.put("dishPrice",dishPrice.getText().toString());
        values.put("dishDescription",dishDescription.getText().toString());
    }

/*    private void ImageViewloader(View view) {

        Log.e("token", "picpth");

        StorageReference strPicRef = FirebaseStorage.getInstance().getReference().child(owner.getRestaurant().getPictureToken());
        Log.e("Sucess", "" + strPicRef.toString());

        strPicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("Sucess", "Success");
                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(dishImage);

            }
        });
    }*/


    private void fileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            dishImage.setImageURI(imgUri);

        }
    }

    //softback key trigger alertdialog
    @Override
    public void onBackPressed() {
        //check if there is any input made
        if(!dishName.getText().toString().equals(values.get("dishName")) || !dishDescription.getText().toString().equals(values.get("dishDescription")) || !dishPrice.getText().toString().equals(values.get("dishPrice"))) {
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Information has not been saved. Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            AddDishesActivity.super.onBackPressed();
                        }
                    }).create().show();
        }
        else {
            AddDishesActivity.super.onBackPressed();
        }
    }


//
//    private void NotClickable() {
//    }
//
//    private void ImageViewloader(Owner ownerTemp, View view) {
//    }
//
//    private void FeedingData(Owner ownerTemp) {
//    }

}
