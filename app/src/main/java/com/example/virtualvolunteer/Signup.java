package com.example.virtualvolunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {
    private Button signIn;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private TextView debug;
    private static final String TAG = "EmailPassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signIn = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        debug = (TextView) findViewById(R.id.debug);
        password.setOnClickListener(
                view -> {
                    String fEmail = email.getText().toString();
                    String fPassword = password.getText().toString();
                    debug.setText(fEmail + " " + fPassword);
                    mAuth.createUserWithEmailAndPassword(fEmail, fPassword)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        debug.append("User created successfully");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                    } else {
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        // If sign in fails, display a message to the user.
                                        debug.append("User not successful" + task.getException());
                                    }
                                }
                            });
                }
        );
    }

    @Override
    public void onStart(){
        super.onStart();
        //Check if user is signed in
        FirebaseUser user = mAuth.getCurrentUser();
    }
}