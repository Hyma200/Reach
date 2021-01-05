package com.example.virtualvolunteer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private Button signUpBtn;
    private Button backBtn;
    private EditText new_email;
    private EditText new_password;
    private TextView debug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUpBtn = findViewById(R.id.sign_up);
        backBtn = findViewById(R.id.back);
        new_email = findViewById(R.id.new_email);
        new_password = findViewById(R.id.new_password);
        debug = findViewById(R.id.debug);

        signUpBtn.setOnClickListener(v -> createAccount(findViewById(R.id.new_email), findViewById(R.id.new_password)));

        backBtn.setOnClickListener(v -> {
            setContentView(R.layout.activity_login);  //return to login
        });
    }

    public void createAccount(EditText new_email, EditText new_password){
        String email = new_email.getText().toString();
        String password = new_password.getText().toString();
        //store email and password in database
    }
}