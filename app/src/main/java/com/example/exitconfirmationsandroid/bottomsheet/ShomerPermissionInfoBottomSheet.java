package com.example.exitconfirmationsandroid.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.exitconfirmationsandroid.databinding.ConfirmationInfoShomerBottomSheetBinding;
import com.example.exitconfirmationsandroid.exit_permissions.ExitPermission;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ShomerPermissionInfoBottomSheet extends BottomSheetDialogFragment {

    private ExitPermission exitPermission;

    public ShomerPermissionInfoBottomSheet(ExitPermission exitPermission) {
        this.exitPermission = exitPermission;
    }

    private ConfirmationInfoShomerBottomSheetBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ConfirmationInfoShomerBottomSheetBinding.inflate(inflater, container, false);

        binding.studentsNames.setText(exitPermission.students_names);
        binding.madrichTv.setText(exitPermission.madrich_name);
        binding.exitTime.setText(exitPermission.exitTime + " " + exitPermission.exitDate);

        return binding.getRoot();
    }
}
