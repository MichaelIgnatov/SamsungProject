package com.example.samsungproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class Teacher {

    public static class TeacherData {
        public int id;
        public String name;
        public String login;
        public String password;
        public String post;
        public String created_at;
        public byte avatar;
    }

    public static class NewStudentData {
        public String student_name;
        public String student_content;
        public int teacher_id;
        public String login;
        public String password;
    }

    public interface TeacherService {
        @GET("/api/get_teacher/<login>/<password>")
        Call<TeacherData> teacherLogin(@Path("login") String login, @Path("password") String password);

        @GET("/api/classes/<teacher_id>")
        Call<?> getClassesData(@Path("teacher_id") int id);

        @POST("/api/add_student")
        Call<?> postAddStudent();
    }

}
