package com.example.virtualvolunteer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class LogVerify extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference hoursRef = database.getReference("Hours");
    private Button verifyBtn;
    private EditText verificationCode;
    private EditText verificationEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_verify);

        verifyBtn = findViewById(R.id.verify_hours);
        verificationCode = findViewById(R.id.verification_code);
        verificationEmail = findViewById(R.id.verification_email);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);

        verifyBtn.setOnClickListener(v -> {
            String fVerificationCode = verificationCode.getText().toString();
            String fVerificationEmail = verificationEmail.getText().toString();
            // add the hours to the user's profile
            hoursRef.orderByChild("verify").equalTo(fVerificationCode).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        String key = dataSnapshot.getKey();
                        Hour hour = dataSnapshot.getValue(Hour.class);
                        if (hour.getEmail().equals(user.getEmail())){
                            Toast toast = Toast.makeText(LogVerify.this, "User Cannot Verify Their Own Hours",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            reload();
                        }
                        else if (hour.getVerified()){
                            Toast toast = Toast.makeText(LogVerify.this, "Hours Have Already Been Verified",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            reload();
                        }
                        else if (hour.getEmail().equals(fVerificationEmail)){
                            hour.setVerified();
                            hoursRef.child(key).setValue(hour);
                            Toast toast = Toast.makeText(LogVerify.this, "Hours Successfully Verified",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            reload();
                        }
                        else{
                            Toast toast = Toast.makeText(LogVerify.this, "Error Processing Request. Error may be due to invalid code.",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            reload();
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        });
}

    public void reload() {
        Intent intent = new Intent(this, LogVerify.class);
        startActivity(intent);
    }

}
