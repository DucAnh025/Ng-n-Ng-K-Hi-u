package com.example.nasaclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangePasswordActivity extends ComponentActivity {

    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // Initialize OkHttpClient
        client = new OkHttpClient();

        // Retrieve views from layout
        EditText oldPasswordEditText = findViewById(R.id.oldPassword); // Field for old password
        EditText newPasswordEditText = findViewById(R.id.newPassword);
        EditText confirmPasswordEditText = findViewById(R.id.confirmPassword);
        Button btnSaveChangesPass = findViewById(R.id.btn_save_changes);
        ImageButton btnBack = findViewById(R.id.backButton);

        // Set up click listener for save button
        btnSaveChangesPass.setOnClickListener(v -> {
            String oldPassword = oldPasswordEditText.getText().toString();
            String newPassword = newPasswordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            // Validate input
            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(ChangePasswordActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(ChangePasswordActivity.this, "New passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            changePassword(oldPassword, newPassword);
        });

        // Set up click listener for back button
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(ChangePasswordActivity.this, SettingActivity.class);
            startActivity(intent);
        });
    }

    private void changePassword(String oldPassword, String newPassword) {
        // Prepare JSON body
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("old_password", oldPassword);
            jsonObject.put("new_password", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        // Retrieve token from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        if (authToken == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create request body with JSON content
        RequestBody body = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json; charset=utf-8"));

        // Build the request with PUT method and Authorization header
        Request request = new Request.Builder()
                .url("https://boxgateway.kozow.com/change_password")
                .put(body) // Use PUT method as required
                .addHeader("Authorization", "Bearer " + authToken)
                .build();

        // Make asynchronous API call
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(ChangePasswordActivity.this, "Connection error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ChangePasswordActivity.this, SettingActivity.class));
                        finish();
                    });
                } else {
                    String errorBody = response.body() != null ? response.body().string() : "Unknown error";
                    runOnUiThread(() -> Toast.makeText(ChangePasswordActivity.this, "Error: " + response.code() + " - " + errorBody, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
