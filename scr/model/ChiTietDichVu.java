package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ChiTietDichVu {
    private String maDatPhong;
    private String maDichVu;
    private LocalDate ngayDatDichVu;
    private LocalDate ngayNhan;
    private int soLuong;
    private BigDecimal donGia;
    private BigDecimal thanhTien;

    public ChiTietDichVu() {
    }

    public ChiTietDichVu(String maDatPhong, String maDichVu, LocalDate ngayDatDichVu, LocalDate ngayNhan,
            int soLuong, BigDecimal donGia) {
        this.maDatPhong = maDatPhong;
        this.maDichVu = maDichVu;
        this.ngayDatDichVu = ngayDatDichVu;
        this.ngayNhan = ngayNhan;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getMaDatPhong() {
        return maDatPhong;
    }

    public void setMaDatPhong(String v) {
        maDatPhong = v;
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String v) {
        maDichVu = v;
    }

    public LocalDate getNgayDatDichVu() {
        return ngayDatDichVu;
    }

    public void setNgayDatDichVu(LocalDate v) {
        ngayDatDichVu = v;
    }

    public LocalDate getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(LocalDate v) {
        ngayNhan = v;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int v) {
        soLuong = v;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal v) {
        donGia = v;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal v) {
        thanhTien = v;
    }

    @Override
    public String toString() {
        return "ChiTietDichVu{maDatPhong='" + maDatPhong + "', maDichVu='" + maDichVu
                + "', soLuong=" + soLuong
                + ", donGia=" + donGia + ", thanhTien=" + thanhTien + '}';
    }
}
