package com.example.group_project_2024_g9.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group_project_2024_g9.Adapter.MealAdapter;
import com.example.group_project_2024_g9.Api.MealAPI;
import com.example.group_project_2024_g9.Api.RetrofitClient;
import com.example.group_project_2024_g9.Model.Meal;
import com.example.group_project_2024_g9.Model.MealResponse;
import com.example.group_project_2024_g9.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    private RecyclerView recyclerViewSeafood, recyclerViewVegetarian, recyclerViewDessert;
    private MealAdapter mealAdapterSeafood, mealAdapterVegetarian, mealAdapterDessert;
    private List<Meal> mealListSeafood = new ArrayList<>();
    private List<Meal> mealListVegetarian = new ArrayList<>();
    private List<Meal> mealListDessert = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerViews
        recyclerViewSeafood = view.findViewById(R.id.recycler_breakfast);
        recyclerViewSeafood.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerViewVegetarian = view.findViewById(R.id.recycler_lunch);
        recyclerViewVegetarian.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerViewDessert = view.findViewById(R.id.recycler_dinner);
        recyclerViewDessert.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Initialize Adapters
        mealAdapterSeafood = new MealAdapter(mealListSeafood, getContext());
        recyclerViewSeafood.setAdapter(mealAdapterSeafood);

        mealAdapterVegetarian = new MealAdapter(mealListVegetarian, getContext());
        recyclerViewVegetarian.setAdapter(mealAdapterVegetarian);

        mealAdapterDessert = new MealAdapter(mealListDessert, getContext());
        recyclerViewDessert.setAdapter(mealAdapterDessert);

        // Load data from APIs
        loadMealsByCategory("Seafood", mealListSeafood, mealAdapterSeafood);
        loadMealsByCategory("Vegetarian", mealListVegetarian, mealAdapterVegetarian);
        loadMealsByCategory("Dessert", mealListDessert, mealAdapterDessert);

        return view;
    }

    // Function to load meals by category
    private void loadMealsByCategory(String category, List<Meal> mealList, MealAdapter mealAdapter) {
        MealAPI mealAPI = RetrofitClient.getClient("https://www.themealdb.com/api/json/v1/1/").create(MealAPI.class);
        Call<MealResponse> call = mealAPI.getMealsByCategory(category);

        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mealList.addAll(response.body().getMeals());
                    mealAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Server returned an error: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getContext(), "Network error. Please check your connection.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Conversion error or other: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
