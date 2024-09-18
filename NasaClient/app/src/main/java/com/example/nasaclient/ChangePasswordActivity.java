package com.example.nasaclient;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;
    private Button saveChangesButton;
    private Button backButton;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // Initialize UI elements
        newPasswordEditText = findViewById(R.id.newPassword);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        saveChangesButton = findViewById(R.id.btn_save_changes);
        backButton = findViewById(R.id.button);
        titleTextView = findViewById(R.id.titleText);

        // Set up event listeners
        saveChangesButton.setOnClickListener(v -> handleSaveChanges());
        backButton.setOnClickListener(v -> finish()); // Close the activity when back button is pressed
    }

    private void handleSaveChanges() {
        String newPassword = newPasswordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            // Show error message if any field is empty
            // You can use Toast or Snackbar here
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            // Show error message if passwords do not match
            // You can use Toast or Snackbar here
            return;
        }

        // Handle the password change logic here
        // For example, send the new password to your server or save it locally
    }
}