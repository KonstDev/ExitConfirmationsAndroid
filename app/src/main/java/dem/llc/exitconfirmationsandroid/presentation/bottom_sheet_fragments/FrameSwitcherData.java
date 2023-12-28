package dem.llc.exitconfirmationsandroid.presentation.bottom_sheet_fragments;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import dem.llc.exitconfirmationsandroid.data.models.Student;

public class FrameSwitcherData{
    FragmentManager fragmentManager;
    ExitPermission exitPermission;
    int frame_layout_id;

    ArrayList<Student> students;

    public FrameSwitcherData(FragmentManager fragmentManager, ExitPermission exitPermission, int frame_layout_id, ArrayList<Student> students) {
        this.fragmentManager = fragmentManager;
        this.exitPermission = exitPermission;
        this.frame_layout_id = frame_layout_id;
        this.students = students;
    }
}
