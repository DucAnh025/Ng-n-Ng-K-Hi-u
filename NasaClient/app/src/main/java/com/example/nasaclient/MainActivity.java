package com.example.nasaclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import com.example.nasaclient.databinding.ActivityWithNavBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    ActivityWithNavBinding binding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set content for the HomeActivity
        getLayoutInflater().inflate(R.layout.activity_main, binding.container, true);

        // Find and set up the Toolbar as the Support ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up DrawerLayout and ActionBarDrawerToggle
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Enable the hamburger icon on the toolbar for opening and closing the drawer
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // Handle navigation item selections in the sidebar menu
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_voucher) {
                    Toast.makeText(MainActivity.this, "Voucher selected", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_chat) {
                    Toast.makeText(MainActivity.this, "Chat selected", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_history) {
                    Toast.makeText(MainActivity.this, "History selected", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_settings) {
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                } else if (id == R.id.nav_help) {
                    Toast.makeText(MainActivity.this, "Help selected", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_logout) {
                    Toast.makeText(MainActivity.this, "Logout selected", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        // Setup bottom navigation
        binding.bottomNavigationView.setSelectedItemId(R.id.home);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                // No need to navigate since we're already on this page
                return true;
            } else if (itemId == R.id.planet) {
                startActivity(new Intent(this, PlanetActivity.class));
                return true;
            } else if (itemId == R.id.news) {
                startActivity(new Intent(this, NewsActivity.class));
                return true;
            } else if (itemId == R.id.setting) {
                startActivity(new Intent(this, SettingActivity.class));
                return true;
            }
            return false;
        });

        // Set up button listeners for specific actions
        Button btnback = findViewById(R.id.btnViewDetail);
        btnback.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DetailFragment.class);
            startActivity(intent);
        });

        ImageButton btnback_notif = findViewById(R.id.btnNotif_Main);
        btnback_notif.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NotifMainFragment.class);
            startActivity(intent);
        });

        Button btnSeeAll = findViewById(R.id.btnSeeAll);
        btnSeeAll.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SeeAllFragment.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Open or close the sidebar menu when the menu button is tapped
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
