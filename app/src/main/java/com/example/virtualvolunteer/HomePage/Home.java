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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private ArrayList<Post> posts = new ArrayList<>();

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
            oppClicked = !oppClicked;
            generatePosts("Opportunity");
        });
        experienceTag.setOnClickListener(v -> {
            Toast.makeText(this, "experience tag", Toast.LENGTH_SHORT).show();
            expClicked = !expClicked;
            generatePosts("Experience");
        });
        virtualTag.setOnClickListener(v -> {
            Toast.makeText(this, "virtual tag", Toast.LENGTH_SHORT).show();
            virClicked = !virClicked;
            generatePosts("Virtual");
        });
        teachingTag.setOnClickListener(v -> {
            Toast.makeText(this, "teaching tag", Toast.LENGTH_SHORT).show();
            teachClicked = !teachClicked;
            generatePosts("Teaching");
        });
        environmentalTag.setOnClickListener(v -> {
            Toast.makeText(this, "environmental tag", Toast.LENGTH_SHORT).show();
            envClicked = !envClicked;
            generatePosts("Environment");
        });
        recreationalTag.setOnClickListener(v -> {
            Toast.makeText(this, "recreational tag", Toast.LENGTH_SHORT).show();
            recClicked = !recClicked;
            generatePosts("Recreational");
        });
        distributionTag.setOnClickListener(v -> {
            Toast.makeText(this, "distribution tag", Toast.LENGTH_SHORT).show();
            disClicked = !disClicked;
            generatePosts("Distribution");
        });
        if (!oppClicked && !expClicked && !virClicked && !teachClicked && !envClicked && !recClicked && !disClicked)
            generatePosts("");
        FloatingActionButton postCreateBtn = findViewById(R.id.create_post_button);
        postCreateBtn.setOnClickListener(v -> openPostCreate());
    }

    public void openPostCreate() {
        Intent intent = new Intent(this, PostCreate.class);
        startActivity(intent);
    }

    public void generatePosts(String tag) {

        FirebaseDatabase.getInstance().getReference().child("Posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Post post = snapshot.getValue(Post.class);
                            if (post.getTags().contains(tag))
                                posts.add(post);
                            else if (tag.isEmpty())
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
