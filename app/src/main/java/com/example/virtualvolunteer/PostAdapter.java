package com.example.virtualvolunteer;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> posts;

    public PostAdapter(ArrayList<Post> items, Home home) {
        this.posts = items;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView post_username;
        private TextView post_description;
        private ImageView post_image;
        private TextView post_relative_time;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView = itemView.findViewById(R.id.title);
            post_username = itemView.findViewById(R.id.post_username);
            post_description = itemView.findViewById(R.id.post_description);
            post_image = itemView.findViewById(R.id.post_image);
            post_relative_time = itemView.findViewById(R.id.post_relative_time);
        }

        public void bind(Post post) {
            post_username.setText(post.getUser());
            post_description.setText(post.getDescription());
            // kotlin... Glide.with(context).load(post.imageUrl).into(itemView.ivPost)
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
