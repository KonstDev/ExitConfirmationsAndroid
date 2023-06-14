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
import com.google.firebase.database.FirebaseDatabase;

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
        binding.returnTime.setText(exitPermission.returnTime + " " + exitPermission.returnDate);
        binding.goingTo.setText(exitPermission.goingTo);
        binding.groupTv.setText(exitPermission.group);

        //if the permission is already confirmed we hide the confirm button
        if (exitPermission.confirmed){
            binding.btnAccept.setVisibility(View.GONE);
        }else{
            binding.btnAccept.setVisibility(View.VISIBLE);
        }

        //close button
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("ExitPermissions")
                        .child(exitPermission.id).child("confirmed").setValue(true);

                dismiss();
            }
        });

        return binding.getRoot();
    }
}
