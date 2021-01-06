package com.example.virtualvolunteer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Log extends AppCompatActivity {

    private Button submitBtn;
    private EditText organization;
    private EditText event;
    private EditText date;
    private EditText hours;
    private TextView debug;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference myRef = database.getReference("Hours");
    DatabaseReference hoursRef = database.getReference("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hoursRef = hoursRef.child(mAuth.getCurrentUser().getEmail().replace('.', '_')).child("hours");
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
            String key = myRef.push().getKey();
            myRef = myRef.child(key);
            myRef.child("Organization").setValue(organization.getText().toString());
            myRef.child("Event").setValue(event.getText().toString());
            myRef.child("Date").setValue(date.getText().toString());
            myRef.child("Hours").setValue(Integer.parseInt(hours.getText().toString()));
            myRef.child("Email").setValue(mAuth.getCurrentUser().getEmail());
            hoursRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot){
                    if (snapshot.exists()){
                        Long value = snapshot.getValue(Long.class);
                        hoursRef.setValue(value + Integer.parseInt(hours.getText().toString()));
                    }
                    else{
                        hoursRef.setValue(Integer.parseInt(hours.getText().toString()));
                    }
                    Toast toast = Toast.makeText(Log.this, "Hours Successfully Added",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    debug.setText("ERROR: " + error.toException());
                }
            });
            reload();
        });
    }

    public void reload(){
        Intent intent = new Intent(this, Log.class);
        startActivity(intent);
    }

}
