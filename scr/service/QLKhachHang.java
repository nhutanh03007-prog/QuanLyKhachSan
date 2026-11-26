package service;

import DAO.DatPhongDAO;
import DAO.KhachHangDAO;
import INTERFACE.IKhachHang;
import model.KhachHang;

public class QLKhachHang implements IKhachHang {
    private final KhachHangDAO khachHangDAO;
    private final DatPhongDAO datPhongDAO;

    public QLKhachHang() {
        this.khachHangDAO = new KhachHangDAO();
        this.datPhongDAO = new DatPhongDAO();
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
        if (loaiKhach == null || loaiKhach.trim().isEmpty()) {
            return new KhachHang[0];
        }
        String loai = loaiKhach.trim();
        KhachHang[] tatCa = khachHangDAO.layDanhSach();
        int count = 0;
        for (KhachHang kh : tatCa) {
            if (kh != null && kh.getLoaiKhach() != null && kh.getLoaiKhach().equalsIgnoreCase(loai)) {
                count++;
            }
        }
        KhachHang[] ketQua = new KhachHang[count];
        int index = 0;
        for (KhachHang kh : tatCa) {
            if (kh != null && kh.getLoaiKhach() != null && kh.getLoaiKhach().equalsIgnoreCase(loai)) {
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
        if (datPhongDAO.coPhieuHoatDongChoKhachHang(maKH)) {
            System.out.println("Loi: Khong the xoa khach hang dang co phieu dat phong hoat dong!");
            return false;
        }
        return khachHangDAO.xoa(maKH);
    }

    @Override
    public KhachHang[] timKhachHangTheoTen(String hoTen) {
        if (hoTen == null || hoTen.trim().isEmpty()) {
            return new KhachHang[0];
        }
        String key = hoTen.trim().toLowerCase();
        KhachHang[] tatCa = khachHangDAO.layDanhSach();
        int count = 0;
        for (KhachHang kh : tatCa) {
            if (kh != null && kh.getHoTen() != null && kh.getHoTen().toLowerCase().contains(key)) {
                count++;
            }
        }
        KhachHang[] ketQua = new KhachHang[count];
        int index = 0;
        for (KhachHang kh : tatCa) {
            if (kh != null && kh.getHoTen() != null && kh.getHoTen().toLowerCase().contains(key)) {
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
