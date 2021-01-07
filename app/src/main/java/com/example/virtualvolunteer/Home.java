package com.example.virtualvolunteer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Home extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference myRef = database.getReference("Users").child(user.getEmail().replace('.', '_')).child("name");

    private static final String TAG = "Database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        RecyclerView rView = findViewById(R.id.rView);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(new PostAdapter(generatePosts(), this));
    }

    public ArrayList<Post> generatePosts(){  //temporary generation for testing
        ArrayList<Post> posts = new ArrayList<Post>();
        Post p1 = new Post("andi", "blah blah", "", 1237645L);
        Post p2 = new Post("leela", "blah blah", "", 1482345L);
        Post p3 = new Post("brenda", "blah blah", "", 1312578L);
        posts.add(p1);
        posts.add(p2);
        posts.add(p3);
        return posts;
    }

}
