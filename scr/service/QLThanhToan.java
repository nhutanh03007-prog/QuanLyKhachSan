package service;

import DAO.ChiTietDichVuDAO;
import DAO.HoaDonDAO;
import DAO.ThanhToanDAO;
import DAO.DatPhongDAO;
import INTERFACE.IThanhToan;
import java.time.LocalDate;
import java.math.BigDecimal;
import model.ChiTietDichVu;
import model.HoaDon;
import model.ThanhToan;
import model.TrangThaiThanhToan;

/** Thực thi nghiệp vụ quản lý hóa đơn và thanh toán. */
public class QLThanhToan implements IThanhToan {

    private final HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private final ThanhToanDAO thanhToanDAO = new ThanhToanDAO();
    private final ChiTietDichVuDAO chiTietDAO = new ChiTietDichVuDAO();
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();

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
    public HoaDon lapHoaDonTuDatPhong(String maHD, String maDatPhong, BigDecimal giamGia) {
        try {
            if (maHD == null || maHD.trim().isEmpty() || maDatPhong == null || maDatPhong.trim().isEmpty() || giamGia == null || giamGia.signum() < 0) return null;
            model.DatPhong dp = datPhongDAO.timTheoMa(maDatPhong);
            if (dp == null) { System.out.println("Lỗi [Lập Hóa Đơn]: Không tìm thấy mã đặt phòng từ hệ thống DatPhongDAO!"); return null; }
            BigDecimal tienPhong = BigDecimal.valueOf(dp.tinhTienPhong());
            if (tienPhong.signum() < 0) System.out.println("Cảnh báo [Lập Hóa Đơn]: Tiền phòng bất thường (âm).");
            BigDecimal tongTienDichVu = BigDecimal.ZERO;
            for (ChiTietDichVu chiTiet : chiTietDAO.layTheoMaDatPhong(maDatPhong)) {
                if (chiTiet == null || chiTiet.getThanhTien() == null) { System.out.println("Lỗi [Lập Hóa Đơn]: Chi tiết dịch vụ không hợp lệ."); return null; }
                chiTiet.tinhThanhTien();
                tongTienDichVu = tongTienDichVu.add(chiTiet.getThanhTien());
            }
            BigDecimal tongGia = tienPhong.add(tongTienDichVu);
            if (giamGia.compareTo(tongGia) > 0) { System.out.println("Lỗi [Lập Hóa Đơn]: Giảm giá không được lớn hơn tổng tiền"); return null; }
            HoaDon hoaDon = new HoaDon(
                    maHD,
                    maDatPhong,
                    LocalDate.now(),
                    tongGia,
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
