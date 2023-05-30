package com.example.samsungproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class    TeacherLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        EditText teacherLoginEditText = findViewById(R.id.teacher_login);
        EditText teacherPasswordEditText = findViewById(R.id.teacher_password);
        Button teacherAuthorizationButton = findViewById(R.id.login);
        Button backButton = findViewById(R.id.back);
    }

    public void backToActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void teacherLogin(View view) {
        Intent intent = new Intent(this, TeacherProfile.class);
        startActivity(intent);
    }
}
