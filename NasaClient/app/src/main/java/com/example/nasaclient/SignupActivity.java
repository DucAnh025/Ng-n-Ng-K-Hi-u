package com.example.nasaclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nasaclient.databinding.ActivitySignupBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        // Check for existing auth token before setting content view
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("auth_token", null);

        // If auth token exists, redirect to MainActivity
        if (authToken != null) {
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close LoginActivity so user can't go back to it
            return; // Exit onCreate to prevent setting up login UI
        }
        setContentView(binding.getRoot());

        // Initialize OkHttpClient
        client = new OkHttpClient();

        // Set up sign-up button click listener
        binding.btnSignUp.setOnClickListener(v -> registerUser());

        // Set up login text click listener to navigate to login screen
        binding.tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser() {
        String fullName = binding.etFullName.getText().toString();
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();
        String confirmPassword = binding.etConfirmPassword.getText().toString();

        // Validate input
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "Password must be at least 8 characters and contain one uppercase letter.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set API endpoint
        String url = "https://boxgateway.kozow.com/register";

        // Create JSON body for the request
        JSONObject requestBodyJson = new JSONObject();
        try {
            requestBodyJson.put("full_name", fullName);
            requestBodyJson.put("email", email);
            requestBodyJson.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating request", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set JSON media type
        RequestBody body = RequestBody.create(requestBodyJson.toString(), MediaType.parse("application/json; charset=utf-8"));

        // Build the request
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // Make asynchronous call
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("SignupError", "Network request failed: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(SignupActivity.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Parse response JSON
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);

                        // Check if signup was successful based on status in JSON
                        if (jsonResponse.has("status") && jsonResponse.getBoolean("status")) {
                            runOnUiThread(() -> {
                                Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                finish();
                            });
                        } else {
                            String errorMessage = jsonResponse.has("message") ? jsonResponse.getString("message") : "Registration failed";
                            runOnUiThread(() -> Toast.makeText(SignupActivity.this, errorMessage, Toast.LENGTH_SHORT).show());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(SignupActivity.this, "Failed to parse response", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    // Handle error response
                    String errorBody = response.body() != null ? response.body().string() : "Unknown error";
                    Log.e("SignupError", "Response unsuccessful: " + response.code() + " - " + errorBody);
                    runOnUiThread(() -> Toast.makeText(SignupActivity.this, "Error: " + response.code() + ". Please try again.", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";
        return password.matches(passwordRegex);
    }
}
