package com.example.exitconfirmationsandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.exitconfirmationsandroid.databinding.StudentsChoosingFragmentBinding;

public class StudentsChoosingFragment extends Fragment {

    private StudentsChoosingFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StudentsChoosingFragmentBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }
}
