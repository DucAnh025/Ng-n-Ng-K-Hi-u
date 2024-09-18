package com.example.nasaclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.ComponentActivity;

public class ChangePasswordActivity extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Button btn_save_changes_pass = findViewById(R.id.btn_save_changes);
        btn_save_changes_pass.setOnClickListener(v -> {
            Intent intent = new Intent(ChangePasswordActivity.this,SettingActivity.class);
            startActivity(intent);
        });
        Button btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(ChangePasswordActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
    }

}
