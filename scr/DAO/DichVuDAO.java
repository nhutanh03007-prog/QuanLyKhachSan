package DAO;

import java.math.BigDecimal;
import java.util.Arrays;
import model.DichVu;

public class DichVuDAO {
    public static DichVu[] danhSachDichVu = new DichVu[100];
    public static int soLuongDichVu = 0;

    static {
        QuanLyDuLieuFile.damBaoDaNap();
    }

    public DichVuDAO() {
    }

    public static void napDuLieu(DichVu[] duLieu) {
        soLuongDichVu = 0;
        danhSachDichVu = new DichVu[Math.max(100, duLieu == null ? 0 : duLieu.length)];
        if (duLieu == null) {
            return;
        }
        for (DichVu d : duLieu) {
            if (hopLe(d) && timNoiBo(d.getMaDichVu()) == null) {
                danhSachDichVu[soLuongDichVu++] = d;
            }
        }
    }

    private static DichVu timNoiBo(String ma) {
        if (rong(ma)) {
            return null;
        }
        String target = ma.trim();
        for (int i = 0; i < soLuongDichVu; i++) {
            if (danhSachDichVu[i] != null && danhSachDichVu[i].getMaDichVu() != null
                    && target.equalsIgnoreCase(danhSachDichVu[i].getMaDichVu())) {
                return danhSachDichVu[i];
            }
        }
        return null;
    }

    private static boolean ghiFile() {
        return QuanLyDuLieuFile.ghiDichVu(danhSachDichVu, soLuongDichVu);
    }

    public boolean kiemTraTenDichVuKhongRong(String s) {
        return !rong(s);
    }

    public boolean kiemTraSoTienKhongAm(BigDecimal n) {
        return n != null && n.signum() >= 0;
    }

    public boolean them(DichVu d) {
        if (!hopLe(d) || timTheoMa(d.getMaDichVu()) != null) {
            return false;
        }
        if (soLuongDichVu >= danhSachDichVu.length) {
            danhSachDichVu = Arrays.copyOf(danhSachDichVu, danhSachDichVu.length * 2);
        }
        int old = soLuongDichVu;
        danhSachDichVu[soLuongDichVu++] = d;
        if (!ghiFile()) {
            soLuongDichVu = old;
            danhSachDichVu[old] = null;
            return false;
        }
        return true;
    }

    public boolean sua(DichVu d) {
        if (!hopLe(d)) {
            return false;
        }
        String target = d.getMaDichVu().trim();
        for (int i = 0; i < soLuongDichVu; i++) {
            if (danhSachDichVu[i] != null && target.equalsIgnoreCase(danhSachDichVu[i].getMaDichVu())) {
                DichVu old = danhSachDichVu[i];
                danhSachDichVu[i] = d;
                if (!ghiFile()) {
                    danhSachDichVu[i] = old;
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
        for (int i = 0; i < soLuongDichVu; i++) {
            if (danhSachDichVu[i] != null && target.equalsIgnoreCase(danhSachDichVu[i].getMaDichVu())) {
                DichVu[] old = Arrays.copyOf(danhSachDichVu, soLuongDichVu);
                int n = soLuongDichVu;
                System.arraycopy(danhSachDichVu, i + 1, danhSachDichVu, i, --soLuongDichVu - i);
                danhSachDichVu[soLuongDichVu] = null;
                if (!ghiFile()) {
                    System.arraycopy(old, 0, danhSachDichVu, 0, n);
                    soLuongDichVu = n;
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public DichVu timTheoMa(String ma) {
        return timNoiBo(ma);
    }

    public DichVu[] timKiem(String key) {
        DichVu[] tam = new DichVu[soLuongDichVu];
        int dem = 0;
        if (!rong(key)) {
            String k = key.trim().toLowerCase();
            for (int i = 0; i < soLuongDichVu; i++) {
                DichVu d = danhSachDichVu[i];
                if (d != null && ((d.getMaDichVu() != null && d.getMaDichVu().toLowerCase().contains(k))
                        || (d.getTenDichVu() != null && d.getTenDichVu().toLowerCase().contains(k)))) {
                    tam[dem++] = d;
                }
            }
        }
        return Arrays.copyOf(tam, dem);
    }

    public DichVu[] timTheoTen(String key) {
        return timKiem(key);
    }

    public DichVu[] layDanhSach() {
        return Arrays.copyOf(danhSachDichVu, soLuongDichVu);
    }

    private static boolean hopLe(DichVu d) {
        return d != null && !rong(d.getMaDichVu()) && !rong(d.getTenDichVu()) && !rong(d.getLoaiDichVu())
                && !rong(d.getDonViTinh()) && d.getGia() != null && d.getGia().signum() > 0;
    }

    private static boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}
