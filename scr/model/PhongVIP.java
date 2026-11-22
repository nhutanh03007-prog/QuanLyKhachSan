
package model;

import java.math.BigDecimal;

public class PhongVIP extends Phong {

    private String dichVuVIP;

    public PhongVIP(String maPhong, String tenPhong,
                    String loaiPhong, BigDecimal giaPhong,
                    TinhTrangPhong tinhTrang,
                    String dichVuVIP) {

        super(maPhong, tenPhong, loaiPhong,
              giaPhong, tinhTrang);

        this.dichVuVIP = dichVuVIP;
    }

    public String getDichVuVIP() {
        return dichVuVIP;
    }

    public void setDichVuVIP(String dichVuVIP) {
        this.dichVuVIP = dichVuVIP;
    }
    private static final BigDecimal GIA_MAC_DINH =
            new BigDecimal("1000000");

    public static BigDecimal getGiaMacDinh() {
        return GIA_MAC_DINH;
    }
    @Override
    public String toString() {
        return super.toString() +
                " | Dich vu VIP: " + dichVuVIP;
    }
}
