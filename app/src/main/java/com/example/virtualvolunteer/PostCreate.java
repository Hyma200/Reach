package com.example.virtualvolunteer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

public class PostCreate extends AppCompatActivity {


    private EditText description;
    private Button postBtn;
    private Button postImagePicker;
    private ImageView postImage;
    private Uri imageUri;
    private final int PICK_IMAGE_REQUEST = 123;

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
            String fDescription = description.getText().toString();
            if (fDescription.isEmpty()) {
                Toast toast = Toast.makeText(PostCreate.this, "Description cannot be empty",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            Toast toast = Toast.makeText(PostCreate.this, "Successfully Made New Post",
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
