package com.example.nasaclient

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nasaclient.ui.theme.NasaClientTheme

class SettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        // Lấy nút Sign In từ layout
        val SettingButton = findViewById<Button>(R.id.btnSetting)

        // Set sự kiện click cho nút Sign In
        SettingButton.setOnClickListener {
            // Tạo intent để điều hướng đến EditProfileActivity
            val intent = Intent(this@SettingActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
