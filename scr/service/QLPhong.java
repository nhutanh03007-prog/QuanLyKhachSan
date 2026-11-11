
package service;
import INTERFACE.IPhong;
import DAO.PhongDAO;
import model.Phong;
import model.TinhTrangPhong;
import java.math.BigDecimal;

public class QLPhong implements IPhong{
    private PhongDAO phongDAO;

    public QLPhong() {
        phongDAO = new PhongDAO();
    }

    @Override
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

    @Override
    public Phong[] layDanhSachPhong() {
        return phongDAO.layDanhSachPhong();
    }

    @Override
    public Phong[] layDanhSachPhongTheoTinhTrang(
            TinhTrangPhong tinhTrang) {
        if (tinhTrang == null) {
            System.out.println("Tinh trang khong hop le!");
            return new Phong[0];
        }
        return phongDAO.layDanhSachPhongTheoTinhTrang(tinhTrang);
    }

    @Override
    public Phong timPhongTheoMa(String maPhong) {
        if (maPhong == null || maPhong.trim().isEmpty()) {
            System.out.println("Ma phong khong hop le!");
            return null;
        }
        return phongDAO.timPhongTheoMa(maPhong);
    }

    @Override
    public boolean suaPhong(Phong phong) {
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
            System.out.println("Gia phong khong hop le!");
            return false;
        }
        Phong phongCu =
                phongDAO.timPhongTheoMa(phong.getMaPhong());
        if (phongCu == null) {
            System.out.println("Khong tim thay phong!");
            return false;
        }
        return phongDAO.suaPhong(phong);
    }

    @Override
    public boolean xoaPhong(String maPhong) {

        if (maPhong == null || maPhong.trim().isEmpty()) {
            System.out.println("Ma phong khong hop le!");
            return false;
        }
        if (phongDAO.timPhongTheoMa(maPhong) == null) {
            System.out.println("Phong khong ton tai!");
            return false;
        }
        return phongDAO.xoaPhong(maPhong);
    }

    @Override
    public Phong[] timPhongTheoTinhTrang(
            TinhTrangPhong tinhTrang) {
        if (tinhTrang == null) {
            System.out.println("Tinh trang khong hop le!");
            return new Phong[0];
        }
        return phongDAO.layDanhSachPhongTheoTinhTrang(tinhTrang);
    }

    @Override
    public int demSoLuongPhong() {
        return phongDAO.demSoLuongPhong();
    }

    @Override
    public int demSoLuongPhongTheoTinhTrang(
            TinhTrangPhong tinhTrang) {
        if (tinhTrang == null) {
            return 0;
        }
        return phongDAO.demSoLuongPhongTheoTinhTrang(tinhTrang);
    }
}
