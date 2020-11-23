package comp231.s5g2.tindeappproject.fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
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

import static android.app.Activity.RESULT_OK;

public class MenuFragment extends Fragment {

    private List<Dish> dishList = new ArrayList<>();

    public Uri imguri;
    StorageReference storageRef;
    private StorageTask uploadTask;

    private EditText dishName, dishDescription;
    private ImageView dishImage;
    private RadioButton petSafe, vegan, vegetarian, nutsFree, halal;
    private Button createDish;

    private Owner owner = new Owner();
    private Restaurant restaurant = new Restaurant();
    private Dish dish = new Dish();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Restaurants");
    private StorageReference refDish;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            owner.setOwnerID("4");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        return view;
    }
}
/*

        petSafe = view.findViewById(R.id.PetSafe);
        vegan = view.findViewById(R.id.rbVegan);
        vegetarian = view.findViewById(R.id.rbVegetarian);
        nutsFree = view.findViewById(R.id.rbNutsFree);
        halal = view.findViewById(R.id.rbHalal);
        dishImage = view.findViewById(R.id.imageViewRestaurant);
        createDish = view.findViewById(R.id.createDishButton);

        storageRef = FirebaseStorage.getInstance().getReference().child("Restaurants")
                .child(owner.getOwnerID())
                .child("DishPictures");

        DatabaseReference restRef = myRef.child(owner.getOwnerID())
                .child("restaurant");

        dishImage.setOnClickListener(v -> fileChooser()); //getting the file from phone storage

        //owner.setRestaurant(restaurant);
        owner.setOwnerID("4");

        createDish.setOnClickListener(v ->{
                    if (imguri == null) {
                        Toast.makeText(getContext(),
                                "select an Image first", Toast.LENGTH_SHORT).show();
                    } else if (uploadTask != null && uploadTask.isInProgress()) {
                        Toast.makeText(getActivity(),
                                "Uploading, please, wait", Toast.LENGTH_SHORT).show();
                    } else {
                        dish.setDescription(dishDescription.getText().toString().trim());
                        dish.setName(dishName.getText().toString().trim());
                        dish.setHalal(halal.isChecked());
                        dish.setNutsFree(nutsFree.isChecked());
                        dish.setPetSafe(petSafe.isChecked());
                        dish.setVegan(vegan.isChecked());
                        dish.setVegetarian(vegetarian.isChecked());
                        Uploader(owner);

                    }
                }
                );


        restRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.e("Restaurant ref", "" + snapshot.getKey());
                    Owner ownerTemp = snapshot.getValue(Owner.class);
                    owner = ownerTemp;
                    restaurant = ownerTemp.getRestaurant();
                    dishList = restaurant.getDishes();

                    if (dishList != null) { //checking if re restaurant has dishes alredy
                        FeedingData(ownerTemp);
                        ImageViewloader(ownerTemp, view);
                        NotClickable();

                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }

    *//** ----------------------------------- **//*

        private String getExtension (Uri uri){
            ContentResolver cr = Objects.requireNonNull(getActivity()).getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
        }


    private void Uploader(Owner owner) {

        refDish = storageRef.child(dish.getDishID()+ getExtension(imguri));
        uploadTask =
                refDish.putFile(imguri)
                        .addOnSuccessListener(taskSnapshot -> {
                            myRef.child(owner.getOwnerID()).setValue(owner);

                            // Get a URL to the uploaded content
                            // Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                            Toast.makeText(getActivity(),
                                    "Dish Created Successfully", Toast.LENGTH_SHORT).show();
                            imguri = null;

                        });

    }

   *//* private void ImageViewloader(View view) {

        Log.e("token", "picpth");

        StorageReference strPicRef = FirebaseStorage.getInstance().getReference().child(owner.getRestaurant().getPictureToken());
        Log.e("Sucess", "" + strPicRef.toString());

        strPicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("Sucess", "Success");
                Glide.with(view.getContext())
                        .load(uri)
                        .into(dishImage);

            }
        });
*//*





    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
            dishImage.setImageURI(imguri);

        }
    }


    private void fileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    private void NotClickable() {
    }

    private void ImageViewloader(Owner ownerTemp, View view) {
    }

    private void FeedingData(Owner ownerTemp) {
    }

}*/