package com.example.virtualvolunteer.HomePage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtualvolunteer.ProfilePage.Profile;
import com.example.virtualvolunteer.R;
import com.example.virtualvolunteer.Upload;
import com.example.virtualvolunteer.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    DatabaseReference usersRef;
    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace('.', '_');
    DatabaseReference currentUserRef = FirebaseDatabase.getInstance().getReference("Users").child(email);
    private final ArrayList<Post> posts;
    private final Context context;
    private User currentUser;
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<User> getData(){
        currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    currentUser = snapshot.getValue(User.class);
                    userLiveData.setValue(currentUser);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return userLiveData;
    }

    public PostAdapter(ArrayList<Post> items, Context home) {
        this.posts = items;
        this.context = home;
        usersRef = FirebaseDatabase.getInstance().getReference("Users");
        currentUser = new User();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView post_username;
        private final TextView post_description;
        private final ImageView post_image;
        private final ImageView post_profile_image;
        private final TextView post_relative_time;
        private final ImageView post_saved;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView = itemView.findViewById(R.id.title);
            post_username = itemView.findViewById(R.id.post_username);
            post_description = itemView.findViewById(R.id.post_description);
            post_image = itemView.findViewById(R.id.post_image);
            post_profile_image = itemView.findViewById(R.id.post_profile_image);
            post_relative_time = itemView.findViewById(R.id.post_relative_time);
            post_saved = itemView.findViewById(R.id.post_save_button);

            post_username.setTypeface(null, Typeface.BOLD);
        }

        public void bind(Post post) {
            String newEmail = post.getEmail().replace('.', '_');
            usersRef = usersRef.child(newEmail);

            post_saved.setOnClickListener(v -> {
                String result = currentUser.addPost(post.getCreationTime());
                if(result.contains("Unsaved")){
                    post_saved.setImageResource(R.drawable.ic_post_save);
                }
                else{
                    post_saved.setImageResource(R.drawable.ic_post_saved);
                }
                currentUserRef.setValue(currentUser);
                Toast.makeText(PostAdapter.this.context, result, Toast.LENGTH_SHORT).show();
            });

            usersRef.addValueEventListener(new ValueEventListener() {
                User user;
                String key = "";

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        key = snapshot.getKey();
                        user = snapshot.getValue(User.class);
                        post_username.setText(user.getName());
                        Upload upload = user.getUpload();
                        if (upload != null)
                            Picasso.with(PostAdapter.this.context).load(upload.getImageUrl()).resize(200, 200).centerCrop().into(post_profile_image);
                        usersRef.removeEventListener(this);
                    }

                    post_description.setText(post.getDescription());
                    Picasso.with(PostAdapter.this.context).load(post.getImageURL()).resize(200, 200).centerCrop().into(post_image);
                    post_relative_time.setText(DateUtils.getRelativeTimeSpanString(post.getCreationTime()));
                    post_username.setOnClickListener(v -> viewProfile(key));

                    getData().observe((LifecycleOwner) PostAdapter.this.context, user -> {
                        if (user.getPosts().contains(post.getCreationTime()))
                            post_saved.setImageResource(R.drawable.ic_post_saved);
                        else
                            post_saved.setImageResource(R.drawable.ic_post_save);
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        usersRef = FirebaseDatabase.getInstance().getReference("Users");
        currentUserRef = FirebaseDatabase.getInstance().getReference("Users").child(email);
        viewHolder.bind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void viewProfile(String email) {
        Intent intent = new Intent(context, Profile.class);
        Bundle bundle = new Bundle();
        bundle.putString("Email", email.replace('.', '_'));
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
