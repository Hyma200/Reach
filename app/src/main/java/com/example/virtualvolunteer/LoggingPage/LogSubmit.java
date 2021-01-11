package com.example.virtualvolunteer.LoggingPage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualvolunteer.HomePage.PostCreate;
import com.example.virtualvolunteer.Navigation;
import com.example.virtualvolunteer.R;
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
    private Hour hour;
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
            if (organization.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(LogSubmit.this, "Organization description cannot be empty",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            if (event.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(LogSubmit.this, "Event description cannot be empty",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            if (date.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(LogSubmit.this, "Date description cannot be empty",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            if (verifyEmail.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(LogSubmit.this, "  Verify email description cannot be empty",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            if (hours.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(LogSubmit.this, "  Hours description cannot be empty",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            String key = myRef.push().getKey();
            myRef = myRef.child(key);
            hour = new Hour(organization.getText().toString(), Integer.parseInt(hours.getText().toString()),event.getText().toString(),mAuth.getCurrentUser().getEmail(),date.getText().toString(), "0", false);
            myRef.child(key).setValue(hour);
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
        //myRef.child("Verification").setValue(random);
        hour.setVerification(random);
        myRef.setValue(hour);
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
