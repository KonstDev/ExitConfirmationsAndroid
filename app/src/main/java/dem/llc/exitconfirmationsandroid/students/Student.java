package dem.llc.exitconfirmationsandroid.students;

public class Student {
    public String name, id, group;
    public boolean selected;

    public Student(String id, String name, String group, boolean selected) {
        this.name = name;
        this.selected = selected;
        this.group = group;
        this.id = id;
    }
}
