package DAO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import model.Phong;
import model.PhongThuong;
import model.PhongVIP;
import model.TinhTrangPhong;

public class PhongDAO {

    private static final String THU_MUC = "DATABASEH";
    private static final String FILE_PATH = THU_MUC + "/phong.dat";
    private static final String FILE_TEMP = THU_MUC + "/phong_temp.dat";
    private static final int MAX_PHONG = 100;

    private static Phong[] danhSachPhong = new Phong[10];
    private static int soLuongPhong = 0;

    static {
        docFile();
    }

    // =====================================
    // DOC / GHI FILE
    // =====================================

    private static String docChuoi(DataInputStream dis) throws IOException {
        String s = dis.readUTF();
        return s.isEmpty() ? null : s;
    }

    private static void ghiChuoi(DataOutputStream dos, String s) throws IOException {
        dos.writeUTF(s == null ? "" : s);
    }

    private static void docFile() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return;
        }

        Phong[] dsTam = new Phong[10];
        int dem = 0;

        try (DataInputStream dis = new DataInputStream(
                new BufferedInputStream(new FileInputStream(file)))) {

            int count = dis.readInt();
            if (count < 0 || count > MAX_PHONG) {
                throw new IOException("So luong phong khong hop le!");
            }

            for (int i = 0; i < count; i++) {
                String loai = dis.readUTF();
                String maPhong = docChuoi(dis);
                String tenPhong = docChuoi(dis);
                docChuoi(dis); // loaiPhong: bo qua, loai do lop con quyet dinh
                BigDecimal giaPhong = new BigDecimal(dis.readUTF());
                TinhTrangPhong tinhTrang = TinhTrangPhong.valueOf(dis.readUTF());
                String dichVu = dis.readUTF(); // giu nguyen "" khi phong khong co dich vu

                Phong phong;
                switch (loai) {
                    case "THUONG" -> phong = new PhongThuong(maPhong, tenPhong, giaPhong, tinhTrang, dichVu);
                    case "VIP" -> phong = new PhongVIP(maPhong, tenPhong, giaPhong, tinhTrang, dichVu);
                    default -> throw new IOException("Loai phong khong hop le: " + loai);
                }

                if (!hopLe(phong)) {
                    throw new IOException("Du lieu phong khong hop le: " + maPhong);
                }

                for (int j = 0; j < dem; j++) {
                    if (dsTam[j].getMaPhong().equalsIgnoreCase(maPhong)) {
                        throw new IOException("Ma phong bi trung: " + maPhong);
                    }
                }

                if (dem >= dsTam.length) {
                    dsTam = Arrays.copyOf(dsTam, Math.min(dsTam.length * 2, MAX_PHONG));
                }
                dsTam[dem++] = phong;
            }

            if (dis.read() != -1) {
                throw new IOException("File phong co du lieu thua!");
            }

            danhSachPhong = dsTam;
            soLuongPhong = dem;

        } catch (IOException | RuntimeException e) {
            System.out.println("Loi doc file phong: " + e.getMessage());
            saoLuuFileLoi();
        }
    }

    /**
     * Doi ten file hong thanh phong.dat.loi_<thoi gian> de khong bi ghi de mat du lieu
     * khi nguoi dung them phong moi.
     */
    private static void saoLuuFileLoi() {
        try {
            Path bak = Path.of(FILE_PATH + ".loi_" + System.currentTimeMillis());
            Files.move(Path.of(FILE_PATH), bak);
            System.out.println("Da giu lai file loi tai: " + bak);
        } catch (IOException e) {
            System.out.println("Khong sao luu duoc file loi: " + e.getMessage());
        }
    }

    private static boolean ghiFile() {
        try {
            Files.createDirectories(Path.of(THU_MUC));
        } catch (IOException e) {
            System.out.println("Khong tao duoc thu muc " + THU_MUC + ": " + e.getMessage());
            return false;
        }

        try (DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(FILE_TEMP)))) {

            dos.writeInt(soLuongPhong);

            for (int i = 0; i < soLuongPhong; i++) {
                Phong p = danhSachPhong[i];
                if (!hopLe(p)) {
                    throw new IOException("Thong tin phong khong hop le!");
                }

                String loai;
                if (p instanceof PhongThuong) {
                    loai = "THUONG";
                } else if (p instanceof PhongVIP) {
                    loai = "VIP";
                } else {
                    throw new IOException("Loai phong khong hop le!");
                }

                dos.writeUTF(loai);
                ghiChuoi(dos, p.getMaPhong());
                ghiChuoi(dos, p.getTenPhong());
                ghiChuoi(dos, p.getLoaiPhong());
                dos.writeUTF(p.getGiaPhong().toPlainString());
                dos.writeUTF(p.getTinhTrang().name());
                ghiChuoi(dos, p.getDichVu());
            }

        } catch (IOException | RuntimeException e) {
            System.out.println("Loi ghi file phong: " + e.getMessage());
            xoaFileTam();
            return false;
        }

        try {
            try {
                Files.move(Path.of(FILE_TEMP), Path.of(FILE_PATH),
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException e) {
                Files.move(Path.of(FILE_TEMP), Path.of(FILE_PATH),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            System.out.println("Loi thay file phong: " + ex.getMessage());
            xoaFileTam();
            return false;
        }

        return true;
    }

    private static void xoaFileTam() {
        try {
            Files.deleteIfExists(Path.of(FILE_TEMP));
        } catch (IOException ignored) {
            // khong can xu ly them
        }
    }

    // =====================================
    // HAM HO TRO
    // =====================================

    private static boolean hopLe(Phong p) {
        return p != null
                && p.getMaPhong() != null && !p.getMaPhong().trim().isEmpty()
                && p.getTenPhong() != null && !p.getTenPhong().trim().isEmpty()
                && p.getGiaPhong() != null && p.getGiaPhong().signum() > 0
                && p.getTinhTrang() != null
                && p.getDichVu() != null
                && (p instanceof PhongThuong || p instanceof PhongVIP);
    }

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