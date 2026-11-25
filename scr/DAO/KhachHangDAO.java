package DAO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
            try (DataInputStream dis = new DataInputStream(new FileInputStream(f))) {
        while (soLuongKhachHang < danhSachKhachHang.length && dis.available() > 0) {
        String maKH = dis.readUTF();
        String hoTen = dis.readUTF();
        String gioiTinh = dis.readUTF();
        String ngaySinhStr = dis.readUTF();
        String diaChi = dis.readUTF();
        String sdt = dis.readUTF();
        String email = dis.readUTF();
        String loaiKhach = dis.readUTF();
        String ghiChu = dis.readUTF();

        LocalDate ngaySinh = ngaySinhStr.isEmpty() ? null : LocalDate.parse(ngaySinhStr);
        danhSachKhachHang[soLuongKhachHang++] = new KhachHang(
                maKH, hoTen, gioiTinh, ngaySinh, diaChi, sdt, email, loaiKhach, ghiChu
        );
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
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(f))) {
    for (int i = 0; i < soLuongKhachHang; i++) {
        KhachHang kh = danhSachKhachHang[i];
        if (kh != null) {
            dos.writeUTF(giaTri(kh.getMaKH()));
            dos.writeUTF(giaTri(kh.getHoTen()));
            dos.writeUTF(giaTri(kh.getGioiTinh()));
            dos.writeUTF(giaTri(kh.getNgaySinh()));
            dos.writeUTF(giaTri(kh.getDiaChi()));
            dos.writeUTF(giaTri(kh.getSdt()));
            dos.writeUTF(giaTri(kh.getEmail()));
            dos.writeUTF(giaTri(kh.getLoaiKhach()));
            dos.writeUTF(giaTri(kh.getGhiChu()));
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