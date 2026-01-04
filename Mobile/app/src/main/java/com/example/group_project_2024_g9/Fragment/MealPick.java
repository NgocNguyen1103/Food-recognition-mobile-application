package com.example.group_project_2024_g9.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group_project_2024_g9.Adapter.MealAdapter;
import com.example.group_project_2024_g9.R;
import com.example.group_project_2024_g9.Model.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealPick extends Fragment {

    private RecyclerView recyclerView;
    private MealAdapter mealAdapter;
    private List<Meal> mealList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_pick, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_meals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tạo dữ liệu mẫu, tìm cách thêm data vao list
        mealList = new ArrayList<>();


        // Tạo adapter và gán cho RecyclerView
        recyclerView.setAdapter(mealAdapter);

        return view;
    }
}
