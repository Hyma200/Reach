package com.example.virtualvolunteer.LoggingPage;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualvolunteer.Navigation;
import com.example.virtualvolunteer.R;
import com.example.virtualvolunteer.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogVerify extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference hoursRef = database.getReference("Hours");
    DatabaseReference usersRef = database.getReference("Users");
    private Button verifyBtn;
    private EditText verificationCode;
    private EditText verificationEmail;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_verify);

        verifyBtn = findViewById(R.id.verify_hours);
        verificationCode = findViewById(R.id.verification_code);
        verificationEmail = findViewById(R.id.verification_email);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        verifyBtn.setOnClickListener(v -> {
            String fVerificationCode = verificationCode.getText().toString();
            String fVerificationEmail = verificationEmail.getText().toString();
            if (verificationCode.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(LogVerify.this, "Verification code description cannot be empty",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            if ((verificationCode.getText().toString().length() > 6) || (verificationCode.getText().toString().length() < 6)) {
                Toast toast = Toast.makeText(LogVerify.this, "Verification code description must be 6 numbers",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            if (verificationEmail.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(LogVerify.this, "Verification email description cannot be empty",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            usersRef = usersRef.child(fVerificationEmail.replace('.', '_'));

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
                            return;
                        }
                        else if (hour.getVerified()){
                            Toast toast = Toast.makeText(LogVerify.this, "Hours Have Been Verified",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return;
                        }
                        else if (hour.getEmail().equals(fVerificationEmail)){
                            hour.setVerified();
                            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    currentUser = snapshot.getValue(User.class);
                                    currentUser.setValidHours(currentUser.getValidHours() + hour.getHours());
                                    usersRef.setValue(currentUser);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            Toast toast = Toast.makeText(LogVerify.this, "Hours Successfully Verified",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            hoursRef.child(key).setValue(hour);
                            return;
                        }
                        else{
                            Toast toast = Toast.makeText(LogVerify.this, "Error Processing Request. Error may be due to invalid code.",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return;
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        });
}

}
