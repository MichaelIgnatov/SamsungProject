package com.example.samsungproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewingClass extends AppCompatActivity {
    String serverURl = "https://6784-178-65-47-77.ngrok-free.app/";
    String teacherId;
    ListView studentList;
    ArrayList<Student.StudentData> classes;
    ArrayList<String> stringClassesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing_class);

        studentList = findViewById(R.id.list_view);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(serverURl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Teacher.TeacherService teacherService = retrofit.create(Teacher.TeacherService.class);

        Call<Teacher.Classes> call = teacherService.getClassesData(teacherId);
        call.enqueue(new Callback<Teacher.Classes>() {
            @Override
            public void onResponse(Call<Teacher.Classes> call, Response<Teacher.Classes> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String headerInfo = response.headers().get("Set-cookie");
                    classes = response.body().students;
                    for(int i = 0; i < classes.size(); i++) {
                        stringClassesList.add(i, classes.get(i).name);
                    }
                    ArrayAdapter<String> studentsListAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.activity_viewing_class,
                            stringClassesList);

                    studentList.setAdapter(studentsListAdapter);
                }
            }

            @Override
            public void onFailure(Call<Teacher.Classes> call, Throwable t) {

            }
        });

        studentList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ViewingStudentsProfile.class);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void backActivity(View view) {
        Intent intent = new Intent(this, ClassesList.class);
        startActivity(intent);
    }

    public void openAddStudentDialog(View view) {
        final Dialog dialog = new Dialog(ViewingClass.this);
        dialog.setContentView(R.layout.add_student_dialog);

        Button addStudentBtn = dialog.findViewById(R.id.add_student_btn);
        addStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button cancelBtn = dialog.findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }
}
