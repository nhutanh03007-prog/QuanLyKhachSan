import java.time.LocalDate;

public class HoaDon {
    private String maHD;
    private String maDatPhong;
    private LocalDate ngayLap;
    private LocalDate ngayThanhToan;
    private double tongGia;
    private double giamGia;
    private double thanhTien;
    private String phuongThucTT;
    private TrangThaiThanhToan trangThai;

    public HoaDon() {
        trangThai = TrangThaiThanhToan.CHUA_THANH_TOAN;
    }

    public HoaDon(String maHD, String maDatPhong, LocalDate ngayLap,
                  double tongGia, double giamGia) {
        this.maHD = maHD;
        this.maDatPhong = maDatPhong;
        this.ngayLap = ngayLap;
        this.tongGia = tongGia;
        this.giamGia = giamGia;
        this.trangThai = TrangThaiThanhToan.CHUA_THANH_TOAN;
        tinhTongTien();
    }

    public void lapHoaDon() { tinhTongTien(); }
    public void tinhTongTien() { thanhTien = tongGia - giamGia; }
    public void thanhToan() {
        trangThai = TrangThaiThanhToan.DA_THANH_TOAN;
        ngayThanhToan = LocalDate.now();
    }
    public void xoaHoaDon() {
        maHD = "";
        maDatPhong = "";
        tongGia = 0;
        giamGia = 0;
        thanhTien = 0;
    }

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }
    public String getMaDatPhong() { return maDatPhong; }
    public void setMaDatPhong(String maDatPhong) { this.maDatPhong = maDatPhong; }
    public LocalDate getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDate ngayLap) { this.ngayLap = ngayLap; }
    public LocalDate getNgayThanhToan() { return ngayThanhToan; }
    public void setNgayThanhToan(LocalDate ngayThanhToan) { this.ngayThanhToan = ngayThanhToan; }
    public double getTongGia() { return tongGia; }
    public void setTongGia(double tongGia) { this.tongGia = tongGia; tinhTongTien(); }
    public double getGiamGia() { return giamGia; }
    public void setGiamGia(double giamGia) { this.giamGia = giamGia; tinhTongTien(); }
    public double getThanhTien() { return thanhTien; }
    public String getPhuongThucTT() { return phuongThucTT; }
    public void setPhuongThucTT(String phuongThucTT) { this.phuongThucTT = phuongThucTT; }
    public TrangThaiThanhToan getTrangThai() { return trangThai; }
    public void setTrangThai(TrangThaiThanhToan trangThai) { this.trangThai = trangThai; }

    @Override
    public String toString() {
        return "Ma hoa don: " + maHD + ", ma dat phong: " + maDatPhong
                + ", tong gia: " + tongGia + ", giam gia: " + giamGia
                + ", thanh tien: " + thanhTien + ", trang thai: " + trangThai;
    }
}