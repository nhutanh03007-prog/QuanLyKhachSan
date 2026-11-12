package service;

import DAO.ChiTietDichVuDAO;
import DAO.DichVuDAO;
import model.ChiTietDichVu;
import model.DichVu;
import java.math.BigDecimal;

public class QLDichVu {
    private DichVuDAO dichVuDAO = new DichVuDAO();
    private ChiTietDichVuDAO chiTietDAO = new ChiTietDichVuDAO();

    public boolean themDichVu(DichVu dichVu) { return dichVu != null && dichVu.getTenDichVu() != null && !dichVu.getTenDichVu().trim().isEmpty() && dichVu.getGia() != null && dichVu.getGia().compareTo(BigDecimal.ZERO) > 0 && dichVuDAO.them(dichVu); }
    public boolean suaDichVu(DichVu dichVu) { return dichVu != null && dichVu.getTenDichVu() != null && !dichVu.getTenDichVu().trim().isEmpty() && dichVu.getGia() != null && dichVu.getGia().compareTo(BigDecimal.ZERO) > 0 && dichVuDAO.sua(dichVu); }
    public boolean xoaDichVu(String maDichVu) { return dichVuDAO.xoa(maDichVu); }
    public DichVu[] layDanhSachDichVu() { return dichVuDAO.layDanhSach(); }

    public boolean themChiTiet(ChiTietDichVu chiTiet) {
        if (chiTiet == null || chiTiet.getSoLuong() <= 0 || chiTiet.getMaDichVu() == null) return false;
        DichVu dichVu = dichVuDAO.timTheoMa(chiTiet.getMaDichVu());
        if (dichVu == null) return false;
        chiTiet.setDonGia(dichVu.getGia());
        chiTiet.tinhThanhTien();
        return chiTietDAO.them(chiTiet);
    }

    public boolean suaChiTiet(ChiTietDichVu chiTiet) {
        if (chiTiet == null || chiTiet.getSoLuong() <= 0 || chiTiet.getMaDichVu() == null) return false;
        DichVu dichVu = dichVuDAO.timTheoMa(chiTiet.getMaDichVu());
        if (dichVu == null) return false;
        chiTiet.setDonGia(dichVu.getGia());
        chiTiet.tinhThanhTien();
        return chiTietDAO.sua(chiTiet);
    }

    public boolean xoaChiTiet(String maDatPhong, String maDichVu) {
        return chiTietDAO.xoa(maDatPhong, maDichVu);
    }

    public ChiTietDichVu[] layDanhSachChiTiet() { return chiTietDAO.layDanhSach(); }

    public ChiTietDichVu[] layChiTietTheoDatPhong(String maDatPhong) {
        return chiTietDAO.layTheoMaDatPhong(maDatPhong);
    }

    public BigDecimal tinhTongTienDichVu(String maDatPhong) {
        BigDecimal tongTien = BigDecimal.ZERO;
        for (ChiTietDichVu chiTiet : chiTietDAO.layTheoMaDatPhong(maDatPhong)) {
            chiTiet.tinhThanhTien();
            tongTien = tongTien.add(chiTiet.getThanhTien());
        }
        return tongTien;
    }
}
