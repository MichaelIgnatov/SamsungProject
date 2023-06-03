package com.example.samsungproject;

import static com.example.samsungproject.StudentLoginActivity.STUDENT_AVATAR;
import static com.example.samsungproject.StudentLoginActivity.STUDENT_ID;
import static com.example.samsungproject.StudentLoginActivity.STUDENT_NAME;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

public class StudentProfile extends AppCompatActivity {
    private Button changeStudentAvatar;
    EditText passwordEditText;
    EditText newPasswordEditText;
    EditText repeatPasswordEditText;
    ImageView studentAvatar;
    int studentId;
    String currentStudentPassword, studentName, avatar;
    String serverURl = Student.serverURl;

    private static final String IMAGE_DIRECTORY = "/img";
    private final int GALLERY = 1, CAMERA = 2;
    String[] activityMenu = {"Профиль", "Портфолио", "Результаты ПА", "Выход"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        requestMultiplePermissions();

        studentName = getIntent().getStringExtra(STUDENT_NAME);
        studentId = getIntent().getIntExtra(STUDENT_ID, 0);
        avatar = getIntent().getStringExtra(STUDENT_AVATAR);

        changeStudentAvatar = findViewById(R.id.change_avatar_btn);
        passwordEditText = findViewById(R.id.current_password);
        newPasswordEditText = findViewById(R.id.new_password);
        repeatPasswordEditText = findViewById(R.id.repeat_password);
        studentAvatar = findViewById(R.id.user_avatar);
        TextView studentData = findViewById(R.id.student_data);
        studentData.setText(studentName);

        if (avatar != null && !avatar.equals("")){
            new Thread(){
                @Override
                public void run() {
                    try {
                        Bitmap bitmap = loadImage(serverURl + avatar);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                studentAvatar.setImageBitmap(bitmap);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, activityMenu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.student_menu);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Title");
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1) {
                    openPortfolioActivity();
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

    public void openPortfolioActivity() {
        Intent intent = new Intent(this, Portfolio.class);
        startActivity(intent);
    }
    public void exitProfile() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void showPictureDialog(View view){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Выберите действие");
        String[] pictureDialogItems = {
                "Выбрать изображение из галереи",
                "Использовать камеру" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        studentAvatar = findViewById(R.id.user_avatar);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(StudentProfile.this, "Изображение сохранено!", Toast.LENGTH_SHORT).show();
                    studentAvatar.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(StudentProfile.this, "Ошибка!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            studentAvatar.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(StudentProfile.this, "Изображение сохранено!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(@NonNull Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    public void changeStudentPassword(View view) {
        String password = passwordEditText.getText().toString();
        String newPassword = newPasswordEditText.getText().toString();
        String repeatPassword = repeatPasswordEditText.getText().toString();
                if(password != currentStudentPassword) {
                    Toast.makeText(getApplicationContext(), "Введён неверный пароль", Toast.LENGTH_SHORT).show();
                } else {
                    if (newPassword != repeatPassword) {
                        Toast.makeText(getApplicationContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                    } else {

                    }
                }
    }
    public Bitmap loadImage(String s) throws IOException {
        URL url = new URL(s);

        return BitmapFactory.decodeStream(url.openStream());
    }
}
