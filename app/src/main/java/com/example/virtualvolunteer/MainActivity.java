package com.example.virtualvolunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button loginBtn;
    private Button signUpBtn;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private TextView debug;
    private static final String TAG = "EmailPassword";
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBtn = findViewById(R.id.login);
        signUpBtn = findViewById(R.id.sign_up);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        debug = findViewById(R.id.debug);
        password.setOnClickListener(
                view -> {
                    String fEmail = email.getText().toString();
                    String fPassword = password.getText().toString();
                    debug.setText(fEmail + " " + fPassword);
                    mAuth.createUserWithEmailAndPassword(fEmail, fPassword)
                            .addOnCompleteListener(this, task -> {
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
                            });
                }
        );
        loginBtn.setOnClickListener(v -> {
            setContentView(R.layout.activity_home);  //view home page after clicking the login button (might need to change to work with authentication)
        });
        signUpBtn.setOnClickListener(v -> {
            setContentView(R.layout.activity_signup);  //view sign up page after clicking the sign up
            createAccount(findViewById(R.id.new_email), findViewById(R.id.new_password));
        });
    }
    @Override
    public void onStart(){
        super.onStart();
        //Check if user is signed in
        FirebaseUser user = mAuth.getCurrentUser();
    }

    public void createAccount(EditText email, EditText password){
        //store email and password in database
    }
}