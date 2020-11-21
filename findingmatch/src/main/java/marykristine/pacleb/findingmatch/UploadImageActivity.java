package marykristine.pacleb.findingmatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class UploadImageActivity extends AppCompatActivity {
    //declare layout controls
    private EditText etFoodName, etRestauLocation;
   private CheckBox cboPeanut, cboSeafood, cboDairy, cboMeat, cboGluten;
    private Button btnAddImg;
    private ImageView foodImageView;

    //constants
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;

    private  String [] cameraPermissions;
    private  String [] storagePermissions;

    private Uri imageUri;

    private String inputFoodName, inputRestauLoc, inputPeanut, inputSeafood, inputDairy, inputMeat, inputGluten, timeStamp;
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);

        init();

        cameraPermissions = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //initialize db
        dbHelper = new SQLiteHelper(this);

        //button to choose image from gallery or camera
        foodImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePickDialog();
            }
        });

        //button to save image
        btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

    }

    //initialized layout objects
    private void init(){
        etFoodName =findViewById(R.id.et_FoodName);
        etRestauLocation =findViewById(R.id.et_RestaurantLocation);

        cboPeanut =findViewById(R.id.cbo_Peanut);
        cboSeafood =findViewById(R.id.cbo_Seafood);
        cboDairy =findViewById(R.id.cbo_Dairy);
        cboMeat =findViewById(R.id.cbo_Meat);
        cboGluten =findViewById(R.id.cbo_Gluten);

        foodImageView =findViewById(R.id.iv_FoodImage);

        btnAddImg =findViewById(R.id.btn_AddImg);

    }


    private void imagePickDialog() {
        String[] options = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select for image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    //open camera and check camera permission
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }else
                    {
                        pickFromCamera();
                    }
                }
                else if (i == 1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }else {
                        pickFromStorage();
                    }
                }
            }
        });
        builder.create().show();
    }

    //get input from layout to db
    private void getData() {

        //get input texts from EditText
        inputFoodName = ""+etFoodName.getText().toString().trim();
        inputRestauLoc = ""+etRestauLocation.getText().toString().trim();

        //get selected text from Checkbox
        inputPeanut = ""+cboPeanut.getText().toString().trim();
        inputSeafood = ""+cboSeafood.getText().toString().trim();
        inputDairy = ""+cboDairy.getText().toString().trim();
        inputMeat = ""+cboMeat.getText().toString().trim();
        inputGluten = ""+cboGluten.getText().toString().trim();

        //get timestamp
        timeStamp = "" +System.currentTimeMillis();

        //store to long variable id
        long id = dbHelper.insertInfo(inputFoodName, inputRestauLoc, ""+imageUri,
                inputPeanut, inputSeafood, inputDairy, inputMeat, inputGluten,
                timeStamp, timeStamp);

        Toast.makeText(this,"Record added to id: " + id, Toast.LENGTH_LONG).show();

    }

    //method get image from the gallery
    private void pickFromStorage() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    //method take image from camera
    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    //method to check storage permission
    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    //method request to storage permission
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);

    }

    //method for camera permission
    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    //method to request camera permission
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0)
                {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && storageAccepted){
                        pickFromCamera();
                    } else {
                        Toast.makeText(this, "Camera permission required!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_REQUEST_CODE:
                if(grantResults.length > 0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (storageAccepted){
                        pickFromStorage();
                    }
                    else {
                        Toast.makeText(this, "Storage permission required!", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    //cropping the image
    //please see build.gradle(Module in findingmatch)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE){
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);

            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if(resultCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    imageUri =resultUri;
                    foodImageView.setImageURI(resultUri);
                }
                else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
                {
                    Exception error = result.getError();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}