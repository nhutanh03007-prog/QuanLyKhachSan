package model;

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