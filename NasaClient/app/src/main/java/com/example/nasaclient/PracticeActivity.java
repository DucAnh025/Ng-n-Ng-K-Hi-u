package com.example.nasaclient;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.nasaclient.databinding.ActivityWithNavBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class PracticeActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 100;
    private ActivityWithNavBinding binding;
    private PreviewView cameraPreview;
    private ImageCapture imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize binding with navigation layout
        binding = ActivityWithNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inflate the PracticeActivity layout into the container
        getLayoutInflater().inflate(R.layout.activity_practice, binding.container, true);

        // Setup bottom navigation
        binding.bottomNavigationView.setSelectedItemId(R.id.practice);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (itemId == R.id.planet) {
                startActivity(new Intent(this, PlanetActivity.class));
                return true;
            } else if (itemId == R.id.practice) {
                startActivity(new Intent(this, PracticeActivity.class));
                return true;
            } else if (itemId == R.id.setting) {
                startActivity(new Intent(this, SettingActivity.class));
                return true;
            }
            return false;
        });

        // Setup camera functionality
        cameraPreview = findViewById(R.id.cameraPreview);
        ImageView btnCapture = findViewById(R.id.btnCapture);
        ImageView btnFlash = findViewById(R.id.btnFlash);

        // Check and request camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            requestCameraPermission();
        }

        // Capture button click handler
        btnCapture.setOnClickListener(v -> capturePhoto());

        // Flash button click handler (placeholder)
        btnFlash.setOnClickListener(v -> {
            Toast.makeText(this, "Flash button clicked!", Toast.LENGTH_SHORT).show();
        });
    }

    // Request camera permission
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_CODE
        );
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Camera permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Start CameraX
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                // Configure the preview
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                androidx.camera.core.Preview preview = new androidx.camera.core.Preview.Builder().build();
                preview.setSurfaceProvider(cameraPreview.getSurfaceProvider());

                // Configure ImageCapture
                imageCapture = new ImageCapture.Builder().build();

                // Bind lifecycle to CameraX
                cameraProvider.unbindAll();
                Camera camera = cameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview,
                        imageCapture
                );

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    // Capture photo and save to internal storage
    private void capturePhoto() {
        if (imageCapture == null) return;

        // Configure the file where the photo will be saved
        File photoFile = new File(getExternalFilesDir(null), "photo.jpg");
        ImageCapture.OutputFileOptions options =
                new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        // Capture photo
        imageCapture.takePicture(
                options,
                ContextCompat.getMainExecutor(this), // Executor to handle callback on UI thread
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        // Photo saved successfully
                        Toast.makeText(
                                PracticeActivity.this,
                                "Photo saved: " + photoFile.getAbsolutePath(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        // Handle error while saving photo
                        Toast.makeText(
                                PracticeActivity.this,
                                "Error saving photo: " + exception.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                        exception.printStackTrace();
                    }
                }
        );
    }
}
