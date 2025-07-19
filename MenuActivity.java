package fpoly.hailxph49396.quanlybanhang;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import fpoly.hailxph49396.quanlybanhang.Fragment.CaNhanFragment;
import fpoly.hailxph49396.quanlybanhang.Fragment.GioHangFragment;
import fpoly.hailxph49396.quanlybanhang.Fragment.ListSanPhamFragment;
import fpoly.hailxph49396.quanlybanhang.Fragment.MatKhauFragment;
import fpoly.hailxph49396.quanlybanhang.Fragment.NhanVienFragment;
import fpoly.hailxph49396.quanlybanhang.Fragment.SanPhamFragment;
import fpoly.hailxph49396.quanlybanhang.Fragment.ThongKeFragment;
import fpoly.hailxph49396.quanlybanhang.Fragment.TrangChuFragment;

public class MenuActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbar = findViewById(R.id.toolbar1);
        drawer = findViewById(R.id.drawer_layout);


        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        FragmentManager manager = getSupportFragmentManager();
        setTitle("Quản lý Sản Phẩm");
        SanPhamFragment sanPhamFragment = new SanPhamFragment();
        manager.beginTransaction()
                .replace(R.id.flContent, sanPhamFragment)
                .commit();

        NavigationView nv = findViewById(R.id.nvView);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager manager = getSupportFragmentManager();
                if (item.getItemId() == R.id.nav_sanPham) {
                    setTitle("Quản lý Sản Phẩm");
                    SanPhamFragment sanPhamFragment = new SanPhamFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent, sanPhamFragment)
                            .commit();
                } else if (item.getItemId() == R.id.nav_nhanVien) {
                    setTitle("Quản lý Đơn Hàng");
                    NhanVienFragment nhanVienFragment = new NhanVienFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent, nhanVienFragment)
                            .commit();
                } else if (item.getItemId() == R.id.nav_gioHang) {
                    setTitle("Quản lý Nhân Viên");
                    GioHangFragment gioHangFragment = new GioHangFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent, gioHangFragment)
                            .commit();
                }
                else if (item.getItemId() == R.id.nav_thongKe) {
                    setTitle("Thống kê doanh thu");
                    ThongKeFragment thongKeFragment = new ThongKeFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent, thongKeFragment)
                            .commit();
                }
                else if (item.getItemId() == R.id.sub_Pass) {
                    setTitle("Đổi mật khẩu");
                    MatKhauFragment matKhauFragment = new MatKhauFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent, matKhauFragment)
                            .commit();
                }
                else if (item.getItemId() == R.id.sub_Logout) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

                drawer.closeDrawers();
                return true;
            }
        });
        BottomNavigationView bn =  findViewById(R.id.bottom_navigation);
        bn.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager manager = getSupportFragmentManager();
                if (menuItem.getItemId() == R.id.bottom_shop) {
                    setTitle("Danh sách sản phẩm");
                    ListSanPhamFragment listSanPhamFragment = new ListSanPhamFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent, listSanPhamFragment)
                            .commit();
                } else if (menuItem.getItemId() == R.id.bottom_home) {
                    setTitle("Trang chủ");
                    TrangChuFragment trangChuFragment = new TrangChuFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent, trangChuFragment)
                            .commit();
                } else if (menuItem.getItemId() == R.id.bottom_account) {
                    setTitle("Thông tin cá nhân");
                    CaNhanFragment caNhanFragment = new CaNhanFragment();
                    manager.beginTransaction()
                            .replace(R.id.flContent, caNhanFragment)
                            .commit();
                }
                return false;
            }
        });
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}