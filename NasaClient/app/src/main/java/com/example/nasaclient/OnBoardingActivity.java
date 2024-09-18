package com.example.nasaclient;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private List<View> dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding01);

        viewPager = findViewById(R.id.viewPager);
        dots = Arrays.asList(
                findViewById(R.id.dot1),
                findViewById(R.id.dot2),
                findViewById(R.id.dot3)
        );

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.onboarding_img1);
        images.add(R.drawable.onboarding_img2);
        images.add(R.drawable.onboarding_img3);

        SliderAdapter adapter = new SliderAdapter(images);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateDots(position);
            }
        });
    }

    private void updateDots(int position) {
        for (int i = 0; i < dots.size(); i++) {
            dots.get(i).setBackgroundColor(
                    getResources().getColor(i == position ? android.R.color.black : android.R.color.darker_gray, getTheme())
            );
        }
    }
}