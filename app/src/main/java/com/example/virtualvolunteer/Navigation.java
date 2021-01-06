package com.example.virtualvolunteer;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Navigation {

    public static void enableNavigationClick(final Context context, BottomNavigationView view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        context.startActivity(new Intent(context, Home.class));
                        break;
                    case R.id.search:
                        context.startActivity(new Intent(context, Search.class));
                        break;
                    case R.id.log:
                        context.startActivity(new Intent(context, Log.class));
                        break;
                    case R.id.saved:
                        context.startActivity(new Intent(context, Saved.class));
                        break;
                    //case R.id.profile:
                    //context.startActivity(new Intent(context, Profile.class));
                }
                return false;
            }
        });
    }
}
