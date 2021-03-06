package com.example.virtualvolunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.virtualvolunteer.HomePage.Home;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private Button loginBtn;
    private Button signUpBtn;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.login);
        signUpBtn = findViewById(R.id.sign_up);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        WebView wbv = findViewById(R.id.login_hand);

        WebSettings webSettings = wbv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        wbv.loadUrl("file:android_asset/reachBG.gif");
        wbv.setVerticalScrollBarEnabled(false);

        loginBtn.setOnClickListener(
                view -> {
                    String fEmail = email.getText().toString();
                    String fPassword = password.getText().toString();
                    if (fEmail.equals("") || fPassword.equals("")) {
                        Toast toast = Toast.makeText(Login.this, "All fields are required",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        mAuth.signInWithEmailAndPassword(fEmail, fPassword)
                                .addOnCompleteListener(this, task -> {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast toast = Toast.makeText(Login.this, "Authentication Success",
                                                Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                        openHome();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast toast = Toast.makeText(Login.this, "Authentication Failure. Please Try Again.",
                                                Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                });
                    }

                });
        signUpBtn.setOnClickListener(v -> openSignUp());
    }


    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            openHome();
    }

    public void openSignUp() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void openHome() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

}