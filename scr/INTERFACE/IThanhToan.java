package INTERFACE;

import model.HoaDon;
import model.TrangThaiThanhToan;

/** Định nghĩa các nghiệp vụ quản lý hóa đơn và trạng thái thanh toán. */
public interface IThanhToan {

    /** Thêm hóa đơn mới. */
    boolean themHoaDon(HoaDon hoaDon);

    /** Cập nhật thông tin hóa đơn đã tồn tại. */
    boolean suaHoaDon(HoaDon hoaDon);

    /** Xóa hóa đơn theo mã. */
    boolean xoaHoaDon(String maHD);

    /** Lấy danh sách hóa đơn hiện có. */
    HoaDon[] layDanhSachHoaDon();

    /** Cập nhật trạng thái thanh toán của hóa đơn theo mã. */
    boolean capNhatTrangThaiThanhToan(String maHD, TrangThaiThanhToan trangThai);
}
