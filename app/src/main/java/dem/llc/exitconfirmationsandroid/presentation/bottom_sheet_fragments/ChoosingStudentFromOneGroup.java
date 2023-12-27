package dem.llc.exitconfirmationsandroid.presentation.bottom_sheet_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import dem.llc.exitconfirmationsandroid.databinding.StudentsChoosingFragmentBinding;
import dem.llc.exitconfirmationsandroid.data.models.Student;
import dem.llc.exitconfirmationsandroid.presentation.recyclerview.students.StudentsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChoosingStudentFromOneGroup extends Fragment {
    private StudentsChoosingFragmentBinding binding;

    private FrameSwitcherData frameSwitcherData;
    private ImageButton go_back_btn;

    public ChoosingStudentFromOneGroup(FrameSwitcherData frameSwitcherData, ImageButton go_back_btn){
        this.frameSwitcherData = frameSwitcherData;
        this.go_back_btn = go_back_btn;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StudentsChoosingFragmentBinding.inflate(inflater, container, false);

        go_back_btn.setVisibility(View.VISIBLE);

        loadStudentsFromGroup();

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chosen students ids
//                String chosenStudentsIds = ((StudentsAdapter)binding.studentsRv.getAdapter()).getChosenStudentsIds();
//                if (chosenStudentsIds.equals("")){
//                    Toast.makeText(getContext(), "צריך לבחור לפחות תלמיד אחת", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                frameSwitcherData.exitPermission.students = ((StudentsAdapter)binding.studentsRv.getAdapter()).students;
//                frameSwitcherData.exitPermission.students_ids = chosenStudentsIds;
//
                frameSwitcherData.students = ((StudentsAdapter)binding.studentsRv.getAdapter()).getChosenStudents();

//                //chosen students names
//                String chosenStudentsNames = ((StudentsAdapter)binding.studentsRv.getAdapter()).getChosenStudentsNames();
//                frameSwitcherData.exitPermission.students_names = chosenStudentsNames;
//
//                //setting group of the student/students
//                //this is choosing students from only one group, so we can set just one group
//                frameSwitcherData.exitPermission.group = ((StudentsAdapter)binding.studentsRv.getAdapter()).students.get(0).group;
//                Log.d("TAG", "group: " + ((StudentsAdapter)binding.studentsRv.getAdapter()).students.get(0).group);

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
                studentsAdapter.filter(newText);
                return true;
            }
        });

        return binding.getRoot();
    }

    private void loadStudentsFromGroup() {
        FirebaseDatabase.getInstance().getReference("Madrichs").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("group").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()){
                            String groupId = task.getResult().getValue().toString();

                            FirebaseDatabase.getInstance().getReference("Groups").child(groupId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task1) {
                                    if (task1.isSuccessful()){
                                        String[] studentsStr = task1.getResult().getValue().toString().split(",");
                                        ArrayList<Student> students = new ArrayList<>();
                                        FirebaseDatabase.getInstance().getReference("Students").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (String studentId: studentsStr){
                                                    students.add(new Student(studentId, snapshot.child(studentId).child("name").getValue().toString(), groupId, false));
                                                }
                                                //setting recycler view
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
                            });
                        }
                    }
                });
    }
}
