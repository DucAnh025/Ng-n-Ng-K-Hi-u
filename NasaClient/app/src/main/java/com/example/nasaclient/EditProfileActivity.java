package com.example.nasaclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Get the Save Changes button from layout
        Button btn_save_changes = findViewById(R.id.btn_save_changes);

        // Set the click listener for the Save Changes button
        btn_save_changes.setOnClickListener(v -> {
            // Create an intent to navigate to EditProfileActivity
            Intent intent = new Intent(EditProfileActivity.this,SettingActivity.class);
            startActivity(intent);
        });
        ImageButton btnback = findViewById(R.id.backButton);
        btnback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(EditProfileActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}
