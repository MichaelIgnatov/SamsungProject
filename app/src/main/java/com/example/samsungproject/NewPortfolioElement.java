package com.example.samsungproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewPortfolioElement extends AppCompatActivity {

    String[] eventLevelList = {"Школьный", "Муниципальный", "Региональный",
            "Всероссийский", "Международный"};
    String[] resultsList = {"Победитель", "Участник", "Призёр", "1 место", "2 место", "3 место"};
    ActivityResultLauncher<Intent> resultLauncher;
    String serverURl = "https://b991-178-65-47-77.ngrok-free.app/";
    EditText portfolioTitle;
    EditText portfolioSubject;
    Spinner spinner;
    Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_elem_portfolio);

        portfolioTitle = findViewById(R.id.title);
        portfolioSubject = findViewById(R.id.subject);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, eventLevelList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = findViewById(R.id.event_level_list);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Title");
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, resultsList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2 = findViewById(R.id.results_list);
        spinner2.setAdapter(adapter2);
        spinner2.setPrompt("Title");
        spinner2.setSelection(0);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts
                        .StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        // проверьте состояние
                        if (data != null) {
                            // Получить uri PDF
                            Uri sUri = data.getData();
                            //  Получить путь PDF
                            String sPath = sUri.getPath();
                        }
                    }
                });
    }


    public void fileSelection(View view){
        if (ActivityCompat.checkSelfPermission(NewPortfolioElement.this, Manifest.permission
                .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    NewPortfolioElement.this, new String[] {
                            Manifest.permission.READ_EXTERNAL_STORAGE },
                                    1);
                        }
                        else {
                            // Когда разрешение будет предоставлено
                            // Вызвать метод
                            selectPDF();
                        }
    }

    private void selectPDF()
    {
        Intent intent
                = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/ /pdf");
        resultLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);

        // проверка состояния
        if (requestCode == 1 && grantResults.length > 0
                && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {
            // Когда разрешение будет предоставлено
            // Вызов метда
            selectPDF();
            Toast
                    .makeText(getApplicationContext(),
                            "Permission Denied",
                            Toast.LENGTH_SHORT)
                    .show();
        }
        else {
            // Когда в разрешении отказано
            // Отобразить тост
            Toast
                    .makeText(getApplicationContext(),
                            "Permission Denied",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void backToActivity(View view) {
        Intent intent = new Intent(this, Portfolio.class);
        startActivity(intent);
    }

    public void addNewElemPortfolio(View view) {
        Student.StudentPortfolio newPortfolio = new Student.StudentPortfolio();
        newPortfolio.name = portfolioTitle.getText().toString();
        newPortfolio.subject = portfolioSubject.getText().toString();
        newPortfolio.level = spinner.getPrompt().toString();
        newPortfolio.result = spinner2.getPrompt().toString();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(serverURl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Student.StudentService studentService = retrofit.create(Student.StudentService.class);

        Call<Student.StudentPortfolio> call = studentService.addPortfolio(newPortfolio, serverURl);
        call.enqueue(new Callback<Student.StudentPortfolio>() {
            @Override
            public void onResponse(Call<Student.StudentPortfolio> call, Response<Student.StudentPortfolio> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getApplicationContext(), "Портфолио добавленно", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Student.StudentPortfolio> call, Throwable t) {

            }
        });



        Intent intent = new Intent(this, Portfolio.class);
        startActivity(intent);
    }
}
