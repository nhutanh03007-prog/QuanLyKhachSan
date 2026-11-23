package service;

import DAO.ChiTietDichVuDAO;
import DAO.DatPhongDAO;
import DAO.HoaDonDAO;
import INTERFACE.IHoaDon;
import java.math.BigDecimal;
import java.time.LocalDate;
import model.DatPhong;
import model.HoaDon;
import model.TrangThaiDatPhong;
import model.TrangThaiThanhToan;

public class QLHoaDon implements IHoaDon {
    private final HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();
    private final ChiTietDichVuDAO chiTietDAO = new ChiTietDichVuDAO();

    public boolean themHoaDon(HoaDon h) {
        return hoaDonDAO.them(h);
    }

    public boolean suaHoaDon(HoaDon h) {
        return hoaDonDAO.sua(h);
    }

    public boolean xoaHoaDon(String ma) {
        return hoaDonDAO.xoa(ma);
    }

    public HoaDon[] layDanhSachHoaDon() {
        return hoaDonDAO.layDanhSach();
    }

    public boolean capNhatTrangThaiThanhToan(String ma, TrangThaiThanhToan t) {
        LocalDate ngayThanhToan = t == TrangThaiThanhToan.DA_THANH_TOAN ? LocalDate.now() : null;
        return hoaDonDAO.capNhatTrangThai(ma, t, null, ngayThanhToan);
    }

    public HoaDon timHoaDon(String ma) {
        return hoaDonDAO.timTheoMa(ma);
    }

    public boolean daCoHoaDon(String dp) {
        return hoaDonDAO.timTheoMaDatPhong(dp) != null;
    }

    public BigDecimal tinhTienPhongTrongHoaDon(HoaDon h) {
        if (h == null || h.getTongGia() == null) {
            return BigDecimal.ZERO;
        }
        return h.getTongGia().subtract(chiTietDAO.tinhTongTienTheoDatPhong(h.getMaDatPhong()));
    }

    public HoaDon lapHoaDonTuDatPhong(String maHD, String maDP, BigDecimal giamGia) {
        DatPhong dp = datPhongDAO.timTheoMa(maDP);
        if (dp == null || dp.getTrangThai() == TrangThaiDatPhong.DA_HUY || daCoHoaDon(maDP) || giamGia == null
                || giamGia.signum() < 0) {
            return null;
        }
        BigDecimal phong = dp.tinhTienPhong();
        BigDecimal tong = phong.add(chiTietDAO.tinhTongTienTheoDatPhong(maDP));
        if (giamGia.compareTo(tong) > 0) {
            return null;
        }
        HoaDon h = new HoaDon(maHD, maDP, LocalDate.now(), tong, giamGia);
        return themHoaDon(h) ? h : null;
    }
}
