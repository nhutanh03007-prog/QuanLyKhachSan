package DAO;

import model.HoaDon;

/** DAO quản lý hóa đơn bằng mảng. */
public class HoaDonDAO {
    public static HoaDon[] danhSachHoaDon = new HoaDon[100];
    public static int soLuongHoaDon = 0;

    public boolean them(HoaDon hoaDon) {
        if (hoaDon == null || rong(hoaDon.getMaHD())
                || timTheoMa(hoaDon.getMaHD()) != null
                || soLuongHoaDon == danhSachHoaDon.length) return false;
        danhSachHoaDon[soLuongHoaDon++] = hoaDon;
        return true;
    }

    public boolean sua(HoaDon hoaDon) {
        if (hoaDon == null || rong(hoaDon.getMaHD())) return false;
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (danhSachHoaDon[i] != null && hoaDon.getMaHD().equals(danhSachHoaDon[i].getMaHD())) {
                danhSachHoaDon[i] = hoaDon;
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maHD) {
        if (rong(maHD)) return false;
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (danhSachHoaDon[i] != null && maHD.equals(danhSachHoaDon[i].getMaHD())) {
                 System.arraycopy(danhSachHoaDon, i + 1, danhSachHoaDon, i,
                        soLuongHoaDon - i - 1);
                danhSachHoaDon[--soLuongHoaDon] = null;
                return true;
            }
        }
        return false;
    }
    public HoaDon timTheoMa(String maHD) {
        if (rong(maHD)) return null;
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (danhSachHoaDon[i] != null && maHD.equals(danhSachHoaDon[i].getMaHD())) return danhSachHoaDon[i];
        }
        return null;
    }
    public HoaDon timTheoMaDatPhong(String maDatPhong) {
        if (rong(maDatPhong)) return null;
        for (int i=0;i<soLuongHoaDon;i++) if (danhSachHoaDon[i]!=null && maDatPhong.equals(danhSachHoaDon[i].getMaDatPhong())) return danhSachHoaDon[i];
        return null;
    }
    private boolean rong(String s) { return s == null || s.trim().isEmpty(); }

    public HoaDon[] layDanhSach() {
        HoaDon[] ketQua = new HoaDon[soLuongHoaDon];
        System.arraycopy(danhSachHoaDon, 0, ketQua, 0, soLuongHoaDon);
        return ketQua;
    }
}
