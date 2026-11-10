
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DatPhong {
    private String maDatPhong;
    private String maKhachHang;
    private String maPhong;
    private LocalDate ngayDat;
    private LocalDate ngayNhanPhong;
    private LocalDate ngayTraPhong;
    private TrangThaiDatPhong trangThai;
    private double giaPhongNgay;

    public DatPhong() {
        this.ngayDat = LocalDate.now();
        this.trangThai = TrangThaiDatPhong.DA_DAT;
    }

    public DatPhong(String maDatPhong, String maKhachHang, String maPhong, 
                    LocalDate ngayNhanPhong, LocalDate ngayTraPhong, double giaPhongNgay) {
        this.maDatPhong = maDatPhong;
        this.maKhachHang = maKhachHang;
        this.maPhong = maPhong;
        this.ngayDat = LocalDate.now();
        this.ngayNhanPhong = ngayNhanPhong;
        this.ngayTraPhong = ngayTraPhong;
        this.giaPhongNgay = giaPhongNgay;
        this.trangThai = TrangThaiDatPhong.DA_DAT;
    }


    public String getMaDatPhong() { return maDatPhong; }
    public void setMaDatPhong(String maDatPhong) { this.maDatPhong = maDatPhong; }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }

    public String getMaPhong() { return maPhong; }
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }

    public LocalDate getNgayDat() { return ngayDat; }
    public void setNgayDat(LocalDate ngayDat) { this.ngayDat = ngayDat; }

    public LocalDate getNgayNhanPhong() { return ngayNhanPhong; }
    public void setNgayNhanPhong(LocalDate ngayNhanPhong) { this.ngayNhanPhong = ngayNhanPhong; }

    public LocalDate getNgayTraPhong() { return ngayTraPhong; }
    public void setNgayTraPhong(LocalDate ngayTraPhong) { this.ngayTraPhong = ngayTraPhong; }

    public TrangThaiDatPhong getTrangThai() { return trangThai; }
    public void setTrangThai(TrangThaiDatPhong trangThai) { this.trangThai = trangThai; }

    public double getGiaPhongNgay() { return giaPhongNgay; }
    public void setGiaPhongNgay(double giaPhongNgay) { this.giaPhongNgay = giaPhongNgay; }



    public long getSoNgayO() {
        if (ngayNhanPhong == null || ngayTraPhong == null) return 0;
        long soNgay = ChronoUnit.DAYS.between(ngayNhanPhong, ngayTraPhong);
        return soNgay > 0 ? soNgay : 1; 
    }

    public double tinhTienPhong() {
        return getSoNgayO() * giaPhongNgay;
    }

    public boolean isNgayHopLe() {
        if (ngayNhanPhong == null || ngayTraPhong == null) return false;
        return !ngayTraPhong.isBefore(ngayNhanPhong);
    }

    public boolean isTrungLich(LocalDate tuNgay, LocalDate denNgay) {
        if (this.trangThai == TrangThaiDatPhong.DA_HUY || this.trangThai == TrangThaiDatPhong.DA_TRA) {
            return false;
        }
        return this.ngayNhanPhong.isBefore(denNgay) && this.ngayTraPhong.isAfter(tuNgay);
    }

    // --- CÁC PHƯƠNG THỨC OVERRIDE CHUẨN JAVA ---

    @Override
    public String toString() {
        return "DatPhong{" +
                "maDatPhong='" + maDatPhong + '\'' +
                ", maKhachHang='" + maKhachHang + '\'' +
                ", maPhong='" + maPhong + '\'' +
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
        return Objects.equals(maDatPhong, datPhong.maDatPhong);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maDatPhong);
    }
}