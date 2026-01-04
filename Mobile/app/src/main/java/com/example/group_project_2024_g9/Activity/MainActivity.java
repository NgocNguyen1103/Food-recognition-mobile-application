package com.example.group_project_2024_g9.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group_project_2024_g9.Activity.LoginActivity;
import com.example.group_project_2024_g9.Helper.DatabaseHelper;
import com.example.group_project_2024_g9.R;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME_OUT = 2500; // 2500ms = 2.5s
    private static final String TAG = "MainActivity";
    private boolean isDestroyed = false; // Biến để kiểm tra trạng thái Activity
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Sử dụng Handler với Looper chính để hiển thị màn hình chờ
        try {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isDestroyed) { // Kiểm tra xem Activity đã bị hủy chưa
                        try {
                            // Sau khi hết 2500ms, chuyển sang LoginActivity
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // Đóng MainActivity để không quay lại được nữa
                        } catch (Exception e) {
                            Log.e(TAG, "Error while transitioning to LoginActivity: " + e.getMessage());
                        }
                    }
                }
            }, SPLASH_SCREEN_TIME_OUT);
        } catch (Exception e) {
            Log.e(TAG, "Error during splash screen: " + e.getMessage());
        }

    }



}
