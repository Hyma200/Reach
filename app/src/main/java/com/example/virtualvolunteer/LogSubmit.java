package com.example.virtualvolunteer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Random;

public class LogSubmit extends AppCompatActivity {

    private Button submitBtn;
    private EditText organization;
    private EditText event;
    private EditText date;
    private EditText verifyEmail;
    private String name = "";
    private EditText hours;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference myRef = database.getReference("Hours");
    DatabaseReference usersRef = database.getReference("Users");
    String email = mAuth.getCurrentUser().getEmail().replace('.', '_');
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseReference hoursRef = usersRef.child(email).child("hours");
        DatabaseReference nameRef = usersRef.child(email).child("name");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_submit);

        submitBtn = findViewById(R.id.submit_hours);
        organization = findViewById(R.id.organization);
        event = findViewById(R.id.event);
        date = findViewById(R.id.date);
        hours = findViewById(R.id.hours);
        verifyEmail = findViewById(R.id.verifyEmail);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);

        submitBtn.setOnClickListener(v -> {
            String key = myRef.push().getKey();
            myRef = myRef.child(key);
            myRef.child("Organization").setValue(organization.getText().toString());
            myRef.child("Event").setValue(event.getText().toString());
            myRef.child("Date").setValue(date.getText().toString());
            myRef.child("Hours").setValue(Integer.parseInt(hours.getText().toString()));
            myRef.child("Email").setValue(mAuth.getCurrentUser().getEmail());
            nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    name = snapshot.getValue(String.class);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

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
                    startEmail();
                    Toast toast = Toast.makeText(LogSubmit.this, "Hours Successfully Added",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
            reload();
        });
    }

    public void reload(){
        Intent intent = new Intent(this, LogSubmit.class);
        startActivity(intent);
    }

    public void startEmail(){
        int num = new Random().nextInt(999999);
        String random = String.format("%06d", num);
        myRef.child("Verification").setValue(random);
        myRef.child("Verified").setValue(false);
        Intent email = new Intent (Intent.ACTION_SENDTO, Uri.fromParts("mailto", verifyEmail.getText().toString(), null));
        email.setData(Uri.parse("mailto:"));
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{verifyEmail.getText().toString()});
        email.putExtra(Intent.EXTRA_SUBJECT, "Verify Volunteer Hours");
        email.putExtra(Intent.EXTRA_TEXT, "Please confirm that " + name + " has finished " + hours.getText().toString() + " hours at your organization. " +
                "Please input the following code under their user profile\n" + random + ".");
        email.putExtra(Intent.ACTION_MEDIA_BUTTON, "Verify");
        if (email.resolveActivity(getPackageManager()) != null){
            startActivity(Intent.createChooser(email, "Send Email"));
        }
    }

}
