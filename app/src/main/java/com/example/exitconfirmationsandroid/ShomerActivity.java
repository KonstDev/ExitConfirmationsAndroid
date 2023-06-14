package com.example.exitconfirmationsandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.exitconfirmationsandroid.databinding.ActivityShomerBinding;
import com.example.exitconfirmationsandroid.exit_permissions.ExitPermission;
import com.example.exitconfirmationsandroid.viewmodels.ExitPermissionsViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ShomerActivity extends AppCompatActivity {

    private ActivityShomerBinding binding;

    private ExitPermissionsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ExitPermissionsViewModel.class);

        ArrayList<ExitPermission> exitPermissions = viewModel.getExitPermissions(FirebaseAuth.getInstance().getCurrentUser().getUid(), 3);
        Log.d("TAG", exitPermissions.toString());
    }
}