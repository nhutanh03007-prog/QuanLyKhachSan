package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

public abstract class Phong {

    private String maPhong;
    private String tenPhong;
    private BigDecimal giaPhong;
    private TinhTrangPhong tinhTrang;

    protected Phong() {
        this.maPhong = "";
        this.tenPhong = "";
        this.giaPhong = BigDecimal.ZERO;
        this.tinhTrang = null;
    }

    protected Phong(String maPhong, String tenPhong,
                    BigDecimal giaPhong, TinhTrangPhong tinhTrang) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.giaPhong = giaPhong;
        this.tinhTrang = tinhTrang;
    }

    // Cac phuong thuc da hinh: lop cha khai bao, lop con (PhongThuong / PhongVIP) cai dat rieng
    public abstract String getLoaiPhong();

    public abstract BigDecimal getGiaMacDinhPhong();

    public abstract String getThongTinRieng();

    public abstract String getTienIch();

    public abstract Phong saoChep();

    // ================= HAM NHAP / XUAT DUNG CHUNG (LOP CON TAI SU DUNG QUA super) =================

    public void nhap(BufferedReader reader) {
        this.maPhong = nhapChuoi(reader, "Nhap ma phong: ").toUpperCase();
        this.tenPhong = nhapChuoi(reader, "Nhap ten phong: ");
        nhapPhanGiaVaTinhTrang(reader);
    }

    public void nhap(BufferedReader reader, String maPhongSan, String tenPhongSan) {
        this.maPhong = maPhongSan == null ? "" : maPhongSan.trim().toUpperCase();
        this.tenPhong = tenPhongSan == null ? "" : tenPhongSan.trim();
        nhapPhanGiaVaTinhTrang(reader);
    }

    private void nhapPhanGiaVaTinhTrang(BufferedReader reader) {
        BigDecimal macDinh = getGiaMacDinhPhong();
        this.giaPhong = nhapGia(
                reader,
                "Nhap gia phong (Enter de dung gia mac dinh " + macDinh.toPlainString() + " VND): ",
                macDinh
        );
        this.tinhTrang = TinhTrangPhong.TRONG;
    }

    public void xuat() {
        System.out.println("Ma phong   : " + maPhong);
        System.out.println("Ten phong  : " + tenPhong);
        System.out.println("Loai phong : " + getLoaiPhong());
        System.out.println("Gia phong  : " + (giaPhong == null ? "0" : giaPhong.toPlainString()) + " VND");
        System.out.println("Tinh trang : " + tinhTrang);
    }

    public void xuatDongBang(int stt) {
        System.out.printf(
                "%-5d %-10s %-15s %-8s %-14s %-18s %-12s %-12s%n",
                stt,
                maPhong,
                tenPhong,
                getLoaiPhong(),
                giaPhong == null ? "0" : giaPhong.toPlainString(),
                tinhTrang,
                getThongTinRieng(),
                getTienIch()
        );
    }

    // ================= HAM HO TRO NHAP DUNG CHUNG CHO LOP CHA VA LOP CON =================

    protected static String docDong(BufferedReader reader) {
        try {
            String line = reader.readLine();
            if (line == null) {
                System.exit(0);
            }
            return line;
        } catch (IOException e) {
            return "";
        }
    }

    protected static String nhapChuoi(BufferedReader reader, String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = docDong(reader).trim();
            if (!s.isEmpty()) {
                return s;
            }
            System.out.println("Khong duoc de trong!");
        }
    }

    protected static int nhapSoNguyenDuong(BufferedReader reader, String thongBao) {
        while (true) {
            System.out.print(thongBao);
            try {
                int so = Integer.parseInt(docDong(reader).trim());
                if (so > 0) {
                    return so;
                }
                System.out.println("Gia tri phai lon hon 0!");
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so nguyen!");
            }
        }
    }

    protected static boolean nhapCoKhong(BufferedReader reader, String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = docDong(reader).trim();
            if (s.equalsIgnoreCase("Y")) {
                return true;
            }
            if (s.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Chi nhap Y hoac N!");
        }
    }

    protected static BigDecimal nhapGia(BufferedReader reader, String thongBao, BigDecimal macDinh) {
        while (true) {
            System.out.print(thongBao);
            String s = docDong(reader).trim();
            if (s.isEmpty() && macDinh != null) {
                return macDinh;
            }
            try {
                BigDecimal gia = new BigDecimal(s);
                if (gia.signum() > 0) {
                    return gia;
                }
                System.out.println("Gia phai lon hon 0!");
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so hop le!");
            }
        }
    }

    // Getter / Setter
    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public BigDecimal getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(BigDecimal giaPhong) {
        this.giaPhong = giaPhong;
    }

    public TinhTrangPhong getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(TinhTrangPhong tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    @Override
    public String toString() {
        return "Ma phong: " + maPhong
                + " | Ten phong: " + tenPhong
                + " | Loai phong: " + getLoaiPhong()
                + " | Gia phong: " + giaPhong
                + " | Tinh trang: " + tinhTrang;
    }
}
