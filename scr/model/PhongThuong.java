package model;

import java.math.BigDecimal;

public class PhongThuong extends Phong {

    public static final String LOAI = "Thuong";
    private static final BigDecimal GIA_MAC_DINH = new BigDecimal("500000");

    private String dichVuThuong;

    public PhongThuong(String maPhong, String tenPhong,
                       BigDecimal giaPhong, TinhTrangPhong tinhTrang,
                       String dichVuThuong) {
        super(maPhong, tenPhong, giaPhong, tinhTrang);
        this.dichVuThuong = chuanHoa(dichVuThuong);
    }

    /** Constructor cu (tham so loaiPhong bi bo qua) de code cu van bien dich duoc. */
    public PhongThuong(String maPhong, String tenPhong, String loaiPhong,
                       BigDecimal giaPhong, TinhTrangPhong tinhTrang,
                       String dichVuThuong) {
        this(maPhong, tenPhong, giaPhong, tinhTrang, dichVuThuong);
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
        return dichVuThuong;
    }

    @Override
    public void setDichVu(String dichVu) {
        this.dichVuThuong = chuanHoa(dichVu);
    }

    public String getDichVuThuong() {
        return dichVuThuong;
    }

    public void setDichVuThuong(String dichVuThuong) {
        setDichVu(dichVuThuong);
    }

    @Override
    public Phong taoBanSao() {
        return new PhongThuong(getMaPhong(), getTenPhong(), getGiaPhong(),
                getTinhTrang(), dichVuThuong);
    }
}