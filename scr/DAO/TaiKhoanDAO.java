package DAO;

import java.util.Arrays;
import model.TaiKhoan;

public final class TaiKhoanDAO {

    private static TaiKhoan[] danhSach = new TaiKhoan[20];
    private static int soLuong = 0;

    static {
        QuanLyDuLieuFile.damBaoDaNap();
    }

    public TaiKhoanDAO() {
    }

    public static void napDuLieu(TaiKhoan[] duLieu) {
        if (duLieu == null) {
            danhSach = new TaiKhoan[20];
            soLuong = 0;
            return;
        }
        danhSach = Arrays.copyOf(duLieu, Math.max(20, duLieu.length));
        soLuong = duLieu.length;
    }

    public boolean docFile() {
        napDuLieu(QuanLyDuLieuFile.docTaiKhoan());
        return true;
    }

    public boolean ghiFile() {
        return QuanLyDuLieuFile.ghiTaiKhoan(danhSach, soLuong);
    }

    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        if (tenDangNhap == null || matKhau == null) {
            return null;
        }
        String tenCanTim = tenDangNhap.trim();
        for (int i = 0; i < soLuong; i++) {
            TaiKhoan tk = danhSach[i];
            if (tk != null
                    && tk.getTenDangNhap() != null
                    && tk.getTenDangNhap().equalsIgnoreCase(tenCanTim)
                    && tk.getMatKhau() != null
                    && tk.getMatKhau().equals(matKhau)) {
                return tk;
            }
        }
        return null;
    }

    public boolean them(TaiKhoan tk) {
        if (tk == null
                || tk.getTenDangNhap() == null
                || tk.getTenDangNhap().trim().isEmpty()
                || tk.getMatKhau() == null
                || tk.getMatKhau().isEmpty()) {
            return false;
        }
        if (timTheoTen(tk.getTenDangNhap()) != null) {
            return false;
        }
        if (soLuong == danhSach.length) {
            moRongMang();
        }

        danhSach[soLuong++] = tk;
        if (!ghiFile()) {
            danhSach[--soLuong] = null;
            return false;
        }
        return true;
    }

    public boolean doiMatKhau(String tenDangNhap, String matKhauMoi) {
        if (tenDangNhap == null || matKhauMoi == null || matKhauMoi.trim().isEmpty()) {
            return false;
        }
        TaiKhoan tk = timTheoTen(tenDangNhap);
        if (tk == null) {
            return false;
        }

        String matKhauCu = tk.getMatKhau();
        tk.setMatKhau(matKhauMoi);

        if (!ghiFile()) {
            tk.setMatKhau(matKhauCu);
            return false;
        }
        return true;
    }

    public boolean xoa(String tenDangNhap) {
        TaiKhoan tk = timTheoTen(tenDangNhap);
        if (tk == null) {
            return false;
        }

        int viTri = -1;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i] == tk) {
                viTri = i;
                break;
            }
        }
        if (viTri == -1) {
            return false;
        }

        TaiKhoan[] mangCu = danhSach.clone();
        int soLuongCu = soLuong;

        for (int i = viTri; i < soLuong - 1; i++) {
            danhSach[i] = danhSach[i + 1];
        }
        danhSach[soLuong - 1] = null;
        soLuong--;

        if (!ghiFile()) {
            danhSach = mangCu;
            soLuong = soLuongCu;
            return false;
        }
        return true;
    }

    public TaiKhoan timTheoTen(String ten) {
        if (ten == null || ten.trim().isEmpty()) {
            return null;
        }
        String tenCanTim = ten.trim();
        for (int i = 0; i < soLuong; i++) {
            TaiKhoan tk = danhSach[i];
            if (tk != null
                    && tk.getTenDangNhap() != null
                    && tk.getTenDangNhap().equalsIgnoreCase(tenCanTim)) {
                return tk;
            }
        }
        return null;
    }

    public TaiKhoan[] layTatCa() {
        return Arrays.copyOf(danhSach, soLuong);
    }

    public int getSoLuong() {
        return soLuong;
    }

    private static void moRongMang() {
        danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
    }
}
