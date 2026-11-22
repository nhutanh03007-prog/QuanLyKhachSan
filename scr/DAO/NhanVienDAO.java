package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import model.NhanVien;

public class NhanVienDAO {

    private NhanVien[] danhSach;
    private int soLuong;
    private static final String FILE_PATH = "DataBase/NhanVien.dat";

    public NhanVienDAO() {
        this.danhSach = new NhanVien[10];
        this.soLuong = 0;
        docFile();
    }

    public final NhanVien[] docFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return layTatCa();
        }

        this.danhSach = new NhanVien[10];
        this.soLuong = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length == 9) {
                    String maNV = parts[0].trim();
                    String hoTen = parts[1].trim();
                    String gioiTinh = parts[2].trim();
                    String ngaySinh = parts[3].trim();
                    String diaChi = parts[4].trim();
                    String soDienThoai = parts[5].trim();
                    String chucVu = parts[6].trim();
                    double luong = Double.parseDouble(parts[7].trim());
                    String trangThai = parts[8].trim();

                    if (soLuong == danhSach.length) {
                        moRongMang();
                    }
                    danhSach[soLuong++] = new NhanVien(
                            maNV, hoTen, gioiTinh, ngaySinh,
                            diaChi, soDienThoai, chucVu, luong, trangThai
                    );
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Lỗi đọc file NhanVien: " + e.getMessage());
        }

        return layTatCa();
    }

    public void ghiFile() {
        File file = new File(FILE_PATH);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < soLuong; i++) {
                NhanVien nv = danhSach[i];
                if (nv != null) {
                    String line = String.join("|",
                            nv.getMaNV(),
                            nv.getHoTen(),
                            nv.getGioiTinh(),
                            nv.getNgaySinh(),
                            nv.getDiaChi(),
                            nv.getSoDienThoai(),
                            nv.getChucVu(),
                            String.valueOf(nv.getLuong()),
                            nv.getTrangThai()
                    );
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file NhanVien: " + e.getMessage());
        }
    }

    public NhanVien[] layTatCa() {
        return Arrays.copyOf(danhSach, soLuong);
    }

    public boolean them(NhanVien nv) {
        if (nv == null || nv.getMaNV() == null || nv.getMaNV().trim().isEmpty()) {
            return false;
        }
        if (nv.getHoTen() == null || nv.getHoTen().trim().isEmpty()) {
            return false;
        }
        if (timTheoMa(nv.getMaNV()) != null) {
            return false;
        }

        if (soLuong == danhSach.length) {
            moRongMang();
        }

        danhSach[soLuong] = nv;
        soLuong++;
        ghiFile();
        return true;
    }

    public boolean capNhat(NhanVien nv) {
        if (nv == null || nv.getMaNV() == null || nv.getMaNV().trim().isEmpty()) {
            return false;
        }
        if (nv.getHoTen() == null || nv.getHoTen().trim().isEmpty()) {
            return false;
        }

        String ma = nv.getMaNV().trim();
        for (int i = 0; i < soLuong; i++) {
            NhanVien nhanVienCu = danhSach[i];
            if (nhanVienCu != null && nhanVienCu.getMaNV() != null
                    && nhanVienCu.getMaNV().equalsIgnoreCase(ma)) {
                danhSach[i] = nv;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maNV) {
        if (maNV == null || maNV.trim().isEmpty()) {
            return false;
        }

        String maCanXoa = maNV.trim();
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i] != null && danhSach[i].getMaNV() != null
                    && danhSach[i].getMaNV().equalsIgnoreCase(maCanXoa)) {
                for (int j = i; j < soLuong - 1; j++) {
                    danhSach[j] = danhSach[j + 1];
                }
                danhSach[soLuong - 1] = null;
                soLuong--;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public NhanVien timTheoMa(String maNV) {
        if (maNV == null || maNV.trim().isEmpty()) {
            return null;
        }
        String maCanTim = maNV.trim();
        for (int i = 0; i < soLuong; i++) {
            NhanVien nv = danhSach[i];
            if (nv != null && nv.getMaNV() != null
                    && nv.getMaNV().equalsIgnoreCase(maCanTim)) {
                return nv;
            }
        }
        return null;
    }

    public boolean kiemTraMaTonTai(String maNV) {
        return timTheoMa(maNV) != null;
    }

    public int demSoLuong() {
        return soLuong;
    }

    private void moRongMang() {
        danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
    }
}
