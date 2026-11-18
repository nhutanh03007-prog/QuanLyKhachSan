package service;

import DAO.NhanVienDao;
import model.NhanVien;

public class QLNhanVien {

    private NhanVienDao nvDAO;

    public QLNhanVien() {
        nvDAO = new NhanVienDao();
    }

    // Lấy danh sách nhân viên
    public NhanVien[] getDanhSach() {
        return nvDAO.layTatCa();
    }

    // Tìm theo mã nhân viên
    public NhanVien timTheoMa(String maNV) {
        if (maNV == null || maNV.trim().isEmpty()) {
            return null;
        }

        return nvDAO.timTheoMa(maNV.trim());
    }

    // Thêm nhân viên
    public boolean them(NhanVien nv) {

        if (nv == null) {
            return false;
        }

        if (nv.getMaNV() == null || nv.getMaNV().trim().isEmpty()) {
            return false;
        }

        if (nv.getHoTen() == null || nv.getHoTen().trim().isEmpty()) {
            return false;
        }

        if (timTheoMa(nv.getMaNV()) != null) {
            return false;
        }

        return nvDAO.them(nv);
    }

    // Sửa nhân viên
    public boolean sua(NhanVien nv) {

        if (nv == null || nv.getMaNV() == null
                || nv.getMaNV().trim().isEmpty()) {
            return false;
        }

        if (nv.getHoTen() == null || nv.getHoTen().trim().isEmpty()) {
            return false;
        }

        if (timTheoMa(nv.getMaNV()) == null) {
            return false;
        }

        return nvDAO.capNhat(nv);
    }

    // Xóa nhân viên
    public boolean xoa(String maNV) {

        if (maNV == null || maNV.trim().isEmpty()) {
            return false;
        }

        if (timTheoMa(maNV) == null) {
            return false;
        }

        return nvDAO.xoa(maNV);
    }

    // Kiểm tra mã nhân viên
    public boolean kiemTraMaTonTai(String maNV) {
        return timTheoMa(maNV) != null;
    }

    // Đếm nhân viên
    public int demSoLuong() {
        return nvDAO.demSoLuong();
    }
}