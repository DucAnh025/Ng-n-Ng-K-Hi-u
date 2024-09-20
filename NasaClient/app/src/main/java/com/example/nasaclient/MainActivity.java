package com.example.nasaclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.ComponentActivity;
import androidx.fragment.app.FragmentActivity;
import com.example.nasaclient.databinding.ActivityWithNavBinding;

public class MainActivity extends FragmentActivity {
    ActivityWithNavBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set content for the HomeActivity
        getLayoutInflater().inflate(R.layout.activity_main, binding.container, true);
        binding.bottomNavigationView.setSelectedItemId(R.id.home);
        // Setup bottom navigation
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
        Button btnback = findViewById(R.id.btnViewDetail);
        btnback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this,DetailFragment.class);
                startActivity(intent);
            }
        });

        ImageButton btnback_notif = findViewById(R.id.btnNotif_Main);
        btnback_notif.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this,NotifMainFragment.class);
                startActivity(intent);
            }
        });
        Button btnSeeAll = findViewById(R.id.btnSeeAll);
        btnSeeAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this,SeeAllFragment.class);
                startActivity(intent);
            }
        });
    }
}
