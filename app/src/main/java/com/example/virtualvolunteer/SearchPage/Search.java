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
import com.example.virtualvolunteer.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Search extends AppCompatActivity implements TextWatcher {

    private EditText query;
    private ListView results;
    private ImageView searchBtn;
    private SearchAdapter adapter;
    private ArrayList<SearchResult> data = new ArrayList();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference users = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        query = findViewById(R.id.query);
        results = findViewById(R.id.search_list);
        searchBtn = findViewById(R.id.search_activity);

        data = new ArrayList<>();
        query.addTextChangedListener(this);

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    SearchResult searchResult = new SearchResult(dataSnapshot.getKey(), user.getName(), user.getBio(), user.getUpload());
                    data.add(searchResult);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new SearchAdapter(this, data);
        results.setAdapter(adapter);

        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);

        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.adapter.getFilter().filter(s);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
