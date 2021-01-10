package com.example.virtualvolunteer.ProfilePage;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.virtualvolunteer.Navigation;
import com.example.virtualvolunteer.R;
import com.example.virtualvolunteer.Upload;
import com.example.virtualvolunteer.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class ProfileEdit extends AppCompatActivity {
   DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
    FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
    private EditText name;
    private EditText location;
    private EditText bio;
    private EditText skills;
    private Button imagePicker;
    private ImageView image;
    private ProgressBar progressBar;
    private User user;
    private Button saveBtn;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "Database";
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference("uploads");
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        name = findViewById(R.id.profileName);
        location = findViewById(R.id.location);
        bio = findViewById(R.id.bio);
        skills = findViewById(R.id.skills);
        imagePicker = findViewById(R.id.image_picker);
        image = findViewById(R.id.image);
        progressBar = findViewById(R.id.progress_bar);
        saveBtn = findViewById(R.id.profile_edit_save);

        usersRef = usersRef.child(userAuth.getEmail().replace('.', '_'));
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    user = snapshot.getValue(User.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        imagePicker.setOnClickListener(v -> {
            openFileChooser();
        });

        saveBtn.setOnClickListener(v -> {
            // TODO: implement storeChanges and uncomment below
            if (!name.getText().toString().equals(""))
                user.setName(name.getText().toString());
            if (!location.getText().toString().equals(""))
                user.setLocation(location.getText().toString());
            if (!bio.getText().toString().equals(""))
                user.setSkills(bio.getText().toString());
            if(!skills.getText().toString().equals(""))
                user.setBio(skills.getText().toString());
            usersRef.setValue(user);
            storeChanges();

            Toast toast = Toast.makeText(ProfileEdit.this, "Successfully edited profile",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            reload();
        });
    }

    private void openFileChooser() {
        Intent imagePickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imagePickerIntent.setType("image/*");
        if (imagePickerIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(imagePickerIntent, PICK_IMAGE_REQUEST);
        }
    }

    public void storeChanges() {
        if (imageUri != null){
            StorageReference photoRef = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            uploadTask = photoRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if (taskSnapshot.getMetadata() != null) {
                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // TODO: store profile data changes to firebase
                                user.setUpload(new Upload("new name", uri.toString()));
                                usersRef.setValue(user);
                            /*Post post = new Post(user.getEmail(), description, uri.toString(), System.currentTimeMillis());
                            String key = postRef.push().getKey();
                            postRef.child(key).setValue(post);*/
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileEdit.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void reload() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }

    /*
    private void uploadFile(){
        if (imageUri != null){
            StorageReference fileReference = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    }, 500);

                    Toast.makeText(Profile.this, "Upload successful",Toast.LENGTH_LONG).show();
                    String downloadUrl = "";
                    if(taskSnapshot.getMetadata() != null){
                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Upload upload = new Upload("No Name", uri.toString());
                                profileUser.setUpload(upload);
                                myRef.setValue(profileUser);
                            }
                        });
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int) progress);
                }
            });
        }
        else
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
    }
     */
}
