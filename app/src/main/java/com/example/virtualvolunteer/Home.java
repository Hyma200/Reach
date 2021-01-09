package com.example.virtualvolunteer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Home extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private static final String TAG = "Database";
    private ImageView postCreateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        generatePosts();

        postCreateBtn = (ImageView) findViewById(R.id.create_post_button);

        postCreateBtn.setOnClickListener(v -> {
            openPostCreate();
        });

    }

    public void openPostCreate() {
        Intent intent = new Intent(this, PostCreate.class);
        startActivity(intent);
    }

    public void generatePosts() {  //temporary generation for testing
        ArrayList<Post> posts = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            Post post = (Post) snapshot.getValue(Post.class);
                            posts.add(post);
                        }
                        RecyclerView rView = findViewById(R.id.rView);
                        rView.setLayoutManager(new LinearLayoutManager(Home.this));
                        rView.setAdapter(new PostAdapter(posts, Home.this));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

}
