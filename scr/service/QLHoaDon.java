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

    @Override
    public boolean themHoaDon(HoaDon h) {
        if (h == null || h.getMaDatPhong() == null) {
            return false;
        }
        DatPhong dp = datPhongDAO.timTheoMa(h.getMaDatPhong());
        if (dp == null || dp.getTrangThai() == TrangThaiDatPhong.DA_HUY) {
            System.out.println("Loi: Phieu dat phong khong ton tai hoac da huy!");
            return false;
        }
        return hoaDonDAO.them(h);
    }

    @Override
    public boolean suaHoaDon(HoaDon h) {
        return hoaDonDAO.sua(h);
    }

    @Override
    public boolean xoaHoaDon(String ma) {
        return hoaDonDAO.xoa(ma);
    }

    @Override
    public HoaDon[] layDanhSachHoaDon() {
        return hoaDonDAO.layDanhSach();
    }

    @Override
    public boolean capNhatTrangThaiThanhToan(String ma, TrangThaiThanhToan t) {
        LocalDate ngayThanhToan = t == TrangThaiThanhToan.DA_THANH_TOAN ? LocalDate.now() : null;
        return hoaDonDAO.capNhatTrangThai(ma, t, null, ngayThanhToan);
    }

    @Override
    public HoaDon timHoaDon(String ma) {
        return hoaDonDAO.timTheoMa(ma);
    }

    @Override
    public boolean daCoHoaDon(String dp) {
        return hoaDonDAO.timTheoMaDatPhong(dp) != null;
    }

    @Override
    public BigDecimal tinhTienPhongTrongHoaDon(HoaDon h) {
        if (h == null || h.getTongGia() == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal tienPhong = h.getTongGia().subtract(chiTietDAO.tinhTongTienTheoDatPhong(h.getMaDatPhong()));
        return tienPhong.signum() < 0 ? BigDecimal.ZERO : tienPhong;
    }

    @Override
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
        HoaDon h = new HoaDon(maHD, dp.getMaDatPhong(), LocalDate.now(), tong, giamGia);
        return themHoaDon(h) ? h : null;
    }
}
