package com.example.virtualvolunteer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference myRef = database.getReference("Users").child(uid).child("name");
    private TextView output;
    private Button button;
    private static final String TAG = "Database";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        output = (TextView) findViewById(R.id.home_name);
        button = (Button) findViewById(R.id.button);
        output.setText(uid);
        button.setOnClickListener(
                view -> {
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String value = snapshot.getValue(String.class);
                            output.setText(value);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            output.setText("ERROR: " + error.toException());
                        }
                    });
                });
    }

}