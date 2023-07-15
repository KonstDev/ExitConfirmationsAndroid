package com.example.exitconfirmationsandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.exitconfirmationsandroid.bottomsheet.CreateExitPermissionBottomSheet;
import com.example.exitconfirmationsandroid.databinding.ActivityMadrichBinding;
import com.example.exitconfirmationsandroid.exit_permissions.ExitPermission;
import com.example.exitconfirmationsandroid.exit_permissions.ExitPermissionsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MadrichActivity extends AppCompatActivity {

    private ActivityMadrichBinding binding;
    private boolean fabRotating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMadrichBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadMadrichInfo();
        loadExitPermissions();

        binding.addPermissionFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateExitPermissionBottomSheet createExitPermissionBottomSheet = new CreateExitPermissionBottomSheet();
                createExitPermissionBottomSheet.show(getSupportFragmentManager(), "CreateExitPermissionBottomSheet");
            }
        });
    }

    public void loadMadrichInfo(){
        binding.progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("Madrichs").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name")
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
        FirebaseDatabase.getInstance().getReference("Madrichs").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("exit_permissions")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        //if there is no exit permissions
                                        if (!snapshot.child("ExitPermissions").exists()) {
                                            //disabling progress bar
                                            binding.progressBar.setVisibility(View.GONE);
                                            binding.thereIsNoExitPermissionsAlert.setVisibility(View.VISIBLE);
                                            return;
                                        }

                                        if (snapshot1.getValue().toString().isEmpty()){
                                            //disabling progress bar
                                            binding.progressBar.setVisibility(View.GONE);
                                            binding.thereIsNoExitPermissionsAlert.setVisibility(View.VISIBLE);
                                            return;
                                        }

                                        snapshot = snapshot.child("ExitPermissions");

                                        Calendar calendar = Calendar.getInstance();
                                        Date currentDate = calendar.getTime();

                                        // Calculate a week earlier date
                                        calendar.add(Calendar.WEEK_OF_YEAR, -1);
                                        Date weekEarlierDate = calendar.getTime();

                                        // Format date to match Firebase database format (yyyy-MM-dd)
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        String weekEarlierDateString = dateFormat.format(weekEarlierDate);

                                        ArrayList<ExitPermission> exitPermissions1 = new ArrayList<>();
                                        String[] exit_permissions = snapshot1.getValue().toString().split(",");
                                        for (int i = 0; i < exit_permissions.length; i++) {
                                            boolean confirmed = Boolean.parseBoolean(snapshot.child(exit_permissions[i]).child("confirmed").getValue().toString());
                                            String exitDate = snapshot.child(exit_permissions[i]).child("exitDate").getValue().toString();
                                            String exitTime = snapshot.child(exit_permissions[i]).child("exitTime").getValue().toString();
                                            String goingTo = snapshot.child(exit_permissions[i]).child("goingTo").getValue().toString();
                                            String group = snapshot.child(exit_permissions[i]).child("group").getValue().toString();
                                            String id = snapshot.child(exit_permissions[i]).child("id").getValue().toString();
                                            String madrich_name = snapshot.child(exit_permissions[i]).child("madrich_name").getValue().toString();
                                            String madrich_id = snapshot.child(exit_permissions[i]).child("madrich_id").getValue().toString();
                                            String returnDate = snapshot.child(exit_permissions[i]).child("returnDate").getValue().toString();
                                            String returnTime = snapshot.child(exit_permissions[i]).child("returnTime").getValue().toString();
                                            String students_ids = snapshot.child(exit_permissions[i]).child("students_ids").getValue().toString();
                                            String students_names = snapshot.child(exit_permissions[i]).child("students_names").getValue().toString();

                                            // Combine exitDate and exitTime into a single DateTime string
                                            String exitDateTimeString = returnDate + " " + returnTime;

                                            //EXIT PERMISSION CHECKING ON OUTDATING
                                            //TODO: FIX EXIT PERMISSION OUTDATE CHECKING
                                            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                                            try {
                                                Date exitDateTime = dateTimeFormat.parse(exitDateTimeString);

                                                // Check if exitDateTime is later than a week from the current date
                                                if (exitDateTime.after(weekEarlierDate)) {
                                                    int finalI = i;
                                                    FirebaseDatabase.getInstance().getReference()
                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                            String madrich_id = snapshot.child("ExitPermissions").child(exit_permissions[finalI])
                                                                                    .child("madrich_id").getValue().toString();
                                                                            String[] students_ids = snapshot.child("ExitPermissions").child(exit_permissions[finalI])
                                                                                    .child("students_ids").getValue().toString().split(",");

                                                                            List<String> madrich_exit_permissions = Arrays.asList(snapshot.child("Madrichs").child(madrich_id)
                                                                                    .child("exit_permissions").getValue().toString().split(","));
                                                                            Log.d("TAG", "MADRICH EXIT PERMISSIONS: " + madrich_exit_permissions.toString());
                                                                            if (madrich_exit_permissions.contains(exit_permissions[finalI])){
                                                                                Log.d("TAG", "POINT");
                                                                                madrich_exit_permissions.remove(exit_permissions[finalI]);
                                                                            }

                                                                            String madrich_exit_permissions_str = "";
                                                                            for (String exitPermissionId : madrich_exit_permissions){
                                                                                if (madrich_exit_permissions_str.isEmpty()){
                                                                                    madrich_exit_permissions_str=exitPermissionId;
                                                                                }else{
                                                                                    madrich_exit_permissions_str+=","+exitPermissionId;
                                                                                }
                                                                            }

                                                                            for (String student_id : students_ids){
                                                                                List<String> student_exit_permissions = Arrays.asList(snapshot.child("Students").child(student_id)
                                                                                        .child("exit_permissions").getValue().toString().split(","));
                                                                                if (student_exit_permissions.contains(exit_permissions[finalI])) {
                                                                                    student_exit_permissions.remove(exit_permissions[finalI]);
                                                                                }

                                                                                String student_exit_permissions_str = "";
                                                                                for (String exitPermissionId : student_exit_permissions){
                                                                                    if (student_exit_permissions_str.isEmpty()){
                                                                                        student_exit_permissions_str=exitPermissionId;
                                                                                    }else{
                                                                                        student_exit_permissions_str+=","+exitPermissionId;
                                                                                    }
                                                                                }

                                                                                FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("exit_permissions")
                                                                                        .setValue(student_exit_permissions_str);
                                                                            }

                                                                            FirebaseDatabase.getInstance().getReference("Madrichs").child(madrich_id).child("exit_permissions")
                                                                                    .setValue(madrich_exit_permissions_str);


                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                                        }
                                                                    });

                                                    // Delete the exit permission
//                                                    FirebaseDatabase.getInstance().getReference("ExitPermissions")
//                                                            .child(exit_permissions[i]).removeValue();
                                                    Log.d("TAG", "REMOVE: " + exit_permissions[i]);
                                                }
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                            exitPermissions1.add(new ExitPermission(id, confirmed, exitDate, exitTime, goingTo, group, madrich_name, madrich_id, returnDate, returnTime, students_ids, students_names));
                                        }

                                        //setting up recycler view
                                        binding.allPermissions.setLayoutManager(new LinearLayoutManager(MadrichActivity.this));
                                        binding.allPermissions.addItemDecoration(new DividerItemDecoration(MadrichActivity.this, DividerItemDecoration.VERTICAL));
                                        Collections.reverse(exitPermissions1);
                                        binding.allPermissions.setAdapter(new ExitPermissionsAdapter(exitPermissions1, 1, getSupportFragmentManager()));

                                        //disabling progress bar
                                        binding.progressBar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
    }
}