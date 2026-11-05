package model;
import java.math.BigDecimal;
public abstract class Phong {
    private String maPhong;
    private String tenPhong;
    private String loaiPhong;
    private BigDecimal giaPhong;
    private TinhTrangPhong tinhTrang;

    public Phong(String maPhong, String tenPhong,
                 String loaiPhong, BigDecimal giaPhong,
                 TinhTrangPhong tinhTrang) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.loaiPhong = loaiPhong;
        this.giaPhong = giaPhong;
        this.tinhTrang = tinhTrang;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public BigDecimal getGiaPhong() {
        return giaPhong;
    }

    public TinhTrangPhong getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(TinhTrangPhong tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public void setGiaPhong(BigDecimal giaPhong) {
        this.giaPhong = giaPhong;
    }

    @Override
    public String toString() {
        return "Phong{" +
                "maPhong='" + maPhong + '\'' +
                ", tenPhong='" + tenPhong + '\'' +
                ", loaiPhong='" + loaiPhong + '\'' +
                ", giaPhong=" + giaPhong +
                ", tinhTrang=" + tinhTrang +
                '}';
    }
}
