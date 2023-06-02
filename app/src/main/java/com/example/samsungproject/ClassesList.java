package com.example.samsungproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClassesList extends AppCompatActivity {

    int teacherId;
    String serverURl = "https://6824-178-65-47-77.ngrok-free.app/";
    String[] activityMenu = {"Профиль", "Классы", "Выход"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_list);

        ListView groupList = findViewById(R.id.group_list);
        teacherId = TeacherLoginActivity.teacherData.id;

        Retrofit retrofit = new Retrofit.Builder().baseUrl(serverURl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Teacher.TeacherService teacherService = retrofit.create(Teacher.TeacherService.class);


        // ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                //android.R.layout.simple_list_item_1, );

        //groupList.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, activityMenu);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.teacher_menu);
        spinner.setAdapter(adapter2);
        spinner.setPrompt("Title");
        spinner.setSelection(1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    openProfileTeacher();
                }
                if(position == 1) {
                    openViewingClass();
                }
                if(position == 2) {
                    exitProfile();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void openProfileTeacher() {
        Intent intent = new Intent(this, TeacherProfile.class);
        startActivity(intent);
    }

    public void openViewingClass() {
        Intent intent = new Intent(this, ViewingClass.class);

        startActivity(intent);
    }

    public void exitProfile() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openAddClassDialog(View view) {
        final Dialog dialog = new Dialog(ClassesList.this);
        dialog.setContentView(R.layout.add_class_dialog);

        Button addClassBtn = dialog.findViewById(R.id.add_class);
        addClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button cancelBtn = dialog.findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }
}
