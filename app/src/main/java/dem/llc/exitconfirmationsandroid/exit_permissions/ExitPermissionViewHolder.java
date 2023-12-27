package dem.llc.exitconfirmationsandroid.exit_permissions;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dem.llc.exitconfirmationsandroid.R;

public class ExitPermissionViewHolder extends RecyclerView.ViewHolder{

    ImageView permission_confirmation_iv;
    TextView students_names_tv, going_to_tv, groups_tv, exit_date_and_time_tv;

    public ExitPermissionViewHolder(@NonNull View itemView) {
        super(itemView);

        permission_confirmation_iv = itemView.findViewById(R.id.confirmed_view);
        students_names_tv = itemView.findViewById(R.id.students_names_tv);
        going_to_tv = itemView.findViewById(R.id.going_to_tv);
        groups_tv = itemView.findViewById(R.id.groups_tv);
        exit_date_and_time_tv = itemView.findViewById(R.id.exit_date_and_time_tv);
    }
}
