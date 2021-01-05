package com.example.virtualvolunteer;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private Button signUpBtn;
    private Button backBtn;
    private EditText new_name;
    private EditText new_email;
    private EditText new_password;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUpBtn = findViewById(R.id.sign_up);
        backBtn = findViewById(R.id.back);
        new_name = findViewById(R.id.new_name);
        new_email = findViewById(R.id.new_email);
        new_password = findViewById(R.id.new_password);

        signUpBtn.setOnClickListener(v -> createAccount(new_name, new_email, new_password));

        backBtn.setOnClickListener(v -> {
            setContentView(R.layout.activity_login);  //return to login
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        //Check if user is signed in
        FirebaseUser user = mAuth.getCurrentUser();
    }

    public void createAccount(EditText new_name, EditText new_email, EditText new_password) {
        String name = new_name.getText().toString();
        String email = new_email.getText().toString();
        String password = new_password.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        myRef.child(name).child("email").setValue(email);
                        Toast toast = Toast.makeText(SignUp.this, "Account Created Successfully",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        Log.w(TAG, "Account Creation Failure, most likely due to an invalid email", task.getException());
                        Toast toast = Toast.makeText(SignUp.this, "Account Creation Failure, most likely due to an invalid email",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        // If sign in fails, display a message to the user.
                    }
                });
    }
}