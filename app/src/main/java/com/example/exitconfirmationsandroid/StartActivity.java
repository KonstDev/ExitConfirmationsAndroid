package com.example.exitconfirmationsandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.example.exitconfirmationsandroid.databinding.ActivityStartBinding;

import java.util.Locale;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.shotBSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginBottomSheet loginBottomSheet = new LoginBottomSheet();
                loginBottomSheet.show(getSupportFragmentManager(), "loginBottomSheet");
            }
        });
    }
}