package com.example.virtualvolunteer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Log extends AppCompatActivity {

    private Button submitBtn;
    private Button verifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        submitBtn = findViewById(R.id.submit_option);
        verifyBtn = findViewById(R.id.verify_option);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);

        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        submitBtn.setOnClickListener(v -> {
            openSubmit();
        });

        verifyBtn.setOnClickListener(v -> {
            openVerify();
        });

    }

    public void openSubmit() {
        Intent intent = new Intent(this, LogSubmit.class);
        startActivity(intent);
    }

    public void openVerify() {
        Intent intent = new Intent(this, LogVerify.class);
        startActivity(intent);
    }
}
