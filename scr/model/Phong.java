import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public enum TinhTrang {
        TRONG,
        DANG_SU_DUNG
    }
class Phong {
    private String maPhong;
    private String loaiPhong;
    private BigDecimal giaPhong;
    private TinhTrang tinhTrang;
    Scanner sc = new Scanner(System.in);

    public Phong(String maPhong, String loaiPhong, BigDecimal giaPhong, TinhTrang tinhTrang) {
        this.maPhong = maPhong;
        this.loaiPhong = loaiPhong;
        this.giaPhong = giaPhong;
        this.tinhTrang = tinhTrang;
    }
    public String getMaPhong() {
        return maPhong;
    }
    public String getLoaiPhong() {
        return loaiPhong;
    }
    public BigDecimal getGiaPhong() {
        return giaPhong;
    }
    public TinhTrang getTinhTrang() {
        return tinhTrang;
    }
    public void setTinhTrang(TinhTrang tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
    public void nhap(){
        System.out.println("nhap ten phong: ");
        
    }
}
class PhongVIP extends Phong {
    private String dichVuVIP;
    private BigDecimal giaPhong;
    public PhongVIP(String maPhong, String loaiPhong, BigDecimal giaPhong, TinhTrang tinhTrang, String dichVuVIP) {
        super(maPhong, loaiPhong, giaPhong, tinhTrang);
        this.dichVuVIP = dichVuVIP;
        this.giaPhong = giaPhong;
    }
    public BigDecimal getGiaPhong() {
        return new BigDecimal("120000.0");
    }
    public String getDichVuVIP() {
        return dichVuVIP;
    }
}
class PhongThuong extends Phong {
    private String dichVuThuong;
    private BigDecimal giaPhong;
    public PhongThuong(String maPhong, String loaiPhong, BigDecimal giaPhong, TinhTrang tinhTrang, String dichVuThuong) {
        super(maPhong, loaiPhong, giaPhong, tinhTrang);
        this.dichVuThuong = dichVuThuong;
        this.giaPhong = giaPhong;
    }
    public BigDecimal getGiaPhong() {
        return new BigDecimal("60000.0");
    }
    public String getDichVuThuong() {
        return dichVuThuong;
    }
}