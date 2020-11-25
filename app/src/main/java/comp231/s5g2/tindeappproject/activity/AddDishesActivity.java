package comp231.s5g2.tindeappproject.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.models.Dish;
import comp231.s5g2.tindeappproject.models.Owner;
import comp231.s5g2.tindeappproject.models.Restaurant;

public class AddDishesActivity extends AppCompatActivity {


    private List<Dish> dishList = new ArrayList<>();

    public Uri imgUri;
    StorageReference storageRef;
    private StorageTask uploadTask;

    private EditText dishName, dishDescription, dishPrice;
    private ImageView dishImage;
    private RadioButton petSafe, vegan, vegetarian, nutsFree, halal;
    private Button createDish;

    private Owner owner = new Owner();
    private Restaurant restaurant = new Restaurant();
    private Dish dish = new Dish();
    private List<Dish> listDish = new ArrayList();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Restaurants");

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dishes);
        Log.e("Dishes", "Loading dishes Activity");

        owner.setOwnerID("3");
        petSafe = findViewById(R.id.PetSafe);
        vegan = findViewById(R.id.rbVegan);
        vegetarian = findViewById(R.id.rbVegetarian);
        nutsFree = findViewById(R.id.rbNutsFree);
        halal = findViewById(R.id.rbHalal);
        dishImage = findViewById(R.id.imageViewDish);
        createDish = findViewById(R.id.createDishButton);
        dishPrice = findViewById(R.id.editTextPrice);

        dishDescription = findViewById(R.id.editTextDishDescription);
        dishName = findViewById(R.id.editTextDishName);


        DatabaseReference ownerRef = myRef.child(owner.getOwnerID());

        dishImage.setOnClickListener(v -> fileChooser()); //getting the file from phone

        ownerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.e("Restaurant ref", "" + snapshot.getKey());
                    owner = snapshot.getValue(Owner.class);
                    Restaurant restaurantDB = owner.getRestaurant();
                    if (!restaurantDB.getDishes().isEmpty()) {
                        dishList = restaurant.getDishes();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        createDish.setOnClickListener(v -> {
            if (imgUri == null) {
                Toast.makeText(this,
                        "select an Image first", Toast.LENGTH_SHORT).show();
                dishImage.animate().scaleX(0.25f);
                dishImage.animate().scaleY(0.25f);
                dishImage.animate().setDuration(400);

                dishImage.clearAnimation();
            } else if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(this,
                        "Uploading, please, wait", Toast.LENGTH_SHORT).show();
            } else {

                dish.setDescription(dishDescription.getText().toString().trim());
                dish.setName(dishName.getText().toString().trim());
                dish.setPrice(Double.parseDouble(dishPrice.getText().toString().trim()));

                dish.setHalal(halal.isChecked());
                dish.setNutsFree(nutsFree.isChecked());
                dish.setPetSafe(petSafe.isChecked());
                dish.setVegan(vegan.isChecked());
                dish.setVegetarian(vegetarian.isChecked());
                dish.setDishID(0);

                Uploader(owner);

            }
        });
    }


    /**
     * -----------------------------------
     **/

    private String getExtension(Uri uri) {
        ContentResolver cr = Objects.requireNonNull(this.getContentResolver());
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }


    private void Uploader(Owner owner) {

        storageRef = FirebaseStorage.getInstance().getReference().child("Restaurants")
                .child(owner.getOwnerID()).child("Dish_" + dish.getDishID() + "." + getExtension(imgUri));
        dish.setImageAccessToken(storageRef.getPath());
        dishList.add(dish);
        Log.e("Upload", "" + storageRef.toString());

        uploadTask =

                storageRef.putFile(imgUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            owner.getRestaurant().setDishes(dishList);
                            myRef.child(owner.getOwnerID()).setValue(owner);

                            // Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                            Toast.makeText(this,
                                    "Dish Created Successfully", Toast.LENGTH_SHORT).show();
                            imgUri = null;

                        });

    }

    private void ImageViewloader(View view) {

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
    }


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


    private void NotClickable() {
    }

    private void ImageViewloader(Owner ownerTemp, View view) {
    }

    private void FeedingData(Owner ownerTemp) {
    }

}
