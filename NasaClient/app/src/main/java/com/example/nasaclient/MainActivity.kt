package com.example.nasaclient

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nasaclient.ui.theme.NasaClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NasaClientTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    Greeting(name = "Mate")

                    // Button to open LoginActivity
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }) {
                        Text("Go to Login")
                    }

                    // Button to open EditProfileActivity
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, EditProfileActivity::class.java)
                        startActivity(intent)
                    }) {
                        Text("Go to Edit Profile")
                    }
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, NewsActivity::class.java)
                        startActivity(intent)
                    }) {
                        Text("Go to News")
                    }
                    Button(onClick = {
                        val intent = Intent(this@MainActivity, SettingActivity::class.java)
                        startActivity(intent)
                    }) {
                        Text("Go to Setting")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Pick the page u wanna see $name!!!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NasaClientTheme {
        Greeting("Android")
    }
}
