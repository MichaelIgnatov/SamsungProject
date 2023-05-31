package com.example.samsungproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button teacherLoginButton = findViewById(R.id.login_teacher_btn);
        Button studentLoginButton = findViewById(R.id.login_student_btn);
        Button userRegistrationButton = findViewById(R.id.user_registration_btn);
    }
    public void teacherLogin(View view) {
        Intent intent = new Intent(this, TeacherLoginActivity.class);
        startActivity(intent);
    }

    public void studentLogin(View view) {
        Intent intent = new Intent(this, StudentLoginActivity.class);
        startActivity(intent);
    }

    public void userRegistration(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}