package com.example.virtualvolunteer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Log extends AppCompatActivity {

    private Button submitBtn;
    private EditText organization;
    private EditText event;
    private EditText date;
    private EditText hours;
    private TextView debug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        submitBtn = findViewById(R.id.submit_hours);
        organization = findViewById(R.id.organization);
        event = findViewById(R.id.event);
        date = findViewById(R.id.date);
        hours = findViewById(R.id.hours);
        debug = findViewById(R.id.debug);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);

        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        submitBtn.setOnClickListener(v -> {
            submitHours();
        });
    }

    public void submitHours() {
        String fOrganization = this.organization.getText().toString();
        String fEvent = this.event.getText().toString();
        String fDate = this.date.getText().toString();
        String fHours = this.hours.getText().toString();

        debug.setText(fOrganization + " " + fEvent + " " + fDate + " " + fHours);
    }

}
