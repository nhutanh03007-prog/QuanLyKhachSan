package model;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ChiTietDichVu {
    private String maDatPhong, maDichVu;
    private LocalDate ngayDatDichVu, ngayNhan;
    private int soLuong;
    private BigDecimal donGia = BigDecimal.ZERO, thanhTien = BigDecimal.ZERO;
    public ChiTietDichVu() { }
    public ChiTietDichVu(String maDatPhong, String maDichVu, LocalDate ngayDatDichVu, LocalDate ngayNhan, int soLuong, BigDecimal donGia) {
        this.maDatPhong=maDatPhong; this.maDichVu=maDichVu; this.ngayDatDichVu=ngayDatDichVu; this.ngayNhan=ngayNhan; this.soLuong=soLuong; this.donGia=donGia; tinhThanhTien();
    }
    public void themChiTiet(){tinhThanhTien();} public void suaChiTiet(){tinhThanhTien();}
    public void xoaChiTiet(){maDatPhong="";maDichVu="";soLuong=0;donGia=BigDecimal.ZERO;thanhTien=BigDecimal.ZERO;}
    public void tinhThanhTien(){thanhTien=donGia.multiply(BigDecimal.valueOf(soLuong));}
    public String getMaDatPhong(){return maDatPhong;} public void setMaDatPhong(String v){maDatPhong=v;}
    public String getMaDichVu(){return maDichVu;} public void setMaDichVu(String v){maDichVu=v;}
    public LocalDate getNgayDatDichVu(){return ngayDatDichVu;} public void setNgayDatDichVu(LocalDate v){ngayDatDichVu=v;}
    public LocalDate getNgayNhan(){return ngayNhan;} public void setNgayNhan(LocalDate v){ngayNhan=v;}
    public int getSoLuong(){return soLuong;} public void setSoLuong(int v){soLuong=v;tinhThanhTien();}
    public BigDecimal getDonGia(){return donGia;} public void setDonGia(BigDecimal v){donGia=v;tinhThanhTien();}
    public BigDecimal getThanhTien(){return thanhTien;}
    @Override public String toString(){return "Ma dat phong: "+maDatPhong+", ma dich vu: "+maDichVu+", so luong: "+soLuong+", don gia: "+donGia+", thanh tien: "+thanhTien;}
}
