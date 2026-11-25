package DAO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import model.NhanVien;

public class NhanVienDAO {

    private NhanVien[] danhSach;
    private int soLuong;
    private static final String FILE_PATH = "DATABASEH/NhanVien.dat";

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

        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            int tongSo = dis.readInt();
            for (int i = 0; i < tongSo; i++) {
                String maNV = dis.readUTF();
                String hoTen = dis.readUTF();
                String gioiTinh = dis.readUTF();
                String ngaySinh = dis.readUTF();
                String diaChi = dis.readUTF();
                String soDienThoai = dis.readUTF();
                String chucVu = dis.readUTF();
                double luong = dis.readDouble();
                String trangThai = dis.readUTF();

                if (soLuong == danhSach.length) {
                    moRongMang();
                }
                danhSach[soLuong++] = new NhanVien(
                        maNV, hoTen, gioiTinh, ngaySinh,
                        diaChi, soDienThoai, chucVu, luong, trangThai
                );
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file NhanVien: " + e.getMessage());
        }

        return layTatCa();
    }

    public void ghiFile() {
        File file = new File(FILE_PATH);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            dos.writeInt(soLuong);
            for (int i = 0; i < soLuong; i++) {
                NhanVien nv = danhSach[i];
                if (nv != null) {
                    dos.writeUTF(nv.getMaNV() != null ? nv.getMaNV() : "");
                    dos.writeUTF(nv.getHoTen() != null ? nv.getHoTen() : "");
                    dos.writeUTF(nv.getGioiTinh() != null ? nv.getGioiTinh() : "");
                    dos.writeUTF(nv.getNgaySinh() != null ? nv.getNgaySinh() : "");
                    dos.writeUTF(nv.getDiaChi() != null ? nv.getDiaChi() : "");
                    dos.writeUTF(nv.getSoDienThoai() != null ? nv.getSoDienThoai() : "");
                    dos.writeUTF(nv.getChucVu() != null ? nv.getChucVu() : "");
                    dos.writeDouble(nv.getLuong());
                    dos.writeUTF(nv.getTrangThai() != null ? nv.getTrangThai() : "");
                }
            }
        } catch (IOException e) {
            System.out.println("Loi ghi file NhanVien: " + e.getMessage());
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