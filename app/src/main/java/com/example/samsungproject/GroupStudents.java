package com.example.samsungproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GroupStudents extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_students);
    }

    public void backActivity(View view) {
        Intent intent = new Intent(this, ClassesList.class);
        startActivity(intent);
    }
}
