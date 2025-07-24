package fpoly.hailxph49396.duan1_quanlybanhang.DTO;

import java.util.Date;

public class DonHangDTO {
    int maDonHang;
    String username;
    String soDienThoaiKH;
    int thanhTien;
    Date ngay;
    String gio;
    int trangThai;

    public DonHangDTO(int maDonHang, String username, String soDienThoaiKH, int thanhTien, Date ngay, String gio, int trangThai) {
        this.maDonHang = maDonHang;
        this.username = username;
        this.soDienThoaiKH = soDienThoaiKH;
        this.thanhTien = thanhTien;
        this.ngay = ngay;
        this.gio = gio;
        this.trangThai = trangThai;
    }
    public DonHangDTO() {
    }

    public int getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(int maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSoDienThoaiKH() {
        return soDienThoaiKH;
    }

    public void setSoDienThoaiKH(String soDienThoaiKH) {
        this.soDienThoaiKH = soDienThoaiKH;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
