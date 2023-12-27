package dem.llc.exitconfirmationsandroid.bottom_sheet_fragments;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import dem.llc.exitconfirmationsandroid.exit_permissions.ExitPermission;
import dem.llc.exitconfirmationsandroid.students.Student;

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
