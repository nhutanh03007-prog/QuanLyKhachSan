package DAO;

import model.TaiKhoan;
import java.util.List;
import java.util.ArrayList;  

/** Quan ly tai khoan tam thoi trong bo nho, khong dung database. */
public class TaiKhoanDAO {
    private final List<TaiKhoan> danhSach = new ArrayList<>();

    public TaiKhoanDAO() {
        // Tai khoan demo de thu chuong trinh.
        danhSach.add(new TaiKhoan("admin", "admin123", "NV001", "ADMIN", "HOAT_DONG"));
    }

    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        if (tenDangNhap == null || matKhau == null) {
            return null;
        }
        String tenCanTim = tenDangNhap.trim();
        for (TaiKhoan tk : danhSach) {
            if (tk.getTenDangNhap() != null
                    && tk.getTenDangNhap().equalsIgnoreCase(tenCanTim)
                    && tk.getMatKhau() != null
                    && tk.getMatKhau().equals(matKhau)
                    && "HOAT_DONG".equalsIgnoreCase(tk.getTrangThai())) {
                return tk;
            }
        }
        return null;
    }

    public boolean them(TaiKhoan tk) {
        if (tk == null || tk.getTenDangNhap() == null
                || tk.getTenDangNhap().trim().isEmpty()
                || tk.getMatKhau() == null || tk.getMatKhau().isEmpty()) {
            return false;
        }
        if (timTheoTen(tk.getTenDangNhap()) != null) {
            return false;
        }
        danhSach.add(tk);
        return true;
    }

    public boolean doiMatKhau(String tenDangNhap, String matKhauMoi) {
        if (matKhauMoi == null || matKhauMoi.trim().isEmpty()) {
            return false;
        }
        TaiKhoan tk = timTheoTen(tenDangNhap);
        if (tk == null) {
            return false;
        }
        tk.setMatKhau(matKhauMoi);
        return true;
    }

    private TaiKhoan timTheoTen(String ten) {
        if (ten == null || ten.trim().isEmpty()) {
            return null;
        }
        String tenCanTim = ten.trim();
        for (TaiKhoan tk : danhSach) {
            if (tk.getTenDangNhap() != null
                    && tk.getTenDangNhap().equalsIgnoreCase(tenCanTim)) {
                return tk;
            }
        }
        return null;
    }
}
