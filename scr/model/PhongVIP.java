package model;
import java.math.BigDecimal;
class PhongVIP extends Phong {
    private String dichVuVIP;
    private BigDecimal giaPhong;
    public PhongVIP(String maPhong, String loaiPhong, BigDecimal giaPhong, TinhTrangPhong tinhTrang, String dichVuVIP) {
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