package dem.llc.exitconfirmationsandroid.presentation.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dem.llc.exitconfirmationsandroid.R;
import dem.llc.exitconfirmationsandroid.databinding.ConfirmationInfoShomerBottomSheetBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
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
        binding.madrichTv.setText(getString(R.string.madrich) + ": "+ exitPermission.madrich_name);
        binding.exitTime.setText(getString(R.string.exit_time) + ": " + exitPermission.exitTime + " " + exitPermission.exitDate);
        binding.returnTime.setText(getString(R.string.return_time) + ": " + exitPermission.returnTime + " " + exitPermission.returnDate);
        binding.goingTo.setText(getString(R.string.going_to) + ": " + exitPermission.goingTo);
        binding.groupTv.setText(getString(R.string.group) + ": " + exitPermission.group);

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

                FirebaseDatabase.getInstance().getReference("Guards").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("exit_permissions").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()){
                                    String exit_permissions = task.getResult().getValue().toString();

                                    if (exit_permissions.isEmpty()){
                                        exit_permissions = exitPermission.id;
                                    }else{
                                        exit_permissions += ","+exitPermission.id;
                                    }

                                    FirebaseDatabase.getInstance().getReference("Guards").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("exit_permissions").setValue(exit_permissions);
                                }
                            }
                        });

                dismiss();
            }
        });

        return binding.getRoot();
    }
}
