package com.example.samsungproject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class Student {

    public static class StudentData {
        public int id;
        public String name;
        public String login;
        public String password;
        public String birth_date;
        public String create_date;
        public int teacher_id;
        public String avatar;
    }

    public static class ExamData {
        public int id;
        public String subject;
        public String mark;
        public int student_id;
    }

    public static class Exams {
        ArrayList<ExamData> exams;
    }

    public static class StudentPortfolio {
        int id;
        String name;
        String subject;
        String file_uuid;
        String level;
        String result;
        String date;
        String created_at;
        int student_id;
    }

    public static class PortfolioAPIFormat {
        StudentData student;
        ArrayList<StudentPortfolio> portfolio;
    }

    public static class PortfolioData {}
    public interface StudentService {
        @GET("/api/get_student/{login}/{password}")
        Call<StudentData> studentLogin(@Path("login") String login, @Path("password") String password);

        @GET("/api/profile/{student_id}")
        Call<PortfolioAPIFormat> profileData(@Path("student_id") int student_id, @Header("Cookie") String cookie);

        @GET("/api/get_exams/{student_id}")
        Call<Exams> examsData(@Path("student_id") int id, @Header("Cookie") String cookie);

        @POST("/api/add_portfolio")
        Call<StudentPortfolio> addPortfolio(@Body StudentPortfolio studentPortfolio, @Header("Cookie") String cookie);

        @POST("/api/delete_student/{student_id}")
        Call<?> deleteStudent();
    }
}
