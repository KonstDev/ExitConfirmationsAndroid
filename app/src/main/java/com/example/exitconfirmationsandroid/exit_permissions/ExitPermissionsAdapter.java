package com.example.exitconfirmationsandroid.exit_permissions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exitconfirmationsandroid.R;
import com.example.exitconfirmationsandroid.bottomsheet.MadrichPermissionInfoBottomSheet;
import com.example.exitconfirmationsandroid.bottomsheets.ShomerPermissionInfoBottomSheet;

import java.util.ArrayList;

public class ExitPermissionsAdapter extends RecyclerView.Adapter<ExitPermissionViewHolder>{

    private ArrayList<ExitPermission> exitPermissions;
    private int account_type;

    private FragmentManager supportFragmentManager;

    public ExitPermissionsAdapter(ArrayList<ExitPermission> exitPermissions, int account_type, FragmentManager supportFragmentManager) {
        this.exitPermissions = exitPermissions;
        this.account_type = account_type;
        this.supportFragmentManager = supportFragmentManager;
    }

    @NonNull
    @Override
    public ExitPermissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.permission_item_rv, parent, false);
        return new ExitPermissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExitPermissionViewHolder holder, int position) {
        //setting if the students is chosen of no
        if (exitPermissions.get(position).confirmed){
            holder.permission_confirmation_iv.setVisibility(View.VISIBLE);
        }else{
            holder.permission_confirmation_iv.setVisibility(View.GONE);
        }

        holder.students_names_tv.setText(exitPermissions.get(position).students_names);
        holder.going_to_tv.setText(exitPermissions.get(position).goingTo);
        holder.groups_tv.setText(exitPermissions.get(position).group);
        holder.exit_date_and_time_tv.setText(exitPermissions.get(position).exitDate + " " + exitPermissions.get(position).exitTime);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (account_type==1){
                    MadrichPermissionInfoBottomSheet bottomSheet = new MadrichPermissionInfoBottomSheet(exitPermissions.get(position));
                    bottomSheet.show(supportFragmentManager, "bottomSheet");
                }

                //if the user is a shomer we open the bottom sheet
                if (account_type == 3){
                    ShomerPermissionInfoBottomSheet bottomSheet = new ShomerPermissionInfoBottomSheet(exitPermissions.get(position));
                    bottomSheet.show(supportFragmentManager, "bottomSheet");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return exitPermissions.size();
    }
}
