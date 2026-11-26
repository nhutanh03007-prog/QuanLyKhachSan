package service;

import DAO.DatPhongDAO;
import DAO.PhongDAO;
import INTERFACE.IPhong;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import model.DatPhong;
import model.Phong;
import model.TinhTrangPhong;
import model.TrangThaiDatPhong;

public class QLPhong implements IPhong {

    private final PhongDAO phongDAO;
    private final DatPhongDAO datPhongDAO;

    public QLPhong() {
        this.phongDAO = new PhongDAO();
        this.datPhongDAO = new DatPhongDAO();
    }

    private String chuanHoaMaPhong(String maPhong) {
        return maPhong == null ? null : maPhong.trim().toUpperCase();
    }

    private boolean kiemTraThongTinPhong(Phong phong) {
        if (phong == null) {
            System.out.println("Thong tin phong khong hop le!");
            return false;
        }
        if (phong.getMaPhong() == null || phong.getMaPhong().trim().isEmpty()) {
            System.out.println("Ma phong khong duoc de trong!");
            return false;
        }
        if (phong.getTenPhong() == null || phong.getTenPhong().trim().isEmpty()) {
            System.out.println("Ten phong khong duoc de trong!");
            return false;
        }
        if (phong.getGiaPhong() == null || phong.getGiaPhong().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Gia phong phai lon hon 0!");
            return false;
        }
        if (phong.getTinhTrang() == null) {
            System.out.println("Tinh trang phong khong hop le!");
            return false;
        }
        return true;
    }

    private boolean phongConPhieuDat(String maPhong) {
        DatPhong[] ds = datPhongDAO.layDanhSach();
        for (DatPhong dp : ds) {
            if (dp != null && dp.coChuaPhong(maPhong)
                    && dp.getTrangThai() != TrangThaiDatPhong.DA_HUY
                    && dp.getTrangThai() != TrangThaiDatPhong.DA_TRA) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean themPhong(Phong phong) {
        if (!kiemTraThongTinPhong(phong)) {
            return false;
        }

        phong.setMaPhong(chuanHoaMaPhong(phong.getMaPhong()));
        phong.setTenPhong(phong.getTenPhong().trim());

        if (phongDAO.timPhongTheoMa(phong.getMaPhong()) != null) {
            System.out.println("Ma phong da ton tai!");
            return false;
        }
        if (phongDAO.timPhongTheoTen(phong.getTenPhong())) {
            System.out.println("Ten phong da ton tai!");
            return false;
        }
        return phongDAO.themPhong(phong);
    }

    @Override
    public boolean suaPhong(Phong phong) {
        if (!kiemTraThongTinPhong(phong)) {
            return false;
        }

        Phong phongCu = phongDAO.timPhongTheoMa(chuanHoaMaPhong(phong.getMaPhong()));
        if (phongCu == null) {
            System.out.println("Khong tim thay phong!");
            return false;
        }

        phong.setMaPhong(phongCu.getMaPhong());
        phong.setTenPhong(phong.getTenPhong().trim());

        if (phongDAO.timPhongTheoTenKhacMa(phong.getTenPhong(), phongCu.getMaPhong())) {
            System.out.println("Ten phong da ton tai o phong khac!");
            return false;
        }

        return phongDAO.suaPhong(phongCu.getMaPhong(), phong);
    }

    @Override
    public boolean capNhatTinhTrang(String maPhong, TinhTrangPhong tinhTrang) {
        if (tinhTrang == null) {
            return false;
        }
        Phong p = phongDAO.timPhongTheoMa(chuanHoaMaPhong(maPhong));
        if (p == null) {
            System.out.println("Khong tim thay phong!");
            return false;
        }
        p.setTinhTrang(tinhTrang);
        return phongDAO.suaPhong(p.getMaPhong(), p);
    }

    @Override
    public boolean xoaPhong(String maPhong) {
        maPhong = chuanHoaMaPhong(maPhong);
        if (maPhong == null || maPhong.trim().isEmpty()) {
            System.out.println("Ma phong khong hop le!");
            return false;
        }

        Phong phong = phongDAO.timPhongTheoMa(maPhong);
        if (phong == null) {
            System.out.println("Phong khong ton tai!");
            return false;
        }
        if (phong.getTinhTrang() == TinhTrangPhong.DANG_SU_DUNG) {
            System.out.println("Khong the xoa phong dang co khach su dung!");
            return false;
        }
        if (phongConPhieuDat(phong.getMaPhong())) {
            System.out.println("Khong the xoa: phong con phieu dat chua huy hoac chua tra!");
            return false;
        }
        return phongDAO.xoaPhong(maPhong);
    }

    @Override
    public Phong[] layDanhSachPhong() {
        return phongDAO.layDanhSachPhong();
    }

    @Override
    public Phong[] layDanhSachPhongTheoTinhTrang(TinhTrangPhong tinhTrang) {
        if (tinhTrang == null) {
            System.out.println("Tinh trang khong hop le!");
            return new Phong[0];
        }
        return phongDAO.layDanhSachPhongTheoTinhTrang(tinhTrang);
    }

    @Override
    public Phong[] layPhongTrong(LocalDate ngayNhan, LocalDate ngayTra) {
        if (ngayNhan == null || ngayTra == null || ngayTra.isBefore(ngayNhan)) {
            return new Phong[0];
        }
        Phong[] tatCa = phongDAO.layDanhSachPhong();
        Phong[] tam = new Phong[tatCa.length];
        int dem = 0;
        for (Phong p : tatCa) {
            if (p.getTinhTrang() != TinhTrangPhong.DANG_BAO_TRI
                    && datPhongDAO.kiemTraPhongTrong(p.getMaPhong(), ngayNhan, ngayTra)) {
                tam[dem++] = p;
            }
        }
        return Arrays.copyOf(tam, dem);
    }

    @Override
    public Phong timPhongTheoMa(String maPhong) {
        maPhong = chuanHoaMaPhong(maPhong);
        if (maPhong == null || maPhong.isEmpty()) {
            System.out.println("Ma phong khong hop le!");
            return null;
        }
        return phongDAO.timPhongTheoMa(maPhong);
    }

    @Override
    public boolean timPhongTheoTen(String tenPhong) {
        return phongDAO.timPhongTheoTen(tenPhong);
    }

    @Override
    public int demSoLuongPhong() {
        return phongDAO.demSoLuongPhong();
    }

    @Override
    public int demSoLuongPhongTheoTinhTrang(TinhTrangPhong tinhTrang) {
        if (tinhTrang == null) {
            return 0;
        }
        return phongDAO.demSoLuongPhongTheoTinhTrang(tinhTrang);
    }

    @Override
    public Phong[] layDanhSachPhongVIP() {
        return phongDAO.layDanhSachPhongVIP();
    }

    @Override
    public Phong[] layDanhSachPhongThuong() {
        return phongDAO.layDanhSachPhongThuong();
    }
}
