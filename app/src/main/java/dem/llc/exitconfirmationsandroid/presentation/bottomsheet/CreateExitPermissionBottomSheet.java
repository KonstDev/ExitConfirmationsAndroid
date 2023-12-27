package dem.llc.exitconfirmationsandroid.presentation.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dem.llc.exitconfirmationsandroid.presentation.bottom_sheet_fragments.FrameSwitcherData;
import dem.llc.exitconfirmationsandroid.presentation.bottom_sheet_fragments.PermissionTypeChoosingFragment;
import dem.llc.exitconfirmationsandroid.databinding.CreateExitPermissionBottomSheetBinding;
import dem.llc.exitconfirmationsandroid.data.models.ExitPermission;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String madrich_name = snapshot.child("Madrichs")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").getValue().toString();

                ExitPermission exitPermission = new ExitPermission();
                exitPermission.madrich_name = madrich_name;
                exitPermission.madrich_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                getChildFragmentManager().beginTransaction()
                        .replace(binding.createPermissionFragmentContainer.getId(),
                                new PermissionTypeChoosingFragment(
                                        new FrameSwitcherData(getChildFragmentManager(),
                                                exitPermission,
                                                binding.createPermissionFragmentContainer.getId(), new ArrayList<>()),
                                        binding.backFragmentBtn
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
