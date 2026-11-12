package DAO;

import model.KhachHang;

public class KhachHangDAO {
    public static KhachHang[] danhSachKhachHang = new KhachHang[100];
    public static int soLuongKhachHang = 0;

    public boolean them(KhachHang khachHang) {
        if (khachHang == null || khachHang.getMaKH() == null 
            || timTheoMa(khachHang.getMaKH()) != null 
            || soLuongKhachHang == danhSachKhachHang.length) {
            return false;
        }
        danhSachKhachHang[soLuongKhachHang] = khachHang;
        soLuongKhachHang++;
        return true;
    }

    public boolean sua(KhachHang khachHang) {
        if (khachHang == null) return false;
        for (int i = 0; i < soLuongKhachHang; i++) {
            if (danhSachKhachHang[i].getMaKH().equals(khachHang.getMaKH())) {
                danhSachKhachHang[i] = khachHang;
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maKhachHang) {
        for (int i = 0; i < soLuongKhachHang; i++) {
            if (danhSachKhachHang[i].getMaKH().equals(maKhachHang)) {
                System.arraycopy(danhSachKhachHang, i + 1, danhSachKhachHang, i, soLuongKhachHang - i - 1);
                danhSachKhachHang[--soLuongKhachHang] = null;
                return true;
            }
        }
        return false;
    }

    public KhachHang timTheoMa(String maKhachHang) {
        for (int i = 0; i < soLuongKhachHang; i++) {
            if (danhSachKhachHang[i].getMaKH().equals(maKhachHang)) {
                return danhSachKhachHang[i];
            }
        }
        return null;
    }

    public KhachHang[] layDanhSach() {
        KhachHang[] ketQua = new KhachHang[soLuongKhachHang];
        System.arraycopy(danhSachKhachHang, 0, ketQua, 0, soLuongKhachHang);
        return ketQua;
    }
}