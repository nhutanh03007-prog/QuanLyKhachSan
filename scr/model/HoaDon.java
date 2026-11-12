package model;
import java.math.BigDecimal;
import java.time.LocalDate;
public class HoaDon {
 private String maHD,maDatPhong; private LocalDate ngayLap,ngayThanhToan;
 private BigDecimal tongGia=BigDecimal.ZERO,giamGia=BigDecimal.ZERO,thanhTien=BigDecimal.ZERO;
 private String phuongThucTT; private TrangThaiThanhToan trangThai;
 public HoaDon(){trangThai=TrangThaiThanhToan.CHUA_THANH_TOAN;}
 public HoaDon(String maHD,String maDatPhong,LocalDate ngayLap,BigDecimal tongGia,BigDecimal giamGia){this.maHD=maHD;this.maDatPhong=maDatPhong;this.ngayLap=ngayLap;this.tongGia=tongGia;this.giamGia=giamGia;this.trangThai=TrangThaiThanhToan.CHUA_THANH_TOAN;tinhTongTien();}
 public void lapHoaDon(){tinhTongTien();} public void tinhTongTien(){thanhTien=tongGia.subtract(giamGia);}
 public void thanhToan(){trangThai=TrangThaiThanhToan.DA_THANH_TOAN;ngayThanhToan=LocalDate.now();}
 public void xoaHoaDon(){maHD="";maDatPhong="";tongGia=BigDecimal.ZERO;giamGia=BigDecimal.ZERO;thanhTien=BigDecimal.ZERO;}
 public String getMaHD(){return maHD;} public void setMaHD(String v){maHD=v;} public String getMaDatPhong(){return maDatPhong;} public void setMaDatPhong(String v){maDatPhong=v;}
 public LocalDate getNgayLap(){return ngayLap;} public void setNgayLap(LocalDate v){ngayLap=v;} public LocalDate getNgayThanhToan(){return ngayThanhToan;} public void setNgayThanhToan(LocalDate v){ngayThanhToan=v;}
 public BigDecimal getTongGia(){return tongGia;} public void setTongGia(BigDecimal v){tongGia=v;tinhTongTien();} public BigDecimal getGiamGia(){return giamGia;} public void setGiamGia(BigDecimal v){giamGia=v;tinhTongTien();} public BigDecimal getThanhTien(){return thanhTien;}
 public String getPhuongThucTT(){return phuongThucTT;} public void setPhuongThucTT(String v){phuongThucTT=v;} public TrangThaiThanhToan getTrangThai(){return trangThai;} public void setTrangThai(TrangThaiThanhToan v){trangThai=v;}
 @Override public String toString(){return "Ma hoa don: "+maHD+", ma dat phong: "+maDatPhong+", tong gia: "+tongGia+", giam gia: "+giamGia+", thanh tien: "+thanhTien+", trang thai: "+trangThai;}
}
