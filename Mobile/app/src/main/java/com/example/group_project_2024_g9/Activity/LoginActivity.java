package com.example.group_project_2024_g9.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group_project_2024_g9.Helper.DatabaseHelper;
import com.example.group_project_2024_g9.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvSignUp; // Khai báo TextView cho "Sign Up"
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Ánh xạ các thành phần giao diện
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvSignUp = findViewById(R.id.tv_sign_up); // Ánh xạ TextView "Sign Up"

        // Đặt sự kiện OnClickListener cho TextView "Sign Up"
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang SignupActivity
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Lấy thông tin đăng nhập
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();

                    // Xác thực người dùng
                    boolean isValidUser = dbHelper.validateUser(username, password);

                    if (isValidUser) {
                        Log.d("LoginActivity", "Login successful.");
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // Chuyển đến HomeActivity sau khi đăng nhập thành công
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish(); // Kết thúc LoginActivity
                    } else {
                        Log.e("LoginActivity", "Invalid username or password.");
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("LoginActivity", "Error during login: " + e.getMessage());
                    Toast.makeText(LoginActivity.this, "An error occurred during login.", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }
}
