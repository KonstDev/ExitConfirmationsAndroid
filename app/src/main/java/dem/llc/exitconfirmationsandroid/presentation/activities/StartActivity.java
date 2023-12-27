package dem.llc.exitconfirmationsandroid.presentation.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dem.llc.exitconfirmationsandroid.databinding.ActivityStartBinding;
import dem.llc.exitconfirmationsandroid.presentation.bottomsheet.LoginBottomSheet;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            binding.progressBar.setVisibility(View.VISIBLE);
            FirebaseDatabase.getInstance().getReference("Madrichs").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                        binding.progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getApplicationContext(), MadrichActivity.class);
                        startActivity(intent);
                    }else{
                        FirebaseDatabase.getInstance().getReference("Students").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                if (snapshot2.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                                    binding.progressBar.setVisibility(View.GONE);
                                    Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                                    startActivity(intent);
                                }else{
                                    FirebaseDatabase.getInstance().getReference("Guards").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                                                Intent intent = new Intent(getApplicationContext(), ShomerActivity.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(getApplicationContext(), "אין משתמש קיים עם הפרטים הללו", Toast.LENGTH_SHORT).show();
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
        }

        binding.shotBSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginBottomSheet loginBottomSheet = new LoginBottomSheet();
                loginBottomSheet.show(getSupportFragmentManager(), "loginBottomSheet");
            }
        });
    }
}