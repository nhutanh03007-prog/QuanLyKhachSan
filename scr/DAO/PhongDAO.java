package DAO;

import java.util.Arrays;
import model.Phong;
import model.TinhTrangPhong;

public class PhongDAO {

    private static final int MAX_PHONG = 100;

    private static Phong[] danhSachPhong = new Phong[20];
    private static int soLuongPhong = 0;

    static {
        QuanLyDuLieuFile.damBaoDaNap();
    }

    public static void napDuLieu(Phong[] duLieu) {
        if (duLieu == null) {
            danhSachPhong = new Phong[20];
            soLuongPhong = 0;
            return;
        }
        danhSachPhong = Arrays.copyOf(duLieu, Math.max(20, duLieu.length));
        soLuongPhong = duLieu.length;
    }

    private static boolean hopLe(Phong p) {
        return p != null
                && p.getMaPhong() != null && !p.getMaPhong().trim().isEmpty()
                && p.getTenPhong() != null && !p.getTenPhong().trim().isEmpty()
                && p.getGiaPhong() != null && p.getGiaPhong().signum() > 0
                && p.getTinhTrang() != null;
    }

    private static boolean ghiFile() {
        return QuanLyDuLieuFile.ghiPhong(danhSachPhong, soLuongPhong);
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

    public boolean timPhongTheoTen(String tenPhong) {
        return timPhongTheoTenKhacMa(tenPhong, null);
    }

    public boolean timPhongTheoTenKhacMa(String tenPhong, String maPhongBoQua) {
        if (tenPhong == null || tenPhong.trim().isEmpty()) {
            return false;
        }
        String ten = tenPhong.trim();
        for (int i = 0; i < soLuongPhong; i++) {
            Phong p = danhSachPhong[i];
            if (p == null) {
                continue;
            }
            if (maPhongBoQua != null && p.getMaPhong() != null
                    && p.getMaPhong().equalsIgnoreCase(maPhongBoQua.trim())) {
                continue;
            }
            if (p.getTenPhong() != null && p.getTenPhong().equalsIgnoreCase(ten)) {
                return true;
            }
        }
        return false;
    }

    public Phong timPhongTheoMa(String maPhong) {
        int vt = timViTri(maPhong);
        return vt == -1 ? null : danhSachPhong[vt];
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
            danhSachPhong = Arrays.copyOf(danhSachPhong, danhSachPhong.length * 2);
        }

        int viTriMoi = soLuongPhong;
        danhSachPhong[soLuongPhong++] = phong;

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
        danhSachPhong[vt] = phongMoi;

        if (!ghiFile()) {
            danhSachPhong[vt] = phongCu;
            return false;
        }
        return true;
    }

    public Phong[] layDanhSachPhong() {
        return Arrays.copyOf(danhSachPhong, soLuongPhong);
    }

    public int demSoLuongPhong() {
        return soLuongPhong;
    }

    public Phong[] layDanhSachPhongTheoTinhTrang(TinhTrangPhong tinhTrang) {
        if (tinhTrang == null) {
            return new Phong[0];
        }
        Phong[] ketQua = new Phong[soLuongPhong];
        int vt = 0;
        for (int i = 0; i < soLuongPhong; i++) {
            if (danhSachPhong[i].getTinhTrang() == tinhTrang) {
                ketQua[vt++] = danhSachPhong[i];
            }
        }
        return Arrays.copyOf(ketQua, vt);
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
