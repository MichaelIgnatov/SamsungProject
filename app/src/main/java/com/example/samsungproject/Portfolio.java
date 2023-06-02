package com.example.samsungproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Portfolio extends AppCompatActivity {
    String studentId;
    public static Student.StudentPortfolio currentPortfolio;
    ArrayList<Student.StudentPortfolio> studentPortfolioList;
    ListView listView;
    ArrayList<String> portfolioLists;
    String serverURl = "https://6824-178-65-47-77.ngrok-free.app/";
    String[] activityMenu = {"Профиль", "Портфолио", "Результаты ПА", "Выход"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        studentId = Integer.toString(StudentLoginActivity.studentData.id);
        listView = findViewById(R.id.portfolio_list);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(serverURl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Student.StudentService studentService = retrofit.create(Student.StudentService.class);

        Call<Student.PortfolioAPIFormat> call = studentService.profileData(studentId,
                StudentLoginActivity.headerInfo);
        call.enqueue(new Callback<Student.PortfolioAPIFormat>() {
            @Override
            public void onResponse(Call<Student.PortfolioAPIFormat> call,
                                   Response<Student.PortfolioAPIFormat> response) {
                if (response.isSuccessful() && response.body() != null) {
                    studentPortfolioList = response.body().arrayList;
                    for(int i = 0; i < studentPortfolioList.size(); i++) {
                        portfolioLists.add(i, studentPortfolioList.get(i).name);
                    }
                    ArrayAdapter<String> portfolioListAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.activity_portfolio, portfolioLists);

                    listView.setAdapter(portfolioListAdapter);
                }
            }

            @Override
            public void onFailure(Call<Student.PortfolioAPIFormat> call, Throwable t) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PortfolioElem.class);
                currentPortfolio = studentPortfolioList.get(position);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, activityMenu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.student_menu);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Title");
        spinner.setSelection(1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    openStudentProfile();
                }
                if(position == 3) {
                    exitProfile();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void openStudentProfile() {
        Intent intent = new Intent(this, StudentProfile.class);
        startActivity(intent);
    }

    public void exitProfile() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openNewElemPortfolio(View view) {
        Intent intent = new Intent(this, NewPortfolioElement.class);
        startActivity(intent);
    }
}
