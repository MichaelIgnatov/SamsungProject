package com.example.samsungproject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class Student {
    public static final String serverURl = "http://192.168.0.11:80";
    public Retrofit retrofit;
    public StudentService studentService;

    public static class StudentData {
        public int id;
        public String name;
        public String login;
        public String password;
        public String birth_date;
        public String create_date;
        public int teacher_id;
        public byte avatar;
    }
    public interface StudentService {
        @GET("/api/get_student/{login}/{password}")
        Call<StudentData> studentLogin(@Path("login") String login, @Path("password") String password);
    }

    public void Student() {
        retrofit = new Retrofit.Builder().baseUrl(serverURl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        studentService = retrofit.create(StudentService.class);
    }
}
