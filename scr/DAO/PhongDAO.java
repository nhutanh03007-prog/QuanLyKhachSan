
package DAO;

import model.Phong;
import model.TinhTrangPhong;

public class PhongDAO {

    private static final int MAX_PHONG = 100;

    private static Phong[] danhSachPhong =new Phong[MAX_PHONG];

    private static int soLuongPhong = 0;

    public boolean themPhong(Phong phong) {
        if (phong == null) {
            return false;
        }
        if (soLuongPhong >= MAX_PHONG) {
            System.out.println("Danh sach phong da day!");
            return false;
        }
        if (timPhongTheoMa(phong.getMaPhong()) != null) {
            System.out.println("Ma phong da ton tai!");
            return false;
        }
        danhSachPhong[soLuongPhong] = phong;
        soLuongPhong++;
        return true;
    }

    public boolean xoaPhong(String maPhong) {
        if (maPhong == null || maPhong.trim().isEmpty()) {
            return false;
        }
        for (int i = 0; i < soLuongPhong; i++) {
            if (danhSachPhong[i] != null &&
                danhSachPhong[i].getMaPhong() != null &&
                danhSachPhong[i].getMaPhong()
                        .equalsIgnoreCase(maPhong.trim())) {
                for (int j = i; j < soLuongPhong - 1; j++) {
                    danhSachPhong[j] = danhSachPhong[j + 1];
                }
                danhSachPhong[soLuongPhong - 1] = null;
                soLuongPhong--;
                return true;
            }
        }
        return false;
    }

    public boolean suaPhong(Phong phong) {
        if (phong == null || phong.getMaPhong() == null) {
            return false;
        }
        for (int i = 0; i < soLuongPhong; i++) {
            if (danhSachPhong[i] != null &&
                danhSachPhong[i].getMaPhong() != null &&
                danhSachPhong[i].getMaPhong()
                        .equalsIgnoreCase(phong.getMaPhong().trim())) {
                danhSachPhong[i] = phong;
                return true;
            }
        }
        return false;
    }

    public Phong timPhongTheoMa(String maPhong) {
        if (maPhong == null || maPhong.trim().isEmpty()) {
            return null;
        }
        for (int i = 0; i < soLuongPhong; i++) {
            if (danhSachPhong[i] != null &&
                danhSachPhong[i].getMaPhong() != null &&
                danhSachPhong[i].getMaPhong()
                        .equalsIgnoreCase(maPhong.trim())) {
                return danhSachPhong[i];
            }
        }
        return null;
    }

    public Phong[] layDanhSachPhong() {
        Phong[] ketQua = new Phong[soLuongPhong];
        for (int i = 0; i < soLuongPhong; i++) {
            ketQua[i] = danhSachPhong[i];
        }
        return ketQua;
    }

    public Phong[] timPhongTheoTinhTrang(
            TinhTrangPhong tinhTrang) {
        return layDanhSachPhongTheoTinhTrang(tinhTrang);
    }

    public int demSoLuongPhong() {
        return soLuongPhong;
    }

    public Phong[] layDanhSachPhongTheoTinhTrang(
            TinhTrangPhong tinhTrang) {
        if (tinhTrang == null) {
            return new Phong[0];
        }
        int dem = demSoLuongPhongTheoTinhTrang(tinhTrang);
        Phong[] ketQua = new Phong[dem];
        int index = 0;
        for (int i = 0; i < soLuongPhong; i++) {
            if (danhSachPhong[i] != null &&
                danhSachPhong[i].getTinhTrang() == tinhTrang) {
                ketQua[index] = danhSachPhong[i];
                index++;
            }
        }
        return ketQua;
    }

    public int demSoLuongPhongTheoTinhTrang(
            TinhTrangPhong tinhTrang) {
        if (tinhTrang == null) {
            return 0;
        }
        int dem = 0;
        for (int i = 0; i < soLuongPhong; i++) {
            if (danhSachPhong[i] != null &&
                danhSachPhong[i].getTinhTrang() == tinhTrang) {
                dem++;
            }
        }
        return dem;
    }
}
