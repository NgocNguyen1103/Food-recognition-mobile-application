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

import com.example.group_project_2024_g9.Adapter.HistoryAdapter;
import com.example.group_project_2024_g9.Model.Meal;
import com.example.group_project_2024_g9.R;

import java.util.ArrayList;
import java.util.List;

public class History extends Fragment {

    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private List<Meal> mealHistoryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tạo dữ liệu mẫu cho lịch sử món ăn, kiếm dc data mon an
        mealHistoryList = new ArrayList<>();


        // Tạo adapter và gán cho RecyclerView
        historyAdapter = new HistoryAdapter(getContext(), mealHistoryList);
        recyclerView.setAdapter(historyAdapter);

        return view;
    }
}
