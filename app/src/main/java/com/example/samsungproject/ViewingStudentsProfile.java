package com.example.samsungproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ViewingStudentsProfile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing_students_profile);
    }

    public void backActivity(View view) {
        Intent intent = new Intent(this, ViewingClass.class);
        startActivity(intent);
    }
}
