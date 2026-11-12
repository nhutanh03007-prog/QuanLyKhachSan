package service;

import DAO.ChiTietDichVuDAO;
import DAO.HoaDonDAO;
import DAO.ThanhToanDAO;
import java.time.LocalDate;
import model.ChiTietDichVu;
import model.HoaDon;
import model.ThanhToan;
import model.TrangThaiThanhToan;

public class QLThanhToan {
    private HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private ThanhToanDAO thanhToanDAO = new ThanhToanDAO();
    private ChiTietDichVuDAO chiTietDAO = new ChiTietDichVuDAO();

    public boolean themHoaDon(HoaDon hoaDon) { return hoaDonDAO.them(hoaDon); }
    public boolean suaHoaDon(HoaDon hoaDon) { return hoaDonDAO.sua(hoaDon); }
    public boolean xoaHoaDon(String maHD) { return hoaDonDAO.xoa(maHD); }
    public HoaDon[] layDanhSachHoaDon() { return hoaDonDAO.layDanhSach(); }

    /** Tạo hóa đơn với tổng tiền gồm tiền dịch vụ của đặt phòng. */
    public HoaDon lapHoaDon(String maHD, String maDatPhong, double tienPhong, double giamGia) {
        double tongTienDichVu = 0;
        for (ChiTietDichVu chiTiet : chiTietDAO.layTheoMaDatPhong(maDatPhong)) {
            chiTiet.tinhThanhTien();
            tongTienDichVu += chiTiet.getThanhTien();
        }

        HoaDon hoaDon = new HoaDon(maHD, maDatPhong, LocalDate.now(),
                tienPhong + tongTienDichVu, giamGia);
        if (hoaDonDAO.them(hoaDon)) return hoaDon;
        return null;
    }

    public boolean thanhToan(String maHD, String maThanhToan, String phuongThuc) {
        HoaDon hoaDon = hoaDonDAO.timTheoMa(maHD);
        if (hoaDon == null || hoaDon.getTrangThai() == TrangThaiThanhToan.DA_THANH_TOAN) return false;

        ThanhToan giaoDich = new ThanhToan(maThanhToan, maHD,
                hoaDon.getThanhTien(), phuongThuc);
        giaoDich.thucHienThanhToan();
        if (!thanhToanDAO.them(giaoDich)) return false;

        hoaDon.setPhuongThucTT(phuongThuc);
        hoaDon.thanhToan();
        hoaDonDAO.sua(hoaDon);
        return true;
    }

    public ThanhToan[] layDanhSachThanhToan() { return thanhToanDAO.layDanhSach(); }
    public HoaDon timHoaDon(String maHD) { return hoaDonDAO.timTheoMa(maHD); }
}
