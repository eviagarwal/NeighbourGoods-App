package com.example.android.simpleblog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SetupActivity extends AppCompatActivity {

    private ImageButton mSetupImageBtn;
    private EditText mNameField;
    private Button mSubmitBtn;
    private static final int GALLERY_REQUEST=1;
    private Uri mImageUri;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;
    private StorageReference mStorageImages;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        mDatabaseUsers= FirebaseDatabase.getInstance().getReference().child("Users");
         mAuth = FirebaseAuth.getInstance();
        mStorageImages= FirebaseStorage.getInstance().getReference().child("Profile_images");
        mProgress = new ProgressDialog(this);
        mSetupImageBtn= (ImageButton) findViewById(R.id.setupImagebtn);
        mNameField= (EditText) findViewById(R.id.setupNameField);
        mSubmitBtn= (Button) findViewById(R.id.setupSubmitBtn);
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSetupAccount();
            }
        });
        mSetupImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent= new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);

            }
        });

    }

    private void startSetupAccount() {
       final String name = mNameField.getText().toString().trim();
        final String user_id= mAuth.getCurrentUser().getUid();
        if(!TextUtils.isEmpty(name) && mImageUri != null){
            mProgress.setMessage("Finishing Setup");
            mProgress.show();

            StorageReference filePath= mStorageImages.child(mImageUri.getLastPathSegment());
            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl= taskSnapshot.getDownloadUrl();

                    mDatabaseUsers.child(user_id).child("name").setValue(name);
                    mDatabaseUsers.child(user_id).child("image").setValue(downloadUrl);
                    mProgress.dismiss();
                    Intent mainIntent= new Intent(SetupActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainIntent);
                }
            });
        }
    }

   /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== GALLERY_REQUEST && resultCode== RESULT_OK){

            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
                mSetupImageBtn.setImageURI(mImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }*/
}
