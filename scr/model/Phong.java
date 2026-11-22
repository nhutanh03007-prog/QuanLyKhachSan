package model;

import java.math.BigDecimal;

public abstract class Phong {

    private String maPhong;
    private String tenPhong;
    private BigDecimal giaPhong;
    private TinhTrangPhong tinhTrang;

    protected Phong(String maPhong, String tenPhong,
                    BigDecimal giaPhong, TinhTrangPhong tinhTrang) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.giaPhong = giaPhong;
        this.tinhTrang = tinhTrang;
    }

    // ===== Phan rieng cua tung loai phong =====

    /** Ten loai phong, vi du "Thuong" hoac "VIP" (do lop con quyet dinh). */
    public abstract String getLoaiPhong();

    /** Chuoi ma dich vu, vi du "DV01,DV02". Khong bao gio null. */
    public abstract String getDichVu();

    public abstract void setDichVu(String dichVu);

    /** Tao ban sao doc lap de tranh sua truc tiep du lieu trong DAO. */
    public abstract Phong taoBanSao();

    // ===== Getter / Setter =====

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public BigDecimal getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(BigDecimal giaPhong) {
        this.giaPhong = giaPhong;
    }

    public TinhTrangPhong getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(TinhTrangPhong tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    @Override
    public String toString() {
        return "Ma phong: " + maPhong
                + " | Ten phong: " + tenPhong
                + " | Loai phong: " + getLoaiPhong()
                + " | Gia phong: " + giaPhong
                + " | Tinh trang: " + tinhTrang
                + " | Dich vu: " + getDichVu();
    }
}
