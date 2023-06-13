package com.example.exitconfirmationsandroid.exit_permissions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exitconfirmationsandroid.R;

import java.util.ArrayList;

public class ExitPermissionsAdapter extends RecyclerView.Adapter<ExitPermissionViewHolder>{

    private ArrayList<ExitPermission> exitPermissions;
    private int account_type;

    public ExitPermissionsAdapter(ArrayList<ExitPermission> exitPermissions, int account_type) {
        this.exitPermissions = exitPermissions;
        this.account_type = account_type;
    }

    @NonNull
    @Override
    public ExitPermissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.permission_item_rv, parent, false);
        return new ExitPermissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExitPermissionViewHolder holder, int position) {
        //setting if the
        if (exitPermissions.get(position).confirmed){
            holder.permission_confirmation_iv.setVisibility(View.VISIBLE);
        }else{
            holder.permission_confirmation_iv.setVisibility(View.GONE);
        }

        holder.students_names_tv.setText(exitPermissions.get(position).students_names);
        holder.going_to_tv.setText(exitPermissions.get(position).goingTo);
        holder.groups_tv.setText(exitPermissions.get(position).group);
        holder.exit_date_and_time_tv.setText(exitPermissions.get(position).exitDate + " " + exitPermissions.get(position).exitTime);
    }

    @Override
    public int getItemCount() {
        return exitPermissions.size();
    }
}
