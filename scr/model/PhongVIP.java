package model;

import java.io.BufferedReader;
import java.math.BigDecimal;

public class PhongVIP extends Phong {

    public static final String LOAI = "VIP";
    private static final BigDecimal GIA_MAC_DINH = new BigDecimal("1000000");

    private int dienTich;
    private boolean coBonTam;

    public PhongVIP() {
        super("", "", GIA_MAC_DINH, null);
        this.dienTich = 30;
        this.coBonTam = true;
    }

    public PhongVIP(String maPhong, String tenPhong, BigDecimal giaPhong,
                    TinhTrangPhong tinhTrang, int dienTich, boolean coBonTam) {
        super(maPhong, tenPhong, giaPhong, tinhTrang);
        this.dienTich = dienTich;
        this.coBonTam = coBonTam;
    }

    public static BigDecimal getGiaMacDinh() { return GIA_MAC_DINH; }

    @Override
    public String getLoaiPhong() { return LOAI; }

    @Override
    public BigDecimal getGiaMacDinhPhong() { return GIA_MAC_DINH; }

    @Override
    public String getThongTinRieng() { return dienTich + " m2"; }

    @Override
    public String getTienIch() { return coBonTam ? "Co bon tam" : "Khong"; }

    @Override
    public Phong saoChep() {
        return new PhongVIP(getMaPhong(), getTenPhong(), getGiaPhong(), getTinhTrang(), dienTich, coBonTam);
    }

    @Override
    public void nhap(BufferedReader reader) {
        super.nhap(reader);
        nhapThongTinRieng(reader);
    }

    @Override
    public void nhap(BufferedReader reader, String maPhong, String tenPhong) {
        super.nhap(reader, maPhong, tenPhong);
        nhapThongTinRieng(reader);
    }

    private void nhapThongTinRieng(BufferedReader reader) {
        System.out.println("\n--- THONG TIN PHONG VIP ---");
        this.dienTich = nhapSoNguyenDuong(reader, "Nhap dien tich (m2): ");
        this.coBonTam = nhapCoKhong(reader, "Phong co bon tam? (Y/N): ");
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println("Dien tich  : " + dienTich + " m2");
        System.out.println("Bon tam    : " + (coBonTam ? "Co" : "Khong"));
    }

    public int getDienTich() { return dienTich; }
    public void setDienTich(int dienTich) { this.dienTich = dienTich; }

    public boolean isCoBonTam() { return coBonTam; }
    public void setCoBonTam(boolean coBonTam) { this.coBonTam = coBonTam; }

    @Override
    public String toString() {
        return super.toString()
                + " | Dien tich: " + dienTich + " m2"
                + " | Bon tam: " + (coBonTam ? "Co" : "Khong");
    }
}
