package com.example.exitconfirmationsandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.exitconfirmationsandroid.databinding.ActivityMadrichBinding;

public class MadrichActivity extends AppCompatActivity {

    private ActivityMadrichBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMadrichBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}