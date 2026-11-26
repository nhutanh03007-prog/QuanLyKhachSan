package DAO;

import java.util.Arrays;
import model.ThanhToan;

public class ThanhToanDAO {
    public static ThanhToan[] danhSachThanhToan = new ThanhToan[100];
    public static int soLuongThanhToan = 0;

    static {
        QuanLyDuLieuFile.damBaoDaNap();
    }

    public static void napDuLieu(ThanhToan[] duLieu) {
        soLuongThanhToan = 0;
        danhSachThanhToan = new ThanhToan[Math.max(100, duLieu == null ? 0 : duLieu.length)];
        if (duLieu == null) {
            return;
        }
        for (ThanhToan t : duLieu) {
            if (hopLe(t) && timNoiBo(t.getMaThanhToan()) == null) {
                danhSachThanhToan[soLuongThanhToan++] = t;
            }
        }
    }

    private static ThanhToan timNoiBo(String ma) {
        if (rong(ma)) {
            return null;
        }
        String target = ma.trim();
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (danhSachThanhToan[i] != null && danhSachThanhToan[i].getMaThanhToan() != null
                    && target.equalsIgnoreCase(danhSachThanhToan[i].getMaThanhToan())) {
                return danhSachThanhToan[i];
            }
        }
        return null;
    }

    private static boolean ghiFile() {
        return QuanLyDuLieuFile.ghiThanhToan(danhSachThanhToan, soLuongThanhToan);
    }

    public boolean them(ThanhToan t) {
        if (!hopLe(t) || timTheoMa(t.getMaThanhToan()) != null) {
            return false;
        }
        if (soLuongThanhToan >= danhSachThanhToan.length) {
            danhSachThanhToan = Arrays.copyOf(danhSachThanhToan, danhSachThanhToan.length * 2);
        }
        int old = soLuongThanhToan;
        danhSachThanhToan[soLuongThanhToan++] = t;
        if (!ghiFile()) {
            soLuongThanhToan = old;
            danhSachThanhToan[old] = null;
            return false;
        }
        return true;
    }

    public boolean sua(ThanhToan t) {
        if (!hopLe(t)) {
            return false;
        }
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (danhSachThanhToan[i] != null
                    && t.getMaThanhToan().trim().equalsIgnoreCase(danhSachThanhToan[i].getMaThanhToan())) {
                ThanhToan old = danhSachThanhToan[i];
                danhSachThanhToan[i] = t;
                if (!ghiFile()) {
                    danhSachThanhToan[i] = old;
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String ma) {
        if (rong(ma)) {
            return false;
        }
        String target = ma.trim();
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (danhSachThanhToan[i] != null && target.equalsIgnoreCase(danhSachThanhToan[i].getMaThanhToan())) {
                ThanhToan[] old = Arrays.copyOf(danhSachThanhToan, soLuongThanhToan);
                int n = soLuongThanhToan;
                System.arraycopy(danhSachThanhToan, i + 1, danhSachThanhToan, i, --soLuongThanhToan - i);
                danhSachThanhToan[soLuongThanhToan] = null;
                if (!ghiFile()) {
                    System.arraycopy(old, 0, danhSachThanhToan, 0, n);
                    soLuongThanhToan = n;
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public ThanhToan timTheoMa(String ma) {
        return timNoiBo(ma);
    }

    public ThanhToan[] layTheoMaHD(String ma) {
        if (rong(ma)) {
            return new ThanhToan[0];
        }
        String target = ma.trim();
        ThanhToan[] ketQuaTam = new ThanhToan[soLuongThanhToan];
        int dem = 0;
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (danhSachThanhToan[i] != null && target.equalsIgnoreCase(danhSachThanhToan[i].getMaHD())) {
                ketQuaTam[dem++] = danhSachThanhToan[i];
            }
        }
        return Arrays.copyOf(ketQuaTam, dem);
    }

    public ThanhToan[] layDanhSach() {
        return Arrays.copyOf(danhSachThanhToan, soLuongThanhToan);
    }

    private static boolean hopLe(ThanhToan t) {
        return t != null && !rong(t.getMaThanhToan()) && !rong(t.getMaHD()) && !rong(t.getPhuongThuc())
                && t.getSoTien() != null && t.getSoTien().signum() >= 0;
    }

    private static boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}
