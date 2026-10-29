package model;
import java.time.LocalDate;

public class ThanhToan {
    private String maThanhToan;
    private String maHD;
    private double soTien;
    private String phuongThuc;
    private LocalDate ngayThanhToan;
    private TrangThaiThanhToan trangThai;

    public ThanhToan() {
        trangThai = TrangThaiThanhToan.CHUA_THANH_TOAN;
    }

    public ThanhToan(String maThanhToan, String maHD, double soTien, String phuongThuc) {
        this.maThanhToan = maThanhToan;
        this.maHD = maHD;
        this.soTien = soTien;
        this.phuongThuc = phuongThuc;
        this.trangThai = TrangThaiThanhToan.CHUA_THANH_TOAN;
    }

    public void thucHienThanhToan() {
        trangThai = TrangThaiThanhToan.DA_THANH_TOAN;
        ngayThanhToan = LocalDate.now();
    }

    public String getMaThanhToan() { return maThanhToan; }
    public void setMaThanhToan(String maThanhToan) { this.maThanhToan = maThanhToan; }
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }
    public double getSoTien() { return soTien; }
    public void setSoTien(double soTien) { this.soTien = soTien; }
    public String getPhuongThuc() { return phuongThuc; }
    public void setPhuongThuc(String phuongThuc) { this.phuongThuc = phuongThuc; }
    public LocalDate getNgayThanhToan() { return ngayThanhToan; }
    public TrangThaiThanhToan getTrangThai() { return trangThai; }
    public void setTrangThai(TrangThaiThanhToan trangThai) { this.trangThai = trangThai; }
}