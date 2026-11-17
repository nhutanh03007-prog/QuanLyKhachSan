package INTERFACE;

import model.KhachHang;

public interface IKhachHang {
    boolean themKhachHang(KhachHang khachHang);

    KhachHang[] layDanhSachKhachHang();

    KhachHang[] layDanhSachKhachHangTheoLoai(String loaiKhach);

    KhachHang timKhachHangTheoMa(String maKH);

    boolean suaKhachHang(KhachHang khachHang);

    boolean xoaKhachHang(String maKH);

    KhachHang[] timKhachHangTheoTen(String hoTen);

    int demSoLuongKhachHang();

    int demSoLuongKhachHangTheoLoai(String loaiKhach);
}