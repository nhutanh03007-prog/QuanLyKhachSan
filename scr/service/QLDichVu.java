package service;

import dao.ChiTietDichVuDAO;
import dao.DichVuDAO;
import model.ChiTietDichVu;
import model.DichVu;

public class QLDichVuService {
    private DichVuDAO dichVuDAO = new DichVuDAO();
    private ChiTietDichVuDAO chiTietDAO = new ChiTietDichVuDAO();

    public boolean themDichVu(DichVu dichVu) { return dichVuDAO.them(dichVu); }
    public boolean suaDichVu(DichVu dichVu) { return dichVuDAO.sua(dichVu); }
    public boolean xoaDichVu(String maDichVu) { return dichVuDAO.xoa(maDichVu); }
    public DichVu[] layDanhSachDichVu() { return dichVuDAO.layDanhSach(); }

    public boolean themChiTiet(ChiTietDichVu chiTiet) {
        if (chiTiet == null || chiTiet.getSoLuong() <= 0) return false;
        DichVu dichVu = dichVuDAO.timTheoMa(chiTiet.getMaDichVu());
        if (dichVu == null) return false;
        chiTiet.setDonGia(dichVu.getGia());
        chiTiet.tinhThanhTien();
        return chiTietDAO.them(chiTiet);
    }

    public boolean suaChiTiet(ChiTietDichVu chiTiet) {
        if (chiTiet == null || chiTiet.getSoLuong() <= 0) return false;
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

    public double tinhTongTienDichVu(String maDatPhong) {
        double tongTien = 0;
        for (ChiTietDichVu chiTiet : chiTietDAO.layTheoMaDatPhong(maDatPhong)) {
            chiTiet.tinhThanhTien();
            tongTien += chiTiet.getThanhTien();
        }
        return tongTien;
    }
}
