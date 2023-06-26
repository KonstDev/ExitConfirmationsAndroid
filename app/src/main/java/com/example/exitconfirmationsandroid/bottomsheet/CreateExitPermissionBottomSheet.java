package com.example.exitconfirmationsandroid.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.exitconfirmationsandroid.bottom_sheet_fragments.PermissionTypeChoosingFragment;
import com.example.exitconfirmationsandroid.databinding.CreateExitPermissionBottomSheetBinding;
import com.example.exitconfirmationsandroid.exit_permissions.ExitPermission;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateExitPermissionBottomSheet extends BottomSheetDialogFragment {

    private CreateExitPermissionBottomSheetBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CreateExitPermissionBottomSheetBinding.inflate(inflater, container, false);

        //on click listener for bottom sheet closing btn
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id = Long.toString(snapshot.child("ExitPermissions").getChildrenCount());
                String madrich_name = snapshot.child("Madrichs")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").getValue().toString();

                ExitPermission exitPermission = new ExitPermission();
                exitPermission.id = id;
                exitPermission.madrich_name = madrich_name;

                getChildFragmentManager().beginTransaction()
                        .replace(binding.createPermissionFragmentContainer.getId(),
                                new PermissionTypeChoosingFragment(
                                        binding.createPermissionFragmentContainer.getId(),
                                        getChildFragmentManager(),
                                        exitPermission
                                )
                        ).commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}
