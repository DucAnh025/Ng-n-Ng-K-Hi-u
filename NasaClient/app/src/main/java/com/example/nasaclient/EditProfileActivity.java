package com.example.nasaclient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.ComponentActivity;


public class EditProfileActivity extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Get the Save Changes button from layout
        Button editProfileButton = findViewById(R.id.btn_save_changes);

        // Set the click listener for the Save Changes button
        editProfileButton.setOnClickListener(v -> {
            // Create an intent to navigate to EditProfileActivity
            Intent intent = new Intent(EditProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });
    }
}
