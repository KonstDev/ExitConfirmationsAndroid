package com.example.exitconfirmationsandroid.students;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exitconfirmationsandroid.R;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsViewHolder>{

    public ArrayList<Student> students;

    private ArrayList<String> chosenStudentsIds = new ArrayList<>(); //for collecting ids of chosen students
    private ArrayList<String> chosenStudentsNames = new ArrayList<>(); //for collecting names of chosen students

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
                //if the student was already chosen
                if (students.get(position).selected){
                    students.get(position).selected = false;
                    chosenStudentsIds.remove(students.get(position).id);
                    chosenStudentsNames.remove(students.get(position).name);
                    holder.student_selection.setVisibility(View.GONE);
                }
                //if the student wasn't already chosen
                else{
                    students.get(position).selected = true;
                    chosenStudentsIds.add(students.get(position).id);
                    chosenStudentsNames.add(students.get(position).name);
                    holder.student_selection.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }


    public String getChosenStudentsIds(){
        String chosenStudentsStr = "";
        for (String chosenStudent : chosenStudentsIds){
            if (chosenStudentsStr.isEmpty()){
                chosenStudentsStr = chosenStudent;
            }else {
                chosenStudentsStr += "," + chosenStudent;
            }
        }
        return chosenStudentsStr;
    }

    public String getChosenStudentsNames(){
        String chosenStudentsStr = "";
        for (String chosenStudent : chosenStudentsNames){
            if (chosenStudentsStr.isEmpty()){
                chosenStudentsStr = chosenStudent;
            }else {
                chosenStudentsStr += "," + chosenStudent;
            }
        }
        return chosenStudentsStr;
    }

    public String getGroups(){
        //we're creating set to have unique elements
        Set<String> groups = new HashSet<>();
        for (Student student : students){
            if (student.selected){
                groups.add(student.group);
            }
        }

        String groupsStr = "";
        for (String group : groups){
            if (groupsStr.isEmpty()){
                groupsStr=group;
            }
            groupsStr += ","+group;
        }
        return groupsStr;
    }
}
