package com.example.virtualvolunteer;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class PostCreate extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference myRef = database.getReference("Users").child(user.getEmail().replace('.', '_'));
    private EditText description;
    private Button postBtn;
    private Button postImagePicker;
    private ImageView postImage;
    private Uri imageUri;
    private final int PICK_IMAGE_REQUEST = 123;
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference("uploads");
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create);

        description = findViewById(R.id.new_post_description);
        postBtn = findViewById(R.id.new_post_button);
        postImagePicker = findViewById(R.id.new_post_image_picker);
        postImage = findViewById(R.id.new_post_image);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);

        postBtn.setOnClickListener(v -> {
            if (description.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(PostCreate.this, "Description cannot be empty",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            //storePost(description.getText().toString());
            Toast toast = Toast.makeText(PostCreate.this, "Successfully made new post",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            reload();
        });

        postImagePicker.setOnClickListener(v -> {
            Intent imagePickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
            imagePickerIntent.setType("image/*");
            if (imagePickerIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(imagePickerIntent, PICK_IMAGE_REQUEST);
            }
        });

    }

    public void storePost(String description) {
        StorageReference photoRef = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
        photoRef.putFile(imageUri).continueWithTask(photoUploadTask -> {
            photoRef.getDownloadUrl();
            return null;
        }).continueWithTask(downloadURLTask -> {
            Post post = new Post(user.getDisplayName(), description, downloadURLTask.getResult().toString(), System.currentTimeMillis());
            // TODO:  add to firebase database post collection
            return null;
        }).addOnCompleteListener(postCreationTask -> {
            if (!postCreationTask.isSuccessful()) {
                Toast toast = Toast.makeText(PostCreate.this, "Failed to create new post",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void reload() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            postImage.setImageURI(imageUri);
        }
    }

}
