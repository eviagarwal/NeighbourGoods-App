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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {

    private ImageButton mSelectImage;
    private static final int GALLERY_REQUEST= 1;
    private EditText mPostTitle;
    private EditText mPostDesc;
    private Button mSubmitBtn;
    private Uri mImageUri= null;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private DatabaseReference mElectronic;
    private DatabaseReference mStudyMaterials;
    private DatabaseReference mOthers;
    private RadioGroup mRadioGroup;
    private RadioButton electronics, studyMaterials, others;
    private int a;
    private EditText mCost;
    private EditText mContactNo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mStorage= FirebaseStorage.getInstance().getReference();
        mPostTitle =(EditText) findViewById(R.id.titleField);
        mPostDesc=(EditText) findViewById(R.id.descField);
        mSubmitBtn =(Button) findViewById(R.id.submitBtn);
        mCost= (EditText) findViewById(R.id.cost);
        mContactNo= (EditText) findViewById(R.id.phone_no);
        mProgress= new ProgressDialog(this);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        mElectronic= mDatabase.child("Electronics");
        mStudyMaterials= mDatabase.child("Study Materials");
        mOthers= mDatabase.child("Others");
        mRadioGroup= (RadioGroup) findViewById(R.id.category);
        electronics= (RadioButton) findViewById(R.id.radio1);
        studyMaterials=(RadioButton)findViewById(R.id.radio2);
        others= (RadioButton)findViewById(R.id.radio3);

        mSelectImage=(ImageButton) findViewById(R.id.imageSelect);
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent= new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting(a);
            }
        });



        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.radio1) {
                    a=1;

                } else if(checkedId == R.id.radio2) {
                    a=2;

                } else {
                    a=3;

                }
            }

        });


    }

    private void startPosting(final int category) {
        mProgress.setMessage("Posting ad..");

        final String title_val= mPostTitle.getText().toString().trim();
        final String desc_val = mPostDesc.getText().toString().trim();
        final String pdct_cost= mCost.getText().toString().trim();
        final String phone_no=mContactNo.getText().toString().trim();
        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val) && mImageUri!= null){
            mProgress.show();

            StorageReference filePath= mStorage.child("Blog_Images").child(mImageUri.getLastPathSegment());
            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  Uri downloadUrl= taskSnapshot.getDownloadUrl();
                    if(category==1){
                        DatabaseReference newPost = mElectronic.push();
                        newPost.child("title").setValue(title_val);
                        newPost.child("desc").setValue(desc_val);
                        newPost.child("image").setValue(downloadUrl.toString());
                        newPost.child("price").setValue(pdct_cost);
                        newPost.child("contact number").setValue(phone_no);
                    }
                    else if(category==2){
                        DatabaseReference newPost = mStudyMaterials.push();
                        newPost.child("title").setValue(title_val);
                        newPost.child("desc").setValue(desc_val);
                        newPost.child("image").setValue(downloadUrl.toString());
                        newPost.child("price").setValue(pdct_cost);
                        newPost.child("contact number").setValue(phone_no);
                    }
                    else if(category==3){
                        DatabaseReference newPost = mOthers.push();
                        newPost.child("title").setValue(title_val);
                        newPost.child("desc").setValue(desc_val);
                        newPost.child("image").setValue(downloadUrl.toString());
                        newPost.child("price").setValue(pdct_cost);
                        newPost.child("contact number").setValue(phone_no);
                    }


                    mProgress.dismiss();

                    startActivity(new Intent(PostActivity.this, MainActivity.class));
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== GALLERY_REQUEST && resultCode==RESULT_OK){
             mImageUri= data.getData();
            mSelectImage.setImageURI(mImageUri);
        }
    }
}
