package com.example.samsungproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentLoginActivity extends AppCompatActivity {
    Student studentObj;
    String login;
    String password;
    boolean flag;
    EditText studentPasswordTextedit;
    EditText studentLoginTextedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        studentLoginTextedit = findViewById(R.id.student_login);
        studentPasswordTextedit = findViewById(R.id.student_password);
        studentObj = new Student();
    }

    public void backToActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void studentLoginClick(View view) {
        //login = studentLoginTextedit.getText().toString();
        //password = studentPasswordTextedit.getText().toString();
        flag = false;
        Call<Student.StudentData> call = studentObj.studentService.studentLogin("Qwe", "123");
        call.enqueue(new Callback<Student.StudentData>() {
            @Override
            public void onResponse(Call<Student.StudentData> call, Response<Student.StudentData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getApplicationContext(), "Вход выполнен", Toast.LENGTH_SHORT).show();
                    flag = true;
                } else{
                    Toast.makeText(getApplicationContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Student.StudentData> call, Throwable t) {

            }
        });

        if(flag == true) {
            Intent intent = new Intent(this, StudentProfile.class);
            startActivity(intent);
        }
    }
}
