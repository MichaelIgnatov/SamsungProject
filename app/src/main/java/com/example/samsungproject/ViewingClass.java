package com.example.samsungproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewingClass extends AppCompatActivity {
    String serverURl = "https://4b33-178-65-47-77.ngrok-free.app/";
    int teacherId;
    ListView studentList;
    ArrayList<Student.StudentData> studentsList;
    ArrayList<String> stringClassesList;
    ArrayAdapter<String> studentsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing_class);
        teacherId = TeacherLoginActivity.teacherData.id;
        studentList = findViewById(R.id.list_view);
        studentsList = new ArrayList<Student.StudentData>();
        stringClassesList = new ArrayList<String>();

        studentsListAdapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_list_item_1,
                stringClassesList);
        studentList.setAdapter(studentsListAdapter);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(serverURl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Teacher.TeacherService teacherService = retrofit.create(Teacher.TeacherService.class);

        Call<Teacher.GroupStudentsList> call = teacherService.getGroupStudentsData(teacherId,
                TeacherLoginActivity.headerInfo);
        call.enqueue(new Callback<Teacher.GroupStudentsList>() {
            @Override
            public void onResponse(Call<Teacher.GroupStudentsList> call,
                                   Response<Teacher.GroupStudentsList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String headerInfo = response.headers().get("Set-cookie");
                    studentsList = response.body().students;
                    if(studentsList != null) {
                        for (int i = 0; i < studentsList.size(); i++) {
                            stringClassesList.add(i, studentsList.get(i).name);
                        }
                        studentsListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<Teacher.GroupStudentsList> call, Throwable t) {

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

        EditText studentName = findViewById(R.id.new_student_name);
        Button addStudentBtn = dialog.findViewById(R.id.add_student_btn);
        addStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Teacher.NewStudentData newStudentData = new Teacher.NewStudentData();
                newStudentData.student_name = studentName.getText().toString();
                newStudentData.login = studentName.getText().toString();
                newStudentData.student_content = "content";
                newStudentData.password = "123qwe";
                newStudentData.teacher_id = teacherId;
                Retrofit retrofit = new Retrofit.Builder().baseUrl(serverURl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Teacher.TeacherService teacherService = retrofit.create(Teacher.TeacherService.class);

                Call<Teacher.NewStudentData> call = teacherService.postAddStudent(newStudentData,
                        TeacherLoginActivity.headerInfo);
                call.enqueue(new Callback<Teacher.NewStudentData>() {
                    @Override
                    public void onResponse(Call<Teacher.NewStudentData> call, Response<Teacher.NewStudentData> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(getApplicationContext(), "Ученик добавлен", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Teacher.NewStudentData> call, Throwable t) {

                    }
                });
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

        stringClassesList.add(studentName.getText().toString());
        studentsListAdapter.notifyDataSetChanged();
    }
}
