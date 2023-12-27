package dem.llc.exitconfirmationsandroid.bottom_sheet_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import dem.llc.exitconfirmationsandroid.databinding.StudentsChoosingFragmentBinding;
import dem.llc.exitconfirmationsandroid.students.Student;
import dem.llc.exitconfirmationsandroid.students.StudentsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllStudentsChoosing extends Fragment {

    private StudentsChoosingFragmentBinding binding;

    private FrameSwitcherData frameSwitcherData;
    private ImageButton go_back_btn;

    public AllStudentsChoosing(FrameSwitcherData frameSwitcherData, ImageButton go_back_btn) {
        this.frameSwitcherData = frameSwitcherData;
        this.go_back_btn = go_back_btn;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StudentsChoosingFragmentBinding.inflate(inflater, container, false);

        go_back_btn.setVisibility(View.VISIBLE);

        loadAllStudents();

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chosen students ids
                String chosenStudentsIds = ((StudentsAdapter)binding.studentsRv.getAdapter()).getChosenStudentsIds();
                if (chosenStudentsIds.equals("")){
                    Toast.makeText(getContext(), "צריך לבחור לפחות תלמיד אחת", Toast.LENGTH_SHORT).show();
                    return;
                }
                frameSwitcherData.exitPermission.students_ids = chosenStudentsIds;

                //chosen students names
                frameSwitcherData.exitPermission.students_names = ((StudentsAdapter)binding.studentsRv.getAdapter()).getChosenStudentsNames();

                //setting group of the student/students
                frameSwitcherData.exitPermission.group = ((StudentsAdapter)binding.studentsRv.getAdapter()).getGroups();

                frameSwitcherData.fragmentManager.beginTransaction()
                        .replace(frameSwitcherData.frame_layout_id, new ExitPermissionDetails(frameSwitcherData, go_back_btn)).commit();
            }
        });

        go_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameSwitcherData.fragmentManager.beginTransaction()
                        .replace(frameSwitcherData.frame_layout_id, new PermissionTypeChoosingFragment(frameSwitcherData, go_back_btn)).commit();
            }
        });

        binding.studentsSearchv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                StudentsAdapter studentsAdapter = (StudentsAdapter) binding.studentsRv.getAdapter();
                assert studentsAdapter != null;
                studentsAdapter.filter(newText);
                return true;
            }
        });

        return binding.getRoot();
    }

    private void loadAllStudents(){
        FirebaseDatabase.getInstance().getReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Student> students = new ArrayList<>();
                        for (DataSnapshot snapshot1 : snapshot.child("Groups").getChildren()){
                            String[] studentsStr = snapshot1.getValue().toString().split(",");

                            for (String studentId : studentsStr){
                                DataSnapshot studentSnapshot = snapshot.child("Students").child(studentId);
                                students.add(new Student(studentId, studentSnapshot.child("name").getValue().toString(),
                                        snapshot1.getKey().toString(), false));
                            }
                        }
                        binding.studentsRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.studentsRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                        binding.studentsRv.setAdapter(new StudentsAdapter(students));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
