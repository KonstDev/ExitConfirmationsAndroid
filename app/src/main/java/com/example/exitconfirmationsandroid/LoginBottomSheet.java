package com.example.exitconfirmationsandroid;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.exitconfirmationsandroid.databinding.LoginBottomSheetBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginBottomSheet extends BottomSheetDialogFragment {

    private LoginBottomSheetBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LoginBottomSheetBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        configureEditText();

        binding.closeDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });


        return view;
    }

    private void logIn(){
        if (binding.emailEt.getText().toString().isEmpty() || binding.passwordEt.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "עריכת טקסטים לא יכולים להיות ריקים", Toast.LENGTH_SHORT).show();
        }else{
            FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.emailEt.getText().toString(), binding.passwordEt.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseDatabase.getInstance().getReference("Madrichs").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                                            binding.progressBar.setVisibility(View.GONE);
                                            Intent intent = new Intent(getContext(), MadrichActivity.class);
                                            startActivity(intent);
                                            dismiss();
                                        }else{
                                            FirebaseDatabase.getInstance().getReference("Students").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                                    if (snapshot2.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                                                        binding.progressBar.setVisibility(View.GONE);
                                                        Intent intent = new Intent(getContext(), StudentActivity.class);
                                                        startActivity(intent);
                                                        dismiss();
                                                    }else{
                                                        FirebaseDatabase.getInstance().getReference("Guards").addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                                                                    Intent intent = new Intent(getContext(), ShomerActivity.class);
                                                                    startActivity(intent);
                                                                    dismiss();
                                                                }else{
                                                                    Toast.makeText(getContext(), "אין משתמש קיים עם הפרטים הללו", Toast.LENGTH_SHORT).show();
                                                                }
                                                                binding.progressBar.setVisibility(View.GONE);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }else{
                                Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void configureEditText(){
        //configuring the password edit text
        // Set input type to password
        binding.passwordEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        binding.passwordEt.setGravity(Gravity.RIGHT);

        // Set transformation method for password input
        binding.passwordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
}
