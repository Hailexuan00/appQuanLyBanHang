package fpoly.hailxph49396.duan1_quanlybanhang.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import fpoly.hailxph49396.duan1_quanlybanhang.DAO.nhanvienDao;
import fpoly.hailxph49396.duan1_quanlybanhang.DTO.NhanVienDTO;
import fpoly.hailxph49396.duan1_quanlybanhang.R;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.NhanVienViewHolder> {
    private final List<NhanVienDTO> nhanVienList;
    private final Context context;
    private final nhanvienDao nhanVienDAO;
    private final OnNhanVienActionListener actionListener;

    // Constructor
    public NhanVienAdapter(List<NhanVienDTO> nhanVienList, Context context, nhanvienDao nhanVienDAO, OnNhanVienActionListener actionListener) {
        this.nhanVienList = nhanVienList;
        this.context = context;
        this.nhanVienDAO = nhanVienDAO;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nhan_vien, parent, false);
        return new NhanVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienViewHolder holder, int position) {
        NhanVienDTO nhanVien = nhanVienList.get(position);
        holder.tv_EmployeeName.setText("Tên: " + nhanVien.getName());
        holder.tv_EmployeePhone.setText("SDT: " + nhanVien.getPhone());



        // Xử lý sự kiện nút sửa
        holder.btnEdit.setOnClickListener(v -> actionListener.onEditNhanVien(nhanVien));
        // Xử lý sự kiện nút xóa
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn xóa nhân viên này?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        boolean isDeleted = nhanVienDAO.deleteEmployee(nhanVien.getUsername());
                        if (isDeleted) {
                            nhanVienList.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Xóa nhân viên thành công!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "Không thể xóa nhân viên!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();

        });

    }

    @Override
    public int getItemCount() {
        return nhanVienList.size();
    }

    public static class NhanVienViewHolder extends RecyclerView.ViewHolder {
        TextView tv_EmployeeName, tv_EmployeePhone;
        ImageButton btnEdit, btnDelete;

        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_EmployeeName = itemView.findViewById(R.id.tvEmployeeName);
            tv_EmployeePhone = itemView.findViewById(R.id.tvEmployeePhone);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public interface OnNhanVienActionListener {
        void onEditNhanVien(NhanVienDTO nhanVien);
    }
}
