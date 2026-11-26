package DAO;

import java.math.BigDecimal;
import java.util.Arrays;
import model.ChiTietDichVu;

public class ChiTietDichVuDAO {
    public static ChiTietDichVu[] danhSachChiTiet = new ChiTietDichVu[100];
    public static int soLuongChiTiet = 0;

    static {
        QuanLyDuLieuFile.damBaoDaNap();
    }

    public static void napDuLieu(ChiTietDichVu[] duLieu) {
        soLuongChiTiet = 0;
        danhSachChiTiet = new ChiTietDichVu[Math.max(100, duLieu == null ? 0 : duLieu.length)];
        if (duLieu == null) {
            return;
        }
        for (ChiTietDichVu c : duLieu) {
            if (hopLe(c) && timNoiBo(c.getMaDatPhong(), c.getMaDichVu()) == null) {
                c.setThanhTien(tinhThanhTienNoiBo(c));
                danhSachChiTiet[soLuongChiTiet++] = c;
            }
        }
    }

    private static ChiTietDichVu timNoiBo(String dp, String dv) {
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (cungKhoa(danhSachChiTiet[i], dp, dv)) {
                return danhSachChiTiet[i];
            }
        }
        return null;
    }

    private static boolean ghiFile() {
        return QuanLyDuLieuFile.ghiChiTietDichVu(danhSachChiTiet, soLuongChiTiet);
    }

    public boolean them(ChiTietDichVu c) {
        if (!hopLe(c)) {
            return false;
        }
        ChiTietDichVu cu = timTheoMa(c.getMaDatPhong(), c.getMaDichVu());
        if (cu != null) {
            int sl = cu.getSoLuong();
            BigDecimal dg = cu.getDonGia(), tt = cu.getThanhTien();
            cu.setSoLuong(sl + c.getSoLuong());
            cu.setDonGia(c.getDonGia());
            cu.setThanhTien(tinhThanhTien(cu));
            if (!ghiFile()) {
                cu.setSoLuong(sl);
                cu.setDonGia(dg);
                cu.setThanhTien(tt);
                return false;
            }
            return true;
        }
        if (soLuongChiTiet >= danhSachChiTiet.length) {
            danhSachChiTiet = Arrays.copyOf(danhSachChiTiet, danhSachChiTiet.length * 2);
        }
        c.setThanhTien(tinhThanhTien(c));
        int old = soLuongChiTiet;
        danhSachChiTiet[soLuongChiTiet++] = c;
        if (!ghiFile()) {
            soLuongChiTiet = old;
            danhSachChiTiet[old] = null;
            return false;
        }
        return true;
    }

    public boolean sua(ChiTietDichVu c) {
        if (!hopLe(c)) {
            return false;
        }
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (cungKhoa(danhSachChiTiet[i], c.getMaDatPhong(), c.getMaDichVu())) {
                ChiTietDichVu old = danhSachChiTiet[i];
                BigDecimal tt = c.getThanhTien();
                c.setThanhTien(tinhThanhTien(c));
                danhSachChiTiet[i] = c;
                if (!ghiFile()) {
                    danhSachChiTiet[i] = old;
                    c.setThanhTien(tt);
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public BigDecimal tinhThanhTien(ChiTietDichVu c) {
        return tinhThanhTienNoiBo(c);
    }

    public boolean xoa(String dp, String dv) {
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (cungKhoa(danhSachChiTiet[i], dp, dv)) {
                ChiTietDichVu[] old = Arrays.copyOf(danhSachChiTiet, soLuongChiTiet);
                int n = soLuongChiTiet;
                System.arraycopy(danhSachChiTiet, i + 1, danhSachChiTiet, i, --soLuongChiTiet - i);
                danhSachChiTiet[soLuongChiTiet] = null;
                if (!ghiFile()) {
                    System.arraycopy(old, 0, danhSachChiTiet, 0, n);
                    soLuongChiTiet = n;
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public ChiTietDichVu timTheoMa(String dp, String dv) {
        return timNoiBo(dp, dv);
    }

    public ChiTietDichVu[] layDanhSach() {
        return Arrays.copyOf(danhSachChiTiet, soLuongChiTiet);
    }

    public ChiTietDichVu[] layTheoMaDatPhong(String dp) {
        ChiTietDichVu[] tam = new ChiTietDichVu[soLuongChiTiet];
        int dem = 0;
        if (!rong(dp)) {
            String target = dp.trim();
            for (int i = 0; i < soLuongChiTiet; i++) {
                if (danhSachChiTiet[i] != null && target.equalsIgnoreCase(danhSachChiTiet[i].getMaDatPhong())) {
                    tam[dem++] = danhSachChiTiet[i];
                }
            }
        }
        return Arrays.copyOf(tam, dem);
    }

    public boolean kiemTraDichVuDangSuDung(String ma) {
        if (rong(ma)) {
            return false;
        }
        String target = ma.trim();
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (danhSachChiTiet[i] != null && target.equalsIgnoreCase(danhSachChiTiet[i].getMaDichVu())) {
                return true;
            }
        }
        return false;
    }

    public BigDecimal tinhTongTienTheoDatPhong(String dp) {
        BigDecimal sum = BigDecimal.ZERO;
        for (ChiTietDichVu c : layTheoMaDatPhong(dp)) {
            sum = sum.add(tinhThanhTien(c));
        }
        return sum;
    }

    private static BigDecimal tinhThanhTienNoiBo(ChiTietDichVu c) {
        return c == null || c.getDonGia() == null ? BigDecimal.ZERO
                : c.getDonGia().multiply(BigDecimal.valueOf(c.getSoLuong()));
    }

    private static boolean hopLe(ChiTietDichVu c) {
        return c != null && !rong(c.getMaDatPhong()) && !rong(c.getMaDichVu()) && c.getSoLuong() > 0
                && c.getDonGia() != null && c.getDonGia().signum() >= 0;
    }

    private static boolean cungKhoa(ChiTietDichVu c, String dp, String dv) {
        return c != null && !rong(dp) && !rong(dv)
                && dp.trim().equalsIgnoreCase(c.getMaDatPhong())
                && dv.trim().equalsIgnoreCase(c.getMaDichVu());
    }

    private static boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}
