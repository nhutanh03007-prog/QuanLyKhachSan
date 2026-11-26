package service;

import DAO.ChiTietDichVuDAO;
import DAO.DatPhongDAO;
import DAO.DichVuDAO;
import DAO.HoaDonDAO;
import java.math.BigDecimal;
import model.ChiTietDichVu;
import model.DatPhong;
import model.DichVu;
import model.TrangThaiDatPhong;

public class QLDichVu {
    private final DichVuDAO dichVuDAO = new DichVuDAO();
    private final ChiTietDichVuDAO chiTietDAO = new ChiTietDichVuDAO();
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();
    private final HoaDonDAO hoaDonDAO = new HoaDonDAO();

    public boolean themDichVu(DichVu d) {
        return dichVuDAO.them(d);
    }

    public boolean suaDichVu(DichVu d) {
        return dichVuDAO.sua(d);
    }

    public boolean xoaDichVu(String ma) {
        if (chiTietDAO.kiemTraDichVuDangSuDung(ma)) {
            System.out.println("Khong the xoa dich vu dang duoc su dung.");
            return false;
        }
        return dichVuDAO.xoa(ma);
    }

    public DichVu[] layDanhSachDichVu() {
        return dichVuDAO.layDanhSach();
    }

    public DichVu timDichVuTheoMa(String ma) {
        return dichVuDAO.timTheoMa(ma);
    }

    public DichVu[] timDichVu(String key) {
        return dichVuDAO.timKiem(key);
    }

    public String layTenDichVu(String ma) {
        DichVu d = timDichVuTheoMa(ma);
        return d == null ? "Dich vu da ngung" : d.getTenDichVu();
    }

    private boolean datPhongChuaChot(String ma) {
        DatPhong dp = datPhongDAO.timTheoMa(ma);
        return dp != null && dp.getTrangThai() != TrangThaiDatPhong.DA_HUY && hoaDonDAO.timTheoMaDatPhong(ma) == null;
    }

    public boolean themChiTiet(ChiTietDichVu c) {
        if (c == null || !datPhongChuaChot(c.getMaDatPhong())) {
            System.out.println("Loi: Phieu dat phong khong ton tai, da huy hoac da lap hoa don!");
            return false;
        }
        DichVu d = dichVuDAO.timTheoMa(c.getMaDichVu());
        if (d == null || "NGUNG_HOAT_DONG".equalsIgnoreCase(d.getTrangThai())) {
            System.out.println("Loi: Dich vu khong ton tai hoac da ngung hoat dong!");
            return false;
        }
        c.setDonGia(d.getGia());
        return chiTietDAO.them(c);
    }

    public boolean suaChiTiet(ChiTietDichVu c) {
        if (c == null || !datPhongChuaChot(c.getMaDatPhong())) {
            return false;
        }
        DichVu d = dichVuDAO.timTheoMa(c.getMaDichVu());
        if (d == null) {
            return false;
        }
        c.setDonGia(d.getGia());
        return chiTietDAO.sua(c);
    }

    public boolean xoaChiTiet(String dp, String dv) {
        return datPhongChuaChot(dp) && chiTietDAO.xoa(dp, dv);
    }

    public ChiTietDichVu timChiTiet(String dp, String dv) {
        return chiTietDAO.timTheoMa(dp, dv);
    }

    public ChiTietDichVu[] layDanhSachChiTiet() {
        return chiTietDAO.layDanhSach();
    }

    public ChiTietDichVu[] layChiTietTheoDatPhong(String dp) {
        return chiTietDAO.layTheoMaDatPhong(dp);
    }

    public BigDecimal tinhTongTienDichVu(String dp) {
        return chiTietDAO.tinhTongTienTheoDatPhong(dp);
    }
}