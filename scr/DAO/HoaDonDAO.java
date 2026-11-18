package DAO;
import java.math.BigDecimal;
import java.time.LocalDate;
import model.HoaDon;
import model.TrangThaiThanhToan;
public class HoaDonDAO {
 public static HoaDon[] danhSachHoaDon=new HoaDon[100]; public static int soLuongHoaDon=0;
 public boolean them(HoaDon h){if(!hopLe(h)||timTheoMa(h.getMaHD())!=null||timTheoMaDatPhong(h.getMaDatPhong())!=null||soLuongHoaDon>=100)return false;if(h.getNgayLap()==null)h.setNgayLap(LocalDate.now());if(h.getTrangThai()==null)h.setTrangThai(TrangThaiThanhToan.CHUA_THANH_TOAN);h.setThanhTien(tinhThanhTien(h));danhSachHoaDon[soLuongHoaDon++]=h;return true;}
 public boolean sua(HoaDon h){if(!hopLe(h))return false;for(int i=0;i<soLuongHoaDon;i++)if(danhSachHoaDon[i]!=null&&h.getMaHD().equals(danhSachHoaDon[i].getMaHD())){if(danhSachHoaDon[i].getTrangThai()==TrangThaiThanhToan.DA_THANH_TOAN)return false;h.setThanhTien(tinhThanhTien(h));danhSachHoaDon[i]=h;return true;}return false;}
 public boolean xoa(String ma){for(int i=0;i<soLuongHoaDon;i++)if(danhSachHoaDon[i]!=null&&ma!=null&&ma.equals(danhSachHoaDon[i].getMaHD())){if(danhSachHoaDon[i].getTrangThai()==TrangThaiThanhToan.DA_THANH_TOAN)return false;System.arraycopy(danhSachHoaDon,i+1,danhSachHoaDon,i,soLuongHoaDon-i-1);danhSachHoaDon[--soLuongHoaDon]=null;return true;}return false;}
 public BigDecimal tinhThanhTien(HoaDon h){return h==null||h.getTongGia()==null||h.getGiamGia()==null?BigDecimal.ZERO:h.getTongGia().subtract(h.getGiamGia());}
 private boolean hopLe(HoaDon h){return h!=null&&!rong(h.getMaHD())&&!rong(h.getMaDatPhong())&&h.getTongGia()!=null&&h.getGiamGia()!=null&&h.getTongGia().signum()>=0&&h.getGiamGia().signum()>=0&&h.getGiamGia().compareTo(h.getTongGia())<=0;}
 public boolean capNhatTrangThai(String ma,TrangThaiThanhToan t,String p,LocalDate d){HoaDon h=timTheoMa(ma);if(h==null||t==null)return false;h.setTrangThai(t);h.setPhuongThucTT(p);h.setNgayThanhToan(d);return true;}
 public HoaDon timTheoMa(String ma){if(rong(ma))return null;for(int i=0;i<soLuongHoaDon;i++)if(danhSachHoaDon[i]!=null&&ma.equals(danhSachHoaDon[i].getMaHD()))return danhSachHoaDon[i];return null;}
 public HoaDon timTheoMaDatPhong(String dp){if(rong(dp))return null;for(int i=0;i<soLuongHoaDon;i++)if(danhSachHoaDon[i]!=null&&dp.equals(danhSachHoaDon[i].getMaDatPhong()))return danhSachHoaDon[i];return null;}
 public HoaDon[] layDanhSach(){return java.util.Arrays.copyOf(danhSachHoaDon,soLuongHoaDon);}
 private boolean rong(String s){return s==null||s.trim().isEmpty();}
}
