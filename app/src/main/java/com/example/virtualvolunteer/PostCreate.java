package com.example.virtualvolunteer;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class PostCreate extends AppCompatActivity {


    private EditText description;
    private Button postBtn;
    private Button postImagePicker;
    private Button postImageUpload;
    private ImageView image;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference("uploads");
    private StorageTask uploadTask;
    private User profileUser;

    private static final String TAG = "Database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef = database.getReference("Users").child(user.getEmail().replace('.', '_'));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create);

        description = findViewById(R.id.new_post_description);
        postBtn = findViewById(R.id.new_post_button);
        postImagePicker = findViewById(R.id.post_image_picker);
        postImageUpload = findViewById(R.id.post_button_upload);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);

        postBtn.setOnClickListener(v -> {
            String fDescription = description.getText().toString();
            Toast toast = Toast.makeText(PostCreate.this, "New Post Successfully Created",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            reload();
        });


        postImagePicker = (Button) findViewById(R.id.image_picker);
        image = (ImageView) findViewById(R.id.post_image);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profileUser = snapshot.getValue(User.class);
                Upload upload = profileUser.getUpload();
                if (upload != null)
                    Picasso.with(PostCreate.this).load(upload.getImageUrl()).fit().centerCrop().into(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        postImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress())
                    Toast.makeText(PostCreate.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                else
                    uploadFile();
            }
        });
    }

    public void reload() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (imageUri != null) {
            StorageReference fileReference = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(PostCreate.this, "Upload successful", Toast.LENGTH_LONG).show();
                    String downloadUrl = "";

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostCreate.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(image);
        }
    }

}
