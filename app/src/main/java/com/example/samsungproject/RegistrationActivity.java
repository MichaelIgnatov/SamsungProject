package com.example.samsungproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EditText userNameTextEdit = findViewById(R.id.user_name);
        EditText userLoginTextEdit = findViewById(R.id.user_login);
        EditText userPasswordTextEdit = findViewById(R.id.user_password);
        EditText userPositionTextEdit = findViewById(R.id.user_position);
        Button userRegistrationButton = findViewById(R.id.register_btn);
        Button backButton = findViewById(R.id.back);

    }

    public void backToActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}