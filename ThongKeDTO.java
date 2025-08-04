package fpoly.hailxph49396.duan1_quanlybanhang.DTO;

public class ThongKeDTO {
    private String tenSP;
    private int soLuong;

    public ThongKeDTO() {
    }

    public ThongKeDTO(String tenSP, int soLuong) {
        this.tenSP = tenSP;
        this.soLuong = soLuong;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
