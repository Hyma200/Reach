package com.example.virtualvolunteer.SavedPage;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtualvolunteer.HomePage.Home;
import com.example.virtualvolunteer.HomePage.Post;
import com.example.virtualvolunteer.HomePage.PostAdapter;
import com.example.virtualvolunteer.Navigation;
import com.example.virtualvolunteer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Saved extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);

        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        generatePosts();
    }

    public void generatePosts() {
        ArrayList<Post> posts = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("SavedPosts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Post post = (Post) snapshot.getValue(Post.class);
                            posts.add(post);
                        }
                        Collections.reverse(posts);
                        RecyclerView rView = findViewById(R.id.rView);
                        rView.setLayoutManager(new LinearLayoutManager(Saved.this));
                        rView.setAdapter(new SavedPostAdapter(posts, Saved.this));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }
}
