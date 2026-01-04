package com.example.group_project_2024_g9.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group_project_2024_g9.Adapter.MealAdapter;
import com.example.group_project_2024_g9.Model.Meal;
import com.example.group_project_2024_g9.R;

import java.util.ArrayList;
import java.util.List;


public class Search extends Fragment {

    private RecyclerView recyclerSearch;
    private MealAdapter searchAdapter;
    private List<Meal> searchList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Khởi tạo RecyclerView
        recyclerSearch = view.findViewById(R.id.recycler_breakfast);


        // Sử dụng LinearLayoutManager với orientation ngang

        recyclerSearch.setLayoutManager(new GridLayoutManager(getContext(), 2));


        ArrayList<Meal> breakfastList = new ArrayList<>();



        // Thiết lập Adapter

        recyclerSearch.setAdapter(searchAdapter);

        return view;
    }
}