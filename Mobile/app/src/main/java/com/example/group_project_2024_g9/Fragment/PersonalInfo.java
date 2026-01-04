package com.example.group_project_2024_g9.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.group_project_2024_g9.Fragment.EditProfile;
import com.example.group_project_2024_g9.R;

public class PersonalInfo extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Liên kết với layout của Fragment
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);

        // Tìm nút Edit (LinearLayout theo như trong XML)
        LinearLayout editButton = view.findViewById(R.id.editButton);

        // Thiết lập sự kiện khi người dùng nhấn vào nút Edit
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyen fragment hien tai sang fragment personal in   fo
                // Tạo instance của Fragment PersonalInfo
                EditProfile editProfile = new EditProfile();

                // Lấy FragmentManager từ Activity chứa Fragment này
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                // Bắt đầu một giao dịch Fragment
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Thay thế Fragment hiện tại bằng PersonalInfo và thêm giao dịch vào backstack nếu muốn
                fragmentTransaction.replace(R.id.frame_layout , editProfile); // fragment_container là ID của ViewGroup nơi chứa Fragment hiện tại

                // Thêm giao dịch vào backstack nếu muốn quay lại Fragment trước đó khi nhấn nút back
                fragmentTransaction.addToBackStack(null);

                // Thực hiện giao dịch
                fragmentTransaction.commit();
            }
        });

        return view; // Trả về view của fragment
    }
}
