package DAO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import model.Phong;
import model.PhongThuong;
import model.PhongVIP;
import model.TinhTrangPhong;

public class PhongDAO {

    private static final String THU_MUC = "DATABASE";
    private static final String FILE_PATH = THU_MUC + "/phong.dat";
    private static final int MAX_PHONG = 100;

    private static Phong[] danhSachPhong = new Phong[10];
    private static int soLuongPhong = 0;

    static {
        docFile();
    }

    // =====================================
    // DOC / GHI FILE
    // =====================================

    private static boolean hopLe(Phong p) {
        return p != null
                && p.getMaPhong() != null && !p.getMaPhong().trim().isEmpty()
                && p.getTenPhong() != null && !p.getTenPhong().trim().isEmpty()
                && p.getGiaPhong() != null && p.getGiaPhong().signum() > 0
                && p.getTinhTrang() != null;
    }

    // Moi phong ghi: loai, ma, ten, gia, tinh trang, dich vu
    private static void docFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }

        Phong[] dsTam = new Phong[MAX_PHONG];
        int dem = 0;

        try (DataInputStream inStream = new DataInputStream(new FileInputStream(file))) {
            while (dem < MAX_PHONG) {
                String loai = inStream.readUTF();      // het file se nem EOFException
                String ma = inStream.readUTF();
                String ten = inStream.readUTF();
                BigDecimal gia = new BigDecimal(inStream.readUTF());
                TinhTrangPhong tinhTrang = TinhTrangPhong.valueOf(inStream.readUTF());
                String dichVu = inStream.readUTF();

                if (loai.equals("THUONG")) {
                    dsTam[dem++] = new PhongThuong(ma, ten, gia, tinhTrang, dichVu);
                } else if (loai.equals("VIP")) {
                    dsTam[dem++] = new PhongVIP(ma, ten, gia, tinhTrang, dichVu);
                } else {
                    throw new IOException("Loai phong khong hop le: " + loai);
                }
            }
        } catch (EOFException e) {
            // doc het file, binh thuong
        } catch (IOException | RuntimeException e) {
            System.out.println("Loi doc file phong: " + e.getMessage());
        }

        danhSachPhong = dsTam;
        soLuongPhong = dem;
    }

    private static boolean ghiFile() {
        File thuMuc = new File(THU_MUC);
        if (!thuMuc.exists() && !thuMuc.mkdirs()) {
            System.out.println("Khong tao duoc thu muc " + THU_MUC);
            return false;
        }

        try (DataOutputStream outStream = new DataOutputStream(new FileOutputStream(FILE_PATH))) {
            for (int i = 0; i < soLuongPhong; i++) {
                Phong p = danhSachPhong[i];
                outStream.writeUTF(p instanceof PhongVIP ? "VIP" : "THUONG");
                outStream.writeUTF(p.getMaPhong());
                outStream.writeUTF(p.getTenPhong());
                outStream.writeUTF(p.getGiaPhong().toPlainString());
                outStream.writeUTF(p.getTinhTrang().name());
                outStream.writeUTF(p.getDichVu());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Loi ghi file phong: " + e.getMessage());
            return false;
        }
    }
    

    // =====================================
    // HAM HO TRO
    // =====================================


    private static int timViTri(String maPhong) {
        if (maPhong == null || maPhong.trim().isEmpty()) {
            return -1;
        }
        String ma = maPhong.trim();
        for (int i = 0; i < soLuongPhong; i++) {
            if (danhSachPhong[i].getMaPhong().equalsIgnoreCase(ma)) {
                return i;
            }
        }
        return -1;
    }

    // =====================================
    // CAC THAO TAC (tra ve ban sao, khong lo doi tuong ben trong)
    // =====================================

    public Phong timPhongTheoMa(String maPhong) {
        int vt = timViTri(maPhong);
        return vt == -1 ? null : danhSachPhong[vt].taoBanSao();
    }

    public boolean themPhong(Phong phong) {
        if (!hopLe(phong)) {
            return false;
        }
        if (soLuongPhong >= MAX_PHONG) {
            System.out.println("Danh sach phong da day!");
            return false;
        }
        if (timViTri(phong.getMaPhong()) != -1) {
            System.out.println("Ma phong da ton tai!");
            return false;
        }

        if (soLuongPhong >= danhSachPhong.length) {
            danhSachPhong = Arrays.copyOf(danhSachPhong,
                    Math.min(danhSachPhong.length * 2, MAX_PHONG));
        }

        int viTriMoi = soLuongPhong;
        danhSachPhong[soLuongPhong++] = phong.taoBanSao();

        if (!ghiFile()) {
            soLuongPhong--;
            danhSachPhong[viTriMoi] = null;
            return false;
        }
        return true;
    }

    public boolean xoaPhong(String maPhong) {
        int vt = timViTri(maPhong);
        if (vt == -1) {
            System.out.println("Khong tim thay phong!");
            return false;
        }

        Phong[] backup = danhSachPhong.clone();
        int soLuongCu = soLuongPhong;

        System.arraycopy(danhSachPhong, vt + 1, danhSachPhong, vt, soLuongPhong - vt - 1);
        danhSachPhong[--soLuongPhong] = null;

        if (!ghiFile()) {
            danhSachPhong = backup;
            soLuongPhong = soLuongCu;
            return false;
        }
        return true;
    }

    public boolean suaPhong(String maPhong, Phong phongMoi) {
        if (!hopLe(phongMoi)) {
            return false;
        }
        int vt = timViTri(maPhong);
        if (vt == -1) {
            System.out.println("Khong tim thay phong!");
            return false;
        }

        int vtTrung = timViTri(phongMoi.getMaPhong());
        if (vtTrung != -1 && vtTrung != vt) {
            System.out.println("Ma phong da ton tai!");
            return false;
        }

        Phong phongCu = danhSachPhong[vt];
        danhSachPhong[vt] = phongMoi.taoBanSao();

        if (!ghiFile()) {
            danhSachPhong[vt] = phongCu;
            return false;
        }
        return true;
    }

    public Phong[] layDanhSachPhong() {
        Phong[] ketQua = new Phong[soLuongPhong];
        for (int i = 0; i < soLuongPhong; i++) {
            ketQua[i] = danhSachPhong[i].taoBanSao();
        }
        return ketQua;
    }

    public int demSoLuongPhong() {
        return soLuongPhong;
    }

    public Phong[] layDanhSachPhongTheoTinhTrang(TinhTrangPhong tinhTrang) {
        if (tinhTrang == null) {
            return new Phong[0];
        }
        Phong[] ketQua = new Phong[demSoLuongPhongTheoTinhTrang(tinhTrang)];
        int vt = 0;
        for (int i = 0; i < soLuongPhong; i++) {
            if (danhSachPhong[i].getTinhTrang() == tinhTrang) {
                ketQua[vt++] = danhSachPhong[i].taoBanSao();
            }
        }
        return ketQua;
    }

    public int demSoLuongPhongTheoTinhTrang(TinhTrangPhong tinhTrang) {
        if (tinhTrang == null) {
            return 0;
        }
        int dem = 0;
        for (int i = 0; i < soLuongPhong; i++) {
            if (danhSachPhong[i].getTinhTrang() == tinhTrang) {
                dem++;
            }
        }
        return dem;
    }
}