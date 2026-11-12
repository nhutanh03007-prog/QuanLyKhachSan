package service;

import DAO.ChiTietDichVuDAO;
import DAO.HoaDonDAO;
import DAO.ThanhToanDAO;
import INTERFACE.IThanhToan;
import java.time.LocalDate;
import model.ChiTietDichVu;
import model.HoaDon;
import model.ThanhToan;
import model.TrangThaiThanhToan;

/** Thực thi nghiệp vụ quản lý hóa đơn và thanh toán. */
public class QLThanhToan implements IThanhToan {

    private final HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private final ThanhToanDAO thanhToanDAO = new ThanhToanDAO();
    private final ChiTietDichVuDAO chiTietDAO = new ChiTietDichVuDAO();

    /** Thêm hóa đơn thông qua DAO. */
    @Override
    public boolean themHoaDon(HoaDon hoaDon) {
        try {
            return hoaDonDAO.them(hoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Cập nhật hóa đơn thông qua DAO. */
    @Override
    public boolean suaHoaDon(HoaDon hoaDon) {
        try {
            return hoaDonDAO.sua(hoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Xóa hóa đơn theo mã thông qua DAO. */
    @Override
    public boolean xoaHoaDon(String maHD) {
        try {
            return hoaDonDAO.xoa(maHD);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Trả về danh sách hóa đơn do DAO cung cấp. */
    @Override
    public HoaDon[] layDanhSachHoaDon() {
        try {
            return hoaDonDAO.layDanhSach();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Cập nhật trạng thái của hóa đơn tìm được theo mã. */
    @Override
    public boolean capNhatTrangThaiThanhToan(
            String maHD, TrangThaiThanhToan trangThai) {
        try {
            if (maHD == null || maHD.trim().isEmpty() || trangThai == null) {
                return false;
            }

            HoaDon hoaDon = hoaDonDAO.timTheoMa(maHD);
            if (hoaDon == null) {
                return false;
            }

            hoaDon.setTrangThai(trangThai);
            return hoaDonDAO.sua(hoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Lập hóa đơn, cộng tiền phòng với tổng tiền dịch vụ của đặt phòng. */
    public HoaDon lapHoaDon(
            String maHD, String maDatPhong, double tienPhong, double giamGia) {
        try {
            double tongTienDichVu = 0;
            for (ChiTietDichVu chiTiet : chiTietDAO.layTheoMaDatPhong(maDatPhong)) {
                chiTiet.tinhThanhTien();
                tongTienDichVu += chiTiet.getThanhTien();
            }

            HoaDon hoaDon = new HoaDon(
                    maHD,
                    maDatPhong,
                    LocalDate.now(),
                    tienPhong + tongTienDichVu,
                    giamGia);
            return hoaDonDAO.them(hoaDon) ? hoaDon : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Ghi nhận giao dịch và đánh dấu hóa đơn đã thanh toán. */
    public boolean thanhToan(String maHD, String maThanhToan, String phuongThuc) {
        try {
            HoaDon hoaDon = hoaDonDAO.timTheoMa(maHD);
            if (hoaDon == null
                    || hoaDon.getTrangThai() == TrangThaiThanhToan.DA_THANH_TOAN) {
                return false;
            }

            ThanhToan giaoDich = new ThanhToan(
                    maThanhToan, maHD, hoaDon.getThanhTien(), phuongThuc);
            giaoDich.thucHienThanhToan();
            if (!thanhToanDAO.them(giaoDich)) {
                return false;
            }

            hoaDon.setPhuongThucTT(phuongThuc);
            hoaDon.thanhToan();
            return hoaDonDAO.sua(hoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Trả về các giao dịch thanh toán do DAO lưu trữ. */
    public ThanhToan[] layDanhSachThanhToan() {
        try {
            return thanhToanDAO.layDanhSach();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Tìm hóa đơn theo mã; trả về null nếu không tìm thấy hoặc xảy ra lỗi. */
    public HoaDon timHoaDon(String maHD) {
        try {
            return hoaDonDAO.timTheoMa(maHD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
