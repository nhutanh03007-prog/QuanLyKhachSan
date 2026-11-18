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
import model.ThanhToan;
import model.TrangThaiThanhToan;

public class ThanhToanDAO {
    private static final String FILE_PATH = "DATABASEH/ThanhToan.txt";

    public static ThanhToan[] danhSachThanhToan = new ThanhToan[100];
    public static int soLuongThanhToan = 0;

    static {
        docFile();
    }

    private static void docFile() {
        soLuongThanhToan = 0;
        Arrays.fill(danhSachThanhToan, null);
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
                while ((line = br.readLine()) != null && soLuongThanhToan < danhSachThanhToan.length) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    try {
                        String[] a = line.split("\\|", -1);
                        if (a.length != 6) {
                            continue;
                        }
                        LocalDate ngayThanhToan = a[4].isEmpty() ? null : LocalDate.parse(a[4]);
                        ThanhToan t = new ThanhToan(a[0], a[1], new BigDecimal(a[2]),
                                a[3].isEmpty() ? null : a[3], ngayThanhToan,
                                TrangThaiThanhToan.valueOf(a[5]));
                        if (!hopLe(t) || timNoiBo(t.getMaThanhToan()) != null) {
                            continue;
                        }
                        danhSachThanhToan[soLuongThanhToan++] = t;
                    } catch (RuntimeException e) {
                        // Bỏ qua dòng lỗi.
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Khong doc duoc " + FILE_PATH + ": " + e.getMessage());
        }
    }

    private static ThanhToan timNoiBo(String ma) {
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (ma.equals(danhSachThanhToan[i].getMaThanhToan())) {
                return danhSachThanhToan[i];
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
                for (int i = 0; i < soLuongThanhToan; i++) {
                    ThanhToan t = danhSachThanhToan[i];
                    bw.write(String.join("|", giaTri(t.getMaThanhToan()), giaTri(t.getMaHD()),
                            giaTri(t.getSoTien()), giaTri(t.getPhuongThuc()), giaTri(t.getNgayThanhToan()),
                            giaTri(t.getTrangThai())));
                    bw.newLine();
                }
            }
            return true;
        } catch (Exception e) {
            System.err.println("Khong ghi duoc " + FILE_PATH + ": " + e.getMessage());
            return false;
        }
    }

    public boolean them(ThanhToan t) {
        if (!hopLe(t) || timTheoMa(t.getMaThanhToan()) != null || soLuongThanhToan >= 100) {
            return false;
        }
        danhSachThanhToan[soLuongThanhToan++] = t;
        ghiFile();
        return true;
    }

    public boolean sua(ThanhToan t) {
        if (!hopLe(t)) {
            return false;
        }
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (danhSachThanhToan[i] != null
                    && t.getMaThanhToan().equals(danhSachThanhToan[i].getMaThanhToan())) {
                danhSachThanhToan[i] = t;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String ma) {
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (danhSachThanhToan[i] != null && ma != null
                    && ma.equals(danhSachThanhToan[i].getMaThanhToan())) {
                System.arraycopy(danhSachThanhToan, i + 1, danhSachThanhToan, i, soLuongThanhToan - i - 1);
                danhSachThanhToan[--soLuongThanhToan] = null;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public ThanhToan timTheoMa(String ma) {
        if (rong(ma)) {
            return null;
        }
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (danhSachThanhToan[i] != null && ma.equals(danhSachThanhToan[i].getMaThanhToan())) {
                return danhSachThanhToan[i];
            }
        }
        return null;
    }

    public ThanhToan[] layTheoMaHD(String ma) {
        ArrayList<ThanhToan> a = new ArrayList<>();
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (danhSachThanhToan[i] != null && ma != null && ma.equals(danhSachThanhToan[i].getMaHD())) {
                a.add(danhSachThanhToan[i]);
            }
        }
        return a.toArray(new ThanhToan[0]);
    }

    public ThanhToan[] layDanhSach() {
        return Arrays.copyOf(danhSachThanhToan, soLuongThanhToan);
    }

    private static boolean hopLe(ThanhToan t) {
        return t != null && !rong(t.getMaThanhToan()) && !rong(t.getMaHD()) && !rong(t.getPhuongThuc())
                && t.getSoTien() != null && t.getSoTien().signum() > 0;
    }

    private static String giaTri(Object o) {
        return o == null ? "" : o.toString();
    }

    private static boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}
