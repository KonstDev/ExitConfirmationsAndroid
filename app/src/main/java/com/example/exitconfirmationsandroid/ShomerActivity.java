package com.example.exitconfirmationsandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.exitconfirmationsandroid.databinding.ActivityShomerBinding;
import com.example.exitconfirmationsandroid.exit_permissions.ExitPermission;
import com.example.exitconfirmationsandroid.exit_permissions.ExitPermissionsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShomerActivity extends AppCompatActivity {

    private ActivityShomerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadExitPermissions();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadGuardInfo();
            }
        }).start();

    }

    public void loadGuardInfo(){
        FirebaseDatabase.getInstance().getReference("Guards").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name")
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()){
                            binding.userNameTv.setText(task.getResult().getValue().toString());
                        }
                    }
                });
    }

    public void loadExitPermissions() {
        FirebaseDatabase.getInstance().getReference("Guards").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("exit_permissions").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                FirebaseDatabase.getInstance().getReference("ExitPermissions").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<ExitPermission> exitPermissions1 = new ArrayList<>();
                        String[] exit_permissions = task.getResult().getValue().toString().split(",");
                        for (int i = 0; i < exit_permissions.length; i++) {
                            boolean confirmed = Boolean.parseBoolean(snapshot.child(exit_permissions[i]).child("confirmed").getValue().toString());
                            String exitDate = snapshot.child(exit_permissions[i]).child("exitDate").getValue().toString();
                            String exitTime = snapshot.child(exit_permissions[i]).child("exitTime").getValue().toString();
                            String goingTo = snapshot.child(exit_permissions[i]).child("goingTo").getValue().toString();
                            String group = snapshot.child(exit_permissions[i]).child("group").getValue().toString();
                            String id = snapshot.child(exit_permissions[i]).child("id").getValue().toString();
                            String madrich_name = snapshot.child(exit_permissions[i]).child("madrich_name").getValue().toString();
                            String returnDate = snapshot.child(exit_permissions[i]).child("returnDate").getValue().toString();
                            String returnTime = snapshot.child(exit_permissions[i]).child("returnTime").getValue().toString();
                            String students_ids = snapshot.child(exit_permissions[i]).child("students_ids").getValue().toString();
                            String students_names = snapshot.child(exit_permissions[i]).child("students_names").getValue().toString();



                            exitPermissions1.add(new ExitPermission(id, confirmed, exitDate, exitTime, goingTo, group, madrich_name, returnDate, returnTime, students_ids, students_names));
                        }

                        binding.allPermissions.setLayoutManager(new LinearLayoutManager(ShomerActivity.this));
                        binding.allPermissions.addItemDecoration(new DividerItemDecoration(ShomerActivity.this, DividerItemDecoration.VERTICAL));
                        binding.allPermissions.setAdapter(new ExitPermissionsAdapter(exitPermissions1, 3, getSupportFragmentManager()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}