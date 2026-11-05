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
    @Override 
    public String toString() {
        return "PhongVIP{" +
                "dichVuVIP='" + dichVuVIP + 
                ", giaPhong=" + giaPhong +
                '}';
    }
}