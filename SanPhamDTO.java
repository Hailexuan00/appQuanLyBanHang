package fpoly.hailxph49396.duan1_quanlybanhang.DTO;

import java.io.Serializable;

public class SanPhamDTO implements Serializable {
    private int maSanPham;
    private int maDanhMuc;
    private String tenSanPham;
    private int giaBan;
    private int tonKho;
    private String maVach;
    private String moTa;

    public SanPhamDTO(){

    }

    public SanPhamDTO(int maSanPham, int maDanhMuc, String tenSanPham, int giaBan, int tonKho, String maVach, String moTa) {
        this.maSanPham = maSanPham;
        this.maDanhMuc = maDanhMuc;
        this.tenSanPham = tenSanPham;
        this.giaBan = giaBan;
        this.tonKho = tonKho;
        this.maVach = maVach;
        this.moTa = moTa;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public int getTonKho() {
        return tonKho;
    }

    public void setTonKho(int tonKho) {
        this.tonKho = tonKho;
    }

    public String getMaVach() {
        return maVach;
    }

    public void setMaVach(String maVach) {
        this.maVach = maVach;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
