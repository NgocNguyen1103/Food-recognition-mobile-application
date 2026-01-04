package com.example.group_project_2024_g9.Api;

import com.example.group_project_2024_g9.Model.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealAPI {
    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);  // Lấy món ăn theo danh mục

    @GET("lookup.php")
    Call<MealResponse> getMealById(@Query("i") String mealId);  // Lấy chi tiết món ăn theo ID

    // Giới hạn số món ăn trả về (Ví dụ: giới hạn 10 món để tăng tốc)
    @GET("filter.php")
    Call<MealResponse> getMealsByCategoryWithLimit(@Query("c") String category, @Query("limit") int limit);
}
