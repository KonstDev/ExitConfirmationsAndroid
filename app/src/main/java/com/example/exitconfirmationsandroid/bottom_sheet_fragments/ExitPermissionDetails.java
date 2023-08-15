package com.example.exitconfirmationsandroid.bottom_sheet_fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.exitconfirmationsandroid.databinding.FrameExitPermissionDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class ExitPermissionDetails extends Fragment {

    private FrameExitPermissionDetailsBinding binding;

    private FrameSwitcherData frameSwitcherData;
    private ImageButton go_back_btn;

    public ExitPermissionDetails(FrameSwitcherData frameSwitcherData, ImageButton go_back_btn){
        this.frameSwitcherData = frameSwitcherData;
        this.go_back_btn = go_back_btn;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FrameExitPermissionDetailsBinding.inflate(getLayoutInflater());

        //creating a Calendar object to get the current date and time
        Calendar calendar = Calendar.getInstance();

        binding.exitTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating and showing time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute1) {
                        //checking and fixing the situation when the hour is 9 (to be 09) for example
                        String hour = Integer.toString(hourOfDay);
                        if (hour.length()==1){
                            hour = "0"+hour;
                        }
                        String minute = Integer.toString(minute1);
                        if (minute.length()==1){
                            minute = "0"+minute;
                        }

                        //showing selected time
                        binding.exitTimeTv.setText(hour+":"+minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        binding.returnTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating and showing time picker dialog
                TimePickerDialog timePickerdialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute1) {
                        //checking and fixing the situation when the hour is 9 (to be 09) for example
                        String hour = Integer.toString(hourOfDay);
                        if (hour.length()==1){
                            hour="0"+hour;
                        }
                        String minute = Integer.toString(minute1);
                        if (minute.length()==1){
                            minute = "0"+minute;
                        }

                        //showing selected time
                        binding.returnTimeTv.setText(hour+":"+minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerdialog.show();
            }
        });

        binding.exitDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating and showing date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthI, int dayOfMonthI) {
                        String dayOfMonth = Integer.toString(dayOfMonthI);
                        if (dayOfMonth.length()==1){
                            dayOfMonth = "0"+dayOfMonth;
                        }
                        String month = Integer.toString(monthI);
                        if (month.length()==1){
                            month="0"+month;
                        }

                        //showing selected date
                        binding.exitDateTv.setText(dayOfMonth+"."+month+"."+year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        binding.returnDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating and showing date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthI, int dayOfMonthI) {
                        String dayOfMonth = Integer.toString(dayOfMonthI);
                        if (dayOfMonth.length()==1){
                            dayOfMonth = "0"+dayOfMonth;
                        }
                        String month = Integer.toString(monthI);
                        if (month.length()==1){
                            month="0"+month;
                        }

                        //showing selected date
                        binding.returnDateTv.setText(dayOfMonth+"."+month+"."+year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        binding.createPermissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.goingToEv.getText().toString().isEmpty() || binding.exitTimeTv.getText().toString().isEmpty()
                    || binding.exitDateTv.getText().toString().isEmpty() || binding.returnTimeTv.getText().toString().isEmpty()
                    || binding.returnDateTv.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "צריך למלא את כל הנתונים", Toast.LENGTH_SHORT).show();
                }else{
                    frameSwitcherData.exitPermission.goingTo = binding.goingToEv.getText().toString();
                    frameSwitcherData.exitPermission.exitTime = binding.exitTimeTv.getText().toString();
                    frameSwitcherData.exitPermission.exitDate = binding.exitDateTv.getText().toString();
                    frameSwitcherData.exitPermission.returnTime = binding.returnTimeTv.getText().toString();
                    frameSwitcherData.exitPermission.returnDate = binding.returnDateTv.getText().toString();

                    FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Long exitPermissionId = new Date().getTime();
                            frameSwitcherData.exitPermission.id = Long.toString(exitPermissionId);

                            //filling the main information about exit permission and setting oncompletelistener if all gone well
                            FirebaseDatabase.getInstance().getReference().child("ExitPermissions").child(Long.toString(exitPermissionId))
                                    .setValue(frameSwitcherData.exitPermission.getAsHashMap())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                //for default exit permission is not confirmed
                                                FirebaseDatabase.getInstance().getReference().child("ExitPermissions").child(Long.toString(exitPermissionId))
                                                        .child("confirmed").setValue(false);

                                                FirebaseDatabase.getInstance().getReference().child("ExitPermissions").child(Long.toString(exitPermissionId))
                                                        .child("confirmationLink").setValue(getDynamicLink(Long.toString(exitPermissionId)));

                                                //adding exit permission to madrich's user permissions
                                                FirebaseDatabase.getInstance().getReference().child("Madrichs").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .child("exit_permissions").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                                if (task.isSuccessful()){
                                                                    String user_exit_permissions = task.getResult().getValue().toString();
                                                                    if (user_exit_permissions.isEmpty()){
                                                                        user_exit_permissions = Long.toString(exitPermissionId);
                                                                    }else{
                                                                        user_exit_permissions += ","+Long.toString(exitPermissionId);
                                                                    }

                                                                    FirebaseDatabase.getInstance().getReference().child("Madrichs").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                            .child("exit_permissions").setValue(user_exit_permissions);

                                                                    //adding exit permission to student's exit permissions for all students
                                                                    String[] students = frameSwitcherData.exitPermission.students_ids.split(",");
                                                                    for (String studentId : students){
                                                                        FirebaseDatabase.getInstance().getReference().child("Students").child(studentId)
                                                                                .child("exit_permissions").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                                                        if (task.isSuccessful()){
                                                                                            String student_exit_permissions = task.getResult().getValue().toString();
                                                                                            if (student_exit_permissions.isEmpty()){
                                                                                                student_exit_permissions = Long.toString(exitPermissionId);
                                                                                            }else{
                                                                                                student_exit_permissions += ","+Long.toString(exitPermissionId);
                                                                                            }

                                                                                            FirebaseDatabase.getInstance().getReference().child("Students").child(studentId)
                                                                                                    .child("exit_permissions").setValue(student_exit_permissions);
                                                                                        }else{
                                                                                            Toast.makeText(getContext(), "Error accessing database", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    }
                                                                                });
                                                                    }
                                                                    ((BottomSheetDialogFragment)getParentFragment()).dismiss();
                                                                }else{
                                                                    Toast.makeText(getContext(), "Failed accessing database", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }else{
                                                Toast.makeText(getContext(), "Error creating exit permission", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        go_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameSwitcherData.fragmentManager.beginTransaction()
                        .replace(frameSwitcherData.frame_layout_id, new PermissionTypeChoosingFragment(frameSwitcherData, go_back_btn)).commit();
            }
        });

        return binding.getRoot();
    }

    private String getDynamicLink(String id){
        return id;
    }
}
