package com.example.samsungproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    public static final String STUDENT_NAME = "STUDENT_NAME";
    public static final String STUDENT_ID = "STUDENT_ID";
    public static final String STUDENT_AVATAR = "STUDENT_AVATAR";

    public static Student.StudentData studentData;
    public static String headerInfo;
    EditText studentPasswordTextedit;
    EditText studentLoginTextedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        studentLoginTextedit = findViewById(R.id.student_login);
        studentPasswordTextedit = findViewById(R.id.student_password);
    }

    public void backToActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void studentLoginClick(View view) {
        String login = studentLoginTextedit.getText().toString();
        String password = studentPasswordTextedit.getText().toString();

        String serverURl = Student.serverURl;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(serverURl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Student.StudentService studentService = retrofit.create(Student.StudentService.class);

        Call<Student.StudentData> call = studentService.studentLogin(login, password);
        call.enqueue(new Callback<Student.StudentData>() {
            @Override
            public void onResponse(Call<Student.StudentData> call, Response<Student.StudentData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getApplicationContext(), "Вход выполнен", Toast.LENGTH_SHORT).show();
                    headerInfo = response.headers().get("Set-cookie");
                    studentData = response.body();
                    Log.i("!!!!", studentData.id + "");
                    Intent intent = new Intent(getApplicationContext(), StudentProfile.class);
                    intent.putExtra(STUDENT_NAME, response.body().name);
                    intent.putExtra(STUDENT_ID, response.body().id);
                    intent.putExtra(STUDENT_AVATAR, response.body().avatar);
                    startActivity(intent);
                    //finish();
                } else{
                    Toast.makeText(getApplicationContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Student.StudentData> call, Throwable t) {

            }
        });
    }
}
