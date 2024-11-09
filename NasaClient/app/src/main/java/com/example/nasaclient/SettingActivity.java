package com.example.nasaclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.ComponentActivity;
import androidx.fragment.app.FragmentActivity;
import com.example.nasaclient.databinding.ActivityWithNavBinding;

public class SettingActivity extends FragmentActivity {
    ActivityWithNavBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set content for the HomeActivity
        getLayoutInflater().inflate(R.layout.activity_setting, binding.container, true);
        binding.bottomNavigationView.setSelectedItemId(R.id.setting);
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

        //button to edit profile
        Button btn_edit_profile = findViewById(R.id.btn_edit_profile);
        btn_edit_profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent intent1 = new Intent(SettingActivity.this,EditProfileActivity.class);
                startActivity(intent1);
            }
        });

        //button to change password
        Button btn_change_password = findViewById(R.id.btn_change_password);
        btn_change_password.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent intent1 = new Intent(SettingActivity.this, ChangePasswordActivity.class);
                startActivity(intent1);
            }
        });

        //button to notification
        Button btn_notification = findViewById(R.id.btn_notification);
        btn_notification.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent intent1 = new Intent(SettingActivity.this,NotificationActivity.class);
                startActivity(intent1);
            }
        });

        //button to notification setting
        Button btn_notification_setting = findViewById(R.id.btn_notification_setting);
        btn_notification_setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent intent1 = new Intent(SettingActivity.this,NotificationSettingActivity.class);
                startActivity(intent1);
            }
        });

        //button to security
        Button btn_security = findViewById(R.id.btn_security);
        btn_security.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent intent1 = new Intent(SettingActivity.this,SecurityActivity.class);
                startActivity(intent1);
            }
        });

        //button to language
        Button btn_language = findViewById(R.id.btn_language);
        btn_language.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent intent1 = new Intent(SettingActivity.this,LanguageActivity.class);
                startActivity(intent1);
            }
        });

        //button to legal and policies
        Button btn_legal_policies = findViewById(R.id.btn_legal_policies);
        btn_legal_policies.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent intent1 = new Intent(SettingActivity.this,LegalAndPoliciesActivity.class);
                startActivity(intent1);
            }
        });

        //button to help and support
        Button btn_help_support = findViewById(R.id.btn_help_support);
        btn_help_support.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent intent1 = new Intent(SettingActivity.this, HelpAndSupportActivity.class);
                startActivity(intent1);
            }
        });

        //button to logout
        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Remove auth token from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("auth_token"); // Remove the auth token
                editor.apply();

                // Redirect to LoginActivity
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                // Clear the back stack so user can't go back after logout
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // Close the SettingActivity
            }
        });
    }
}
