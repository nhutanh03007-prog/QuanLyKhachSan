
package service;

import DAO.TaiKhoanDao;
import model.TaiKhoan;

public class QLTaiKhoan {

    private TaiKhoanDao tkDAO;

    public QLTaiKhoan() {
        tkDAO = new TaiKhoanDao();
    }

    // ================= ĐĂNG NHẬP =================

    public TaiKhoan kiemTraDangNhap(
            String tenDangNhap,
            String matKhau) {

        if (rong(tenDangNhap) || rong(matKhau)) {
            return null;
        }

        return tkDAO.dangNhap(tenDangNhap.trim(), matKhau);
    }

    // ================= TÌM TÀI KHOẢN =================

    public TaiKhoan timTheoTenDN(String tenDangNhap) {

        if (rong(tenDangNhap)) {
            return null;
        }

        return tkDAO.timTheoTen(tenDangNhap.trim());
    }

    // ================= THÊM =================

    public boolean them(TaiKhoan tk) {

        if (tk == null
                || rong(tk.getTenDangNhap())
                || rong(tk.getMatKhau())) {

            return false;
        }

        // Kiểm tra trùng tên đăng nhập
        if (timTheoTenDN(tk.getTenDangNhap()) != null) {
            return false;
        }

        // DAO tự thêm và ghi file
        return tkDAO.them(tk);
    }

    // ================= ĐỔI MẬT KHẨU =================

    public boolean doiMatKhau(
            String tenDangNhap,
            String matKhauMoi) {

        if (rong(tenDangNhap) || rong(matKhauMoi)) {
            return false;
        }

        // Kiểm tra tài khoản tồn tại
        if (timTheoTenDN(tenDangNhap) == null) {
            return false;
        }

        // DAO tự đổi và ghi file
        return tkDAO.doiMatKhau(
                tenDangNhap.trim(),
                matKhauMoi
        );
    }

    // ================= XÓA =================

    public boolean xoa(String tenDangNhap) {

        if (rong(tenDangNhap)) {
            return false;
        }

        // Kiểm tra tồn tại
        if (timTheoTenDN(tenDangNhap) == null) {
            return false;
        }

        // DAO tự xóa và ghi file
        return tkDAO.xoa(tenDangNhap.trim());
    }

    // ================= DANH SÁCH =================

    public TaiKhoan[] getDanhSach() {
        return tkDAO.layTatCa();
    }

    // ================= SỐ LƯỢNG =================

    public int getSoLuong() {
        return tkDAO.getSoLuong();
    }

    // ================= KIỂM TRA RỖNG =================

    private boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}
