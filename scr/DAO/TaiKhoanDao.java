package DAO;

import model.TaiKhoan;

public class TaiKhoanDao {
    private TaiKhoan[] danhSach;
    private int soLuong; 

    public TaiKhoanDao() {
        this.danhSach = new TaiKhoan[10];
        this.soLuong = 0;

        // Tai khoan demo
        them(new TaiKhoan("admin", "admin123", "NV001", "ADMIN", "HOAT_DONG"));
    }

    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        if (tenDangNhap == null || matKhau == null) {
            return null;
        }
        String tenCanTim = tenDangNhap.trim();

        for (int i = 0; i < soLuong; i++) {
            TaiKhoan tk = danhSach[i];
            if (tk != null && tk.getTenDangNhap() != null
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

        if (soLuong == danhSach.length) {
            moRongMang();
        }

        danhSach[soLuong] = tk;
        soLuong++;
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
        for (int i = 0; i < soLuong; i++) {
            TaiKhoan tk = danhSach[i];
            if (tk != null && tk.getTenDangNhap() != null
                    && tk.getTenDangNhap().equalsIgnoreCase(tenCanTim)) {
                return tk;
            }
        }
        return null;
    }

    private void moRongMang() {
        TaiKhoan[] mangMoi = new TaiKhoan[danhSach.length * 2];
        for (int i = 0; i < soLuong; i++) {
            mangMoi[i] = danhSach[i];
        }
        danhSach = mangMoi;
    }

    public TaiKhoan[] layTatCa() {
        TaiKhoan[] ketQua = new TaiKhoan[soLuong];
        for (int i = 0; i < soLuong; i++) {
            ketQua[i] = danhSach[i];
        }
        return ketQua;
    }
}