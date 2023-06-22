package com.example.exitconfirmationsandroid.students;

public class Student {
    public String name, id;
    public boolean selected;

    public Student(String id, String name, boolean selected) {
        this.name = name;
        this.selected = selected;
        this.id = id;
    }
}
