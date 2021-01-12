package com.example.virtualvolunteer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.virtualvolunteer.HomePage.Home;
import com.example.virtualvolunteer.LoggingPage.Log;
import com.example.virtualvolunteer.ProfilePage.Profile;
import com.example.virtualvolunteer.SavedPage.Saved;
import com.example.virtualvolunteer.SearchPage.Search;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

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
                    case R.id.profile:
                        Intent intent = new Intent(context, Profile.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Email", FirebaseAuth.getInstance().getCurrentUser().getEmail().replace('.', '_'));
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                }
                return false;
            }
        });
    }
}
