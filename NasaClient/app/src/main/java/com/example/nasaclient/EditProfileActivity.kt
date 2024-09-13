package com.example.nasaclient

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nasaclient.ui.theme.NasaClientTheme

class EditProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Lấy nút Sign In từ layout
        val editProfileButton = findViewById<Button>(R.id.btn_save_changes)

        // Set sự kiện click cho nút Sign In
        editProfileButton.setOnClickListener {
            // Tạo intent để điều hướng đến EditProfileActivity
            val intent = Intent(this@EditProfileActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
