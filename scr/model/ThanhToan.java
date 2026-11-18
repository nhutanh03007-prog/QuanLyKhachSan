package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ThanhToan {
    private String maThanhToan;
    private String maHD;
    private BigDecimal soTien;
    private String phuongThuc;
    private LocalDate ngayThanhToan;
    private TrangThaiThanhToan trangThai;

    public ThanhToan() {
    }

    public ThanhToan(String maThanhToan, String maHD, BigDecimal soTien, String phuongThuc,
            LocalDate ngayThanhToan, TrangThaiThanhToan trangThai) {
        this.maThanhToan = maThanhToan;
        this.maHD = maHD;
        this.soTien = soTien;
        this.phuongThuc = phuongThuc;
        this.ngayThanhToan = ngayThanhToan;
        this.trangThai = trangThai;
    }

    public String getMaThanhToan() {
        return maThanhToan;
    }

    public void setMaThanhToan(String v) {
        maThanhToan = v;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String v) {
        maHD = v;
    }

    public BigDecimal getSoTien() {
        return soTien;
    }

    public void setSoTien(BigDecimal v) {
        soTien = v;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(String v) {
        phuongThuc = v;
    }

    public LocalDate getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(LocalDate v) {
        ngayThanhToan = v;
    }

    public TrangThaiThanhToan getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiThanhToan v) {
        trangThai = v;
    }

    @Override
    public String toString() {
        return "ThanhToan{maThanhToan='" + maThanhToan + "', maHD='" + maHD + "', soTien=" + soTien
                + ", phuongThuc='"
                + phuongThuc + "', ngayThanhToan=" + ngayThanhToan + ", trangThai=" + trangThai + '}';
    }
}
