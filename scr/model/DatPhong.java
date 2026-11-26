package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;

public class DatPhong {
    private String maDatPhong;
    private String maKhachHang;
    private String[] danhSachMaPhong = new String[0];
    private LocalDate ngayDat;
    private LocalDate ngayNhanPhong;
    private LocalDate ngayTraPhong;
    private TrangThaiDatPhong trangThai;
    private BigDecimal giaPhongNgay;

    public DatPhong() {
    }

    public DatPhong(String maDatPhong, String maKhachHang, String maPhong,
                    LocalDate ngayNhanPhong, LocalDate ngayTraPhong, BigDecimal giaPhongNgay) {
        this.maDatPhong = maDatPhong;
        this.maKhachHang = maKhachHang;
        ganMaPhongNoiBo(maPhong);
        this.ngayDat = LocalDate.now();
        this.ngayNhanPhong = ngayNhanPhong;
        this.ngayTraPhong = ngayTraPhong;
        this.giaPhongNgay = giaPhongNgay;
        this.trangThai = TrangThaiDatPhong.DA_DAT;
    }

    public DatPhong(String maDatPhong, String maKhachHang, String[] danhSachMaPhong,
                    LocalDate ngayNhanPhong, LocalDate ngayTraPhong, BigDecimal giaPhongNgay) {
        this.maDatPhong = maDatPhong;
        this.maKhachHang = maKhachHang;
        ganDanhSachPhongNoiBo(danhSachMaPhong);
        this.ngayDat = LocalDate.now();
        this.ngayNhanPhong = ngayNhanPhong;
        this.ngayTraPhong = ngayTraPhong;
        this.giaPhongNgay = giaPhongNgay;
        this.trangThai = TrangThaiDatPhong.DA_DAT;
    }

    public DatPhong(DatPhong other) {
        if (other != null) {
            this.maDatPhong = other.maDatPhong;
            this.maKhachHang = other.maKhachHang;
            this.danhSachMaPhong = Arrays.copyOf(other.danhSachMaPhong, other.danhSachMaPhong.length);
            this.ngayDat = other.ngayDat;
            this.ngayNhanPhong = other.ngayNhanPhong;
            this.ngayTraPhong = other.ngayTraPhong;
            this.trangThai = other.trangThai;
            this.giaPhongNgay = other.giaPhongNgay;
        }
    }

    public String getMaDatPhong() { return maDatPhong; }
    public void setMaDatPhong(String maDatPhong) { this.maDatPhong = maDatPhong; }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }

    public String getMaPhong() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < danhSachMaPhong.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(danhSachMaPhong[i]);
        }
        return sb.toString();
    }

    public void setMaPhong(String maPhong) {
        ganMaPhongNoiBo(maPhong);
    }

    private void ganMaPhongNoiBo(String maPhong) {
        danhSachMaPhong = new String[0];
        if (maPhong == null || maPhong.trim().isEmpty()) {
            return;
        }
        String[] parts = maPhong.split(",");
        for (String p : parts) {
            themMaPhongNoiBo(p);
        }
    }

    public String[] getDanhSachMaPhong() {
        return Arrays.copyOf(danhSachMaPhong, danhSachMaPhong.length);
    }

    public void setDanhSachMaPhong(String[] ds) {
        ganDanhSachPhongNoiBo(ds);
    }

    private void ganDanhSachPhongNoiBo(String[] ds) {
        danhSachMaPhong = new String[0];
        if (ds != null) {
            for (String ma : ds) {
                themMaPhongNoiBo(ma);
            }
        }
    }

    public boolean themMaPhong(String maPhong) {
        return themMaPhongNoiBo(maPhong);
    }

    private boolean themMaPhongNoiBo(String maPhong) {
        if (maPhong == null || maPhong.trim().isEmpty()) {
            return false;
        }
        String chuan = maPhong.trim().toUpperCase();
        if (coChuaPhongNoiBo(chuan)) {
            return false;
        }
        danhSachMaPhong = Arrays.copyOf(danhSachMaPhong, danhSachMaPhong.length + 1);
        danhSachMaPhong[danhSachMaPhong.length - 1] = chuan;
        return true;
    }

    public boolean coChuaPhong(String maPhong) {
        return coChuaPhongNoiBo(maPhong);
    }

    private boolean coChuaPhongNoiBo(String maPhong) {
        if (maPhong == null || maPhong.trim().isEmpty()) {
            return false;
        }
        String target = maPhong.trim();
        for (String p : danhSachMaPhong) {
            if (p != null && p.equalsIgnoreCase(target)) {
                return true;
            }
        }
        return false;
    }

    public int getSoLuongPhong() {
        return danhSachMaPhong.length;
    }

    public LocalDate getNgayDat() { return ngayDat; }
    public void setNgayDat(LocalDate ngayDat) { this.ngayDat = ngayDat; }

    public LocalDate getNgayNhanPhong() { return ngayNhanPhong; }
    public void setNgayNhanPhong(LocalDate ngayNhanPhong) { this.ngayNhanPhong = ngayNhanPhong; }

    public LocalDate getNgayTraPhong() { return ngayTraPhong; }
    public void setNgayTraPhong(LocalDate ngayTraPhong) { this.ngayTraPhong = ngayTraPhong; }

    public TrangThaiDatPhong getTrangThai() { return trangThai; }
    public void setTrangThai(TrangThaiDatPhong trangThai) { this.trangThai = trangThai; }

    public BigDecimal getGiaPhongNgay() { return giaPhongNgay; }
    public void setGiaPhongNgay(BigDecimal giaPhongNgay) { this.giaPhongNgay = giaPhongNgay; }

    public long getSoNgayO() {
        if (ngayNhanPhong == null || ngayTraPhong == null) return 0;
        long soNgay = ChronoUnit.DAYS.between(ngayNhanPhong, ngayTraPhong);
        return soNgay > 0 ? soNgay : 1;
    }

    public BigDecimal tinhTienPhong() {
        if (giaPhongNgay == null) return BigDecimal.ZERO;
        return BigDecimal.valueOf(getSoNgayO()).multiply(giaPhongNgay);
    }

    public boolean isNgayHopLe() {
        if (ngayNhanPhong == null || ngayTraPhong == null) return false;
        return !ngayTraPhong.isBefore(ngayNhanPhong);
    }

    public boolean isTrungLich(LocalDate tuNgay, LocalDate denNgay) {
        if (this.trangThai == TrangThaiDatPhong.DA_HUY || this.trangThai == TrangThaiDatPhong.DA_TRA) {
            return false;
        }
        if (this.ngayNhanPhong == null || this.ngayTraPhong == null || tuNgay == null || denNgay == null) {
            return false;
        }
        LocalDate end1 = this.ngayTraPhong.isAfter(this.ngayNhanPhong)
                ? this.ngayTraPhong
                : this.ngayNhanPhong.plusDays(1);
        LocalDate end2 = denNgay.isAfter(tuNgay)
                ? denNgay
                : tuNgay.plusDays(1);
        return this.ngayNhanPhong.isBefore(end2) && end1.isAfter(tuNgay);
    }

    @Override
    public String toString() {
        return "DatPhong{" +
                "maDatPhong='" + maDatPhong + '\'' +
                ", maKhachHang='" + maKhachHang + '\'' +
                ", danhSachPhong=" + Arrays.toString(danhSachMaPhong) +
                ", ngayDat=" + ngayDat +
                ", ngayNhanPhong=" + ngayNhanPhong +
                ", ngayTraPhong=" + ngayTraPhong +
                ", trangThai=" + (trangThai != null ? trangThai.getTenTrangThai() : "") +
                ", giaPhongNgay=" + giaPhongNgay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatPhong datPhong = (DatPhong) o;
        return maDatPhong != null && datPhong.maDatPhong != null
                ? maDatPhong.equalsIgnoreCase(datPhong.maDatPhong)
                : Objects.equals(maDatPhong, datPhong.maDatPhong);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maDatPhong == null ? null : maDatPhong.toUpperCase());
    }
}
