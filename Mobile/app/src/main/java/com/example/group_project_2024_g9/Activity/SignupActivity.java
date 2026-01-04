package com.example.group_project_2024_g9.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group_project_2024_g9.Helper.DatabaseHelper;
import com.example.group_project_2024_g9.R;

public class SignupActivity extends AppCompatActivity {

    private EditText etEmail, etFullname, etUsername, etPassword, etPhoneNumber;
    private Button btnSignup;
    private TextView tvLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Ánh xạ các thành phần giao diện
        etEmail = findViewById(R.id.et_emailaddress);
        etFullname = findViewById(R.id.et_fullname);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        btnSignup = findViewById(R.id.btn_signup);
        tvLogin = findViewById(R.id.tv_login); // Ánh xạ TextView "Login"

        // Xử lý sự kiện bấm nút "Sign Up"
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin người dùng nhập vào
                String email = etEmail.getText().toString().trim();
                String fullname = etFullname.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String phoneNumber = etPhoneNumber.getText().toString().trim();

                // Kiểm tra các trường dữ liệu có rỗng hay không
                if (email.isEmpty() || fullname.isEmpty() || username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thêm người dùng vào database
                boolean isInserted = dbHelper.insert_user(email, username, password, phoneNumber, "USER");

                if (isInserted) {
                    Log.d("SignupActivity", "User registered successfully.");
                    Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    // Hiển thị danh sách người dùng sau khi đăng ký thành công
                    showAllUsers();

                    // Chuyển về màn hình đăng nhập sau khi đăng ký thành công
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("SignupActivity", "Failed to register user.");
                    Toast.makeText(SignupActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện bấm vào TextView "Login" để chuyển sang màn hình đăng nhập
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Phương thức để hiển thị tất cả người dùng sau khi đăng ký
    private void showAllUsers() {
        Cursor cursor = dbHelper.get_all_users();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range")
                String email = cursor.getString(cursor.getColumnIndex("email"));
                @SuppressLint("Range")
                String username = cursor.getString(cursor.getColumnIndex("user_name"));
                @SuppressLint("Range")
                String phoneNumber = cursor.getString(cursor.getColumnIndex("phone_number"));

                // In ra thông tin của từng người dùng qua Logcat
                Log.d("SignupActivity", "User: " + username + ", Email: " + email + ", Phone: " + phoneNumber);
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            Log.d("SignupActivity", "No users found in the database.");
        }
    }
}
