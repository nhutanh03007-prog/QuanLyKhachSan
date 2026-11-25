
package service;

import DAO.TaiKhoanDAO;
import model.TaiKhoan;

public class QLTaiKhoan {

    private final TaiKhoanDAO tkDAO;

    public QLTaiKhoan() {
        tkDAO = new TaiKhoanDAO();
    }
    public TaiKhoan kiemTraDangNhap(
            String tenDangNhap,
            String matKhau) {

        if (rong(tenDangNhap) || rong(matKhau)) {
            return null;
        }

        return tkDAO.dangNhap(tenDangNhap.trim(), matKhau);
    }

    public TaiKhoan timTheoTenDN(String tenDangNhap) {

        if (rong(tenDangNhap)) {
            return null;
        }

        return tkDAO.timTheoTen(tenDangNhap.trim());
    }

    public boolean them(TaiKhoan tk) {

        if (tk == null
                || rong(tk.getTenDangNhap())
                || rong(tk.getMatKhau())) {

            return false;
        }

        if (timTheoTenDN(tk.getTenDangNhap()) != null) {
            return false;
        }

        return tkDAO.them(tk);
    }

    public boolean doiMatKhau(
            String tenDangNhap,
            String matKhauMoi) {

        if (rong(tenDangNhap) || rong(matKhauMoi)) {
            return false;
        }

        if (timTheoTenDN(tenDangNhap) == null) {
            return false;
        }

        return tkDAO.doiMatKhau(
                tenDangNhap.trim(),
                matKhauMoi
        );
    }

    public boolean xoa(String tenDangNhap) {

        if (rong(tenDangNhap)) {
            return false;
        }

        if (timTheoTenDN(tenDangNhap) == null) {
            return false;
        }

        return tkDAO.xoa(tenDangNhap.trim());
    }

    public TaiKhoan[] getDanhSach() {
        return tkDAO.layTatCa();
    }

    public int getSoLuong() {
        return tkDAO.getSoLuong();
    }

    private boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}
