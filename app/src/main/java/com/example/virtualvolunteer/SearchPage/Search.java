package com.example.virtualvolunteer.SearchPage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualvolunteer.Navigation;
import com.example.virtualvolunteer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Search extends AppCompatActivity {

    private EditText query;
    private ListView results;
    private ImageView searchBtn;
    private ArrayAdapter<String> adapter;

    String[] data = {"SPCA NOVA", "SPCA Bethesda", "SPCA Richmond", "SPCA Virginia Beach", "Food Pantry", "CrisisLink", "Food Donors"};
    //temporary data array

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference users = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        query = (EditText) findViewById(R.id.query);
        results = findViewById(R.id.search_list);
        adapter = new ArrayAdapter<String>(this, R.layout.search_item, R.id.search_name, data);
        results.setAdapter(adapter);

        searchBtn = (ImageView) findViewById(R.id.search_activity);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);

        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Search.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        searchBtn.setOnClickListener(v -> users.orderByChild("name").equalTo(query.getText().toString()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                /*debug.append(snapshot.toString());
                debug.append(snapshot.child("email").getValue().toString());
                debug.append(snapshot.child("name").getValue().toString());*/
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        }));
    }
}
