package com.example.group_project_2024_g9.Model;

public class Meal {
    private String idMeal;
    private String strMeal;           // Tên món ăn
    private String strCategory;       // Danh mục món ăn
    private String strInstructions;   // Hướng dẫn nấu món ăn
    private String strMealThumb;      // Hình ảnh món ăn

    // Getter và Setter cho các trường dữ liệu
    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }
}


