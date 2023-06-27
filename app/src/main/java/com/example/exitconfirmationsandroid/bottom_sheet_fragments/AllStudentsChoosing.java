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
import com.example.exitconfirmationsandroid.students.Student;
import com.example.exitconfirmationsandroid.students.StudentsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllStudentsChoosing extends Fragment {

    private StudentsChoosingFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StudentsChoosingFragmentBinding.inflate(inflater, container, false);


        loadAllStudents();


        return binding.getRoot();
    }

    private void loadAllStudents(){
        FirebaseDatabase.getInstance().getReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.child("Groups").getChildren()){
                            String[] studentsStr = snapshot1.getValue().toString().split(",");
                            ArrayList<Student> students = new ArrayList<>();

                            for (String studentId : studentsStr){
                                DataSnapshot studentSnapshot = snapshot.child("Students").child(studentId);
                                students.add(new Student(studentId, studentSnapshot.child("name").getValue().toString(),
                                        snapshot1.getKey().toString(), false));
                            }

                            StudentsAdapter adapter = new StudentsAdapter(students);
                            binding.studentsRv.setLayoutManager(new LinearLayoutManager(getContext()));
                            binding.studentsRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                            binding.studentsRv.setAdapter(new StudentsAdapter(students));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
