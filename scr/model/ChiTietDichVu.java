package model;
import java.time.LocalDate;

public class ChiTietDichVu {
    private String maDatPhong;
    private String maDichVu;
    private LocalDate ngayDatDichVu;
    private LocalDate ngayNhan;
    private int soLuong;
    private double donGia;
    private double thanhTien;

    public ChiTietDichVu() { }

    public ChiTietDichVu(String maDatPhong, String maDichVu, LocalDate ngayDatDichVu,
                         LocalDate ngayNhan, int soLuong, double donGia) {
        this.maDatPhong = maDatPhong;
        this.maDichVu = maDichVu;
        this.ngayDatDichVu = ngayDatDichVu;
        this.ngayNhan = ngayNhan;
        this.soLuong = soLuong;
        this.donGia = donGia;
        tinhThanhTien();
    }

    public void themChiTiet() { tinhThanhTien(); }
    public void suaChiTiet() { tinhThanhTien(); }
    public void xoaChiTiet() {
        maDatPhong = "";
        maDichVu = "";
        soLuong = 0;
        donGia = 0;
        thanhTien = 0;
    }
    public void tinhThanhTien() { thanhTien = soLuong * donGia; }

    public String getMaDatPhong() { return maDatPhong; }
    public void setMaDatPhong(String maDatPhong) { this.maDatPhong = maDatPhong; }
    public String getMaDichVu() { return maDichVu; }
    public void setMaDichVu(String maDichVu) { this.maDichVu = maDichVu; }
    public LocalDate getNgayDatDichVu() { return ngayDatDichVu; }
    public void setNgayDatDichVu(LocalDate ngayDatDichVu) { this.ngayDatDichVu = ngayDatDichVu; }
    public LocalDate getNgayNhan() { return ngayNhan; }
    public void setNgayNhan(LocalDate ngayNhan) { this.ngayNhan = ngayNhan; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; tinhThanhTien(); }
    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; tinhThanhTien(); }
    public double getThanhTien() { return thanhTien; }

    @Override
    public String toString() {
        return "Ma dat phong: " + maDatPhong + ", ma dich vu: " + maDichVu
                + ", so luong: " + soLuong + ", don gia: " + donGia
                + ", thanh tien: " + thanhTien;
    }
}
