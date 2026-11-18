package INTERFACE;
import model.HoaDon;
import model.TrangThaiThanhToan;
public interface IHoaDon {
 boolean themHoaDon(HoaDon hoaDon);
 boolean suaHoaDon(HoaDon hoaDon);
 boolean xoaHoaDon(String maHD);
 HoaDon[] layDanhSachHoaDon();
 boolean capNhatTrangThaiThanhToan(String maHD, TrangThaiThanhToan trangThai);
}
