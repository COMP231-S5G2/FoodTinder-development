package comp231.s5g2.tindeappproject.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import comp231.s5g2.tindeappproject.R;
import comp231.s5g2.tindeappproject.activity.CreateRestaurantActivity;
import comp231.s5g2.tindeappproject.models.Restaurant;

import static android.app.Activity.RESULT_OK;
import static android.view.KeyEvent.*;

public class RestaurantFragment extends Fragment {


    Button uploadButton;
    public Uri imguri;
    ImageView restaurantImg;
    StorageReference mStorageRef;
    private StorageTask uploadTask;
    private EditText mSearchText;
    private GoogleMap mMap;
    EditText editTextRestName, editTextPhoneNumber, editTextWebsite;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Restaurants");

    Restaurant restaurant = new Restaurant();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resturant, container, false);

        editTextRestName = (EditText) view.findViewById(R.id.editTextRestaurantName);
        editTextPhoneNumber = (EditText) view.findViewById(R.id.editTextPhone);
        editTextWebsite = (EditText) view.findViewById(R.id.editTextTextWebsite);

        //Widgets - Place Search autocomplete
        mSearchText = (EditText) view.findViewById(R.id.input_search);
        mSearchText.setFocusable(false);
        mSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(getActivity().getApplicationContext());
                //Start activity
                startActivityForResult(intent, 2);

            }
        });


        //using API key
        Places.initialize(getContext(), "AIzaSyDf41P6aELMfuikTAUvnW0k4Lii7hOIr6o");

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
                restaurant.setRestaurantAddress(mSearchText.toString().trim());
                restaurant.setRestaurantPhone(editTextPhoneNumber.getText().toString().trim());
                Uploader();

            }
        });


        return view;
    }


    private String getExtension(Uri uri) {
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Uploader() {


        StorageReference ref = mStorageRef.child(restaurant.getRestaurantPhone()).child(System.currentTimeMillis() + "." + getExtension(imguri));

        uploadTask =
                ref.putFile(imguri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                               /* restaurant.setPictureToken(ref.getPath());
                                Log.e("MSG",""+ref.getPath());*/

                                /** Needed to implement to get the picture from the Storage into the restaurant**/

                                myRef.child(restaurant.getRestaurantPhone()).setValue(restaurant);

                                // Get a URL to the uploaded content
                                // Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                                Toast.makeText(getActivity(),
                                        "Restaurant Created Successfully", Toast.LENGTH_SHORT).show();
                                imguri = null;

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
        if (requestCode ==2 && resultCode == RESULT_OK) {

            Place place = Autocomplete.getPlaceFromIntent(data);
            Log.d("ADDRESS", " :" + place.getAddress());

            mSearchText.setText(place.getAddress());
            Log.d("ADDRESS", " Locality Name:" + place.getName());
            editTextRestName.setText(place.getName());
            Log.d("ADDRESS", " Lat & Long:" + place.getLatLng());
          //editTextPhoneNumber.setText( place.getPhoneNumber().toString() );
           // editTextWebsite.setText(place.getBusinessStatus().toString());*/
        }
        else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Log.d("asdasd", " BAh"+status.getStatusMessage());
            Toast.makeText(getActivity().getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }

    }


}



