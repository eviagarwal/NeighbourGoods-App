package com.example.android.simpleblog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AdDetailActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference mElectronic;
    private DatabaseReference mStudyMaterials;
    private DatabaseReference mOthers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_detail);
        Intent intent = getIntent();
        String title = intent.getStringExtra("Ad_Title");
        int category_num= intent.getIntExtra("Category",1);
        if(category_num==1) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog").child("Others");
            Query adDetail=mDatabase.orderByChild("title").equalTo(title);
        }
        else if(category_num==2)
        {

        }
        else
        {}
    }
}
