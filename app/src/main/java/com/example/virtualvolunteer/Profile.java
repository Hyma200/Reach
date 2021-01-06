package com.example.virtualvolunteer;

import androidx.appcompat.app.AppCompatActivity;
//import android.widget.TextView;
//import android.widget.ImageView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {
    //private TextView name;
    //private ImageView image;
//comment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //name = (TextView) findViewById(R.id.name);
        //image = (ImageView) findViewById(R.id.image);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);

        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
    }
}