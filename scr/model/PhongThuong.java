package model;

import java.math.BigDecimal;

public class PhongThuong extends Phong {

    public static final String LOAI = "Thuong";
    private static final BigDecimal GIA_MAC_DINH = new BigDecimal("500000");

    private int soGiuong;        
    private boolean coDieuHoa;     

    public PhongThuong() {
        super();
        setGiaPhong(GIA_MAC_DINH);
        this.soGiuong = 1;
        this.coDieuHoa = false;
    }

    public PhongThuong(String maPhong, String tenPhong, BigDecimal giaPhong,
                       TinhTrangPhong tinhTrang, int soGiuong, boolean coDieuHoa) {
        super(maPhong, tenPhong, giaPhong, tinhTrang);
        this.soGiuong = soGiuong;
        this.coDieuHoa = coDieuHoa;
    }

    public static BigDecimal getGiaMacDinh() { return GIA_MAC_DINH; }

    @Override
    public String getLoaiPhong() { return LOAI; }

    public int getSoGiuong() { return soGiuong; }
    public void setSoGiuong(int soGiuong) { this.soGiuong = soGiuong; }

    public boolean isCoDieuHoa() { return coDieuHoa; }
    public void setCoDieuHoa(boolean coDieuHoa) { this.coDieuHoa = coDieuHoa; }

    @Override
    public String toString() {
        return super.toString()
                + " | So giuong: " + soGiuong
                + " | Dieu hoa: " + (coDieuHoa ? "Co" : "Khong");
    }
}