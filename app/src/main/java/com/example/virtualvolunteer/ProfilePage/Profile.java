package com.example.virtualvolunteer.ProfilePage;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.virtualvolunteer.Navigation;
import com.example.virtualvolunteer.R;
import com.example.virtualvolunteer.Upload;
import com.example.virtualvolunteer.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;


public class Profile extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference myRef = database.getReference("Users").child(user.getEmail().replace('.', '_'));

    private User profileUser;
    private TextView name;
    private TextView email;
    private TextView location;
    private TextView bio;
    private TextView skills;
    private TextView hours;
    private ListView orgs;
    private TextView orgTitle;
    private ImageView image;
    private Button editBtn;
    private ArrayAdapter<String> adapter;

    String[] data = {"SPCA NOVA", "SPCA Bethesda", "SPCA Richmond", "SPCA Virginia Beach", "Food Pantry", "CrisisLink", "Food Donors"};
    //temporary data array

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        name = (TextView) findViewById(R.id.profile_name);
        email = (TextView) findViewById(R.id.profile_email);
        location = (TextView) findViewById(R.id.profile_location);
        bio = (TextView) findViewById(R.id.profile_bio);
        skills = (TextView) findViewById(R.id.profile_skills);
        hours = (TextView) findViewById(R.id.profile_hours);
        orgTitle = findViewById(R.id.profile_org_title);
        orgs = (ListView) findViewById(R.id.profile_orgs);
        image = (ImageView) findViewById(R.id.profile_image);
        editBtn = (Button) findViewById(R.id.profile_edit_button);

        adapter = new ArrayAdapter<String>(this, R.layout.profile_org_item, R.id.profile_org_item, data);
        orgs.setAdapter(adapter);

        email.setText(user.getEmail());
        email.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        name.setTypeface(null, Typeface.BOLD);
        orgTitle.setTypeface(null, Typeface.BOLD);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profileUser = snapshot.getValue(User.class);
                name.setText(profileUser.getName());
                String h = (profileUser.getHours() != 1) ? " Hours" : " Hour";
                hours.setText(profileUser.getHours() + h + " Volunteered");
                location.setText(profileUser.getLocation());
                bio.setText(profileUser.getBio());
                skills.setText(profileUser.getSkills());
                Upload upload = profileUser.getUpload();
                if (upload != null)
                    Picasso.with(Profile.this).load(upload.getImageUrl()).resize(200, 200).centerCrop().into(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editBtn.setOnClickListener(v -> {
            openProfileEdit();
        });

    }

    public void openProfileEdit() {
        Intent intent = new Intent(this, ProfileEdit.class);
        startActivity(intent);
    }

}
