package DAO;

import model.ThanhToan;

public class ThanhToanDAO {
    public static ThanhToan[] danhSachThanhToan = new ThanhToan[100];
    public static int soLuongThanhToan = 0;

    public boolean them(ThanhToan thanhToan) {
        if (thanhToan == null || rong(thanhToan.getMaThanhToan())
                || timTheoMa(thanhToan.getMaThanhToan()) != null
                || soLuongThanhToan == danhSachThanhToan.length) return false;
        danhSachThanhToan[soLuongThanhToan++] = thanhToan;
        return true;
    }

    public boolean sua(ThanhToan thanhToan) {
        if (thanhToan == null || rong(thanhToan.getMaThanhToan())) return false;
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (danhSachThanhToan[i] != null && thanhToan.getMaThanhToan().equals(danhSachThanhToan[i].getMaThanhToan())) {
                danhSachThanhToan[i] = thanhToan;
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maThanhToan) {
        if (rong(maThanhToan)) return false;
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (danhSachThanhToan[i] != null && maThanhToan.equals(danhSachThanhToan[i].getMaThanhToan())) {
                System.arraycopy(danhSachThanhToan, i + 1, danhSachThanhToan, i,
                        soLuongThanhToan - i - 1);
                danhSachThanhToan[--soLuongThanhToan] = null;
                return true;
            }
        }
        return false;
    }

    public ThanhToan timTheoMa(String maThanhToan) {
        if (rong(maThanhToan)) return null;
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (danhSachThanhToan[i] != null && maThanhToan.equals(danhSachThanhToan[i].getMaThanhToan())) return danhSachThanhToan[i];
        }
        return null;
    }
    public ThanhToan[] layTheoMaHD(String maHD) {
        if (rong(maHD)) return new ThanhToan[0];
        java.util.List<ThanhToan> ketQua = new java.util.ArrayList<>();
        for (int i=0;i<soLuongThanhToan;i++) if (danhSachThanhToan[i]!=null && maHD.equals(danhSachThanhToan[i].getMaHD())) ketQua.add(danhSachThanhToan[i]);
        return ketQua.toArray(new ThanhToan[0]);
    }
    private boolean rong(String s) { return s == null || s.trim().isEmpty(); }

    public ThanhToan[] layDanhSach() {
        ThanhToan[] ketQua = new ThanhToan[soLuongThanhToan];
          System.arraycopy(danhSachThanhToan, 0, ketQua, 0, soLuongThanhToan);
        return ketQua;
    }
}
