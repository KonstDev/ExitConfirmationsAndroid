package com.example.exitconfirmationsandroid.bottom_sheet_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.exitconfirmationsandroid.databinding.PermissionParametersChoosingFragmentBinding;

public class PermissionTypeChoosingFragment extends Fragment {

    private PermissionParametersChoosingFragmentBinding binding;
    private int frame_layout_id;
    private FragmentManager fragmentManager;

    public PermissionTypeChoosingFragment(int frame_layout_id, FragmentManager fragmentManager) {
        this.frame_layout_id = frame_layout_id;
        this.fragmentManager = fragmentManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PermissionParametersChoosingFragmentBinding.inflate(inflater, container, false);

        binding.createPermissionForThisGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(frame_layout_id, new ChoosingStudentFromOneGroup()).commit();
            }
        });

        binding.createPermissionForEveryoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(frame_layout_id, new AllStudentsChoosing()).commit();
            }
        });

        return binding.getRoot();
    }
}
