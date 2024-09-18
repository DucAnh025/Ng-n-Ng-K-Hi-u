package com.example.nasaclient;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PlanetInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_info);

        String planetName = getIntent().getStringExtra("PLANET_NAME");
        int planetImage = getIntent().getIntExtra("PLANET_IMAGE", 0);
        String planetInfo = getIntent().getStringExtra("PLANET_INFO");

        TextView nameTextView = findViewById(R.id.planetNameTextView);
        ImageView imageView = findViewById(R.id.planetImageView);
        TextView infoTextView = findViewById(R.id.planetInfoTextView);

        nameTextView.setText(planetName);
        imageView.setImageResource(planetImage);
        infoTextView.setText(planetInfo);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}