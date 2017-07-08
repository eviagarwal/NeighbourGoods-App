package com.example.android.simpleblog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

private RecyclerView mBlogList;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mElectronic;
    private DatabaseReference mStudyMaterials;
    private DatabaseReference mOthers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth= FirebaseAuth.getInstance();
        mAuthStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    Intent loginIntent= new Intent(MainActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }
        };
        mDatabaseUsers= FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseUsers.keepSynced(true);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout= (TabLayout)findViewById(R.id.tabs);
        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);
       /* mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mElectronic= mDatabase.child("Electronics");
        mStudyMaterials= mDatabase.child("Study Materials");
        mOthers= mDatabase.child("Others");
        mDatabaseUsers= FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseUsers.keepSynced(true);
        mBlogList = (RecyclerView) findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));*/
        checkUserExist();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthStateListener);

       /* FirebaseRecyclerAdapter<Blog, BlogViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                Blog.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                mOthers
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(), model.getImage());

            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
*/
    }

    private void checkUserExist() {

        if(mAuth.getCurrentUser()!= null) {

            final String user_id = mAuth.getCurrentUser().getUid();
            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(user_id)) {

                        Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);
                        setupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(setupIntent);

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

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
        public void setImage(Context ctx,String image){
            ImageView post_image= (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
       /* MenuItem item= menu.findItem(R.id.menuSearch);
        SearchView searchView= (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_add)
        {
            startActivity(new Intent(MainActivity.this, PostActivity.class));
        }
        if(item.getItemId()== R.id.action_logout)
        {
            logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOut() {

        mAuth.signOut();
    }
}
