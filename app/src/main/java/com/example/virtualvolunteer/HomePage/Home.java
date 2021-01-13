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
    private boolean oppClicked = false;
    private Button virtualTag;
    private boolean virClicked = false;
    private Button teachingTag;
    private boolean teachClicked = false;
    private Button environmentalTag;
    private boolean envClicked = false;
    private Button recreationalTag;
    private boolean recClicked = false;
    private Button distributionTag;
    private boolean disClicked = false;
    private Button experienceTag;
    private boolean expClicked = false;

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
        ArrayList<String> tags = new ArrayList<String>();

        opportunityTag.setOnClickListener(v -> {
            oppClicked = !oppClicked;
            int resId = oppClicked ? R.color.purple : R.color.gray;
            opportunityTag.setBackgroundColor(getResources().getColor(resId));
            if (oppClicked) {
                tags.add("Opportunity");
            } else {
                tags.remove("Opportunity");
            }
            generatePosts(tags);
        });
        experienceTag.setOnClickListener(v -> {
            expClicked = !expClicked;
            int resId = expClicked ? R.color.purple : R.color.gray;
            experienceTag.setBackgroundColor(getResources().getColor(resId));
            if (expClicked)
                tags.add("Experience");
            else
                tags.remove("Experience");
            generatePosts(tags);
        });
        virtualTag.setOnClickListener(v -> {
            virClicked = !virClicked;
            int resId = virClicked ? R.color.purple : R.color.gray;
            virtualTag.setBackgroundColor(getResources().getColor(resId));
            if (virClicked)
                tags.add("Virtual");
            else
                tags.remove("Virtual");
            generatePosts(tags);
        });
        teachingTag.setOnClickListener(v -> {
            teachClicked = !teachClicked;
            int resId = teachClicked ? R.color.purple : R.color.gray;
            teachingTag.setBackgroundColor(getResources().getColor(resId));
            if (teachClicked)
                tags.add("Teaching");
            else
                tags.remove("Teaching");
            generatePosts(tags);
        });
        environmentalTag.setOnClickListener(v -> {
            envClicked = !envClicked;
            int resId = envClicked ? R.color.purple : R.color.gray;
            environmentalTag.setBackgroundColor(getResources().getColor(resId));
            if (envClicked)
                tags.add("Environment");
            else
                tags.remove("Environment");
            generatePosts(tags);
        });
        recreationalTag.setOnClickListener(v -> {
            recClicked = !recClicked;
            int resId = recClicked ? R.color.purple : R.color.gray;
            recreationalTag.setBackgroundColor(getResources().getColor(resId));
            if (recClicked) {
                tags.add("Recreational");
            } else
                tags.remove("Recreational");
            generatePosts(tags);
        });
        distributionTag.setOnClickListener(v -> {
            disClicked = !disClicked;
            int resId = disClicked ? R.color.purple : R.color.gray;
            distributionTag.setBackgroundColor(getResources().getColor(resId));
            if (disClicked)
                tags.add("Distribution");
            else
                tags.remove("Distribution");
            generatePosts(tags);
        });
        if (!oppClicked && !expClicked && !virClicked && !teachClicked && !envClicked && !recClicked && !disClicked)
            generatePosts(null);
        ImageView postCreateBtn = findViewById(R.id.create_post_button);
        postCreateBtn.setOnClickListener(v -> openPostCreate());
    }

    public void openPostCreate() {
        Intent intent = new Intent(this, PostCreate.class);
        startActivity(intent);
    }

    public void generatePosts(ArrayList<String> tags) {
        ArrayList<Post> posts = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Post post = snapshot.getValue(Post.class);
                            if (tags == null)
                                posts.add(post);
                            else {
                                for (String currentTag : tags) {
                                    if (post.getTags().contains(currentTag) && !posts.contains(post))
                                        posts.add(post);
                                }
                            }
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
