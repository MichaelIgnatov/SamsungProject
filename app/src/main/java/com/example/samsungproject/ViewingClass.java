package com.example.samsungproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ViewingClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing_class);
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
