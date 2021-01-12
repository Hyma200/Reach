package com.example.virtualvolunteer.LoggingPage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtualvolunteer.ProfilePage.Profile;
import com.example.virtualvolunteer.R;
import com.example.virtualvolunteer.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.ViewHolder> {

    private ArrayList<Hour> hours;
    private Context context;
    DatabaseReference usersRef;

    public HourAdapter(ArrayList<Hour> items, Context context) {
        this.context = context;
        this.hours = items;
        usersRef = FirebaseDatabase.getInstance().getReference("Users");
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView log_event;
        private TextView log_organization;
        private TextView log_email;
        private TextView log_date;
        private TextView log_hours;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView = itemView.findViewById(R.id.log_title);
            log_event = itemView.findViewById(R.id.log_event);
            log_organization = itemView.findViewById(R.id.log_organization);
            log_email = itemView.findViewById(R.id.log_email);
            log_date = itemView.findViewById(R.id.log_date);
            log_hours = itemView.findViewById(R.id.log_hours);

            log_organization.setTypeface(null, Typeface.BOLD);
        }

        public void bind(Hour hour) {
            String newEmail = hour.getEmail().replace('.', '_');
            usersRef = usersRef.child(newEmail);

            log_event.setText(hour.getEvent());
            log_organization.setText(hour.getOrg());
            log_email.setText(hour.getEmail());
            log_date.setText(hour.getDate());
            String h = (hour.getHours() != 1) ? " Hours" : " Hour";
            log_hours.setText(hour.getHours() + h);

            usersRef.addValueEventListener(new ValueEventListener() {
                User user;
                String key = "";

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        key = snapshot.getKey();
                        user = snapshot.getValue(User.class);
                        usersRef.removeEventListener(this);
                    }

                    log_organization.setOnClickListener(v -> {
                        viewProfile(key);
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.hour_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.bind(hours.get(position));
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public void viewProfile(String email) {
        Intent intent = new Intent(context, Profile.class);
        Bundle bundle = new Bundle();
        bundle.putString("Email", email);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}