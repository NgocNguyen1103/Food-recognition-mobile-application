package com.example.group_project_2024_g9.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.group_project_2024_g9.Activity.MealDetailActivity;
import com.example.group_project_2024_g9.Model.Meal;
import com.example.group_project_2024_g9.R;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<Meal> meals;
    private Context context;

    public MealAdapter(List<Meal> meals, Context context) {
        this.meals = meals;
        this.context = context;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.mealName.setText(meal.getStrMeal());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.mealImage);

        // Lắng nghe sự kiện click vào item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MealDetailActivity.class);
            // Đảm bảo truyền đúng key "MEAL_ID"
            intent.putExtra("MEAL_ID", meal.getIdMeal());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.tvFoodName);
            mealImage = itemView.findViewById(R.id.imgFood);
        }
    }
}
