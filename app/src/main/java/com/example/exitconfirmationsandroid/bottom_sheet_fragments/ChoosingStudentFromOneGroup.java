package com.example.exitconfirmationsandroid.bottom_sheet_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.exitconfirmationsandroid.databinding.StudentsChoosingFragmentBinding;
import com.example.exitconfirmationsandroid.exit_permissions.ExitPermission;
import com.example.exitconfirmationsandroid.students.Student;
import com.example.exitconfirmationsandroid.students.StudentsAdapter;
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

    private ExitPermission exitPermission;

    public ChoosingStudentFromOneGroup(ExitPermission exitPermission){
        this.exitPermission = exitPermission;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StudentsChoosingFragmentBinding.inflate(inflater, container, false);

        loadStudentsFromGroup();

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
                                        FirebaseDatabase.getInstance().getReference("Students").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (String studentId: studentsStr){
                                                    students.add(new Student(studentId, snapshot.child(studentId).child("name").getValue().toString(),
                                                            snapshot.child(studentId).getValue().toString(), true));
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
