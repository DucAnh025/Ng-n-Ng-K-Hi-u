package com.example.nasaclient;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int READ_EXTERNAL_STORAGE_REQUEST = 2;
    private ImageView profileImage;
    private Uri selectedImageUri;
    private EditText fullNameEditText;
    private EditText usernameEditText;
    private EditText phoneNumberEditText;
    private EditText mailEditText;
    private String authToken; // Store the auth token

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    openImagePicker();
                } else {
                    Toast.makeText(this, "Permission denied.  Cannot select image.", Toast.LENGTH_SHORT).show();
                }
            });

    private final ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        selectedImageUri = data.getData();
                        try {
                            // Use Glide to load and display the selected image
                            Glide.with(EditProfileActivity.this)
                                    .load(selectedImageUri)
                                    .apply(RequestOptions.circleCropTransform())
                                    .placeholder(R.drawable.ic_profile_placeholder)
                                    .error(R.drawable.ic_profile_placeholder)
                                    .skipMemoryCache(true)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .into(profileImage);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                            Toast.makeText(EditProfileActivity.this, "Error loading image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Retrieve token from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        authToken = sharedPreferences.getString("auth_token", null);

        if (authToken == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        profileImage = findViewById(R.id.profile_image);
        fullNameEditText = findViewById(R.id.et_full_name);
        usernameEditText = findViewById(R.id.et_username);
        phoneNumberEditText = findViewById(R.id.et_phone_number);
        mailEditText = findViewById(R.id.et_email);

        ImageButton backButton = findViewById(R.id.backButton);
        Button saveChangesButton = findViewById(R.id.btn_save_changes);


        // Fetch and display user info on create
        getUserInfo();

        profileImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            } else {
                openImagePicker();
            }
        });


        backButton.setOnClickListener(v -> {
            finish();
        });


        saveChangesButton.setOnClickListener(v -> updateUserInfo());
    }


    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK); // No need to specify URI
        intent.setType("image/*"); // Allow any image type
        pickImageLauncher.launch(intent);
    }


    private void getUserInfo() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://boxgateway.kozow.com/get_user_info")
                .header("Authorization", "Bearer " + authToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(EditProfileActivity.this, "Failed to fetch user info", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject json = new JSONObject(responseBody);
                        final String fullName = json.getString("full_name");
                        final String username = json.getString("username");
                        final String phoneNumber = json.getString("phone_number");
                        final String imageUrl = json.getString("image_url");
                        final String mail = json.getString("email");

                        runOnUiThread(() -> {
                            fullNameEditText.setText(fullName);
                            usernameEditText.setText(username);
                            phoneNumberEditText.setText(phoneNumber);
                            mailEditText.setText(mail);

                            // ... (set other fields)
                            Glide.with(EditProfileActivity.this)
                                    .load(imageUrl)
                                    .apply(RequestOptions.circleCropTransform())
                                    .placeholder(R.drawable.ic_profile_placeholder)
                                    .error(R.drawable.ic_profile_placeholder)
                                    .skipMemoryCache(true) // Skip memory cache
                                    .diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk cache
                                    .into(profileImage);
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(EditProfileActivity.this, "Failed to parse user info", Toast.LENGTH_SHORT).show());

                    }


                } else {

                    runOnUiThread(() -> Toast.makeText(EditProfileActivity.this, "Failed to fetch user info" + response.code(), Toast.LENGTH_SHORT).show());

                    Log.e("API Call", "getUserInfo Request failed with code: " + response.code());
                }


            }
        });
    }





    private void updateUserInfo() {
        String fullName = fullNameEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();

        OkHttpClient client = new OkHttpClient();

        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("full_name", fullName)
                .addFormDataPart("username", username)
                .addFormDataPart("phone_number", phoneNumber);


        if (selectedImageUri != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                byte[] imageBytes = new byte[inputStream.available()];
                inputStream.read(imageBytes);

                requestBodyBuilder.addFormDataPart("image", "profile.jpg",
                        RequestBody.create(MediaType.parse("image/jpeg"), imageBytes));

            } catch (IOException e) {
                e.printStackTrace();
                // Handle the error, e.g., show a toast message
            }


        }


        RequestBody requestBody = requestBodyBuilder.build();


        Request request = new Request.Builder()
                .url("https://boxgateway.kozow.com/change_user_info")
                .header("Authorization", "Bearer " + authToken)
                .put(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(EditProfileActivity.this, "Failed to update user info", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();

                runOnUiThread(() -> {
                    try {
                        if (response.isSuccessful()) {
                            Toast.makeText(EditProfileActivity.this, "User info updated successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditProfileActivity.this, SettingActivity.class);
                            startActivity(intent);
                        } else {
                            // Parse the error response
                            JSONObject errorJson = new JSONObject(responseBody);
                            String errorMessage = errorJson.optString("message", "Unknown error occurred");

                                // Show generic error message for other errors
                                Toast.makeText(EditProfileActivity.this,
                                        "Failed to update user info: " + errorMessage,
                                        Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(EditProfileActivity.this,
                                "Failed to update user info: " + response.code(),
                                Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                // Handle permission denial
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}