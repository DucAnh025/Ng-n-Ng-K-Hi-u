package com.example.nasaclient;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup); // Đặt layout cho Activity

        // Lấy các View từ layout
        EditText emailEditText = findViewById(R.id.etEmail);
        EditText passwordEditText = findViewById(R.id.etPassword);
        EditText confirmPasswordEditText = findViewById(R.id.etConfirmPassword);
        Button signUpButton = findViewById(R.id.btnSignUp);
        TextView alreadyHaveAccountText = findViewById(R.id.tvSignUp);

        // Đặt hành động cho nút Đăng ký
        signUpButton.setOnClickListener(v -> {
            // Xử lý hành động Đăng ký tại đây
        });

        // Đặt hành động cho nút Đăng nhập (nếu cần)
        alreadyHaveAccountText.setOnClickListener(v -> {
            // Chuyển đến LoginActivity hoặc thực hiện hành động khác
        });
    }
}
