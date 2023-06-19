package com.example.exitconfirmationsandroid.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.exitconfirmationsandroid.databinding.CreateExitPermissionBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CreateExitPermissionBottomSheet extends BottomSheetDialogFragment {

    private CreateExitPermissionBottomSheetBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CreateExitPermissionBottomSheetBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }
}
