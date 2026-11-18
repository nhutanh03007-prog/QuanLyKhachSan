package DAO;
import java.math.BigDecimal;
import java.util.ArrayList;
import model.ChiTietDichVu;
public class ChiTietDichVuDAO {
 public static ChiTietDichVu[] danhSachChiTiet=new ChiTietDichVu[100]; public static int soLuongChiTiet=0;
 public boolean them(ChiTietDichVu c){if(!hopLe(c)||timTheoMa(c.getMaDatPhong(),c.getMaDichVu())!=null||soLuongChiTiet>=100)return false;c.setThanhTien(tinhThanhTien(c));danhSachChiTiet[soLuongChiTiet++]=c;return true;}
 public boolean sua(ChiTietDichVu c){if(!hopLe(c))return false;for(int i=0;i<soLuongChiTiet;i++)if(cungKhoa(danhSachChiTiet[i],c.getMaDatPhong(),c.getMaDichVu())){c.setThanhTien(tinhThanhTien(c));danhSachChiTiet[i]=c;return true;}return false;}
 private boolean hopLe(ChiTietDichVu c){return c!=null&&!rong(c.getMaDatPhong())&&!rong(c.getMaDichVu())&&c.getSoLuong()>0&&c.getDonGia()!=null&&c.getDonGia().signum()>=0;}
 public BigDecimal tinhThanhTien(ChiTietDichVu c){return c==null||c.getDonGia()==null?BigDecimal.ZERO:c.getDonGia().multiply(BigDecimal.valueOf(c.getSoLuong()));}
 public boolean xoa(String dp,String dv){for(int i=0;i<soLuongChiTiet;i++)if(cungKhoa(danhSachChiTiet[i],dp,dv)){System.arraycopy(danhSachChiTiet,i+1,danhSachChiTiet,i,soLuongChiTiet-i-1);danhSachChiTiet[--soLuongChiTiet]=null;return true;}return false;}
 public ChiTietDichVu timTheoMa(String dp,String dv){for(int i=0;i<soLuongChiTiet;i++)if(cungKhoa(danhSachChiTiet[i],dp,dv))return danhSachChiTiet[i];return null;}
 public ChiTietDichVu[] layDanhSach(){return java.util.Arrays.copyOf(danhSachChiTiet,soLuongChiTiet);}
 public ChiTietDichVu[] layTheoMaDatPhong(String dp){ArrayList<ChiTietDichVu> a=new ArrayList<>();for(int i=0;i<soLuongChiTiet;i++)if(danhSachChiTiet[i]!=null&&dp!=null&&dp.equals(danhSachChiTiet[i].getMaDatPhong()))a.add(danhSachChiTiet[i]);return a.toArray(new ChiTietDichVu[0]);}
 public boolean kiemTraDichVuDangSuDung(String ma){for(int i=0;i<soLuongChiTiet;i++)if(danhSachChiTiet[i]!=null&&ma!=null&&ma.equals(danhSachChiTiet[i].getMaDichVu()))return true;return false;}
 public BigDecimal tinhTongTienTheoDatPhong(String dp){BigDecimal sum=BigDecimal.ZERO;for(ChiTietDichVu c:layTheoMaDatPhong(dp))sum=sum.add(tinhThanhTien(c));return sum;}
 private boolean cungKhoa(ChiTietDichVu c,String dp,String dv){return c!=null&&dp!=null&&dv!=null&&dp.equals(c.getMaDatPhong())&&dv.equals(c.getMaDichVu());}
 private boolean rong(String s){return s==null||s.trim().isEmpty();}
}
