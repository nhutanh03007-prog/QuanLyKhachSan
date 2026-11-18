package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HoaDon {
    private String maHD;
    private String maDatPhong;
    private LocalDate ngayLap;
    private LocalDate ngayThanhToan;
    private BigDecimal tongGia;
    private BigDecimal giamGia;
    private BigDecimal thanhTien;
    private String phuongThucTT;
    private TrangThaiThanhToan trangThai;

    public HoaDon() {
    }

    public HoaDon(String maHD, String maDatPhong, LocalDate ngayLap, BigDecimal tongGia, BigDecimal giamGia) {
        this.maHD = maHD;
        this.maDatPhong = maDatPhong;
        this.ngayLap = ngayLap;
        this.tongGia = tongGia;
        this.giamGia = giamGia;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String v) {
        maHD = v;
    }

    public String getMaDatPhong() {
        return maDatPhong;
    }

    public void setMaDatPhong(String v) {
        maDatPhong = v;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate v) {
        ngayLap = v;
    }

    public LocalDate getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(LocalDate v) {
        ngayThanhToan = v;
    }

    public BigDecimal getTongGia() {
        return tongGia;
    }

    public void setTongGia(BigDecimal v) {
        tongGia = v;
    }

    public BigDecimal getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(BigDecimal v) {
        giamGia = v;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal v) {
        thanhTien = v;
    }

    public String getPhuongThucTT() {
        return phuongThucTT;
    }

    public void setPhuongThucTT(String v) {
        phuongThucTT = v;
    }

    public TrangThaiThanhToan getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiThanhToan v) {
        trangThai = v;
    }

    @Override
    public String toString() {
        return "HoaDon{maHD='" + maHD + "', maDatPhong='" + maDatPhong + "', tongGia=" + tongGia + ", giamGia="
                + giamGia + ", thanhTien=" + thanhTien + ", trangThai=" + trangThai + '}';
    }
}
