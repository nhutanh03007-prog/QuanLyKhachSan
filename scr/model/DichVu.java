package model;

import java.math.BigDecimal;

public class DichVu {
    private String maDichVu, tenDichVu, loaiDichVu, donViTinh;
    private BigDecimal gia = BigDecimal.ZERO;
    private String trangThai, moTa;

    public DichVu() { }
    public DichVu(String maDichVu, String tenDichVu, String loaiDichVu, String donViTinh,
                  BigDecimal gia, String trangThai, String moTa) {
        this.maDichVu = maDichVu; this.tenDichVu = tenDichVu; this.loaiDichVu = loaiDichVu;
        this.donViTinh = donViTinh; this.gia = gia; this.trangThai = trangThai; this.moTa = moTa;
    }
    public void nhapThongTin() { }
    public void xuatThongTin() { System.out.println(this); }
    public void themDichVu() { System.out.println("Da them dich vu: " + tenDichVu); }
    public void suaDichVu() { System.out.println("Da cap nhat dich vu: " + tenDichVu); }
    public void xoaDichVu() { System.out.println("Da xoa dich vu: " + tenDichVu); }
    public boolean kiemTra(String tuKhoa) { return tuKhoa != null && ((maDichVu != null && maDichVu.contains(tuKhoa)) || (tenDichVu != null && tenDichVu.contains(tuKhoa))); }
    public BigDecimal thongKeDoanhThu(int soLuong) { return gia.multiply(BigDecimal.valueOf(soLuong)); }
    public String getMaDichVu() { return maDichVu; }
    public void setMaDichVu(String v) { maDichVu = v; }
    public String getTenDichVu() { return tenDichVu; }
    public void setTenDichVu(String v) { tenDichVu = v; }
    public String getLoaiDichVu() { return loaiDichVu; }
    public void setLoaiDichVu(String v) { loaiDichVu = v; }
    public String getDonViTinh() { return donViTinh; }
    public void setDonViTinh(String v) { donViTinh = v; }
    public BigDecimal getGia() { return gia; }
    public void setGia(BigDecimal v) { gia = v; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String v) { trangThai = v; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String v) { moTa = v; }
    @Override public String toString() { return "Ma dich vu: " + maDichVu + ", ten: " + tenDichVu + ", loai: " + loaiDichVu + ", don vi: " + donViTinh + ", gia: " + gia + ", trang thai: " + trangThai; }
}
