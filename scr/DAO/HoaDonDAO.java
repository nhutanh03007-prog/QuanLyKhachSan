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
import java.util.Arrays;
import model.HoaDon;
import model.TrangThaiThanhToan;

public class HoaDonDAO {
    private static final String FILE_PATH = "DATABASEH/HoaDon.txt";

    public static HoaDon[] danhSachHoaDon = new HoaDon[100];
    public static int soLuongHoaDon = 0;

    static {
        docFile();
    }

    private static void docFile() {
        soLuongHoaDon = 0;
        Arrays.fill(danhSachHoaDon, null);
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
                while ((line = br.readLine()) != null && soLuongHoaDon < 100) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    try {
                        String[] a = line.split("\\|", -1);
                        if (a.length != 9) {
                            continue;
                        }

                        HoaDon h = new HoaDon();
                        h.setMaHD(a[0]);
                        h.setMaDatPhong(a[1]);
                        h.setNgayLap(ngay(a[2]));
                        h.setNgayThanhToan(ngay(a[3]));
                        h.setTongGia(new BigDecimal(a[4]));
                        h.setGiamGia(new BigDecimal(a[5]));
                        h.setThanhTien(new BigDecimal(a[6]));
                        h.setPhuongThucTT(a[7].isEmpty() ? null : a[7]);
                        h.setTrangThai(TrangThaiThanhToan.valueOf(a[8]));

                        if (!hopLe(h) || timNoiBo(h.getMaHD()) != null
                                || timNoiBoDatPhong(h.getMaDatPhong()) != null) {
                            continue;
                        }
                        h.setThanhTien(tinhThanhTienNoiBo(h));
                        danhSachHoaDon[soLuongHoaDon++] = h;
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

    private static HoaDon timNoiBo(String ma) {
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (ma.equals(danhSachHoaDon[i].getMaHD())) {
                return danhSachHoaDon[i];
            }
        }
        return null;
    }

    private static HoaDon timNoiBoDatPhong(String ma) {
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (ma.equals(danhSachHoaDon[i].getMaDatPhong())) {
                return danhSachHoaDon[i];
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
                for (int i = 0; i < soLuongHoaDon; i++) {
                    HoaDon h = danhSachHoaDon[i];
                    bw.write(String.join("|", giaTri(h.getMaHD()), giaTri(h.getMaDatPhong()),
                            giaTri(h.getNgayLap()), giaTri(h.getNgayThanhToan()), giaTri(h.getTongGia()),
                            giaTri(h.getGiamGia()), giaTri(tinhThanhTienNoiBo(h)),
                            giaTri(h.getPhuongThucTT()),
                            giaTri(h.getTrangThai())));
                    bw.newLine();
                }
            }
            return true;
        } catch (Exception e) {
            System.err.println("Khong ghi duoc " + FILE_PATH + ": " + e.getMessage());
            return false;
        }
    }

    public boolean them(HoaDon h) {
        if (!hopLe(h) || timTheoMa(h.getMaHD()) != null || timTheoMaDatPhong(h.getMaDatPhong()) != null
                || soLuongHoaDon >= 100) {
            return false;
        }
        if (h.getNgayLap() == null) {
            h.setNgayLap(LocalDate.now());
        }
        if (h.getTrangThai() == null) {
            h.setTrangThai(TrangThaiThanhToan.CHUA_THANH_TOAN);
        }
        h.setThanhTien(tinhThanhTien(h));
        danhSachHoaDon[soLuongHoaDon++] = h;
        ghiFile();
        return true;
    }

    public boolean sua(HoaDon h) {
        if (!hopLe(h)) {
            return false;
        }
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (danhSachHoaDon[i] != null && h.getMaHD().equals(danhSachHoaDon[i].getMaHD())) {
                if (danhSachHoaDon[i].getTrangThai() == TrangThaiThanhToan.DA_THANH_TOAN) {
                    return false;
                }
                h.setThanhTien(tinhThanhTien(h));
                danhSachHoaDon[i] = h;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String ma) {
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (danhSachHoaDon[i] != null && ma != null && ma.equals(danhSachHoaDon[i].getMaHD())) {
                if (danhSachHoaDon[i].getTrangThai() == TrangThaiThanhToan.DA_THANH_TOAN) {
                    return false;
                }
                System.arraycopy(danhSachHoaDon, i + 1, danhSachHoaDon, i, --soLuongHoaDon - i);
                danhSachHoaDon[soLuongHoaDon] = null;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public BigDecimal tinhThanhTien(HoaDon h) {
        return tinhThanhTienNoiBo(h);
    }

    public boolean capNhatTrangThai(String ma, TrangThaiThanhToan t, String p, LocalDate d) {
        HoaDon h = timTheoMa(ma);
        if (h == null || t == null) {
            return false;
        }
        h.setTrangThai(t);
        h.setPhuongThucTT(p);
        h.setNgayThanhToan(d);
        ghiFile();
        return true;
    }

    public HoaDon timTheoMa(String ma) {
        if (rong(ma)) {
            return null;
        }
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (danhSachHoaDon[i] != null && ma.equals(danhSachHoaDon[i].getMaHD())) {
                return danhSachHoaDon[i];
            }
        }
        return null;
    }

    public HoaDon timTheoMaDatPhong(String dp) {
        if (rong(dp)) {
            return null;
        }
        for (int i = 0; i < soLuongHoaDon; i++) {
            if (danhSachHoaDon[i] != null && dp.equals(danhSachHoaDon[i].getMaDatPhong())) {
                return danhSachHoaDon[i];
            }
        }
        return null;
    }

    public HoaDon[] layDanhSach() {
        return Arrays.copyOf(danhSachHoaDon, soLuongHoaDon);
    }

    private static BigDecimal tinhThanhTienNoiBo(HoaDon h) {
        return h == null || h.getTongGia() == null || h.getGiamGia() == null ? BigDecimal.ZERO
                : h.getTongGia().subtract(h.getGiamGia());
    }

    private static boolean hopLe(HoaDon h) {
        return h != null && !rong(h.getMaHD()) && !rong(h.getMaDatPhong()) && h.getTongGia() != null
                && h.getGiamGia() != null && h.getTongGia().signum() >= 0 && h.getGiamGia().signum() >= 0
                && h.getGiamGia().compareTo(h.getTongGia()) <= 0;
    }

    private static String giaTri(Object o) {
        return o == null ? "" : o.toString();
    }

    private static boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}
