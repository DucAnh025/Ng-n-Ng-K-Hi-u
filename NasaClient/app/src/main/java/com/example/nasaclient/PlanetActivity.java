package com.example.nasaclient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import com.example.nasaclient.databinding.ActivityWithNavBinding;

public class PlanetActivity extends FragmentActivity {
    private static final String TAG = "PlanetActivity";
    ActivityWithNavBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getLayoutInflater().inflate(R.layout.activity_planets, binding.container, true);
        binding.bottomNavigationView.setSelectedItemId(R.id.planet);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (itemId == R.id.planet) {
                // We're already on the Planet screen, so no need to start a new activity
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
        setupPlanetButtons();
    }

    private void setupPlanetButtons() {
        int[] buttonIds = {
                R.id.earthExploreButton,
                R.id.mercuryExploreButton,
                R.id.venusExploreButton,
                R.id.marsExploreButton,
                R.id.saturnExploreButton,
                R.id.uranusExploreButton,
                R.id.neptuneExploreButton,
        };

        for (int id : buttonIds) {
            View button = findViewById(id);
            if (button != null) {
                button.setOnClickListener(this::onExploreButtonClick);
                Log.d(TAG, "Button set up: " + getResources().getResourceEntryName(id));
            } else {
                Log.e(TAG, "Button not found: " + getResources().getResourceEntryName(id));
            }
        }
    }

    public void onExploreButtonClick(View view) {
        Log.d(TAG, "Button clicked: " + view.getId());

        Intent intent = new Intent(this, PlanetInfoActivity.class);

        int viewId = view.getId();
        if (viewId == R.id.earthExploreButton) {
            Log.d(TAG, "Earth button clicked");
            intent.putExtra("PLANET_NAME", "Earth");
            intent.putExtra("PLANET_IMAGE", R.drawable.earth);
            intent.putExtra("PLANET_INFO", "Earth is the third planet from the Sun and the only astronomical object known to harbor life...");
        } else if (viewId == R.id.mercuryExploreButton) {
            Log.d(TAG, "Mercury button clicked");
            intent.putExtra("PLANET_NAME", "Mercury");
            intent.putExtra("PLANET_IMAGE", R.drawable.mercury);
            intent.putExtra("PLANET_INFO", "Mercury is the smallest planet in the Solar System and the closest to the Sun...");
        } else if (viewId == R.id.marsExploreButton) {
            Log.d(TAG, "Mars button clicked");
            intent.putExtra("PLANET_NAME", "Mars");
            intent.putExtra("PLANET_IMAGE", R.drawable.mars);
            intent.putExtra("PLANET_INFO", "Mars gaming");
        } else if (viewId == R.id.venusExploreButton) {
            Log.d(TAG, "Venus button clicked");
            intent.putExtra("PLANET_NAME", "Venus");
            intent.putExtra("PLANET_IMAGE", R.drawable.venus);
            intent.putExtra("PLANET_INFO", "Venus gaming");
        } else if (viewId == R.id.saturnExploreButton) {
            Log.d(TAG, "Saturn button clicked");
            intent.putExtra("PLANET_NAME", "Mercury");
            intent.putExtra("PLANET_IMAGE", R.drawable.saturn);
            intent.putExtra("PLANET_INFO", "Saturnus");
        } else if (viewId == R.id.neptuneExploreButton) {
            Log.d(TAG, "Neptune button clicked");
            intent.putExtra("PLANET_NAME", "Mercury");
            intent.putExtra("PLANET_IMAGE", R.drawable.neptune);
            intent.putExtra("PLANET_INFO", "Dau an neptune");
        } else if (viewId == R.id.uranusExploreButton) {
            Log.d(TAG, "Uranus button clicked");
            intent.putExtra("PLANET_NAME", "Mercury");
            intent.putExtra("PLANET_IMAGE", R.drawable.uranus);
            intent.putExtra("PLANET_INFO", "Ubuntu");
        } else {
            Log.e(TAG, "Unknown button clicked: " + viewId);
            Toast.makeText(this, "Unknown planet selected", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(intent);
    }
}