package com.example.exitconfirmationsandroid.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.exitconfirmationsandroid.exit_permissions.ExitPermission;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExitPermissionsViewModel extends ViewModel {

    public MutableLiveData<ArrayList<ExitPermission>> exitPermissions = new MutableLiveData<>();

    public ArrayList<ExitPermission> getExitPermissions(String user_id, int account_type) {
        exitPermissions = new MutableLiveData<>();
        loadExitPermissions(user_id, account_type);
        return exitPermissions.getValue();
    }

    private void loadExitPermissions(String user_id, int account_type) {

        if (account_type==1){

        }
        else if (account_type==2){

        }
        //shomer
        else if (account_type==3){
            FirebaseDatabase.getInstance().getReference("Guard").child(user_id).child("exit_permissions").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference("ExitPermissions").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<ExitPermission> exitPermissions1 = new ArrayList<>();
                            String[] exit_permissions = task.getResult().toString().split(",");
                            for (int i = 0; i < exit_permissions.length; i++) {
                                exitPermissions1.add(snapshot.child(exit_permissions[i]).getValue(ExitPermission.class));
                            }
                            exitPermissions.setValue(exitPermissions1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });
        }
    }
}
