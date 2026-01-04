package com.example.group_project_2024_g9.Activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group_project_2024_g9.Api.MealAPI;
import com.example.group_project_2024_g9.Api.RetrofitClient;
import com.example.group_project_2024_g9.Model.Meal;
import com.example.group_project_2024_g9.Model.MealResponse;
import com.example.group_project_2024_g9.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDetailActivity extends AppCompatActivity {

    private TextView  mealInstructions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mealInstructions = findViewById(R.id.meal_instruction);

        // Nhận ID món ăn từ Intent
        String mealId = getIntent().getStringExtra("MEAL_ID");
        loadMealDetails(mealId);
    }

    private void loadMealDetails(String mealId) {
        // Sử dụng Retrofit để lấy thông tin chi tiết món ăn
        MealAPI mealAPI = RetrofitClient.getClient("https://www.themealdb.com/api/json/v1/1/").create(MealAPI.class);
        Call<MealResponse> call = mealAPI.getMealById(mealId);

        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Meal meal = response.body().getMeals().get(0);
                    String instructions = meal.getStrInstructions();

                    // Hiển thị instruction trong TextView
                    mealInstructions.setText(instructions);
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Toast.makeText(MealDetailActivity.this, "Failed to load meal details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

