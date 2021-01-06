package com.example.virtualvolunteer;

import androidx.appcompat.app.AppCompatActivity;
//import android.widget.TextView;
//import android.widget.ImageView;
import android.os.Bundle;

public class profile extends AppCompatActivity {
    //private TextView name;
    //private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //name = (TextView) findViewById(R.id.name);
        //image = (ImageView) findViewById(R.id.image);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
}