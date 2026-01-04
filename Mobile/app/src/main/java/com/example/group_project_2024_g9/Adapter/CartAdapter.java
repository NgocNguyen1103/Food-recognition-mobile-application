package com.example.group_project_2024_g9.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group_project_2024_g9.R;
import com.example.group_project_2024_g9.Model.Meal;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<Meal> mealList;
    private OnRemoveClickListener onRemoveClickListener;

    public CartAdapter(Context context, List<Meal> mealList, OnRemoveClickListener onRemoveClickListener) {
        this.context = context;
        this.mealList = mealList;
        this.onRemoveClickListener = onRemoveClickListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_meal_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Meal meal = mealList.get(position);

        // Xử lý khi nhấn vào nút xóa
        holder.removeButton.setOnClickListener(v -> {
            if (onRemoveClickListener != null) {
                onRemoveClickListener.onRemoveClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage, removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.tv_meal_name_cart);
            mealImage = itemView.findViewById(R.id.img_meal_cart);
            removeButton = itemView.findViewById(R.id.img_remove_cart);
        }
    }

    // Interface để xử lý sự kiện xóa món ăn
    public interface OnRemoveClickListener {
        void onRemoveClick(int position);
    }
}
