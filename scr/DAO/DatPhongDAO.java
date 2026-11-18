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
import model.DatPhong;
import model.TrangThaiDatPhong;

public class DatPhongDAO {
    private static final String FILE_PATH = "DATABASEH/DatPhong.txt";
    private static final String FILE_PATH_DA_XOA = "DATABASEH/DatPhongDaXoa.txt";

    public static DatPhong[] danhSachDatPhong = new DatPhong[100];
    public static int soLuongDatPhong = 0;

    public static DatPhong[] danhSachDaXoa = new DatPhong[100];
    public static int soLuongDaXoa = 0;

    static {
        docFile();
    }

    public boolean them(DatPhong datPhong) {
        if (datPhong == null || datPhong.getMaDatPhong() == null 
            || timTheoMa(datPhong.getMaDatPhong()) != null 
            || soLuongDatPhong == danhSachDatPhong.length) {
            return false;
        }
        danhSachDatPhong[soLuongDatPhong] = datPhong;
        soLuongDatPhong++;
        ghiFile();
        return true;
    }

    public boolean sua(DatPhong datPhong) {
        if (datPhong == null) return false;
        for (int i = 0; i < soLuongDatPhong; i++) {
            if (danhSachDatPhong[i].getMaDatPhong().equals(datPhong.getMaDatPhong())) {
                danhSachDatPhong[i] = datPhong;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maDatPhong) {
        for (int i = 0; i < soLuongDatPhong; i++) {
            if (danhSachDatPhong[i].getMaDatPhong().equals(maDatPhong)) {
                if (soLuongDaXoa < danhSachDaXoa.length) {
                    danhSachDaXoa[soLuongDaXoa] = danhSachDatPhong[i];
                    soLuongDaXoa++;
                }
                System.arraycopy(danhSachDatPhong, i + 1, danhSachDatPhong, i, soLuongDatPhong - i - 1);
                danhSachDatPhong[--soLuongDatPhong] = null;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public DatPhong timTheoMa(String maDatPhong) {
        for (int i = 0; i < soLuongDatPhong; i++) {
            if (danhSachDatPhong[i].getMaDatPhong().equals(maDatPhong)) {
                return danhSachDatPhong[i];
            }
        }
        return null;
    }

    
    public boolean kiemTraPhongTrong(String maPhong, LocalDate ngayNhan, LocalDate ngayTra) {
        for (int i = 0; i < soLuongDatPhong; i++) {
            DatPhong dp = danhSachDatPhong[i];
            
            if (dp.getMaPhong().equalsIgnoreCase(maPhong) &&
                dp.getTrangThai() != TrangThaiDatPhong.DA_HUY &&
                dp.getTrangThai() != TrangThaiDatPhong.DA_TRA) {
                
                
                if (dp.isTrungLich(ngayNhan, ngayTra)) {
                    return false;
                }
            }
        }
        return true;
    }

    public DatPhong[] layDanhSach() {
        DatPhong[] ketQua = new DatPhong[soLuongDatPhong];
        System.arraycopy(danhSachDatPhong, 0, ketQua, 0, soLuongDatPhong);
        return ketQua;
    }
    
    public boolean khoiPhuc(String maDatPhong) {
        for (int i = 0; i < soLuongDaXoa; i++) {
            if (danhSachDaXoa[i].getMaDatPhong().equals(maDatPhong)) {
                if (soLuongDatPhong >= danhSachDatPhong.length) {
                    return false; // Danh sách chính đã đầy
                }
                
                danhSachDatPhong[soLuongDatPhong] = danhSachDaXoa[i];
                soLuongDatPhong++;

                
                System.arraycopy(danhSachDaXoa, i + 1, danhSachDaXoa, i, soLuongDaXoa - i - 1);
                danhSachDaXoa[--soLuongDaXoa] = null;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    
    public DatPhong[] layDanhSachDaXoa() {
        DatPhong[] ketQua = new DatPhong[soLuongDaXoa];
        System.arraycopy(danhSachDaXoa, 0, ketQua, 0, soLuongDaXoa);
        return ketQua;
    }
    private static void docFile() {
        soLuongDatPhong = docDanhSachNoiBo(FILE_PATH, danhSachDatPhong);
        soLuongDaXoa = docDanhSachNoiBo(FILE_PATH_DA_XOA, danhSachDaXoa);
    }

    private static int docDanhSachNoiBo(String path, DatPhong[] mang) {
        int count = 0;
        Arrays.fill(mang, null);
        File f = new File(path);
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
                while ((line = br.readLine()) != null && count < mang.length) {
                    if (line.trim().isEmpty()) continue;
                    try {
                        String[] a = line.split("\\|", -1);
                        if (a.length != 8) continue;
                        DatPhong dp = new DatPhong();
                        dp.setMaDatPhong(a[0].trim());
                        dp.setMaKhachHang(a[1].trim());
                        dp.setMaPhong(a[2].trim());
                        dp.setNgayDat(a[3].trim().isEmpty() ? null : LocalDate.parse(a[3].trim()));
                        dp.setNgayNhanPhong(a[4].trim().isEmpty() ? null : LocalDate.parse(a[4].trim()));
                        dp.setNgayTraPhong(a[5].trim().isEmpty() ? null : LocalDate.parse(a[5].trim()));
                        dp.setTrangThai(a[6].trim().isEmpty()
                                ? TrangThaiDatPhong.DA_DAT
                                : TrangThaiDatPhong.valueOf(a[6].trim()));
                        dp.setGiaPhongNgay(Double.parseDouble(a[7].trim()));
                        mang[count++] = dp;
                    } catch (RuntimeException e) {
                        // Bỏ qua dòng lỗi
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Khong doc duoc " + path + ": " + e.getMessage());
        }
        return count;
    }

    private static boolean ghiFile() {
        boolean ok1 = ghiDanhSachNoiBo(FILE_PATH, danhSachDatPhong, soLuongDatPhong);
        boolean ok2 = ghiDanhSachNoiBo(FILE_PATH_DA_XOA, danhSachDaXoa, soLuongDaXoa);
        return ok1 && ok2;
    }

    private static boolean ghiDanhSachNoiBo(String path, DatPhong[] mang, int soLuong) {
        File f = new File(path);
        try {
            if (f.getParentFile() != null) {
                f.getParentFile().mkdirs();
            }
            try (BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
                for (int i = 0; i < soLuong; i++) {
                    DatPhong dp = mang[i];
                    if (dp != null) {
                        bw.write(String.join("|",
                                giaTri(dp.getMaDatPhong()),
                                giaTri(dp.getMaKhachHang()),
                                giaTri(dp.getMaPhong()),
                                giaTri(dp.getNgayDat()),
                                giaTri(dp.getNgayNhanPhong()),
                                giaTri(dp.getNgayTraPhong()),
                                dp.getTrangThai() == null ? TrangThaiDatPhong.DA_DAT.name() : dp.getTrangThai().name(),
                                giaTri(dp.getGiaPhongNgay())
                        ));
                        bw.newLine();
                    }
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Khong ghi duoc " + path + ": " + e.getMessage());
            return false;
        }
    }

    private static String giaTri(Object o) {
        return o == null ? "" : o.toString();
    }
}