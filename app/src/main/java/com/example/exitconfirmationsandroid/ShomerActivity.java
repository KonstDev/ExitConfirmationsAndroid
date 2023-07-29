package com.example.exitconfirmationsandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.exitconfirmationsandroid.bottomsheet.ShomerPermissionInfoBottomSheet;
import com.example.exitconfirmationsandroid.databinding.ActivityShomerBinding;
import com.example.exitconfirmationsandroid.exit_permissions.ExitPermission;
import com.example.exitconfirmationsandroid.exit_permissions.ExitPermissionsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ShomerActivity extends AppCompatActivity {

    private ActivityShomerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadGuardInfo();
        loadExitPermissions();

        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent()).addOnSuccessListener(new OnSuccessListener<PendingDynamicLinkData>() {
            @Override
            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                Uri deepLink = null;
                if (pendingDynamicLinkData!=null){
                    deepLink = pendingDynamicLinkData.getLink();
                }
                if (deepLink!=null){
                    String exitPermissionId = deepLink.getQueryParameter("exitPermissionId");

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
            }
        });

    }

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
                        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                //if there is no exit permissions
                                if (!snapshot.child("ExitPermissions").exists()){
                                    binding.thereIsNoExitPermissionsAlert.setVisibility(View.VISIBLE);
                                    return;
                                }

                                if (snapshot1.getValue().toString().isEmpty()){
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
                                    String confirmationLink = snapshot.child(exit_permissions[i]).child("confirmationLink").getValue().toString();

                                    // Combine exitDate and exitTime into a single DateTime string
                                    String exitDateTimeString = exitDate + " " + exitTime;

                                    // Parse exitDateTime string to Date object
                                    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                                    try {
                                        Date exitDateTime = dateTimeFormat.parse(exitDateTimeString);

                                        // Check if exitDateTime is later than a week from the current date
                                        if (weekEarlierDate.after(exitDateTime)) {
                                            // Delete the exit permission
                                            FirebaseDatabase.getInstance().getReference("ExitPermissions")
                                                    .child(exit_permissions[i]).removeValue();
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    exitPermissions1.add(new ExitPermission(id, confirmed, exitDate, exitTime, goingTo, group, madrich_name, madrich_id, returnDate, returnTime, students_ids, students_names, confirmationLink));
                                }

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