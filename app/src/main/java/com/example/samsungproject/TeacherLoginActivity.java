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

public class    TeacherLoginActivity extends AppCompatActivity {

    EditText teacherPasswordEditText;
    EditText teacherLoginEditText;
    public static String headerInfo;
    public static Teacher.TeacherData teacherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        teacherLoginEditText = findViewById(R.id.teacher_login);
        teacherPasswordEditText = findViewById(R.id.teacher_password);
    }

    public void backToActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void teacherLogin(View view) {
        String login = teacherLoginEditText.getText().toString();
        String password = teacherPasswordEditText.getText().toString();

        String serverURl = "https://b991-178-65-47-77.ngrok-free.app/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(serverURl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Teacher.TeacherService teacherService = retrofit.create(Teacher.TeacherService.class);

        Call<Teacher.TeacherData> call = teacherService.teacherLogin(login, password);
        call.enqueue(new Callback<Teacher.TeacherData>() {
            @Override
            public void onResponse(Call<Teacher.TeacherData> call, Response<Teacher.TeacherData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getApplicationContext(), "Вход выполнен", Toast.LENGTH_SHORT).show();
                    headerInfo = response.headers().get("Set-cookie");
                    teacherData = response.body();
                    Intent intent = new Intent(getApplicationContext(), TeacherProfile.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Teacher.TeacherData> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
