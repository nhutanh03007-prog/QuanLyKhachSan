package DAO;

import java.util.Arrays;
import model.KhachHang;

public class KhachHangDAO {
    public static KhachHang[] danhSachKhachHang = new KhachHang[100];
    public static int soLuongKhachHang = 0;

    static {
        QuanLyDuLieuFile.damBaoDaNap();
    }

    public static void napDuLieu(KhachHang[] duLieu) {
        if (duLieu == null) {
            danhSachKhachHang = new KhachHang[100];
            soLuongKhachHang = 0;
            return;
        }
        danhSachKhachHang = Arrays.copyOf(duLieu, Math.max(100, duLieu.length));
        soLuongKhachHang = duLieu.length;
    }

    private static boolean ghiFile() {
        return QuanLyDuLieuFile.ghiKhachHang(danhSachKhachHang, soLuongKhachHang);
    }

    public boolean them(KhachHang khachHang) {
        if (khachHang == null || khachHang.getMaKH() == null || khachHang.getMaKH().trim().isEmpty()
                || timTheoMa(khachHang.getMaKH()) != null) {
            return false;
        }
        if (soLuongKhachHang >= danhSachKhachHang.length) {
            danhSachKhachHang = Arrays.copyOf(danhSachKhachHang, danhSachKhachHang.length * 2);
        }
        danhSachKhachHang[soLuongKhachHang++] = khachHang;
        if (!ghiFile()) {
            danhSachKhachHang[--soLuongKhachHang] = null;
            return false;
        }
        return true;
    }

    public boolean sua(KhachHang khachHang) {
        if (khachHang == null || khachHang.getMaKH() == null) {
            return false;
        }
        String ma = khachHang.getMaKH().trim();
        for (int i = 0; i < soLuongKhachHang; i++) {
            if (danhSachKhachHang[i] != null && danhSachKhachHang[i].getMaKH().equalsIgnoreCase(ma)) {
                KhachHang cu = danhSachKhachHang[i];
                danhSachKhachHang[i] = khachHang;
                if (!ghiFile()) {
                    danhSachKhachHang[i] = cu;
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maKhachHang) {
        if (maKhachHang == null || maKhachHang.trim().isEmpty()) {
            return false;
        }
        String ma = maKhachHang.trim();
        for (int i = 0; i < soLuongKhachHang; i++) {
            if (danhSachKhachHang[i] != null && danhSachKhachHang[i].getMaKH().equalsIgnoreCase(ma)) {
                System.arraycopy(danhSachKhachHang, i + 1, danhSachKhachHang, i, soLuongKhachHang - i - 1);
                danhSachKhachHang[--soLuongKhachHang] = null;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public KhachHang timTheoMa(String maKhachHang) {
        if (maKhachHang == null || maKhachHang.trim().isEmpty()) {
            return null;
        }
        String ma = maKhachHang.trim();
        for (int i = 0; i < soLuongKhachHang; i++) {
            if (danhSachKhachHang[i] != null && danhSachKhachHang[i].getMaKH().equalsIgnoreCase(ma)) {
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
