
package service;

import DAO.PhongDAO;
import model.Phong;
import model.TinhTrangPhong;

import java.math.BigDecimal;
import java.util.List;

public class QLPhong {
    private PhongDAO phongDAO;

    public QLPhong() {
        phongDAO = new PhongDAO();
    }

    // 1. Thêm phòng
    public boolean themPhong(Phong phong) {
        if (phong == null) {
            System.out.println("Thong tin phong khong hop le!");
            return false;
        }
        if (phong.getMaPhong() == null ||
            phong.getMaPhong().trim().isEmpty()) {
            System.out.println("Ma phong khong duoc de trong!");
            return false;
        }
        if (phong.getGiaPhong() == null ||
            phong.getGiaPhong().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Gia phong phai lon hon 0!");
            return false;
        }
        if (phongDAO.timPhongTheoMa(phong.getMaPhong()) != null) {
            System.out.println("Ma phong da ton tai!");
            return false;
        }
        return phongDAO.themPhong(phong);
    }
    public List<Phong> layDanhSachPhong() {
        return phongDAO.layDanhSachPhong();
    }

    // 3. Tìm phòng theo mã
    public Phong timPhongTheoMa(String maPhong) {

        if (maPhong == null || maPhong.trim().isEmpty()) {
            return null;
        }

        return phongDAO.timPhongTheoMa(maPhong);
    }

    // 4. Sửa thông tin phòng
    public boolean suaPhong(Phong phong) {

        if (phong == null) {
            return false;
        }

        Phong phongCu = phongDAO.timPhongTheoMa(phong.getMaPhong());

        if (phongCu == null) {
            System.out.println("Khong tim thay phong!");
            return false;
        }

        if (phong.getGiaPhong() == null ||
            phong.getGiaPhong().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Gia phong khong hop le!");
            return false;
        }

        return phongDAO.suaPhong(phong);
    }
    public boolean xoaPhong(String maPhong) {

        if (maPhong == null || maPhong.trim().isEmpty()) {
            return false;
        }

        Phong phong = phongDAO.timPhongTheoMa(maPhong);

        if (phong == null) {
            System.out.println("Phong khong ton tai!");
            return false;
        }
        return phongDAO.xoaPhong(maPhong);
    }
    public List<Phong> timPhongTheoTinhTrang(
            TinhTrangPhong tinhTrang) {

        if (tinhTrang == null) {
            return null;
        }

        return phongDAO.timPhongTheoTinhTrang(tinhTrang);
    }
    public int demSoLuongPhong() {
        return phongDAO.demSoLuongPhong();
    }
    public int demSoLuongPhongTheoTinhTrang(TinhTrangPhong tinhTrang) {
        if (tinhTrang == null) {
            return 0;
        }
        return phongDAO.demSoLuongPhongTheoTinhTrang(tinhTrang);
    }
}
