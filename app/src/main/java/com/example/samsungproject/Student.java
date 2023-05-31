package com.example.samsungproject;

import retrofit2.Call;
import retrofit2.http.GET;
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
        public byte avatar;
    }

    public static class ExamData {
        public int id;
        public String subject;
        public String mark;
        public int student_id;
    }

    public static class PortfolioData {}
    public interface StudentService {
        @GET("/api/get_student/{login}/{password}")
        Call<StudentData> studentLogin(@Path("login") String login, @Path("password") String password);

        @GET("/api/profile/<string:student_id>")
        Call<?> profileData(@Path("student_id") String student_id);

        @GET("/api/get_exams/<student_id>")
        Call<?> examsData(@Path("student_id") int id);


    }
}
