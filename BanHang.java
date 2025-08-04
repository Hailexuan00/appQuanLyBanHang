package fpoly.hailxph49396.duan1_quanlybanhang;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import fpoly.hailxph49396.duan1_quanlybanhang.Adapter.SPofDHAdapter;
import fpoly.hailxph49396.duan1_quanlybanhang.DAO.ChiTietDonHangDAO;
import fpoly.hailxph49396.duan1_quanlybanhang.DAO.DonHangDAO;
import fpoly.hailxph49396.duan1_quanlybanhang.DAO.SanPhamDAo;
import fpoly.hailxph49396.duan1_quanlybanhang.DTO.ChiTietDonHangDTO;
import fpoly.hailxph49396.duan1_quanlybanhang.DTO.DonHangDTO;
import fpoly.hailxph49396.duan1_quanlybanhang.DTO.SanPhamDTO;
import fpoly.hailxph49396.duan1_quanlybanhang.ShowMoney.ShowMoney;

public class BanHang extends AppCompatActivity {
    private DecoratedBarcodeView barcodeView;
    private Handler handler = new Handler(Looper.getMainLooper());
    private long lastScanTime = 0;  // Lưu thời gian quét lần cuối
    private final long scanDelay = 1000;  // Đặt khoảng thời gian giữa các lần quét (500ms)

    TextView txtTongTien;
    RecyclerView rcvSPofDH;
    ArrayList<ChiTietDonHangDTO> list;
    SPofDHAdapter sPofDHAdapter;
    SanPhamDAo sanPhamDAo;
    ChiTietDonHangDAO chiTietDonHangDAO;
    ChiTietDonHangDTO chiTietDonHangDTO;
    DonHangDAO donHangDAO;
    DonHangDTO donHangDTO;
    SharedPreferences sharedPreferences;
    ShowMoney showMoney;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    int tongTien = 0;
    Button btnThanhToan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ban_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        donHangDAO = new DonHangDAO(BanHang.this);
        barcodeView = findViewById(R.id.barcode_scanner);
        barcodeView.decodeContinuous(callback);
        rcvSPofDH = findViewById(R.id.rcvSPofDH);
        txtTongTien = findViewById(R.id.txtTongTien);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        list = new ArrayList<>();
        donHangDTO = donHangDAO.getLatestDonHang();
        chiTietDonHangDAO = new ChiTietDonHangDAO(this);
        sPofDHAdapter = new SPofDHAdapter(this, list, new SPofDHAdapter.OnItemActionListener() {
            @Override
            public void onAction(int position, String actionType) {
                switch (actionType) {
                    case "cong":
                        list.get(position).setSoLuong(list.get(position).getSoLuong() + 1);
                        long updateCong = chiTietDonHangDAO.updateChiTietDonHang(list.get(position));
                        Toast.makeText(BanHang.this, updateCong > 0 ? "Cập nhật thành công" : "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        list.clear();
                        list.addAll(chiTietDonHangDAO.getCTDHByIdDonHang(donHangDTO.getMaDonHang()));
                        sPofDHAdapter.notifyDataSetChanged();
                        getTongTien();
                        break;
                    case "tru":
                        if (list.get(position).getSoLuong() == 1) {
                            long delete = chiTietDonHangDAO.deleteChiTietDonHang(list.get(position).getIdChiTietDonHang());
                            Toast.makeText(BanHang.this, delete > 0 ? "Xóa thành công" : "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            tongTien = tongTien - sanPhamDAo.findProductById(list.get(position).getIdSanPham()).getGiaBan();
                            txtTongTien.setText( showMoney.formatCurrency(tongTien) + "VNĐ");
                            list.clear();
                            list.addAll(chiTietDonHangDAO.getCTDHByIdDonHang(donHangDTO.getMaDonHang()));

                        }else {
                            list.get(position).setSoLuong(list.get(position).getSoLuong() - 1);
                            long updateTru = chiTietDonHangDAO.updateChiTietDonHang(list.get(position));
                            Toast.makeText(BanHang.this, updateTru > 0 ? "Cập nhật thành công" : "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(chiTietDonHangDAO.getCTDHByIdDonHang(donHangDTO.getMaDonHang()));
                        }
                        sPofDHAdapter.notifyDataSetChanged();
                        getTongTien();
                        break;
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcvSPofDH.setLayoutManager(layoutManager);
        rcvSPofDH.setAdapter(sPofDHAdapter);
        sanPhamDAo = new SanPhamDAo(this);
        btnThanhToan.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
            String user = sharedPreferences.getString("USERNAME", "");
            donHangDTO.setThanhTien(tongTien);
            donHangDTO.setUsername(user);
            try {
                donHangDTO.setNgay(sdf.parse(calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                        (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR)));
            } catch (Exception e) {
                Toast.makeText(BanHang.this, "Sai ngày", Toast.LENGTH_SHORT).show();
            }
            donHangDTO.setGio(calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                    calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
            donHangDTO.setTrangThai(1);
            int check = donHangDAO.updateOrder(donHangDTO);
            for (ChiTietDonHangDTO item : list) {
                SanPhamDTO sp = sanPhamDAo.findProductById(item.getIdSanPham());
                sp.setTonKho(sp.getTonKho() - item.getSoLuong());
                int update = sanPhamDAo.updateProduct(sp);
                Toast.makeText(BanHang.this, update > 0 ? "Cập nhật ton kho thành công" + sp.getTonKho(): "Cập nhật ton kho thất bại", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(BanHang.this, check > 0 ? "Xác nhận đơn hàng thành công" : "Xác nhận đơn hàng thất bại", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastScanTime > scanDelay) {
                lastScanTime = currentTime;
                String code = result.getText();
                processBarcode(code);
            }
        }

        private void processBarcode(final String code) {
            new Thread(() -> {
                SanPhamDTO sanPhamDTO = sanPhamDAo.findProductByBarcode(code);
                runOnUiThread(() -> {
                    playBeepSound(); // Phát âm thanh thông báo mỗi lần quét
                    if (sanPhamDTO != null) {
                        boolean chk = false;
                        for (ChiTietDonHangDTO item : list) {
                            if (item.getIdSanPham() == sanPhamDTO.getMaSanPham()) {
                                item.setSoLuong(item.getSoLuong() + 1);
                                int update = chiTietDonHangDAO.updateChiTietDonHang(item);
                                if (update > 0) {
                                    Toast.makeText(BanHang.this, "Cập nhật thành công" + update, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(BanHang.this, "Cập nhật thất bại" + update, Toast.LENGTH_SHORT).show();
                                }
                                chk = true;
                                break;
                            }
                        }
                        if (chk) {
                            sPofDHAdapter.notifyDataSetChanged();
                            getTongTien();
                        } else {
                            chiTietDonHangDTO = new ChiTietDonHangDTO();
                            chiTietDonHangDTO.setIdSanPham(sanPhamDTO.getMaSanPham());
                            chiTietDonHangDTO.setIdDonHang(donHangDTO.getMaDonHang());
                            chiTietDonHangDTO.setSoLuong(1);
                            long add = chiTietDonHangDAO.insertChiTietDonHang(chiTietDonHangDTO);
                            list.clear();
                            list.addAll(chiTietDonHangDAO.getCTDHByIdDonHang(donHangDTO.getMaDonHang()));
                            if (add > 0) {
                                Toast.makeText(BanHang.this, "Thêm sản phẩm thành công" + add, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(BanHang.this, "Thêm sản phẩm thất bại" + add, Toast.LENGTH_SHORT).show();
                            }
                            sPofDHAdapter.notifyDataSetChanged();
                            getTongTien();
                        }
                    } else {
                        Toast.makeText(BanHang.this, "Không tìm thấy sản phẩm với mã vạch " + code, Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        }
    };

    public int getTongTien() {
        tongTien = 0;
        if (list.isEmpty()) {
            tongTien = 0;
        }else {
            for (ChiTietDonHangDTO item : list) {
                int giaSP = sanPhamDAo.findProductById(item.getIdSanPham()).getGiaBan() * item.getSoLuong();
                tongTien += giaSP;
                txtTongTien.setText( showMoney.formatCurrency(tongTien) + "VNĐ");
            }
        }
        return tongTien;
    }

    private void playBeepSound() {
        ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
//    @Override
//    public void onBackPressed() {
//        long delete = donHangDAO.deleteDonHang(donHangDTO.getMaDonHang());
//        Toast.makeText(this, delete > 0 ? "Xóa thành công" : "Xóa thất bại", Toast.LENGTH_SHORT).show();
//        super.onBackPressed();
//    }
}
