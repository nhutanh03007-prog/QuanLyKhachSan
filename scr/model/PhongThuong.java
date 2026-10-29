package model;
import java.math.BigDecimal;
class PhongThuong extends Phong {
    private String dichVuThuong;
    private BigDecimal giaPhong;
    public PhongThuong(String maPhong, String loaiPhong, BigDecimal giaPhong, TinhTrangPhong tinhTrang, String dichVuThuong) {
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