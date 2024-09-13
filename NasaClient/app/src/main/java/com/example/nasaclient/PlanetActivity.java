package com.example.nasaclient;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.fragment.app.FragmentActivity;
import com.example.nasaclient.databinding.ActivityWithNavBinding;

public class PlanetActivity extends FragmentActivity {
    ActivityWithNavBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set content for the HomeActivity
        getLayoutInflater().inflate(R.layout.activity_planets, binding.container, true);
        binding.bottomNavigationView.setSelectedItemId(R.id.planet);
        // Setup bottom navigation
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(this, MainActivity.class));
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
    }
}
