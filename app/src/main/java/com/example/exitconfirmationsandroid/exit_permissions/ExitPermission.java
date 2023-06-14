package com.example.exitconfirmationsandroid.exit_permissions;

public class ExitPermission {
    public boolean confirmed;
    public String id,exitDate, exitTime, goingTo, group, madrich_name, returnDate, returnTime,
            students_ids, students_names;

    public ExitPermission(boolean confirmed, String exitDate, String exitTime, String goingTo,
                          String group, String madrich_name, String returnDate,
                          String returnTime, String students_ids, String students_names) {
        this.confirmed = confirmed;
        this.exitDate = exitDate;
        this.exitTime = exitTime;
        this.goingTo = goingTo;
        this.group = group;
        this.madrich_name = madrich_name;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.students_ids = students_ids;
        this.students_names = students_names;
    }
}
