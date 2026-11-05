package DAO;

import model.HoaDon;

/** DAO quản lý hóa đơn bằng mảng. */
public class HoaDonDAO {
    public static HoaDon[] danhSachHoaDon = new HoaDon[100];
    public static int soLuongHoaDon = 0;

    public boolean them(HoaDon hoaDon) {
        if (hoaDon == null || hoaDon.getMaHD() == null
                || timTheoMa(hoaDon.getMaHD()) != null
                || soLuongHoaDon == danhSachHoaDon.length) return false;
        danhSachHoaDon[soLuongHoaDon++] = hoaDon;
        return true;
    }

    public boolean sua(HoaDon hoaDon) {
        if (hoaDon == null) return false;
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (danhSachHoaDon[i].getMaHD().equals(hoaDon.getMaHD())) {
                danhSachHoaDon[i] = hoaDon;
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maHD) {
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (danhSachHoaDon[i].getMaHD().equals(maHD)) {
                for (int j = i; j < soLuongHoaDon - 1; j++) danhSachHoaDon[j] = danhSachHoaDon[j + 1];
                danhSachHoaDon[--soLuongHoaDon] = null;
                return true;
            }
        }
        return false;
    }

    public HoaDon timTheoMa(String maHD) {
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (danhSachHoaDon[i].getMaHD().equals(maHD)) return danhSachHoaDon[i];
        }
        return null;
    }

    public HoaDon[] layDanhSach() {
        HoaDon[] ketQua = new HoaDon[soLuongHoaDon];
        for (int i = 0; i < soLuongHoaDon; i++) ketQua[i] = danhSachHoaDon[i];
        return ketQua;
    }
}
