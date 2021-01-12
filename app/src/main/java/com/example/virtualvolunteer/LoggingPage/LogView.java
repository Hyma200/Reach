package com.example.virtualvolunteer.LoggingPage;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtualvolunteer.HomePage.Post;
import com.example.virtualvolunteer.HomePage.PostAdapter;
import com.example.virtualvolunteer.HomePage.PostCreate;
import com.example.virtualvolunteer.Navigation;
import com.example.virtualvolunteer.ProfilePage.Profile;
import com.example.virtualvolunteer.R;
import com.example.virtualvolunteer.Upload;
import com.example.virtualvolunteer.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;


public class LogView extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    DatabaseReference myRef;

    private static final String TAG = "Database";
    private User logUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_view);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        generateHours();
    }

    public void generateHours() {
        ArrayList<Hour> hours = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Hours")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Hour hour = (Hour) snapshot.getValue(Hour.class);
                            hours.add(hour);
                        }
                        Collections.reverse(hours);
                        RecyclerView rView = findViewById(R.id.log_rView);
                        rView.setLayoutManager(new LinearLayoutManager(LogView.this));
                        rView.setAdapter(new HourAdapter(hours, LogView.this));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

}
