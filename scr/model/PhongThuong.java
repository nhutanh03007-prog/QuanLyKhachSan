package model;

import java.io.BufferedReader;
import java.math.BigDecimal;

public class PhongThuong extends Phong {

    public static final String LOAI = "Thuong";
    private static final BigDecimal GIA_MAC_DINH = new BigDecimal("500000");

    private int soGiuong;
    private boolean coDieuHoa;

    public PhongThuong() {
        super("", "", GIA_MAC_DINH, null);
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

    @Override
    public BigDecimal getGiaMacDinhPhong() { return GIA_MAC_DINH; }

    @Override
    public String getThongTinRieng() { return soGiuong + " giuong"; }

    @Override
    public String getTienIch() { return coDieuHoa ? "Co dieu hoa" : "Khong"; }

    @Override
    public Phong saoChep() {
        return new PhongThuong(getMaPhong(), getTenPhong(), getGiaPhong(), getTinhTrang(), soGiuong, coDieuHoa);
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
        System.out.println("\n--- THONG TIN PHONG THUONG ---");
        this.soGiuong = nhapSoNguyenDuong(reader, "Nhap so giuong: ");
        this.coDieuHoa = nhapCoKhong(reader, "Phong co dieu hoa? (Y/N): ");
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println("So giuong  : " + soGiuong);
        System.out.println("Dieu hoa   : " + (coDieuHoa ? "Co" : "Khong"));
    }

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
