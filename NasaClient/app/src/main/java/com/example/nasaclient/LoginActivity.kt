package com.example.nasaclient

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nasaclient.ui.theme.NasaClientTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Đặt layout cho Activity

        // Lấy nút Sign In từ layout
        val signInButton = findViewById<Button>(R.id.btnSignIn)

        // Set sự kiện click cho nút Sign In
        signInButton.setOnClickListener {
            // Tạo intent để điều hướng đến EditProfileActivity
            val intent = Intent(this@LoginActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
