package com.example.nasaclient

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nasaclient.ui.theme.NasaClientTheme

class NewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        // Lấy nút Sign In từ layout
        val newsButton = findViewById<Button>(R.id.btnGoToNews)

        // Set sự kiện click cho nút Sign In
        newsButton.setOnClickListener {
            // Tạo intent để điều hướng đến EditProfileActivity
            val intent = Intent(this@NewsActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
