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

public class LogVerify extends AppCompatActivity {

    private Button verifyBtn;
    private EditText verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_verify);

        verifyBtn = findViewById(R.id.verify_hours);
        verificationCode = findViewById(R.id.verification_code);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);

        verifyBtn.setOnClickListener(v -> {
            String fVerificationCode = verificationCode.getText().toString();
            // add the hours to the user's profile
            Toast toast = Toast.makeText(LogVerify.this, "Hours Successfully Verified",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            reload();
        });
    }

    public void reload() {
        Intent intent = new Intent(this, LogVerify.class);
        startActivity(intent);
    }

}
