package INTERFACE;

import model.HoaDon;
import model.TrangThaiThanhToan;
import java.math.BigDecimal;

public interface IHoaDon {
    boolean themHoaDon(HoaDon hoaDon);

    boolean suaHoaDon(HoaDon hoaDon);

    boolean xoaHoaDon(String maHD);

    HoaDon[] layDanhSachHoaDon();

    boolean capNhatTrangThaiThanhToan(String maHD, TrangThaiThanhToan trangThai);

    HoaDon timHoaDon(String maHD);

    boolean daCoHoaDon(String maDatPhong);

    BigDecimal tinhTienPhongTrongHoaDon(HoaDon hoaDon);

    HoaDon lapHoaDonTuDatPhong(String maHD, String maDatPhong, BigDecimal giamGia);
}
