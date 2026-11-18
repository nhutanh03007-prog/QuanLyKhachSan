package DAO;

import java.util.ArrayList;
import model.ThanhToan;

public class ThanhToanDAO {
    public static ThanhToan[] danhSachThanhToan = new ThanhToan[100];
    public static int soLuongThanhToan = 0;

    private boolean hopLe(ThanhToan t) {
        return t != null && !rong(t.getMaThanhToan()) && !rong(t.getMaHD()) && !rong(t.getPhuongThuc())
                && t.getSoTien() != null && t.getSoTien().signum() > 0;
    }

    public boolean them(ThanhToan t) {
        if (!hopLe(t) || timTheoMa(t.getMaThanhToan()) != null || soLuongThanhToan >= 100)
            return false;
        danhSachThanhToan[soLuongThanhToan++] = t;
        return true;
    }

    public boolean sua(ThanhToan t) {
        if (!hopLe(t))
            return false;
        for (int i = 0; i < soLuongThanhToan; i++)
            if (danhSachThanhToan[i] != null && t.getMaThanhToan().equals(danhSachThanhToan[i].getMaThanhToan())) {
                danhSachThanhToan[i] = t;
                return true;
            }
        return false;
    }

    public boolean xoa(String ma) {
        for (int i = 0; i < soLuongThanhToan; i++)
            if (danhSachThanhToan[i] != null && ma != null && ma.equals(danhSachThanhToan[i].getMaThanhToan())) {
                System.arraycopy(danhSachThanhToan, i + 1, danhSachThanhToan, i, soLuongThanhToan - i - 1);
                danhSachThanhToan[--soLuongThanhToan] = null;
                return true;
            }
        return false;
    }

    public ThanhToan timTheoMa(String ma) {
        if (rong(ma))
            return null;
        for (int i = 0; i < soLuongThanhToan; i++)
            if (danhSachThanhToan[i] != null && ma.equals(danhSachThanhToan[i].getMaThanhToan()))
                return danhSachThanhToan[i];
        return null;
    }

    public ThanhToan[] layTheoMaHD(String ma) {
        ArrayList<ThanhToan> a = new ArrayList<>();
        for (int i = 0; i < soLuongThanhToan; i++)
            if (danhSachThanhToan[i] != null && ma != null && ma.equals(danhSachThanhToan[i].getMaHD()))
                a.add(danhSachThanhToan[i]);
        return a.toArray(new ThanhToan[0]);
    }

    public ThanhToan[] layDanhSach() {
        return java.util.Arrays.copyOf(danhSachThanhToan, soLuongThanhToan);
    }

    private boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}
