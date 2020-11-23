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
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.activity.CreateRestaurantActivity;
import comp231.s5g2.tindeappproject.models.Owner;
import comp231.s5g2.tindeappproject.models.Restaurant;
import static android.app.Activity.RESULT_OK;

public class RestaurantFragment extends Fragment {


    Button uploadButton;
    public Uri imguri;
    ImageView restaurantImg;
    StorageReference mStorageRef ;
    private StorageTask uploadTask;
    private EditText mSearchText;
    private GoogleMap mMap;
    EditText editTextRestName, editTextPhoneNumber, editTextWebsite;
    Owner owner = new Owner();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Restaurants");

    StorageReference refProfilePic;

    Restaurant restaurant = new Restaurant();
    private String readableAddress;

   // boolean userAuthorized = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resturant, container, false);




        owner.setRestaurant(restaurant);
        owner.setOwnerID("4");

        DatabaseReference ownerRef = myRef.child(owner.getOwnerID());
        ownerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.e("owner ref", "" + snapshot.getKey());
                    Owner ownerTemp = snapshot.getValue(Owner.class);
                    assert ownerTemp != null;
                    if (ownerTemp.getRestaurant() != null) {
                        FeedingData(ownerTemp);
                        ImageViewloader(ownerTemp, view);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        editTextRestName = view.findViewById(R.id.editTextRestaurantName);
        editTextPhoneNumber = view.findViewById(R.id.editTextPhone);
        editTextWebsite = view.findViewById(R.id.editTextTextWebsite);

        //Widgets - Place Search autocomplete
        mSearchText = view.findViewById(R.id.input_search);
        mSearchText.setFocusable(false);
        mSearchText.setOnClickListener(v -> {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                    Place.Field.LAT_LNG, Place.Field.NAME);

            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                    fieldList).build(view.getContext());
            //Start activity
            startActivityForResult(intent, 2);

        });


        //using API key
        Places.initialize(view.getContext(), "AIzaSyB5DgFnl3KJU2xBGoVSo_7SisLobRtmelE");

        mStorageRef = FirebaseStorage.getInstance().getReference().child("Restaurants");
        uploadButton = view.findViewById(R.id.buttonUpload);
        restaurantImg = view.findViewById(R.id.imageViewRestaurant);


        restaurantImg.setOnClickListener(v -> fileChooser());

        uploadButton.setOnClickListener(v -> {

            if (imguri == null) {
                Toast.makeText(getContext(),
                        "select an Image first", Toast.LENGTH_SHORT).show();
            } else if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getActivity(),
                        "Uploading, please, wait", Toast.LENGTH_SHORT).show();
            } else {

                restaurant.setRestaurantName(editTextRestName.getText().toString().trim());
                restaurant.setRestaurantAddress(readableAddress);
                restaurant.setRestaurantPhone(editTextPhoneNumber.getText().toString().trim());
                restaurant.setWebSite(editTextWebsite.getText().toString().trim().toLowerCase());
                Uploader();
            }
        });

        return view;
    }


    private void FeedingData(Owner ownerTemp) {


        Restaurant restaurantView = ownerTemp.getRestaurant();
        editTextRestName.setText(restaurantView.getRestaurantName());
        mSearchText.setText(restaurantView.getRestaurantAddress());
        editTextPhoneNumber.setText(restaurantView.getRestaurantPhone());
        editTextWebsite.setText(restaurantView.getWebSite());

    }


    private String getExtension(Uri uri) {
        ContentResolver cr = Objects.requireNonNull(getActivity()).getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Uploader() {

        refProfilePic = mStorageRef.child(owner.getOwnerID()).child("Profile." +getExtension(imguri));

        restaurant.setPictureToken(refProfilePic.getPath());

        uploadTask =
                refProfilePic.putFile(imguri)
                        .addOnSuccessListener(taskSnapshot -> {
                            myRef.child(owner.getOwnerID()).setValue(owner);

                            // Get a URL to the uploaded content
                            // Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                            Toast.makeText(getActivity(),
                                    "Restaurant Created Successfully", Toast.LENGTH_SHORT).show();
                            imguri = null;

                        });

    }

    private void ImageViewloader(Owner owner, View view){

        Log.e("tokem", "picpth");

     StorageReference strPicRef =  FirebaseStorage.getInstance().getReference().child(owner.getRestaurant().getPictureToken());
                  Log.e("Sucess",""+strPicRef.toString());

            strPicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("Sucess","Success");
                Glide.with(view.getContext())
                        .load(uri)
                        .into(restaurantImg);

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
            imguri = data.getData();
            restaurantImg.setImageURI(imguri);

        }
        if (requestCode == 2 && resultCode == RESULT_OK) {

            assert data != null;
            Place place = Autocomplete.getPlaceFromIntent(data);
            Log.d("ADDRESS", " :" + place.getAddress());

            mSearchText.setText(place.getAddress());
            readableAddress = mSearchText.getText().toString();
            editTextRestName.setText(place.getName());
            Log.d("ADDRESS", " Lat & Long:" + place.getLatLng());
            //String foundPhoneNumber = place.getPhoneNumber();
            //editTextPhoneNumber.setText(foundPhoneNumber);
            //editTextWebsite.setText(place.getWebsiteUri().toString());
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            assert data != null;
            Status status = Autocomplete.getStatusFromIntent(data);
            Log.d("asdasd", " BAh" + status.getStatusMessage());
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }

    }


}



