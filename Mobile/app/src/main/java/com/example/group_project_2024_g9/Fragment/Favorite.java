package com.example.group_project_2024_g9.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group_project_2024_g9.Adapter.MealAdapter;
import com.example.group_project_2024_g9.Model.Meal;
import com.example.group_project_2024_g9.R;

import java.util.ArrayList;
import java.util.List;

// Duy
public class Favorite extends Fragment {

    private RecyclerView recyclerView;
    private MealAdapter foodAdapter;
    private List<Meal> foodList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        // Khởi tạo RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_history);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Tạo foodlist
        foodList = new ArrayList<>();

        // Kiem cach them data vào list sau nay


        // Tạo adapter và gán cho RecyclerView
        foodAdapter = new MealAdapter(foodList, getContext());
        recyclerView.setAdapter(foodAdapter);

        return view;
    }
}
