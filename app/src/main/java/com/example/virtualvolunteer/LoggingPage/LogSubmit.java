package com.example.virtualvolunteer.LoggingPage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualvolunteer.HomePage.PostCreate;
import com.example.virtualvolunteer.Navigation;
import com.example.virtualvolunteer.R;
import com.example.virtualvolunteer.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

public class LogSubmit extends AppCompatActivity {

    private Button submitBtn;
    private EditText organization;
    private EditText event;
    //private TextView date;
    //
    private static final String TAG = "LogSubmit";
    private TextView date;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    //
    private EditText verifyEmail;
    private String name = "";
    private EditText hours;
    private User currentUser;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference myRef = database.getReference("Hours");
    DatabaseReference usersRef = database.getReference("Users");
    String email = mAuth.getCurrentUser().getEmail().replace('.', '_');
    private Hour hour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        usersRef = usersRef.child(email);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(LogSubmit.this,
                        android.R.style.Widget_Holo_ActionBar_Solid, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String displayDate = month + "/" + dayOfMonth + "/" + year;
                date.setText(displayDate);
            }
        };


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
            myRef.setValue(hour);
            currentUser.addOrg(organization.getText().toString());
            currentUser.setHours(currentUser.getHours() + Integer.parseInt(hours.getText().toString()));
            usersRef.setValue(currentUser);
            startEmail();
            Toast toast = Toast.makeText(LogSubmit.this, "Hours Successfully Added",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
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
        hour.setVerification(random);
        myRef.setValue(hour);
        Intent email = new Intent (Intent.ACTION_SENDTO, Uri.fromParts("mailto", verifyEmail.getText().toString(), null));
        email.setData(Uri.parse("mailto:"));
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{verifyEmail.getText().toString()});
        email.putExtra(Intent.EXTRA_SUBJECT, "Verify Volunteer Hours");
        email.putExtra(Intent.EXTRA_TEXT, "Please confirm that I" + " have finished " + hours.getText().toString() + " hours at your organization. " +
                "Please input the following code under their user profile\n" + random + ".");
        email.putExtra(Intent.ACTION_MEDIA_BUTTON, "Verify");
        if (email.resolveActivity(getPackageManager()) != null){
            startActivity(Intent.createChooser(email, "Send Email"));
        }
    }

}
