package com.example.virtualvolunteer.HomePage;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.virtualvolunteer.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> posts;
    private Context home;

    public PostAdapter(ArrayList<Post> items, Context home) {
        this.posts = items;
        this.home = home;
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
        private TextView post_relative_time;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView = itemView.findViewById(R.id.title);
            post_username = itemView.findViewById(R.id.post_username);
            post_description = itemView.findViewById(R.id.post_description);
            post_image = itemView.findViewById(R.id.post_image);
            post_relative_time = itemView.findViewById(R.id.post_relative_time);
        }

        public void bind(Post post) {
            post_username.setText(post.getEmail());
            post_description.setText(post.getDescription());
            Picasso.with(home).load(post.getImageURL()).resize(200, 200).centerCrop().into(post_image);
            post_relative_time.setText(DateUtils.getRelativeTimeSpanString(post.getCreationTime()));
        }
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
        viewHolder.bind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
