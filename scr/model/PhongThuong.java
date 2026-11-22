
package model;

import java.math.BigDecimal;

public class PhongThuong extends Phong {

    private String dichVuThuong;

    public PhongThuong(String maPhong, String tenPhong,
                       String loaiPhong, BigDecimal giaPhong,
                       TinhTrangPhong tinhTrang,
                       String dichVuThuong) {

        super(maPhong, tenPhong, loaiPhong,
              giaPhong, tinhTrang);

        this.dichVuThuong = dichVuThuong;
    }

    public String getDichVuThuong() {
        return dichVuThuong;
    }

    public void setDichVuThuong(String dichVuThuong) {
        this.dichVuThuong = dichVuThuong;
    }
    private static final BigDecimal GIA_MAC_DINH =
            new BigDecimal("500000");

    public static BigDecimal getGiaMacDinh() {
        return GIA_MAC_DINH;
    }
    @Override
    public String toString() {
        return super.toString() +
                " | Dich vu thuong: " + dichVuThuong;
    }
}
