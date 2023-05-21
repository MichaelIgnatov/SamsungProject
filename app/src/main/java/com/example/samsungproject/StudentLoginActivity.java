package com.example.samsungproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class StudentLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        EditText studentLoginTextedit = findViewById(R.id.student_login);
        EditText studentPasswordTextedit = findViewById(R.id.student_password);
        Button studentLoginButton = findViewById(R.id.login);
        Button backButton = findViewById(R.id.back);
    }

    public void backToActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void studentLogin(View view) {
        Intent intent = new Intent(this, StudentProfile.class);
        startActivity(intent);
    }
}
