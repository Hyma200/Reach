package com.example.virtualvolunteer.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.virtualvolunteer.Navigation;
import com.example.virtualvolunteer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class Home extends AppCompatActivity {


    private Button opportunityTag;
    private Button virtualTag;
    private Button teachingTag;
    private Button environmentalTag;
    private Button recreationalTag;
    private Button distributionTag;
    private Button experienceTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        opportunityTag = findViewById(R.id.tag_opportunity);
        experienceTag = findViewById(R.id.tag_experience);
        virtualTag = findViewById(R.id.tag_virtual);
        teachingTag = findViewById(R.id.tag_teaching);
        environmentalTag = findViewById(R.id.tag_environmental);
        recreationalTag = findViewById(R.id.tag_recreational);
        distributionTag = findViewById(R.id.tag_distribution);

        opportunityTag.setOnClickListener(v -> {
            Toast.makeText(this, "opportunity tag", Toast.LENGTH_SHORT).show();
        });
        experienceTag.setOnClickListener(v -> {
            Toast.makeText(this, "experience tag", Toast.LENGTH_SHORT).show();
        });
        virtualTag.setOnClickListener(v -> {
            Toast.makeText(this, "virtual tag", Toast.LENGTH_SHORT).show();
        });
        teachingTag.setOnClickListener(v -> {
            Toast.makeText(this, "teaching tag", Toast.LENGTH_SHORT).show();
        });
        environmentalTag.setOnClickListener(v -> {
            Toast.makeText(this, "environmental tag", Toast.LENGTH_SHORT).show();
        });
        recreationalTag.setOnClickListener(v -> {
            Toast.makeText(this, "recreational tag", Toast.LENGTH_SHORT).show();
        });
        distributionTag.setOnClickListener(v -> {
            Toast.makeText(this, "distribution tag", Toast.LENGTH_SHORT).show();
        });

        generatePosts();
        ImageView postCreateBtn = findViewById(R.id.create_post_button);
        postCreateBtn.setOnClickListener(v -> openPostCreate());
    }

    public void openPostCreate() {
        Intent intent = new Intent(this, PostCreate.class);
        startActivity(intent);
    }

    public void generatePosts() {
        ArrayList<Post> posts = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Post post = snapshot.getValue(Post.class);
                            posts.add(post);
                        }
                        Collections.reverse(posts);
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
