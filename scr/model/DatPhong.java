import java.time.LocalDate;
import java.util.Scanner;
public class DatPhong {
    private String maDatPhong;
    private String maKH;
    private String maPhong;
    private LocalDate ngayDat;
    private LocalDate ngayNhan;
    private LocalDate ngayTra;
    private int soNguoi;
    private TrangThaiDatPhong trangThai;
    private double tongTien;


    public DatPhong(String maDatPhong, String maKH, String maPhong,
                    LocalDate ngayDat, LocalDate ngayNhan,
                    LocalDate ngayTra, int soNguoi,
                    TrangThaiDatPhong trangThai, double tongTien) {
        this.maDatPhong = maDatPhong;
        this.maKH = maKH;
        this.maPhong = maPhong;
        this.ngayDat = ngayDat;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
        this.soNguoi = soNguoi;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
    }

    public String getMaDatPhong() {
        return maDatPhong;
    }

    public void setMaDatPhong(String maDatPhong) {
        this.maDatPhong = maDatPhong;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public LocalDate getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(LocalDate ngayDat) {
        this.ngayDat = ngayDat;
    }

    public LocalDate getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(LocalDate ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

    public LocalDate getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(LocalDate ngayTra) {
        this.ngayTra = ngayTra;
    }

    public int getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(int soNguoi) {
        this.soNguoi = soNguoi;
    }
    public DatPhong() {
        this.trangThai = TrangThaiDatPhong.CHO_XAC_NHAN;
    }
    public TrangThaiDatPhong getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiDatPhong trangThai) {
        this.trangThai = trangThai;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }


    public double tinhTien(double giaPhong) {
    long soNgay = ngayTra.toEpochDay()
                 - ngayNhan.toEpochDay();

    tongTien = soNgay * giaPhong;

    return tongTien;
    }

   