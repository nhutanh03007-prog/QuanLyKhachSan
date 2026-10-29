package model;
import java.math.BigDecimal;
class PhongVIP extends Phong {
    private String dichVuVIP;
    private BigDecimal giaPhong;
    public PhongVIP(String maPhong, String tenPhong, String loaiPhong, BigDecimal giaPhong, TinhTrangPhong tinhTrang, String dichVuVIP) {
        super(maPhong, tenPhong, loaiPhong, giaPhong, tinhTrang);
        this.dichVuVIP = dichVuVIP;
        this.giaPhong = giaPhong;
    }
    public BigDecimal getGiaPhong() {
        return new BigDecimal("120000.0");
    }
    public String getDichVuVIP() {
        return dichVuVIP;
    }
    public void setDichVuVIP(String dichVuVIP) {
        this.dichVuVIP = dichVuVIP;
    }
    public void setGiaPhong(BigDecimal giaPhong) {
        this.giaPhong = giaPhong;
    }
    public void nhap(){
        super.nhap();
        System.out.println("nhap dich vu VIP: ");
        dichVuVIP = sc.nextLine();
    }
    public void xuat(){
        super.xuat();
        System.out.println("|%-20s|".formatted(dichVuVIP));
    }
}