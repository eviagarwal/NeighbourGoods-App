package com.example.android.simpleblog;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by DELL PC on 4/11/2017.
 */

public class ElectronicsFragment extends Fragment {
    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mElectronic;
    private DatabaseReference mStudyMaterials;
    private DatabaseReference mOthers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ad_list, container, false);



        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mElectronic= mDatabase.child("Electronics");

        mBlogList = (RecyclerView) rootView.findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerAdapter<Blog, ElectronicsFragment.BlogViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Blog, ElectronicsFragment.BlogViewHolder>(
                Blog.class,
                R.layout.blog_row,
                ElectronicsFragment.BlogViewHolder.class,
                mElectronic
        ) {
            @Override
            protected void populateViewHolder(ElectronicsFragment.BlogViewHolder viewHolder, Blog model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getContext(), model.getImage());
               viewHolder.setCost(model.getCost());
                viewHolder.setContact(model.getContact());

            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);



        return rootView;
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView= itemView;
        }

        public void setTitle(String title){
            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void setDesc(String desc){
            TextView post_desc= (TextView) mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }
        public void setImage(Context ctx, String image){
            ImageView post_image= (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }
       public void setCost(String cost){
            TextView pdct_cost= (TextView) mView.findViewById(R.id.pdct_cost);
            pdct_cost.setText("Rs. 2000" );
        }
       public void setContact(String contact){
            TextView phone_number= (TextView) mView.findViewById(R.id.phone_number);
            phone_number.setText("Contact No: 8989234563");
        }
    }

}
