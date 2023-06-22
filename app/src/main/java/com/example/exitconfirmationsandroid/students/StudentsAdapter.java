package com.example.exitconfirmationsandroid.students;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exitconfirmationsandroid.R;

import java.util.ArrayList;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsViewHolder>{

    public ArrayList<Student> students;

    public StudentsAdapter(ArrayList<Student> students){
        this.students = students;
    }

    @NonNull
    @Override
    public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item_rv, parent, false);
        return new StudentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsViewHolder holder, int position) {
        holder.student_name.setText(students.get(position).name);
        if (students.get(position).selected) {
            holder.student_selection.setVisibility(View.VISIBLE);
        }else{
            holder.student_selection.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                students.get(position).selected = true;
                holder.student_selection.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}
