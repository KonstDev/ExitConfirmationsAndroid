package com.example.exitconfirmationsandroid.bottom_sheet_fragments;

import androidx.fragment.app.FragmentManager;

import com.example.exitconfirmationsandroid.exit_permissions.ExitPermission;

public class FrameSwitcherData{
    FragmentManager fragmentManager;
    ExitPermission exitPermission;
    int frame_layout_id;

    public FrameSwitcherData(FragmentManager fragmentManager, ExitPermission exitPermission, int frame_layout_id) {
        this.fragmentManager = fragmentManager;
        this.exitPermission = exitPermission;
        this.frame_layout_id = frame_layout_id;
    }
}
