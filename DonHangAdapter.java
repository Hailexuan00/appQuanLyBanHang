package fpoly.hailxph49396.duan1_quanlybanhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fpoly.hailxph49396.duan1_quanlybanhang.DAO.DonHangDAO;
import fpoly.hailxph49396.duan1_quanlybanhang.DTO.DonHangDTO;
import fpoly.hailxph49396.duan1_quanlybanhang.R;
import fpoly.hailxph49396.duan1_quanlybanhang.ShowMoney.ShowMoney;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.DonHangViewHolders> {

    Context context;
    ArrayList<DonHangDTO> list;
    DonHangDTO donHangDTO;
    DonHangDAO donHangDAO;
    OnItemClickListener onItemClickListner;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    ShowMoney showMoney;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void updateList(ArrayList<DonHangDTO> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }
    public void updateData(ArrayList<DonHangDTO> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }


    public DonHangAdapter(Context context, ArrayList<DonHangDTO> list, OnItemClickListener onItemClickListner) {
        this.context = context;
        this.list = list;
        this.onItemClickListner = onItemClickListner;
        donHangDAO = new DonHangDAO(context);
    }

    @NonNull
    @Override
    public DonHangViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_don_hang, parent, false);
        return new DonHangViewHolders(view, onItemClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangViewHolders holder, int position) {
        donHangDTO = list.get(position);
        showMoney = new ShowMoney();
        if (donHangDTO.getNgay() != null) {
            holder.txtNgay.setText(sdf.format(donHangDTO.getNgay()));
        } else {
            holder.txtNgay.setText("Lỗi ngày");
        }
        holder.txtGio.setText(donHangDTO.getGio());
        holder.txtMaDH.setText("MĐH: " + donHangDTO.getMaDonHang());
        holder.txtUser.setText("User: " + donHangDTO.getUsername());
        holder.txtTongTien.setText(showMoney.formatCurrency(donHangDTO.getThanhTien()) + "VNĐ");
        if (donHangDTO.getTrangThai() == 0) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.doNhat));
        } else if (donHangDTO.getTrangThai() == 1) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DonHangViewHolders extends RecyclerView.ViewHolder {

        TextView txtNgay;
        TextView txtGio;
        TextView txtMaDH;
        TextView txtUser;
        TextView txtTongTien;
        CardView cardView;

        public DonHangViewHolders(@NonNull View itemView, OnItemClickListener onItemClickListner) {
            super(itemView);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtGio = itemView.findViewById(R.id.txtGio);
            txtMaDH = itemView.findViewById(R.id.txtMaDH);
            txtUser = itemView.findViewById(R.id.txtUser);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
            cardView = itemView.findViewById(R.id.card_view);

            itemView.setOnClickListener(v -> {
                if (onItemClickListner != null) {
                    onItemClickListner.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
