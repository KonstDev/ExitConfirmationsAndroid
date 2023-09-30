package com.example.exitconfirmationsandroid;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.exitconfirmationsandroid.bottomsheet.ShomerPermissionInfoBottomSheet;
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
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ShomerActivity extends AppCompatActivity {

    private ActivityShomerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadGuardInfo();
        loadExitPermissions();

        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadGuardInfo();
                loadExitPermissions();

                binding.refresh.setRefreshing(false);
            }
        });

        binding.scanQrCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barcodeLauncher.launch(new ScanOptions());
            }
        });

    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    String exitPermissionId = result.getContents();

                    FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child("ExitPermissions").child(exitPermissionId).exists()){
                                snapshot = snapshot.child("ExitPermissions").child(exitPermissionId);

                                String confirmationLink = snapshot.child("confirmationLink").getValue().toString();
                                boolean confirmed = Boolean.parseBoolean(snapshot.child("confirmed").getValue().toString());
                                String exitDate = snapshot.child("exitDate").getValue().toString();
                                String exitTime = snapshot.child("exitTime").getValue().toString();
                                String goingTo = snapshot.child("goingTo").getValue().toString();
                                String group = snapshot.child("group").getValue().toString();
                                String id = snapshot.child("id").getValue().toString();
                                String madrich_id = snapshot.child("madrich_id").getValue().toString();
                                String madrich_name = snapshot.child("madrich_name").getValue().toString();
                                String returnDate = snapshot.child("returnDate").getValue().toString();
                                String returnTime = snapshot.child("returnTime").getValue().toString();
                                String students_ids = snapshot.child("students_ids").getValue().toString();
                                String students_names = snapshot.child("students_names").getValue().toString();

                                ShomerPermissionInfoBottomSheet shomerPermissionInfoBottomSheet =
                                        new ShomerPermissionInfoBottomSheet(
                                                new ExitPermission(id, confirmed, exitDate, exitTime, goingTo, group, madrich_name, madrich_id, returnDate, returnTime, students_ids, students_names, confirmationLink));
                                shomerPermissionInfoBottomSheet.show(getSupportFragmentManager(), "shomer bs");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });


    public void loadGuardInfo(){
        binding.progressBar.setVisibility(View.VISIBLE);
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
        FirebaseDatabase.getInstance().getReference("Guards").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("exit_permissions").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                //if there is no exit permissions
                                if (!snapshot.child("ExitPermissions").exists()){
                                    binding.thereIsNoExitPermissionsAlert.setVisibility(View.VISIBLE);
                                    return;
                                }

                                if (snapshot1.getValue().toString().isEmpty()){
                                    //disabling progress bar
                                    binding.allPermissions.setAdapter(null);
                                    binding.progressBar.setVisibility(View.GONE);
                                    binding.thereIsNoExitPermissionsAlert.setVisibility(View.VISIBLE);
                                    return;
                                }else{
                                    binding.thereIsNoExitPermissionsAlert.setVisibility(View.GONE);
                                }

                                ArrayList<ExitPermission> exitPermissions1 = new ArrayList<>();

                                List<String> exit_permissions = Arrays.stream(snapshot1.getValue().toString().split(",")).collect(Collectors.toList());
                                for (int i = 0; i < exit_permissions.size(); i++) {

                                    if (!snapshot.child("ExitPermissions").child(exit_permissions.get(i)).exists()){
                                        exit_permissions.remove(exit_permissions.get(i));
                                        continue;
                                    }

                                    boolean confirmed = Boolean.parseBoolean(snapshot.child("ExitPermissions").child(exit_permissions.get(i)).child("confirmed").getValue().toString());
                                    String exitDate = snapshot.child("ExitPermissions").child(exit_permissions.get(i)).child("exitDate").getValue().toString();
                                    String exitTime = snapshot.child("ExitPermissions").child(exit_permissions.get(i)).child("exitTime").getValue().toString();
                                    String goingTo = snapshot.child("ExitPermissions").child(exit_permissions.get(i)).child("goingTo").getValue().toString();
                                    String group = snapshot.child("ExitPermissions").child(exit_permissions.get(i)).child("group").getValue().toString();
                                    String id = snapshot.child("ExitPermissions").child(exit_permissions.get(i)).child("id").getValue().toString();
                                    String madrich_name = snapshot.child("ExitPermissions").child(exit_permissions.get(i)).child("madrich_name").getValue().toString();
                                    String madrich_id = snapshot.child("ExitPermissions").child(exit_permissions.get(i)).child("madrich_id").getValue().toString();
                                    String returnDate = snapshot.child("ExitPermissions").child(exit_permissions.get(i)).child("returnDate").getValue().toString();
                                    String returnTime = snapshot.child("ExitPermissions").child(exit_permissions.get(i)).child("returnTime").getValue().toString();
                                    String students_ids = snapshot.child("ExitPermissions").child(exit_permissions.get(i)).child("students_ids").getValue().toString();
                                    String students_names = snapshot.child("ExitPermissions").child(exit_permissions.get(i)).child("students_names").getValue().toString();
                                    String confirmationLink = snapshot.child("ExitPermissions").child(exit_permissions.get(i)).child("confirmationLink").getValue().toString();

                                    exitPermissions1.add(new ExitPermission(id, confirmed, exitDate, exitTime, goingTo, group, madrich_name, madrich_id, returnDate, returnTime, students_ids, students_names, confirmationLink));
                                }


                                //updating student's exit permissions
                                String guard_exit_permissions_str = "";
                                for (String exitPermissionId : exit_permissions){
                                    if (guard_exit_permissions_str.isEmpty()){
                                        guard_exit_permissions_str=exitPermissionId;
                                    }else{
                                        guard_exit_permissions_str+=","+exitPermissionId;
                                    }
                                }
                                FirebaseDatabase.getInstance().getReference().child("Guards")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("exit_permissions")
                                        .setValue(guard_exit_permissions_str);

                                //setting up recycler view
                                binding.allPermissions.setLayoutManager(new LinearLayoutManager(ShomerActivity.this));
                                binding.allPermissions.addItemDecoration(new DividerItemDecoration(ShomerActivity.this, DividerItemDecoration.VERTICAL));
                                binding.allPermissions.setAdapter(new ExitPermissionsAdapter(exitPermissions1, 3, getSupportFragmentManager()));

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