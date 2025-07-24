package fpoly.hailxph49396.duan1_quanlybanhang.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import fpoly.hailxph49396.duan1_quanlybanhang.Adapter.NhanVienAdapter;
import fpoly.hailxph49396.duan1_quanlybanhang.DAO.nhanvienDao;
import fpoly.hailxph49396.duan1_quanlybanhang.DTO.NhanVienDTO;
import fpoly.hailxph49396.duan1_quanlybanhang.R;

public class NhanVienFragment extends Fragment {
    private RecyclerView recyclerView;
    private NhanVienAdapter nhanVienAdapter;
    private List<NhanVienDTO> nhanVienList;
    private nhanvienDao nhanVienDAO;
    private FloatingActionButton fabAddEmployee;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nhan_vien, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerViewNhanVien);
        fabAddEmployee = view.findViewById(R.id.fabAddEmployee);

        // Initialize DAO and fetch employee list
        nhanVienDAO = new nhanvienDao(getContext());
        nhanVienList = nhanVienDAO.getAllEmployees();

        if (nhanVienList == null) {
            nhanVienList = new ArrayList<>();
        }

        // Set up RecyclerView and Adapter
        nhanVienAdapter = new NhanVienAdapter(nhanVienList, getContext(), nhanVienDAO, this::showEditEmployeeDialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(nhanVienAdapter);

        // Set up FAB click listener for adding employees
        fabAddEmployee.setOnClickListener(v -> showAddEmployeeDialog());

        return view;
    }

    private void showAddEmployeeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thêm nhân viên");

        // Tạo Layout cho Dialog
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_add_nhanvien, null);
        builder.setView(customLayout);

        final EditText etUsername = customLayout.findViewById(R.id.edtUserName);
        final EditText etPassword = customLayout.findViewById(R.id.edtPassword);
        final EditText etName = customLayout.findViewById(R.id.edtName);
        final EditText etMiddleName = customLayout.findViewById(R.id.edtMiddleName);
        final EditText etGender = customLayout.findViewById(R.id.edtGender);
        final EditText etPhone = customLayout.findViewById(R.id.edtPhone);
        final EditText etEmail = customLayout.findViewById(R.id.edtEmail);
        final EditText etAddress = customLayout.findViewById(R.id.edtAddress);
        final Button btnAdd = customLayout.findViewById(R.id.btnAdd);
        final Button btnCancel = customLayout.findViewById(R.id.btnCancel);

        final AlertDialog dialog = builder.create();
        dialog.show();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các EditText
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String name = etName.getText().toString();
                String middleName = etMiddleName.getText().toString();
                String gender = etGender.getText().toString();
                String phone = etPhone.getText().toString();
                String email = etEmail.getText().toString();
                String address = etAddress.getText().toString();

                // Kiểm tra các trường bắt buộc không được để trống
                if (username.isEmpty() || password.isEmpty() || name.isEmpty() || gender.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo đối tượng nhân viên mới
                NhanVienDTO newNhanVien = new NhanVienDTO(
                        username,
                        password,
                        name,
                        middleName,
                        gender,
                        phone,
                        email,
                        address
                );

                // Thêm nhân viên vào cơ sở dữ liệu
                boolean isAdded = nhanVienDAO.addEmployee(newNhanVien);
                if (isAdded) {
                    // Cập nhật lại danh sách nhân viên trong Adapter
                    nhanVienList.clear();
                    nhanVienList.addAll(nhanVienDAO.getAllEmployees());
                    nhanVienAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Thêm nhân viên thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }

                // Đóng dialog
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đóng dialog mà không thực hiện thay đổi
                dialog.dismiss();
            }
        });
    }


    private void showEditEmployeeDialog(@Nullable NhanVienDTO nhanVien) {
        if (nhanVien == null) {
            return; // Nếu nhanVien là null, không thực hiện bất kỳ hành động nào
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Cập nhật thông tin nhân viên");

        // Tạo Layout cho Dialog
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_add_nhanvien, null);
        builder.setView(customLayout);

        final EditText etUsername = customLayout.findViewById(R.id.edtUserName);
        final EditText etPassword = customLayout.findViewById(R.id.edtPassword);
        final EditText etName = customLayout.findViewById(R.id.edtName);
        final EditText etMiddleName = customLayout.findViewById(R.id.edtMiddleName);
        final EditText etGender = customLayout.findViewById(R.id.edtGender);
        final EditText etPhone = customLayout.findViewById(R.id.edtPhone);
        final EditText etEmail = customLayout.findViewById(R.id.edtEmail);
        final EditText etAddress = customLayout.findViewById(R.id.edtAddress);
        final Button btnAdd = customLayout.findViewById(R.id.btnAdd);
        final Button btnCancel = customLayout.findViewById(R.id.btnCancel);

        // Điền dữ liệu hiện tại vào các EditText
        etUsername.setText(nhanVien.getUsername());
        etPassword.setText(nhanVien.getPassword());
        etName.setText(nhanVien.getName());
        etMiddleName.setText(nhanVien.getMiddleName());
        etGender.setText(nhanVien.getGender());
        etPhone.setText(nhanVien.getPhone());
        etEmail.setText(nhanVien.getEmail());
        etAddress.setText(nhanVien.getAddress());

        final AlertDialog dialog = builder.create(); // Khai báo biến dialog ở đây
        dialog.show();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cập nhật thông tin nhân viên
                nhanVien.setUsername(etUsername.getText().toString());
                nhanVien.setPassword(etPassword.getText().toString());
                nhanVien.setName(etName.getText().toString());
                nhanVien.setMiddleName(etMiddleName.getText().toString());
                nhanVien.setGender(etGender.getText().toString());
                nhanVien.setPhone(etPhone.getText().toString());
                nhanVien.setEmail(etEmail.getText().toString());
                nhanVien.setAddress(etAddress.getText().toString());

                // Cập nhật vào cơ sở dữ liệu
                boolean isUpdated = nhanVienDAO.updateEmployee(nhanVien);
                if (isUpdated) {
                    // Cập nhật lại danh sách nhân viên trong Adapter
                    nhanVienList.clear();
                    nhanVienList.addAll(nhanVienDAO.getAllEmployees());
                    nhanVienAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Cập nhật thông tin nhân viên thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }

                // Đóng dialog
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đóng dialog mà không thực hiện thay đổi
                dialog.dismiss();
            }
        });
    }
}
