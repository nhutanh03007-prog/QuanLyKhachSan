
package service;

import INTERFACE.IPhong;
import DAO.PhongDAO;
import model.Phong;
import model.TinhTrangPhong;

import java.math.BigDecimal;

public class QLPhong implements IPhong {

    private PhongDAO phongDAO;

    public QLPhong() {
        phongDAO = new PhongDAO();
    }

    // =========================
    // CHUẨN HÓA MÃ PHÒNG
    // =========================

    private String chuanHoaMaPhong(String maPhong) {
        if (maPhong == null) {
            return null;
        }

        return maPhong.trim().toUpperCase();
    }

    // =========================
    // KIỂM TRA THÔNG TIN PHÒNG
    // =========================

    private boolean kiemTraThongTinPhong(Phong phong) {

        if (phong == null) {
            System.out.println("Thong tin phong khong hop le!");
            return false;
        }

        if (phong.getMaPhong() == null ||
            phong.getMaPhong().trim().isEmpty()) {

            System.out.println("Ma phong khong duoc de trong!");
            return false;
        }

        if (phong.getTenPhong() == null ||
            phong.getTenPhong().trim().isEmpty()) {

            System.out.println("Ten phong khong duoc de trong!");
            return false;
        }

        if (phong.getGiaPhong() == null ||
            phong.getGiaPhong().compareTo(BigDecimal.ZERO) <= 0) {

            System.out.println("Gia phong phai lon hon 0!");
            return false;
        }

        if (phong.getTinhTrang() == null) {
            System.out.println("Tinh trang phong khong hop le!");
            return false;
        }

        return true;
    }

    // =========================
    // THÊM PHÒNG
    // =========================

    @Override
    public boolean themPhong(Phong phong) {

        if (!kiemTraThongTinPhong(phong)) {
            return false;
        }

        // Chuẩn hóa mã phòng trước khi kiểm tra trùng
        String maPhong = chuanHoaMaPhong(phong.getMaPhong());

        // Gán mã đã chuẩn hóa vào đối tượng
        phong.setMaPhong(maPhong);

        // Kiểm tra mã phòng đã tồn tại chưa
        if (phongDAO.timPhongTheoMa(maPhong) != null) {
            System.out.println("Ma phong da ton tai!");
            return false;
        }

        return phongDAO.themPhong(phong);
    }

    // =========================
    // LẤY DANH SÁCH PHÒNG
    // =========================

    @Override
    public Phong[] layDanhSachPhong() {
        return phongDAO.layDanhSachPhong();
    }

    // =========================
    // LẤY DANH SÁCH THEO TÌNH TRẠNG
    // =========================

    @Override
    public Phong[] layDanhSachPhongTheoTinhTrang(
            TinhTrangPhong tinhTrang) {
        
        if (tinhTrang == null) {
            System.out.println("Tinh trang khong hop le!");
            return new Phong[0];
        }

        return phongDAO.layDanhSachPhongTheoTinhTrang(tinhTrang);
    }

    // =========================
    // TÌM PHÒNG THEO MÃ
    // =========================

    @Override
    public Phong timPhongTheoMa(String maPhong) {

        maPhong = chuanHoaMaPhong(maPhong);

        if (maPhong == null || maPhong.isEmpty()) {
            System.out.println("Ma phong khong hop le!");
            return null;
        }

        return phongDAO.timPhongTheoMa(maPhong);
    }

    // =========================
    // SỬA PHÒNG
    // Không cho phép thay đổi mã phòng
    // =========================

    @Override
    public boolean suaPhong(Phong phong) {

        if (!kiemTraThongTinPhong(phong)) {
            return false;
        }

        // Chuẩn hóa mã phòng
        String maPhong = chuanHoaMaPhong(phong.getMaPhong());

        // Tìm phòng cũ bằng mã phòng
        Phong phongCu = phongDAO.timPhongTheoMa(maPhong);

        if (phongCu == null) {
            System.out.println("Khong tim thay phong!");
            return false;
        }

        // Giữ nguyên mã phòng cũ
        phong.setMaPhong(phongCu.getMaPhong());

        // Cập nhật thông tin phòng
        return phongDAO.suaPhong(
                phongCu.getMaPhong(),
                phong
        );
    }

    // =========================
    // XÓA PHÒNG
    // =========================

    @Override
    public boolean xoaPhong(String maPhong) {

        maPhong = chuanHoaMaPhong(maPhong);

        if (maPhong == null || maPhong.isEmpty()) {
            System.out.println("Ma phong khong hop le!");
            return false;
        }

        if (phongDAO.timPhongTheoMa(maPhong) == null) {
            System.out.println("Phong khong ton tai!");
            return false;
        }

        return phongDAO.xoaPhong(maPhong);
    }

    // =========================
    // ĐẾM SỐ LƯỢNG PHÒNG
    // =========================

    @Override
    public int demSoLuongPhong() {
        return phongDAO.demSoLuongPhong();
    }

    // =========================
    // ĐẾM PHÒNG THEO TÌNH TRẠNG
    // =========================

    @Override
    public int demSoLuongPhongTheoTinhTrang(
            TinhTrangPhong tinhTrang) {

        if (tinhTrang == null) {
            return 0;
        }

        return phongDAO.demSoLuongPhongTheoTinhTrang(tinhTrang);
    }
}
