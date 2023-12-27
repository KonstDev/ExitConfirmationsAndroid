package dem.llc.exitconfirmationsandroid.presentation.bottom_sheet_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dem.llc.exitconfirmationsandroid.databinding.PermissionParametersChoosingFragmentBinding;

public class PermissionTypeChoosingFragment extends Fragment {

    private PermissionParametersChoosingFragmentBinding binding;
    private FrameSwitcherData frameSwitcherData;

    private ImageButton go_back_btn;

    public PermissionTypeChoosingFragment(FrameSwitcherData frameSwitcherData, ImageButton go_back_btn) {
        this.frameSwitcherData = frameSwitcherData;
        this.go_back_btn = go_back_btn;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PermissionParametersChoosingFragmentBinding.inflate(inflater, container, false);

        //it's the first fragment so there is no fragments to go back
        go_back_btn.setVisibility(View.GONE);

        binding.createPermissionForThisGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameSwitcherData.fragmentManager.beginTransaction().replace(frameSwitcherData.frame_layout_id,
                        new ChoosingStudentFromOneGroup(frameSwitcherData, go_back_btn)).commit();
            }
        });

        binding.createPermissionForEveryoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameSwitcherData.fragmentManager.beginTransaction().replace(frameSwitcherData.frame_layout_id,
                        new AllStudentsChoosing(frameSwitcherData, go_back_btn)).commit();
            }
        });


        return binding.getRoot();
    }
}
