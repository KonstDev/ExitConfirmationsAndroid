package com.example.exitconfirmationsandroid.exit_permissions;

import java.util.HashMap;

public class ExitPermission {
    public boolean confirmed;
    public String id,exitDate, exitTime, goingTo, group, madrich_id, madrich_name, returnDate, returnTime,
            students_ids, students_names, confirmationLink, realReturnDate, realReturnTime;

    public ExitPermission() {
    }

    public ExitPermission(String id,boolean confirmed, String exitDate, String exitTime, String goingTo,
                          String group, String madrich_name, String madrich_id, String returnDate,
                          String returnTime, String students_ids, String students_names, String confirmationLink, String realReturnDate, String realReturnTime) {
        this.id = id;
        this.confirmed = confirmed;
        this.exitDate = exitDate;
        this.exitTime = exitTime;
        this.goingTo = goingTo;
        this.group = group;
        this.madrich_name = madrich_name; //filling in the CreateExitPermissionBottomSheet.java
        this.madrich_id = madrich_id;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.students_ids = students_ids; //filling in the choosing students fragment
        this.students_names = students_names; //filling in the choosing students fragment
        this.confirmationLink = confirmationLink;
        this.realReturnDate = realReturnDate;
        this.realReturnTime = realReturnTime;
    }

    public HashMap<String, String> getAsHashMap(){
        HashMap<String, String> exitPermission = new HashMap<>();
        exitPermission.put("id", id);
        exitPermission.put("exitDate", exitDate);
        exitPermission.put("exitTime", exitTime);
        exitPermission.put("goingTo", goingTo);
        exitPermission.put("group", group);
        exitPermission.put("madrich_name", madrich_name);
        exitPermission.put("madrich_id", madrich_id);
        exitPermission.put("returnDate", returnDate);
        exitPermission.put("returnTime", returnTime);
        exitPermission.put("students_ids", students_ids);
        exitPermission.put("students_names", students_names);
        return exitPermission;
    }
}
