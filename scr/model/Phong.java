package model;

import java.math.BigDecimal;

public abstract class Phong {

    private String maPhong;
    private String tenPhong;
    private BigDecimal giaPhong;
    private TinhTrangPhong tinhTrang;

    protected Phong() {
        this.maPhong = "";
        this.tenPhong = "";
        this.giaPhong = BigDecimal.ZERO;
        this.tinhTrang = null;
    }

    protected Phong(String maPhong, String tenPhong,
                    BigDecimal giaPhong, TinhTrangPhong tinhTrang) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.giaPhong = giaPhong;
        this.tinhTrang = tinhTrang;
    }

    public abstract String getLoaiPhong();

    public String getMaPhong() { return maPhong; }
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }

    public String getTenPhong() { return tenPhong; }
    public void setTenPhong(String tenPhong) { this.tenPhong = tenPhong; }

    public BigDecimal getGiaPhong() { return giaPhong; }
    public void setGiaPhong(BigDecimal giaPhong) { this.giaPhong = giaPhong; }

    public TinhTrangPhong getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(TinhTrangPhong tinhTrang) { this.tinhTrang = tinhTrang; }

    @Override
    public String toString() {
        return "Ma phong: " + maPhong
                + " | Ten phong: " + tenPhong
                + " | Loai phong: " + getLoaiPhong()
                + " | Gia phong: " + giaPhong
                + " | Tinh trang: " + tinhTrang;
    }
}