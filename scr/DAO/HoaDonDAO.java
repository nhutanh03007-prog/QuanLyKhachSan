package DAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import model.HoaDon;
import model.TrangThaiThanhToan;

public class HoaDonDAO {
    public static HoaDon[] danhSachHoaDon = new HoaDon[100];
    public static int soLuongHoaDon = 0;

    static {
        QuanLyDuLieuFile.damBaoDaNap();
    }

    public static void napDuLieu(HoaDon[] duLieu) {
        soLuongHoaDon = 0;
        danhSachHoaDon = new HoaDon[Math.max(100, duLieu == null ? 0 : duLieu.length)];
        if (duLieu == null) {
            return;
        }
        for (HoaDon h : duLieu) {
            if (hopLe(h) && timNoiBo(h.getMaHD()) == null && timNoiBoDatPhong(h.getMaDatPhong()) == null) {
                h.setThanhTien(tinhThanhTienNoiBo(h));
                danhSachHoaDon[soLuongHoaDon++] = h;
            }
        }
    }

    private static HoaDon timNoiBo(String ma) {
        if (rong(ma)) {
            return null;
        }
        String target = ma.trim();
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (danhSachHoaDon[i] != null && target.equalsIgnoreCase(danhSachHoaDon[i].getMaHD())) {
                return danhSachHoaDon[i];
            }
        }
        return null;
    }

    private static HoaDon timNoiBoDatPhong(String ma) {
        if (rong(ma)) {
            return null;
        }
        String target = ma.trim();
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (danhSachHoaDon[i] != null && target.equalsIgnoreCase(danhSachHoaDon[i].getMaDatPhong())) {
                return danhSachHoaDon[i];
            }
        }
        return null;
    }

    private static boolean ghiFile() {
        return QuanLyDuLieuFile.ghiHoaDon(danhSachHoaDon, soLuongHoaDon);
    }

    public boolean them(HoaDon h) {
        if (!hopLe(h) || timTheoMa(h.getMaHD()) != null || timTheoMaDatPhong(h.getMaDatPhong()) != null) {
            return false;
        }
        if (soLuongHoaDon >= danhSachHoaDon.length) {
            danhSachHoaDon = Arrays.copyOf(danhSachHoaDon, danhSachHoaDon.length * 2);
        }
        if (h.getNgayLap() == null) {
            h.setNgayLap(LocalDate.now());
        }
        if (h.getTrangThai() == null) {
            h.setTrangThai(TrangThaiThanhToan.CHUA_THANH_TOAN);
        }
        h.setThanhTien(tinhThanhTien(h));
        int old = soLuongHoaDon;
        danhSachHoaDon[soLuongHoaDon++] = h;
        if (!ghiFile()) {
            soLuongHoaDon = old;
            danhSachHoaDon[old] = null;
            return false;
        }
        return true;
    }

    public boolean sua(HoaDon h) {
        if (!hopLe(h)) {
            return false;
        }
        String target = h.getMaHD().trim();
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (danhSachHoaDon[i] != null && target.equalsIgnoreCase(danhSachHoaDon[i].getMaHD())) {
                if (danhSachHoaDon[i].getTrangThai() == TrangThaiThanhToan.DA_THANH_TOAN) {
                    return false;
                }
                HoaDon old = danhSachHoaDon[i];
                h.setThanhTien(tinhThanhTien(h));
                danhSachHoaDon[i] = h;
                if (!ghiFile()) {
                    danhSachHoaDon[i] = old;
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
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (danhSachHoaDon[i] != null && target.equalsIgnoreCase(danhSachHoaDon[i].getMaHD())) {
                if (danhSachHoaDon[i].getTrangThai() == TrangThaiThanhToan.DA_THANH_TOAN) {
                    return false;
                }
                HoaDon[] old = Arrays.copyOf(danhSachHoaDon, soLuongHoaDon);
                int n = soLuongHoaDon;
                System.arraycopy(danhSachHoaDon, i + 1, danhSachHoaDon, i, --soLuongHoaDon - i);
                danhSachHoaDon[soLuongHoaDon] = null;
                if (!ghiFile()) {
                    System.arraycopy(old, 0, danhSachHoaDon, 0, n);
                    soLuongHoaDon = n;
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public BigDecimal tinhThanhTien(HoaDon h) {
        return tinhThanhTienNoiBo(h);
    }

    public boolean capNhatTrangThai(String ma, TrangThaiThanhToan t, String p, LocalDate d) {
        HoaDon h = timTheoMa(ma);
        if (h == null || t == null) {
            return false;
        }
        TrangThaiThanhToan oldT = h.getTrangThai();
        String oldP = h.getPhuongThucTT();
        LocalDate oldD = h.getNgayThanhToan();
        h.setTrangThai(t);
        if (p != null) {
            h.setPhuongThucTT(p);
        }
        h.setNgayThanhToan(d);
        if (!ghiFile()) {
            h.setTrangThai(oldT);
            h.setPhuongThucTT(oldP);
            h.setNgayThanhToan(oldD);
            return false;
        }
        return true;
    }

    public HoaDon timTheoMa(String ma) {
        return timNoiBo(ma);
    }

    public HoaDon timTheoMaDatPhong(String dp) {
        return timNoiBoDatPhong(dp);
    }

    public HoaDon[] layDanhSach() {
        return Arrays.copyOf(danhSachHoaDon, soLuongHoaDon);
    }

    private static BigDecimal tinhThanhTienNoiBo(HoaDon h) {
        return h == null || h.getTongGia() == null || h.getGiamGia() == null ? BigDecimal.ZERO
                : h.getTongGia().subtract(h.getGiamGia());
    }

    private static boolean hopLe(HoaDon h) {
        return h != null && !rong(h.getMaHD()) && !rong(h.getMaDatPhong()) && h.getTongGia() != null
                && h.getGiamGia() != null && h.getTongGia().signum() >= 0 && h.getGiamGia().signum() >= 0
                && h.getGiamGia().compareTo(h.getTongGia()) <= 0;
    }

    private static boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}
