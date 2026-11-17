package service;

import DAO.KhachHangDAO;
import INTERFACE.IKhachHang;
import model.KhachHang;

public class QLKhachHang implements IKhachHang {
    private KhachHangDAO khachHangDAO;

    public QLKhachHang() {
        this.khachHangDAO = new KhachHangDAO();
    }

    @Override
    public boolean themKhachHang(KhachHang khachHang) {
        return khachHangDAO.them(khachHang);
    }

    @Override
    public KhachHang[] layDanhSachKhachHang() {
        return khachHangDAO.layDanhSach();
    }

    @Override
    public KhachHang[] layDanhSachKhachHangTheoLoai(String loaiKhach) {
        KhachHang[] tatCa = khachHangDAO.layDanhSach();
        int count = 0;
        for (KhachHang kh : tatCa) {
            if (kh != null && kh.getLoaiKhach().equalsIgnoreCase(loaiKhach)) {
                count++;
            }
        }
        KhachHang[] ketQua = new KhachHang[count];
        int index = 0;
        for (KhachHang kh : tatCa) {
            if (kh != null && kh.getLoaiKhach().equalsIgnoreCase(loaiKhach)) {
                ketQua[index++] = kh;
            }
        }
        return ketQua;
    }

    @Override
    public KhachHang timKhachHangTheoMa(String maKH) {
        return khachHangDAO.timTheoMa(maKH);
    }

    @Override
    public boolean suaKhachHang(KhachHang khachHang) {
        return khachHangDAO.sua(khachHang);
    }

    @Override
    public boolean xoaKhachHang(String maKH) {
        return khachHangDAO.xoa(maKH);
    }

    @Override
    public KhachHang[] timKhachHangTheoTen(String hoTen) {
        KhachHang[] tatCa = khachHangDAO.layDanhSach();
        int count = 0;
        for (KhachHang kh : tatCa) {
            if (kh != null && kh.getHoTen().toLowerCase().contains(hoTen.toLowerCase())) {
                count++;
            }
        }
        KhachHang[] ketQua = new KhachHang[count];
        int index = 0;
        for (KhachHang kh : tatCa) {
            if (kh != null && kh.getHoTen().toLowerCase().contains(hoTen.toLowerCase())) {
                ketQua[index++] = kh;
            }
        }
        return ketQua;
    }

    @Override
    public int demSoLuongKhachHang() {
        return KhachHangDAO.soLuongKhachHang;
    }

    @Override
    public int demSoLuongKhachHangTheoLoai(String loaiKhach) {
        return layDanhSachKhachHangTheoLoai(loaiKhach).length;
    }
}