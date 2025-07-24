package fpoly.hailxph49396.duan1_quanlybanhang.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import fpoly.hailxph49396.duan1_quanlybanhang.Adapter.SanPhamAdapter;
import fpoly.hailxph49396.duan1_quanlybanhang.DAO.DanhMucDAO;
import fpoly.hailxph49396.duan1_quanlybanhang.DAO.SanPhamDAo;
import fpoly.hailxph49396.duan1_quanlybanhang.DTO.DanhMucDTO;
import fpoly.hailxph49396.duan1_quanlybanhang.DTO.SanPhamDTO;
import fpoly.hailxph49396.duan1_quanlybanhang.R;
import fpoly.hailxph49396.duan1_quanlybanhang.ShowMoney.ShowMoney;

public class SanPhamFragment extends Fragment {

    private RecyclerView rvSanPham;
    private Button btnThem, btnSua, btnXoa, btnQuetMaVach;
    private SanPhamDAo sanPhamDao;
    DanhMucDAO danhMucDAO;
    public static ArrayList<SanPhamDTO> sanPhamList;
    ArrayList<DanhMucDTO> danhMucList;
    private SanPhamAdapter sanPhamAdapter;
    Spinner spDanhMuc;
    ShowMoney showMoney;
    View dialogScanView;
    private AlertDialog.Builder barcodeDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_san_pham, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSanPham = view.findViewById(R.id.rvSanPham);
        btnThem = view.findViewById(R.id.btnThemSanPham);
        spDanhMuc = view.findViewById(R.id.spDanhMuc);
        sanPhamDao = new SanPhamDAo(getContext());
        danhMucDAO = new DanhMucDAO(getContext());
        sanPhamList = sanPhamDao.getAllProducts();
        btnThem = view.findViewById(R.id.btnThemSanPham);
        danhMucList = new ArrayList<>();
        showMoney = new ShowMoney();
        danhMucList.add(new DanhMucDTO(-1, "Tất cả"));
        danhMucList.addAll(danhMucDAO.getListDanhMuc());
        ArrayAdapter<DanhMucDTO> adapterDanhMuc = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, danhMucList);
        adapterDanhMuc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDanhMuc.setAdapter(adapterDanhMuc);



        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanDialogFragment dialogFragment = new ScanDialogFragment();
                dialogFragment.setOnScanResultListener(result -> {
                    EditText etMaVach = dialogFragment.getDialog().findViewById(R.id.etMaVach);
                    EditText etTenSanPham = dialogFragment.getDialog().findViewById(R.id.etTenSanPham);
                    EditText etGiaBan = dialogFragment.getDialog().findViewById(R.id.etGiaBan);
                    EditText etTonKho = dialogFragment.getDialog().findViewById(R.id.etTonKho);
                    EditText etMoTa = dialogFragment.getDialog().findViewById(R.id.etMoTa);
                    Spinner spDM = dialogFragment.getDialog().findViewById(R.id.spDanhMuc);
                    Button btnSaveProduct = dialogFragment.getDialog().findViewById(R.id.btnSaveProduct);
                    etMaVach.setText(result);
                    ArrayAdapter<DanhMucDTO> adapterDM = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, danhMucList);
                    adapterDanhMuc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDM.setAdapter(adapterDM);
                    btnSaveProduct.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String tenSanPham = etTenSanPham.getText().toString();
                            DanhMucDTO danhMucDTO = (DanhMucDTO) spDM.getSelectedItem();
                            int maDanhMuc = danhMucDTO.getMaDanhMuc();
                            int giaBan = Integer.parseInt(etGiaBan.getText().toString());
                            int tonKho = Integer.parseInt(etTonKho.getText().toString());
                            String maVach = etMaVach.getText().toString();
                            String moTa = etMoTa.getText().toString();
                            SanPhamDTO sanPham = new SanPhamDTO();
                            sanPham.setTenSanPham(tenSanPham);
                            sanPham.setMaDanhMuc(maDanhMuc);
                            sanPham.setGiaBan(giaBan);
                            sanPham.setTonKho(tonKho);
                            sanPham.setMaVach(maVach);
                            sanPham.setMoTa(moTa);
                            long them = sanPhamDao.addProduct(sanPham);
                            dialogFragment.dismiss();
                            Toast.makeText(getContext(), them != -1 ? "Thêm thành công" : "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
                dialogFragment.show(getChildFragmentManager(), "scan_dialog");
                getChildFragmentManager().executePendingTransactions(); // Đảm bảo DialogFragment được thêm vào trước khi thiết lập tiêu đề
                dialogFragment.setDialogTitle("Thêm sản phẩm");

            }
        });

        sanPhamAdapter = new SanPhamAdapter(getContext(), sanPhamList, new SanPhamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.dialog_san_pham, null);
                TextView tvTenSanPham = dialogView.findViewById(R.id.tvTenSanPham);
                TextView tvGiaBan = dialogView.findViewById(R.id.tvGiaBan);
                TextView tvTonKho = dialogView.findViewById(R.id.tvTonKho);
                TextView tvMaVach = dialogView.findViewById(R.id.tvMaVach);
                TextView tvMoTa = dialogView.findViewById(R.id.tvMoTa);
                Button btnNhap = dialogView.findViewById(R.id.btnNhap);
                Button btnSua = dialogView.findViewById(R.id.btnSua);
                Button btnXoa = dialogView.findViewById(R.id.btnXoa);
                // Điền dữ liệu vào các TextView
                tvTenSanPham.setText(sanPhamList.get(position).getTenSanPham());
                tvGiaBan.setText("Giá bán: " + showMoney.formatCurrency(sanPhamList.get(position).getGiaBan()) + " VNĐ");
                tvTonKho.setText("Tồn kho: " + sanPhamList.get(position).getTonKho());
                tvMaVach.setText("Mã vạch: " + sanPhamList.get(position).getMaVach());
                tvMoTa.setText("Mô tả: " + sanPhamList.get(position).getMoTa());
                btnNhap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        LayoutInflater inflater = LayoutInflater.from(getContext());
                        View dialogView = inflater.inflate(R.layout.dialog_nhap, null);

                        // Tham chiếu các thành phần
                        EditText etNumberInput = dialogView.findViewById(R.id.etNumberInput);
                        Button btnOk = dialogView.findViewById(R.id.btnOk);
                        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

                        builder.setView(dialogView);
                        AlertDialog dialogNhap = builder.create();
                        dialogNhap.setCancelable(false);

                        // Xử lý nút OK
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String input = etNumberInput.getText().toString().trim();
                                if (input.isEmpty()) {
                                    etNumberInput.setError("Vui lòng nhập số");
                                } else {
                                    int number = Integer.parseInt(input);
                                    sanPhamList.get(position).setTonKho(sanPhamList.get(position).getTonKho() + number);
                                    int update = sanPhamDao.updateProduct(sanPhamList.get(position));
                                    sanPhamAdapter.notifyDataSetChanged();
                                    dialogNhap.dismiss();
                                }
                            }
                        });

                        // Xử lý nút Cancel
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogNhap.dismiss();
                            }
                        });

                        // Hiển thị dialogNhap
                        dialogNhap.show();
                    }
                });

                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setTitle("Xác nhận")
                                .setMessage("Bạn có chắc chắn muốn tiếp tục?");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int delete = sanPhamDao.deleteProduct(sanPhamList.get(position).getMaVach());
                                sanPhamList.clear();
                                sanPhamList.addAll(sanPhamDao.getAllProducts());
                                sanPhamAdapter.notifyDataSetChanged();
                                if (delete > 0) {
                                    Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        // Tạo và hiển thị dialogSP
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        SuaSanPhamDialogFragment dialogSua = new SuaSanPhamDialogFragment();
                        SuaSanPhamDialogFragment dialogSua = SuaSanPhamDialogFragment.newInstance(sanPhamList.get(position));
                        dialogSua.setOnScanResultListener(result -> {
                            EditText etMaVach = dialogSua.getDialog().findViewById(R.id.etMaVach);
                            EditText etTenSanPham = dialogSua.getDialog().findViewById(R.id.etTenSanPham);
                            EditText etGiaBan = dialogSua.getDialog().findViewById(R.id.etGiaBan);
                            EditText etMoTa = dialogSua.getDialog().findViewById(R.id.etMoTa);
                            Button btnSave = dialogSua.getDialog().findViewById(R.id.btnSave);
                            Button btnCancel = dialogSua.getDialog().findViewById(R.id.btnCancel);
                            etMaVach.setText(result);

                            btnSave.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String tenSanPham = etTenSanPham.getText().toString();
                                    int giaBan = Integer.parseInt(etGiaBan.getText().toString());
                                    String moTa = etMoTa.getText().toString();
                                    String maVach = etMaVach.getText().toString();
                                    sanPhamList.get(position).setTenSanPham(tenSanPham);
                                    sanPhamList.get(position).setGiaBan(giaBan);
                                    sanPhamList.get(position).setMoTa(moTa);
                                    sanPhamList.get(position).setMaVach(maVach);
                                    int update = sanPhamDao.updateProduct(sanPhamList.get(position));
                                    sanPhamAdapter.notifyDataSetChanged();
                                    Toast.makeText(getContext(), update > 0 ? "Sửa thành công" : "Sửa thất bại", Toast.LENGTH_SHORT).show();
                                    dialogSua.dismiss();
                                }
                            });
                            btnCancel.setOnClickListener(cancelView -> {
                                dialogSua.dismiss();
                            });
                        });
                        dialogSua.show(getChildFragmentManager(), "sua_dialog");
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogView);
                AlertDialog dialogSP = builder.create();
                dialogSP.getWindow().setBackgroundDrawableResource(R.drawable.dialog_shape);
                dialogSP.show();
            }
        });
        rvSanPham.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvSanPham.setAdapter(sanPhamAdapter);
        spDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DanhMucDTO selectedDanhMuc = (DanhMucDTO) parent.getItemAtPosition(position);
                if (selectedDanhMuc.getMaDanhMuc() == -1) {
                    sanPhamList.clear();
                    sanPhamList.addAll(sanPhamDao.getAllProducts());
                    Toast.makeText(getContext(), "if", Toast.LENGTH_SHORT).show();
                    sanPhamAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "else", Toast.LENGTH_SHORT).show();
                    sanPhamList.clear();
                    sanPhamList.addAll(sanPhamDao.filterProductsByCategory(selectedDanhMuc.getMaDanhMuc()));
                    sanPhamAdapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}