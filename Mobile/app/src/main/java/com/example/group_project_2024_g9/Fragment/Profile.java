
package com.example.group_project_2024_g9.Fragment;

import com.example.group_project_2024_g9.Fragment.PersonalInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.group_project_2024_g9.R;

public class Profile extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Tìm các nút layout trong XML
        LinearLayout historyButton = view.findViewById(R.id.history);
        LinearLayout favoriteButton = view.findViewById(R.id.favorite);
        LinearLayout notificationButton = view.findViewById(R.id.notification);
        LinearLayout personalInfoButton = view.findViewById(R.id.personalInfoButton); // Tìm nút PersonalInfo

        // Chuyển sang HistoryFragment khi nhấn vào historyButton
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo instance của fragment mới
                Fragment historyFragment = new History();
                // Sử dụng FragmentManager để thay thế fragment hiện tại
                replaceFragment(historyFragment);
            }
        });

        // Chuyển sang FavoriteFragment khi nhấn vào favoriteButton
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo instance của fragment mới
                Fragment favoriteFragment = new Favorite();
                // Sử dụng FragmentManager để thay thế fragment hiện tại
                replaceFragment(favoriteFragment);
            }
        });

        // Chuyển sang PersonalInfoFragment khi nhấn vào personalInfoButton
        personalInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo instance của fragment mới
                Fragment personalInfoFragment = new PersonalInfo();
                // Sử dụng FragmentManager để thay thế fragment hiện tại
                replaceFragment(personalInfoFragment);
            }
        });

        return view;
    }

    // Hàm chung để thay thế fragment hiện tại bằng fragment mới
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment); // R.id.frame_layout là ID của vùng chứa fragment
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
