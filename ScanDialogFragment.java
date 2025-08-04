package fpoly.hailxph49396.duan1_quanlybanhang.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.Nullable;

import fpoly.hailxph49396.duan1_quanlybanhang.R;

public class ScanDialogFragment extends DialogFragment {

    private OnScanResultListener listener;

    public interface OnScanResultListener {
        void onScanResult(String result);
    }

    public void setOnScanResultListener(OnScanResultListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_them_sp, container, false);

        EditText etTenSanPham = view.findViewById(R.id.etTenSanPham);
        EditText etGiaBan = view.findViewById(R.id.etGiaBan);
        EditText etTonKho = view.findViewById(R.id.etTonKho);
        EditText etMaVach = view.findViewById(R.id.etMaVach);
        EditText etMoTa = view.findViewById(R.id.etMoTa);
        Spinner spDanhMuc = view.findViewById(R.id.spDanhMuc);
        ImageButton btnScan = view.findViewById(R.id.btnScan);
        Button btnSaveProduct = view.findViewById(R.id.btnSaveProduct);
        btnScan.setOnClickListener(v -> {
            IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
            integrator.setPrompt("Đưa mã vạch vào khung quét");
            integrator.setBeepEnabled(true);
            integrator.setOrientationLocked(true);
            integrator.initiateScan();
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                if (listener != null) {
                    listener.onScanResult(result.getContents());
                }
            } else {
                Toast.makeText(getActivity(), "Không quét được mã nào!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void setDialogTitle(String title) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setTitle(title);
        }
    }

}



