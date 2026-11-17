package DAO;

import model.NhanVien;

public class NhanVienDAO {

    private NhanVien[] danhSach;
    private int soLuong;

    public NhanVienDAO() {
        this.danhSach = new NhanVien[10];
        this.soLuong = 0;
    }

    public NhanVien[] layTatCa() {
        NhanVien[] ketQua = new NhanVien[soLuong];
        for (int i = 0; i < soLuong; i++) {
            ketQua[i] = danhSach[i];
        }
        return ketQua;
    }


    public boolean them(NhanVien nv) {
        if (nv == null) {
            return false;
        }

        if (nv.getMaNV() == null || nv.getMaNV().trim().isEmpty()) {
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

        return true;
    }

    public boolean capNhat(NhanVien nv) {
        if (nv == null) {
            return false;
        }

        if (nv.getMaNV() == null || nv.getMaNV().trim().isEmpty()) {
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
        NhanVien[] mangMoi = new NhanVien[danhSach.length * 2];
        for (int i = 0; i < soLuong; i++) {
            mangMoi[i] = danhSach[i];
        }
        danhSach = mangMoi;
    }
}