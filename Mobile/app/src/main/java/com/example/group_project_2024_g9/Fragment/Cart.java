package com.example.group_project_2024_g9.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group_project_2024_g9.Adapter.CartAdapter;
import com.example.group_project_2024_g9.Model.Meal;
import com.example.group_project_2024_g9.R;
import com.example.group_project_2024_g9.Model.Meal;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Meal> cartMealList;
    private LinearLayout cookButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_cart);
        cookButton = view.findViewById(R.id.btn_cook);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tạo dữ liệu mẫu cho giỏ hàng, phai kiem dc data
        cartMealList = new ArrayList<>();


        // Tạo adapter và gán cho RecyclerView
        cartAdapter = new CartAdapter(getContext(), cartMealList, position -> {
            // Xử lý khi xóa món ăn
            cartMealList.remove(position);
            cartAdapter.notifyItemRemoved(position);
        });
        recyclerView.setAdapter(cartAdapter);

        // Xử lý khi bấm nút "Cook"
        cookButton.setOnClickListener(v -> {
            if (cartMealList.size() >= 3) {
                // Mở màn hình công thức nấu ăn hoặc thông báo có đủ nguyên liệu
                Toast.makeText(getContext(), "Cooking started!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Cần ít nhất 3 nguyên liệu để nấu ăn!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
