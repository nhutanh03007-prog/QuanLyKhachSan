package service;

import DAO.KhachHangDAO;
import model.KhachHang;

public class QLKhachHang {
    private KhachHangDAO khachHangDAO = new KhachHangDAO();

    public boolean themKhachHang(KhachHang kh) {
        if (kh == null || kh.getMaKH() == null || kh.getMaKH().trim().isEmpty()) {
            System.out.println("Thông tin khách hàng không hợp lệ!");
            return false;
        }
        boolean kq = khachHangDAO.them(kh);
        if (kq) System.out.println("Thêm khách hàng thành công!");
        else System.out.println("Thêm thất bại (Trùng mã hoặc danh sách đầy)!");
        return kq;
    }

    public boolean suaKhachHang(KhachHang kh) {
        boolean kq = khachHangDAO.sua(kh);
        if (kq) System.out.println("Cập nhật khách hàng thành công!");
        else System.out.println("Không tìm thấy mã khách hàng để sửa!");
        return kq;
    }

    public boolean xoaKhachHang(String maKH) {
        boolean kq = khachHangDAO.xoa(maKH);
        if (kq) System.out.println("Xóa khách hàng thành công!");
        else System.out.println("Không tìm thấy mã khách hàng để xóa!");
        return kq;
    }

    public KhachHang timKhachHang(String maKH) {
        return khachHangDAO.timTheoMa(maKH);
    }

    public KhachHang[] layTatCa() {
        return khachHangDAO.layDanhSach();
    }
}