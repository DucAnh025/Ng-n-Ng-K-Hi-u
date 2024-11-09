package com.example.nasaclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.nasaclient.databinding.ActivityWithNavBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners; // For rounded corners
import com.bumptech.glide.request.RequestOptions;
import android.widget.ImageView;
public class MainActivity extends AppCompatActivity {
    ActivityWithNavBinding binding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private OkHttpClient client;
    private TextView tvFullName; // TextView to display the user's full name
    private ImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set content for the HomeActivity
        getLayoutInflater().inflate(R.layout.activity_main, binding.container, true);

        // Initialize OkHttpClient
        client = new OkHttpClient();

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

        // Set up NavigationView and get reference to header view
        NavigationView navigationView = findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);
        tvFullName = headerView.findViewById(R.id.tvFullName);
        profileImage = headerView.findViewById(R.id.profile_image);// Assuming tvFullName is the TextView ID in nav_header

        // Fetch user info when this activity loads
        fetchUserInfo();

        // Handle navigation item selections in the sidebar menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_voucher) {
                    startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
                } else if (id == R.id.nav_chat) {
                    startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                } else if (id == R.id.nav_history) {
                    startActivity(new Intent(MainActivity.this, ChangePasswordActivity.class));
                } else if (id == R.id.nav_settings) {
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                } else if (id == R.id.nav_help) {
                    startActivity(new Intent(MainActivity.this, HelpAndSupportActivity.class));
                } else if (id == R.id.nav_logout) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("auth_token"); // Remove the auth token
                    editor.apply();

                    // Redirect to LoginActivity
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    // Clear the back stack so user can't go back after logout
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish(); // Close the SettingActivity                    Toast.makeText(MainActivity.this, "Logout selected", Toast.LENGTH_SHORT).show();
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
        Button btnViewDetail = findViewById(R.id.btnViewDetail);
        btnViewDetail.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DetailFragment.class);
            startActivity(intent);
        });

        ImageButton btnNotifMain = findViewById(R.id.btnNotif_Main);
        btnNotifMain.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NotifMainFragment.class);
            startActivity(intent);
        });

        Button btnSeeAll = findViewById(R.id.btnSeeAll);
        btnSeeAll.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SeeAllFragment.class);
            startActivity(intent);
        });
    }

    private void fetchUserInfo() {
        // Retrieve token from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        // Build the request
        Request request = new Request.Builder()
                .url("https://boxgateway.kozow.com/get_user_info")
                .get()
                .addHeader("Authorization", "Bearer " + authToken)
                .build();

        // Make asynchronous API call
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Failed to fetch user info", Toast.LENGTH_SHORT).show());
                Log.e("UserInfoError", "Network error: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        String fullName = jsonResponse.getString("full_name");
                        String imageUrl = jsonResponse.getString("image_url");
                        // Update UI on the main thread
                        runOnUiThread(() -> {
                        tvFullName.setText(fullName);
                        Glide.with(MainActivity.this)
                                .load(imageUrl)
                                .placeholder(R.drawable.circle_background_01) // Placeholder
                                .error(R.drawable.circle_background_01)       // Error
                                .apply(RequestOptions.circleCropTransform())
                                .skipMemoryCache(true) // Skip memory cache
                                .diskCacheStrategy(DiskCacheStrategy.NONE)// Radius in pixels
                                .into(profileImage);
                    }

                        );

                    } catch (JSONException e) {
                        Log.e("UserInfoError", "JSON parsing error: " + e.getMessage());
                    }
                } else {
                    Log.e("UserInfoError", "Response unsuccessful: " + response.code());
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Failed to load user info", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
