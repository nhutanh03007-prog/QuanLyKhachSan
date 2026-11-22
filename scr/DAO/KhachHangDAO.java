package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import model.KhachHang;

public class KhachHangDAO {
    public static KhachHang[] danhSachKhachHang = new KhachHang[100];
    public static int soLuongKhachHang = 0;

    private static final String FILE_PATH = "DATABASEH/KhachHang.dat";

    static {
        docFile();
    }

    public boolean them(KhachHang khachHang) {
        if (khachHang == null || khachHang.getMaKH() == null 
            || timTheoMa(khachHang.getMaKH()) != null 
            || soLuongKhachHang == danhSachKhachHang.length) {
            return false;
        }
        danhSachKhachHang[soLuongKhachHang] = khachHang;
        soLuongKhachHang++;
        ghiFile();
        return true;
    }

    public boolean sua(KhachHang khachHang) {
        if (khachHang == null) return false;
        for (int i = 0; i < soLuongKhachHang; i++) {
            if (danhSachKhachHang[i].getMaKH().equals(khachHang.getMaKH())) {
                danhSachKhachHang[i] = khachHang;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maKhachHang) {
        for (int i = 0; i < soLuongKhachHang; i++) {
            if (danhSachKhachHang[i].getMaKH().equals(maKhachHang)) {
                System.arraycopy(danhSachKhachHang, i + 1, danhSachKhachHang, i, soLuongKhachHang - i - 1);
                danhSachKhachHang[--soLuongKhachHang] = null;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public KhachHang timTheoMa(String maKhachHang) {
        for (int i = 0; i < soLuongKhachHang; i++) {
            if (danhSachKhachHang[i].getMaKH().equals(maKhachHang)) {
                return danhSachKhachHang[i];
            }
        }
        return null;
    }

    public KhachHang[] layDanhSach() {
        KhachHang[] ketQua = new KhachHang[soLuongKhachHang];
        System.arraycopy(danhSachKhachHang, 0, ketQua, 0, soLuongKhachHang);
        return ketQua;
    }
    private static void docFile() {
        soLuongKhachHang = 0;
        Arrays.fill(danhSachKhachHang, null);
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
                while ((line = br.readLine()) != null && soLuongKhachHang < danhSachKhachHang.length) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    try {
                        String[] a = line.split("\\|", -1);
                        if (a.length != 9) {
                            continue;
                        }
                        KhachHang kh = new KhachHang(
                                a[0].trim(),
                                a[1].trim(),
                                a[2].trim(),
                                a[3].trim().isEmpty() ? null : LocalDate.parse(a[3].trim()),
                                a[4].trim(),
                                a[5].trim(),
                                a[6].trim(),
                                a[7].trim(),
                                a[8].trim()
                        );
                        danhSachKhachHang[soLuongKhachHang++] = kh;
                    } catch (RuntimeException e) {
                        // Bo qua dong loi
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Khong doc duoc " + FILE_PATH + ": " + e.getMessage());
        }
    }

    private static boolean ghiFile() {
        File f = new File(FILE_PATH);
        try {
            if (f.getParentFile() != null) {
                f.getParentFile().mkdirs();
            }
            try (BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
                for (int i = 0; i < soLuongKhachHang; i++) {
                    KhachHang kh = danhSachKhachHang[i];
                    if (kh != null) {
                        bw.write(String.join("|",
                                giaTri(kh.getMaKH()),
                                giaTri(kh.getHoTen()),
                                giaTri(kh.getGioiTinh()),
                                giaTri(kh.getNgaySinh()),
                                giaTri(kh.getDiaChi()),
                                giaTri(kh.getSdt()),
                                giaTri(kh.getEmail()),
                                giaTri(kh.getLoaiKhach()),
                                giaTri(kh.getGhiChu())
                        ));
                        bw.newLine();
                    }
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Khong ghi duoc " + FILE_PATH + ": " + e.getMessage());
            return false;
        }
    }

    private static String giaTri(Object o) {
        return o == null ? "" : o.toString();
    }
}