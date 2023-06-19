package com.example.exitconfirmationsandroid.students;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exitconfirmationsandroid.R;

public class StudentsViewHolder extends RecyclerView.ViewHolder{

    TextView student_name;
    ImageView student_selection;

    public StudentsViewHolder(@NonNull View itemView) {
        super(itemView);

        student_name = itemView.findViewById(R.id.student_name);
        student_selection = itemView.findViewById(R.id.student_selection_iv);
    }
}
