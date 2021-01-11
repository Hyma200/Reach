package com.example.virtualvolunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virtualvolunteer.LoggingPage.LogVerify;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private Button signUp;
    private EditText email;
    private EditText password;
    private EditText name;
    private FirebaseAuth mAuth;
    private CheckBox isOrganizer;
    private static final String TAG = "EmailPassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signUp = findViewById(R.id.sign_up);
        email = findViewById(R.id.new_email);
        password = findViewById(R.id.new_password);
        name = findViewById(R.id.new_name);
        isOrganizer = findViewById(R.id.is_organizer);

        signUp.setOnClickListener(
                view -> {
                    String fEmail = email.getText().toString();
                    String storeEmail = fEmail.replace('.', '_');
                    String fPassword = password.getText().toString();
                    String fName = name.getText().toString();
                    if (email.getText().toString().isEmpty()) {
                        Toast toast = Toast.makeText(SignUp.this, "Email description cannot be empty",
                                Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if (password.getText().toString().isEmpty()) {
                        Toast toast = Toast.makeText(SignUp.this, "Password description cannot be empty",
                                Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if (name.getText().toString().isEmpty()) {
                        Toast toast = Toast.makeText(SignUp.this, "Name description cannot be empty",
                                Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    mAuth.createUserWithEmailAndPassword(fEmail, fPassword)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        User user = new User (fName, fEmail, null, "", 0, "", "", null, null, 0);
                                        myRef.child(storeEmail).setValue(user);
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