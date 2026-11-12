package model;
import java.math.BigDecimal;
import java.time.LocalDate;
public class ThanhToan {
 private String maThanhToan,maHD; private BigDecimal soTien=BigDecimal.ZERO; private String phuongThuc; private LocalDate ngayThanhToan; private TrangThaiThanhToan trangThai;
 public ThanhToan(){trangThai=TrangThaiThanhToan.CHUA_THANH_TOAN;}
 public ThanhToan(String maThanhToan,String maHD,BigDecimal soTien,String phuongThuc){this.maThanhToan=maThanhToan;this.maHD=maHD;this.soTien=soTien;this.phuongThuc=phuongThuc;this.trangThai=TrangThaiThanhToan.CHUA_THANH_TOAN;}
 public void thucHienThanhToan(){trangThai=TrangThaiThanhToan.DA_THANH_TOAN;ngayThanhToan=LocalDate.now();}
 public String getMaThanhToan(){return maThanhToan;} public void setMaThanhToan(String v){maThanhToan=v;} public String getMaHD(){return maHD;} public void setMaHD(String v){maHD=v;}
 public BigDecimal getSoTien(){return soTien;} public void setSoTien(BigDecimal v){soTien=v;} public String getPhuongThuc(){return phuongThuc;} public void setPhuongThuc(String v){phuongThuc=v;}
 public LocalDate getNgayThanhToan(){return ngayThanhToan;} public TrangThaiThanhToan getTrangThai(){return trangThai;} public void setTrangThai(TrangThaiThanhToan v){trangThai=v;}
}
