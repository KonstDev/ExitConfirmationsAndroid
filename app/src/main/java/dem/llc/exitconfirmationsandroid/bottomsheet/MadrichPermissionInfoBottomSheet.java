package dem.llc.exitconfirmationsandroid.bottomsheet;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dem.llc.exitconfirmationsandroid.R;
import dem.llc.exitconfirmationsandroid.databinding.ConfirmationInfoMadrichBottomSheetBinding;
import dem.llc.exitconfirmationsandroid.exit_permissions.ExitPermission;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MadrichPermissionInfoBottomSheet extends BottomSheetDialogFragment {

    private ExitPermission exitPermission;

    private ConfirmationInfoMadrichBottomSheetBinding binding;

    public MadrichPermissionInfoBottomSheet(ExitPermission exitPermission){
        this.exitPermission = exitPermission;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ConfirmationInfoMadrichBottomSheetBinding.inflate(getLayoutInflater());

        //btn for closing bottom sheet
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.studentsNames.setText(exitPermission.students_names);
        binding.madrichTv.setText(getString(R.string.madrich) + ": "+ exitPermission.madrich_name);
        binding.exitTime.setText(getString(R.string.exit_time) + ": "+exitPermission.exitTime + " " + exitPermission.exitDate);
        binding.returnTime.setText(getString(R.string.return_time) + ": "+exitPermission.returnTime + " " + exitPermission.returnDate);
        binding.goingTo.setText(getString(R.string.going_to) + ": "+exitPermission.goingTo);
        binding.groupTv.setText(getString(R.string.group) + ": "+exitPermission.group);

        binding.showQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.encodeBitmap(exitPermission.confirmationLink, BarcodeFormat.QR_CODE, 400, 400);
                    binding.qrCodeIv.setImageBitmap(bitmap);
                    binding.qrCodeIv.setVisibility(View.VISIBLE);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                binding.showQrCode.setVisibility(View.GONE);
            }
        });

        return binding.getRoot();
    }
}
