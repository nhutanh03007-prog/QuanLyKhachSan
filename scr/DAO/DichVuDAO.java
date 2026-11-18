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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.DichVu;

public class DichVuDAO {
    private static final String FILE_PATH = "DATABASEH/DichVu.txt";

    public static DichVu[] danhSachDichVu = new DichVu[100];
    public static int soLuongDichVu = 0;

    static {
        docFile();
    }

    public DichVuDAO() {
    }

    private static void docFile() {
        soLuongDichVu = 0;
        Arrays.fill(danhSachDichVu, null);
        File f = new File(FILE_PATH);

        try {
            File parent = f.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile();
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null && soLuongDichVu < danhSachDichVu.length) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    try {
                        String[] c = line.split("\\|", -1);
                        if (c.length != 7) {
                            continue;
                        }

                        DichVu d = new DichVu(c[0], c[1], c[2], c[3], new BigDecimal(c[4]), c[5], c[6]);
                        if (hopLe(d) && timTheoMaNoiBo(d.getMaDichVu()) == null) {
                            danhSachDichVu[soLuongDichVu++] = d;
                        }
                    } catch (RuntimeException e) {
                        // Bỏ qua bản ghi lỗi.
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Khong doc duoc " + FILE_PATH + ": " + e.getMessage());
        }
    }

    private static DichVu timTheoMaNoiBo(String ma) {
        for (int i = 0; i < soLuongDichVu; i++) {
            if (ma.equals(danhSachDichVu[i].getMaDichVu())) {
                return danhSachDichVu[i];
            }
        }
        return null;
    }

    private static boolean ghiFile() {
        File f = new File(FILE_PATH);
        try {
            File parent = f.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }

            try (BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
                for (int i = 0; i < soLuongDichVu; i++) {
                    DichVu d = danhSachDichVu[i];
                    bw.write(String.join("|", giaTri(d.getMaDichVu()), giaTri(d.getTenDichVu()),
                            giaTri(d.getLoaiDichVu()), giaTri(d.getDonViTinh()), giaTri(d.getGia()),
                            giaTri(d.getTrangThai()), giaTri(d.getMoTa())));
                    bw.newLine();
                }
            }
            return true;
        } catch (Exception e) {
            System.err.println("Khong ghi duoc " + FILE_PATH + ": " + e.getMessage());
            return false;
        }
    }

    public boolean kiemTraTenDichVuKhongRong(String s) {
        return !rong(s);
    }

    public boolean kiemTraSoTienKhongAm(BigDecimal n) {
        return n != null && n.signum() >= 0;
    }

    public boolean them(DichVu d) {
        if (!hopLe(d) || timTheoMa(d.getMaDichVu()) != null || soLuongDichVu >= 100) {
            return false;
        }
        danhSachDichVu[soLuongDichVu++] = d;
        ghiFile();
        return true;
    }

    public boolean sua(DichVu d) {
        if (!hopLe(d)) {
            return false;
        }
        for (int i = 0; i < soLuongDichVu; i++) {
            if (danhSachDichVu[i] != null && d.getMaDichVu().equals(danhSachDichVu[i].getMaDichVu())) {
                danhSachDichVu[i] = d;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String ma) {
        for (int i = 0; i < soLuongDichVu; i++) {
            if (danhSachDichVu[i] != null && ma != null && ma.equals(danhSachDichVu[i].getMaDichVu())) {
                System.arraycopy(danhSachDichVu, i + 1, danhSachDichVu, i, --soLuongDichVu - i);
                danhSachDichVu[soLuongDichVu] = null;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public DichVu timTheoMa(String ma) {
        if (rong(ma)) {
            return null;
        }
        for (int i = 0; i < soLuongDichVu; i++) {
            if (danhSachDichVu[i] != null && ma.equals(danhSachDichVu[i].getMaDichVu())) {
                return danhSachDichVu[i];
            }
        }
        return null;
    }

    public DichVu[] timKiem(String key) {
        List<DichVu> out = new ArrayList<>();
        if (!rong(key)) {
            for (int i = 0; i < soLuongDichVu; i++) {
                DichVu d = danhSachDichVu[i];
                if (d != null && ((d.getMaDichVu() != null
                        && d.getMaDichVu().toLowerCase().contains(key.trim().toLowerCase()))
                        || (d.getTenDichVu() != null
                                && d.getTenDichVu().toLowerCase().contains(key.trim().toLowerCase())))) {
                    out.add(d);
                }
            }
        }
        return out.toArray(new DichVu[0]);
    }

    public DichVu[] timTheoTen(String key) {
        return timKiem(key);
    }

    public DichVu[] layDanhSach() {
        return Arrays.copyOf(danhSachDichVu, soLuongDichVu);
    }

    private static boolean hopLe(DichVu d) {
        return d != null && !rong(d.getMaDichVu()) && !rong(d.getTenDichVu()) && !rong(d.getLoaiDichVu())
                && !rong(d.getDonViTinh()) && d.getGia() != null && d.getGia().signum() > 0;
    }

    private static String giaTri(Object v) {
        return v == null ? "" : v.toString();
    }

    private static boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}
