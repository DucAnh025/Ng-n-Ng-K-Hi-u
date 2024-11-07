package com.example.nasaclient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nasaclient.databinding.ActivitySignupBinding;
import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestQueue = Volley.newRequestQueue(this);

        binding.btnSignUp.setOnClickListener(v -> registerUser());

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

        // Input validation
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }


        String url = "https://boxgateway.kozow.com/register";  // Correct API endpoint

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("full_name", fullName);
            requestBody.put("email", email);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace(); // Handle JSON exception
            Toast.makeText(this, "Error creating request", Toast.LENGTH_SHORT).show();
            return; // Stop further execution if JSON creation fails
        }



        Log.d("SignupRequest", requestBody.toString());  // Log the request body for debugging

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> {
                    Log.d("SignupResponse", response.toString()); // Log the response
                    try {
                        // Check 'status' inside the response
                        if (response.has("status") && response.getBoolean("status")) { // Check if "status" key exists and its value

                            Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            // Navigate to login after successful signup
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            finish(); // Finish signup activity
                        } else {

                            String errorMessage = response.has("message") ? response.getString("message") : "Registration failed";
                            Toast.makeText(SignupActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SignupActivity.this, "Failed to parse response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("SignupError", error.toString());
                    if (error.networkResponse != null) {
                        Log.e("SignupError", "Status Code: " + error.networkResponse.statusCode);

                        if(error.networkResponse.data != null) {
                            String errorBody = new String(error.networkResponse.data);
                            Log.e("SignupError", "Error Body: " + errorBody);

                            try {
                                JSONObject errorJson = new JSONObject(errorBody);
                                if (errorJson.has("message")) {
                                    Toast.makeText(SignupActivity.this, errorJson.getString("message"), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignupActivity.this, "Registration Failed. Check your network and try again later." + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(SignupActivity.this, "Error parsing error response: " + errorBody, Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            Toast.makeText(SignupActivity.this, "Registration failed. Server did not return an error message.", Toast.LENGTH_SHORT).show();
                        }




                    } else {

                        Toast.makeText(SignupActivity.this, "Registration failed. Check your network and try again later.", Toast.LENGTH_SHORT).show();

                    }

                }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }


        };


        requestQueue.add(jsonObjectRequest);
    }
}