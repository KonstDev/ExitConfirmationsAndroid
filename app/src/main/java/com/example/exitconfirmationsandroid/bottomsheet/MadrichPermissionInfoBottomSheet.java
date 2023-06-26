package com.example.exitconfirmationsandroid.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.exitconfirmationsandroid.R;
import com.example.exitconfirmationsandroid.databinding.ConfirmationInfoMadrichBottomSheetBinding;
import com.example.exitconfirmationsandroid.exit_permissions.ExitPermission;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MadrichPermissionInfoBottomSheet extends BottomSheetDialogFragment {

    private ExitPermission exitPermission;

    private ConfirmationInfoMadrichBottomSheetBinding binding;

    public MadrichPermissionInfoBottomSheet(ExitPermission exitPermission){
        this.exitPermission = exitPermission;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ConfirmationInfoMadrichBottomSheetBinding.inflate(getLayoutInflater());

        //btn for closing bottom sheet
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.studentsNames.setText(exitPermission.students_names);
        binding.madrichTv.setText(getString(R.string.madrich) + ": "+ exitPermission.madrich_name);
        binding.exitTime.setText(getString(R.string.exit_time) + ": "+exitPermission.exitTime + " " + exitPermission.exitDate);
        binding.returnTime.setText(getString(R.string.return_time) + ": "+exitPermission.returnTime + " " + exitPermission.returnDate);
        binding.goingTo.setText(getString(R.string.going_to) + ": "+exitPermission.goingTo);
        binding.groupTv.setText(getString(R.string.group) + ": "+exitPermission.group);

        return binding.getRoot();
    }
}
