package comp231.s5g2.tindeappproject.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import comp231.s5g2.tindeappproject.R;

public class CreateRestaurantActivity extends AppCompatActivity {

    Button uploadButton;
    public Uri imguri;
    ImageView restaurantImg;
    StorageReference mStorageRef;
    private StorageTask uploadTask;


    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);

        mStorageRef = FirebaseStorage.getInstance().getReference().child("Restaurants");
        uploadButton = findViewById(R.id.buttonUpload);
        restaurantImg = findViewById(R.id.imageViewRestaurant);


        restaurantImg.setOnClickListener(v -> fileChooser());

        uploadButton.setOnClickListener(v -> {

            if (imguri == null) {
                Toast.makeText(CreateRestaurantActivity.this,
                        "select an Image first", Toast.LENGTH_SHORT).show();
                }

             else if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(CreateRestaurantActivity.this,
                            "Uploading, please, wait", Toast.LENGTH_SHORT).show();
             } else{
                Uploader();
            }
        });

    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Uploader() {

        StorageReference ref = mStorageRef.child(System.currentTimeMillis() + "." + getExtension(imguri));

        uploadTask =
                ref.putFile(imguri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                // Get a URL to the uploaded content
                                // Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                                Toast.makeText(CreateRestaurantActivity.this,
                                        "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                imguri = null;
                                restaurantImg.setImageURI(imguri);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
            restaurantImg.setImageURI(imguri);

        }
    }
}



