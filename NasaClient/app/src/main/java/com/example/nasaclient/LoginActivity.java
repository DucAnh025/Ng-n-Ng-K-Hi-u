package com.example.nasaclient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.ComponentActivity;

public class LoginActivity extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Set the layout for the Activity

        // Get the Sign In button from layout
        Button signInButton = findViewById(R.id.btnSignIn);

        // Set the click listener for the Sign In button
        signInButton.setOnClickListener(v -> {
            // Create an intent to navigate to EditProfileActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
