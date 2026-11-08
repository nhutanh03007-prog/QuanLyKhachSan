package DAO;

import model.ThanhToan;

public class ThanhToanDAO {
    public static ThanhToan[] danhSachThanhToan = new ThanhToan[100];
    public static int soLuongThanhToan = 0;

    public boolean them(ThanhToan thanhToan) {
        if (thanhToan == null || thanhToan.getMaThanhToan() == null
                || timTheoMa(thanhToan.getMaThanhToan()) != null
                || soLuongThanhToan == danhSachThanhToan.length) return false;
        danhSachThanhToan[soLuongThanhToan++] = thanhToan;
        return true;
    }

    public boolean sua(ThanhToan thanhToan) {
        if (thanhToan == null) return false;
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (danhSachThanhToan[i].getMaThanhToan().equals(thanhToan.getMaThanhToan())) {
                danhSachThanhToan[i] = thanhToan;
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maThanhToan) {
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (danhSachThanhToan[i].getMaThanhToan().equals(maThanhToan)) {
                for (int j = i; j < soLuongThanhToan - 1; j++) danhSachThanhToan[j] = danhSachThanhToan[j + 1];
                danhSachThanhToan[--soLuongThanhToan] = null;
                return true;
            }
        }
        return false;
    }

    public ThanhToan timTheoMa(String maThanhToan) {
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (danhSachThanhToan[i].getMaThanhToan().equals(maThanhToan)) return danhSachThanhToan[i];
        }
        return null;
    }

    public ThanhToan[] layDanhSach() {
        ThanhToan[] ketQua = new ThanhToan[soLuongThanhToan];
        for (int i = 0; i < soLuongThanhToan; i++) ketQua[i] = danhSachThanhToan[i];
        return ketQua;
    }
}