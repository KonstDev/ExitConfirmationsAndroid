package dem.llc.exitconfirmationsandroid.presentation.recyclerview.students;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dem.llc.exitconfirmationsandroid.R;

public class StudentsViewHolder extends RecyclerView.ViewHolder{

    TextView student_name, student_group;
    ImageView student_selection;

    public StudentsViewHolder(@NonNull View itemView) {
        super(itemView);

        student_name = itemView.findViewById(R.id.student_name);
        student_selection = itemView.findViewById(R.id.student_selection_iv);
        student_group = itemView.findViewById(R.id.student_group_tv);
    }
}
