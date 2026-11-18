package service;
import DAO.HoaDonDAO;
import DAO.ThanhToanDAO;
import java.time.LocalDate;
import model.*;
public class QLThanhToan {
 private final HoaDonDAO hoaDonDAO=new HoaDonDAO(); private final ThanhToanDAO thanhToanDAO=new ThanhToanDAO();
 public boolean thanhToan(String maHD,String maThanhToan,String phuongThuc){HoaDon h=hoaDonDAO.timTheoMa(maHD);if(h==null||h.getTrangThai()==TrangThaiThanhToan.DA_THANH_TOAN||h.getThanhTien()==null)return false;LocalDate ngay=LocalDate.now();ThanhToan t=new ThanhToan(maThanhToan,maHD,h.getThanhTien(),phuongThuc,ngay,TrangThaiThanhToan.DA_THANH_TOAN);if(!thanhToanDAO.them(t))return false;return hoaDonDAO.capNhatTrangThai(maHD,TrangThaiThanhToan.DA_THANH_TOAN,phuongThuc,ngay);}
 public ThanhToan[] layDanhSachThanhToan(){return thanhToanDAO.layDanhSach();} public ThanhToan timThanhToan(String ma){return thanhToanDAO.timTheoMa(ma);} public ThanhToan[] layThanhToanTheoHoaDon(String ma){return thanhToanDAO.layTheoMaHD(ma);}
}
