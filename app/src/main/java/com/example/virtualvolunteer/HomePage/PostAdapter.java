package com.example.virtualvolunteer.HomePage;

import android.content.Context;
import android.graphics.Typeface;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtualvolunteer.Login;
import com.example.virtualvolunteer.ProfilePage.Profile;
import com.example.virtualvolunteer.R;
import com.example.virtualvolunteer.Upload;
import com.example.virtualvolunteer.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    DatabaseReference usersRef;
    private ArrayList<Post> posts;
    private Context home;

    public PostAdapter(ArrayList<Post> items, Context home) {
        this.posts = items;
        this.home = home;
        usersRef  = FirebaseDatabase.getInstance().getReference("Users");
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView post_username;
        private TextView post_description;
        private ImageView post_image;
        private ImageView post_profile_image;
        private TextView post_relative_time;
        private ImageView post_saved;
        private Context context;

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

            post_saved.setOnClickListener(v -> {
                Toast toast = Toast.makeText(home, "Post saved",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                savePost();
            });
        }

        public void bind(Post post) {
            String newEmail = post.getEmail().replace('.', '_');
            usersRef = usersRef.child(newEmail);
            usersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        User user = snapshot.getValue(User.class);
                        post_username.setText(user.getName());
                        Upload upload = user.getUpload();
                        if (upload != null)
                            Picasso.with(home).load(upload.getImageUrl()).resize(200, 200).centerCrop().into(post_profile_image);
                        usersRef.removeEventListener(this);
                    }
                    post_description.setText(post.getDescription());
                    Picasso.with(home).load(post.getImageURL()).resize(200, 200).centerCrop().into(post_image);
                    post_relative_time.setText(DateUtils.getRelativeTimeSpanString(post.getCreationTime()));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private void savePost() {
        // TODO:  save post to firebase (use time as unique identifier)
        // each user will have a list of postID's (use post time) of posts that were saved "savedPosts"
        // this method will grab the postID and append it to the user's savedPosts
        // then in Saved.java the posts sent to the SavedPostAdapter will be grabbed from the user's savedPosts
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        usersRef  = FirebaseDatabase.getInstance().getReference("Users");
        viewHolder.bind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
