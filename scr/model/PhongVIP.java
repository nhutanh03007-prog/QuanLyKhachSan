package model;

import java.math.BigDecimal;

public class PhongVIP extends Phong {

    public static final String LOAI = "VIP";
    private static final BigDecimal GIA_MAC_DINH = new BigDecimal("1000000");

    private String dichVuVIP;

    public PhongVIP(String maPhong, String tenPhong,
                    BigDecimal giaPhong, TinhTrangPhong tinhTrang,
                    String dichVuVIP) {
        super(maPhong, tenPhong, giaPhong, tinhTrang);
        this.dichVuVIP = chuanHoa(dichVuVIP);
    }

    /** Constructor cu (tham so loaiPhong bi bo qua) de code cu van bien dich duoc. */
    public PhongVIP(String maPhong, String tenPhong, String loaiPhong,
                    BigDecimal giaPhong, TinhTrangPhong tinhTrang,
                    String dichVuVIP) {
        this(maPhong, tenPhong, giaPhong, tinhTrang, dichVuVIP);
    }

    private static String chuanHoa(String s) {
        return s == null ? "" : s.trim();
    }

    public static BigDecimal getGiaMacDinh() {
        return GIA_MAC_DINH;
    }

    @Override
    public String getLoaiPhong() {
        return LOAI;
    }

    @Override
    public String getDichVu() {
        return dichVuVIP;
    }

    @Override
    public void setDichVu(String dichVu) {
        this.dichVuVIP = chuanHoa(dichVu);
    }

    public String getDichVuVIP() {
        return dichVuVIP;
    }

    public void setDichVuVIP(String dichVuVIP) {
        setDichVu(dichVuVIP);
    }

    @Override
    public Phong taoBanSao() {
        return new PhongVIP(getMaPhong(), getTenPhong(), getGiaPhong(),
                getTinhTrang(), dichVuVIP);
    }
}