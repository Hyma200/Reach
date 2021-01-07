package com.example.virtualvolunteer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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



public class Profile extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference myRef = database.getReference("Users").child(user.getEmail().replace('.', '_'));
    DatabaseReference nameRef = myRef.child("name");
    DatabaseReference locationRef = myRef.child("location");
    DatabaseReference hoursRef = myRef.child("hours");
    DatabaseReference ageRef = myRef.child("age");
    private TextView nameOutput;
    private TextView locationOutput;
    private TextView hoursOutput;
    private static final String TAG = "Database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        nameOutput = (TextView) findViewById(R.id.profileName);
        hoursOutput = (TextView) findViewById(R.id.hoursProfile);
        locationOutput = (TextView) findViewById(R.id.location);


        //Sets value of name on profile page
        nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                String value = snapshot.getValue(String.class);
                nameOutput.setText(value);
                if (value == null)
                    nameOutput.setText("Add Your Name to Your Profile");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                nameOutput.setText("ERROR: " + error.toException());
            }
        });

        //Sets location on profile page
        locationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                String value = snapshot.getValue(String.class);
                locationOutput.setText(value);
                if (value == null)
                    locationOutput.setText("Add Your Location");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                locationOutput.setText("ERROR: " + error.toException());
            }
        });

        //Sets hours on profile page
        hoursRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                Long value = snapshot.getValue(Long.class);
                hoursOutput.setText(value.toString() + " Hours");
                if (value == null)
                    hoursOutput.setText("0");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hoursOutput.setText("ERROR: " + error.toException());
            }
        });

        //Sets age on profile page
        ageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                String value = snapshot.getValue(String.class);
                //ageOutput.setText(value);
             //   if (value == null)
              //      ageOutput.setText("Please enter your date of birth");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               // ageOutput.setText("ERROR: " + error.toException());
            }
        });
    }
}
