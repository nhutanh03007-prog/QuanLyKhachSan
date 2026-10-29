package model;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
abstract class Phong {
    private String maPhong;
    private String tenPhong;
    private String loaiPhong;
    private BigDecimal giaPhong;
    private TinhTrangPhong tinhTrang;
    Scanner sc = new Scanner(System.in);

    public Phong(String maPhong, String tenPhong, String loaiPhong, BigDecimal giaPhong, TinhTrangPhong tinhTrang) {
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
    public void nhap(){
        System.out.println("nhap ten phong: ");
        tenPhong = sc.nextLine();
        System.out.println("nhap ma phong: ");
        maPhong = sc.nextLine();
        System.out.println("nhap loai phong: ");
        loaiPhong = sc.nextLine();
        System.out.println("nhap gia phong: ");
        giaPhong = new BigDecimal(sc.nextLine());
        System.out.println("nhap tinh trang phong: ");
        tinhTrang = TinhTrangPhong.valueOf(sc.nextLine().toUpperCase());
    }
    public void xuat(){
        System.out.println("|-%-10s|%-20s|%-20s|%-20s|%-20s|%-20s|".formatted(maPhong, tenPhong, loaiPhong, giaPhong, tinhTrang));
    }
}

