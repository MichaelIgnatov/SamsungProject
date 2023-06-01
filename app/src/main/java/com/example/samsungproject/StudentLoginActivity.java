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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentLoginActivity extends AppCompatActivity {
    Student studentObj;
    boolean flag = false;
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
        String login = studentLoginTextedit.getText().toString();
        String password = studentPasswordTextedit.getText().toString();

        String serverURl = "https://6784-178-65-47-77.ngrok-free.app/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(serverURl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Student.StudentService studentService = retrofit.create(Student.StudentService.class);

        Call<Student.StudentData> call = studentService.studentLogin(login, password);
        call.enqueue(new Callback<Student.StudentData>() {
            @Override
            public void onResponse(Call<Student.StudentData> call, Response<Student.StudentData> responseStudentLogin) {
                if (responseStudentLogin.isSuccessful() && responseStudentLogin.body() != null) {
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
