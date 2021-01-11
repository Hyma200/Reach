package com.example.virtualvolunteer.SavedPage;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtualvolunteer.HomePage.Home;
import com.example.virtualvolunteer.HomePage.Post;
import com.example.virtualvolunteer.HomePage.PostAdapter;
import com.example.virtualvolunteer.Navigation;
import com.example.virtualvolunteer.R;
import com.example.virtualvolunteer.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Saved extends AppCompatActivity {
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace('.', '_');
    DatabaseReference currentUserRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser);

    private MutableLiveData<ArrayList<Post>> postLiveData = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Post>> getData(){
        currentUserRef
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Post> posts = new ArrayList<>();
                        User user = dataSnapshot.getValue(User.class);
                        ArrayList<Long> postTimes = user.getPosts();
                        DatabaseReference postsReference = FirebaseDatabase.getInstance().getReference("Posts");
                        for (Long time: postTimes){
                            postsReference.orderByChild("creationTime").equalTo(time).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                            Post post = (Post) snapshot.child(dataSnapshot.getKey()).getValue(Post.class);
                                            posts.add(post);
                                            postLiveData.setValue(posts);
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        return postLiveData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        BottomNavigationView navView = findViewById(R.id.Bottom_navigation_icon);
        Navigation.enableNavigationClick(this, navView);

        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        generatePosts();
    }

    public void generatePosts() {
        getData().observe(this, new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> posts) {
                Collections.reverse(posts);
                RecyclerView rView = findViewById(R.id.saved_rView);
                rView.setLayoutManager(new LinearLayoutManager(Saved.this));
                rView.setAdapter(new PostAdapter(posts, Saved.this));
            }
        });

    }
}
