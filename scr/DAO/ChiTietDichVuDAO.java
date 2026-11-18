package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import model.ChiTietDichVu;

public class ChiTietDichVuDAO {
    private static final String FILE_PATH = "DATABASEH/ChiTietDichVu.txt";

    public static ChiTietDichVu[] danhSachChiTiet = new ChiTietDichVu[100];
    public static int soLuongChiTiet = 0;

    static {
        docFile();
    }

    private static void docFile() {
        soLuongChiTiet = 0;
        Arrays.fill(danhSachChiTiet, null);
        File f = new File(FILE_PATH);

        try {
            if (f.getParentFile() != null) {
                f.getParentFile().mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile();
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null && soLuongChiTiet < danhSachChiTiet.length) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    try {
                        String[] a = line.split("\\|", -1);
                        if (a.length != 7) {
                            continue;
                        }

                        ChiTietDichVu c = new ChiTietDichVu(a[0], a[1], ngay(a[2]), ngay(a[3]),
                                Integer.parseInt(a[4]), new BigDecimal(a[5]));
                        if (!hopLe(c) || timNoiBo(c.getMaDatPhong(), c.getMaDichVu()) != null) {
                            continue;
                        }

                        BigDecimal tinh = tinhThanhTienNoiBo(c);
                        c.setThanhTien(a[6].isEmpty() ? tinh : new BigDecimal(a[6]));
                        if (c.getThanhTien().compareTo(tinh) != 0) {
                            c.setThanhTien(tinh);
                        }
                        danhSachChiTiet[soLuongChiTiet++] = c;
                    } catch (RuntimeException e) {
                        // Bỏ qua dòng lỗi.
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Khong doc duoc " + FILE_PATH + ": " + e.getMessage());
        }
    }

    private static LocalDate ngay(String s) {
        return s.isEmpty() ? null : LocalDate.parse(s);
    }

    private static ChiTietDichVu timNoiBo(String dp, String dv) {
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (cungKhoa(danhSachChiTiet[i], dp, dv)) {
                return danhSachChiTiet[i];
            }
        }
        return null;
    }

    private static boolean ghiFile() {
        File f = new File(FILE_PATH);
        try {
            if (f.getParentFile() != null) {
                f.getParentFile().mkdirs();
            }

            try (BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
                for (int i = 0; i < soLuongChiTiet; i++) {
                    ChiTietDichVu c = danhSachChiTiet[i];
                    bw.write(String.join("|", giaTri(c.getMaDatPhong()), giaTri(c.getMaDichVu()),
                            giaTri(c.getNgayDatDichVu()), giaTri(c.getNgayNhan()), giaTri(c.getSoLuong()),
                            giaTri(c.getDonGia()), giaTri(tinhThanhTienNoiBo(c))));
                    bw.newLine();
                }
            }
            return true;
        } catch (Exception e) {
            System.err.println("Khong ghi duoc " + FILE_PATH + ": " + e.getMessage());
            return false;
        }
    }

    public boolean them(ChiTietDichVu c) {
        if (!hopLe(c)) {
            return false;
        }

        ChiTietDichVu cu = timTheoMa(c.getMaDatPhong(), c.getMaDichVu());
        if (cu != null) {
            cu.setSoLuong(cu.getSoLuong() + c.getSoLuong());
            cu.setDonGia(c.getDonGia());
            cu.setThanhTien(tinhThanhTien(cu));
            ghiFile();
            return true;
        }

        if (soLuongChiTiet >= 100) {
            return false;
        }
        c.setThanhTien(tinhThanhTien(c));
        danhSachChiTiet[soLuongChiTiet++] = c;
        ghiFile();
        return true;
    }

    public boolean sua(ChiTietDichVu c) {
        if (!hopLe(c)) {
            return false;
        }
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (cungKhoa(danhSachChiTiet[i], c.getMaDatPhong(), c.getMaDichVu())) {
                c.setThanhTien(tinhThanhTien(c));
                danhSachChiTiet[i] = c;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public BigDecimal tinhThanhTien(ChiTietDichVu c) {
        return tinhThanhTienNoiBo(c);
    }

    public boolean xoa(String dp, String dv) {
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (cungKhoa(danhSachChiTiet[i], dp, dv)) {
                System.arraycopy(danhSachChiTiet, i + 1, danhSachChiTiet, i, soLuongChiTiet - i - 1);
                danhSachChiTiet[--soLuongChiTiet] = null;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public ChiTietDichVu timTheoMa(String dp, String dv) {
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (cungKhoa(danhSachChiTiet[i], dp, dv)) {
                return danhSachChiTiet[i];
            }
        }
        return null;
    }

    public ChiTietDichVu[] layDanhSach() {
        return Arrays.copyOf(danhSachChiTiet, soLuongChiTiet);
    }

    public ChiTietDichVu[] layTheoMaDatPhong(String dp) {
        ArrayList<ChiTietDichVu> a = new ArrayList<>();
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (danhSachChiTiet[i] != null && dp != null && dp.equals(danhSachChiTiet[i].getMaDatPhong())) {
                a.add(danhSachChiTiet[i]);
            }
        }
        return a.toArray(new ChiTietDichVu[0]);
    }

    public boolean kiemTraDichVuDangSuDung(String ma) {
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (danhSachChiTiet[i] != null && ma != null && ma.equals(danhSachChiTiet[i].getMaDichVu())) {
                return true;
            }
        }
        return false;
    }

    public BigDecimal tinhTongTienTheoDatPhong(String dp) {
        BigDecimal sum = BigDecimal.ZERO;
        for (ChiTietDichVu c : layTheoMaDatPhong(dp)) {
            sum = sum.add(tinhThanhTien(c));
        }
        return sum;
    }

    private static BigDecimal tinhThanhTienNoiBo(ChiTietDichVu c) {
        return c == null || c.getDonGia() == null ? BigDecimal.ZERO
                : c.getDonGia().multiply(BigDecimal.valueOf(c.getSoLuong()));
    }

    private static boolean hopLe(ChiTietDichVu c) {
        return c != null && !rong(c.getMaDatPhong()) && !rong(c.getMaDichVu()) && c.getSoLuong() > 0
                && c.getDonGia() != null && c.getDonGia().signum() >= 0;
    }

    private static boolean cungKhoa(ChiTietDichVu c, String dp, String dv) {
        return c != null && dp != null && dv != null && dp.equals(c.getMaDatPhong())
                && dv.equals(c.getMaDichVu());
    }

    private static String giaTri(Object o) {
        return o == null ? "" : o.toString();
    }

    private static boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}
