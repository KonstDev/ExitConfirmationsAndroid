package dem.llc.exitconfirmationsandroid.dialog;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Objects;

import dem.llc.exitconfirmationsandroid.R;
import dem.llc.exitconfirmationsandroid.databinding.ProfileAvatarDialogBinding;

public class ProfileImageDialog extends DialogFragment {

    private ProfileAvatarDialogBinding binding;

    private String accountType;

    private Uri image_uri;

    public ProfileImageDialog(String accountType){
        this.accountType = accountType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProfileAvatarDialogBinding.inflate(inflater, container, false);

        loadProfileImage();

        binding.profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String readImagePermission;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    readImagePermission = Manifest.permission.READ_MEDIA_IMAGES;
                } else {
                    readImagePermission = Manifest.permission.READ_EXTERNAL_STORAGE;
                }
                if (ContextCompat.checkSelfPermission(requireContext(), readImagePermission) != PackageManager.PERMISSION_GRANTED) {
                    galleryRequestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    pickImageActivityResultLauncher.launch(intent);
                }
            }
        });

        return binding.getRoot();
    }

    private void loadProfileImage(){
        binding.progressBar.setVisibility(View.VISIBLE);

        FirebaseDatabase.getInstance().getReference().child(accountType)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("profile_image_url")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String profile_image_url = snapshot.getValue().toString();
                        if (!profile_image_url.equals("")){
                            Glide.with(requireContext()).load(profile_image_url).into(binding.profileIv);
                        }else{
                            binding.profileIv.setImageResource(R.drawable.profile_img);
                        }
                        binding.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void uploadImage(){
        if (image_uri != null) {
            //id изображения в базе данных
            String imageDbId = String.valueOf(System.currentTimeMillis());
            FirebaseStorage.getInstance().getReference().child("profile_images")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(imageDbId)
                    .putFile(image_uri).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    String imageUrl = task1.getResult().toString();

                                    //добавляем одежду в базу данных
                                    FirebaseDatabase.getInstance().getReference().child(accountType)
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profile_image_url")
                                            .setValue(imageUrl);
                                }
                            });
                        }
                    });
        }
    }

    ActivityResultLauncher<Intent> pickImageActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()== Activity.RESULT_OK && result.getData()!=null && result.getData().getData()!=null){
                        image_uri = result.getData().getData();

                        binding.profileIv.setImageURI(image_uri);

                        uploadImage();
                    }
                }
            }
    );

    //для получение разрешение на доступ к картинкам
    ActivityResultLauncher<String> galleryRequestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    pickImageActivityResultLauncher.launch(intent);
                } else {
                    Toast.makeText(getContext(), "Нет разрешения на доступ к галерее", Toast.LENGTH_SHORT).show();
                }
            });
}
