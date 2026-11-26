package DAO;

import java.util.Arrays;
import model.NhanVien;

public class NhanVienDAO {

    private static NhanVien[] danhSach = new NhanVien[20];
    private static int soLuong = 0;

    static {
        QuanLyDuLieuFile.damBaoDaNap();
    }

    public NhanVienDAO() {
    }

    public static void napDuLieu(NhanVien[] duLieu) {
        if (duLieu == null) {
            danhSach = new NhanVien[20];
            soLuong = 0;
            return;
        }
        danhSach = Arrays.copyOf(duLieu, Math.max(20, duLieu.length));
        soLuong = duLieu.length;
    }

    public final NhanVien[] docFile() {
        napDuLieu(QuanLyDuLieuFile.docNhanVien());
        return layTatCa();
    }

    public void ghiFile() {
        QuanLyDuLieuFile.ghiNhanVien(danhSach, soLuong);
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
        if (nv.getLuong() <= 0) {
            return false;
        }
        if (timTheoMa(nv.getMaNV()) != null) {
            return false;
        }

        if (soLuong == danhSach.length) {
            moRongMang();
        }

        danhSach[soLuong++] = nv;
        if (!QuanLyDuLieuFile.ghiNhanVien(danhSach, soLuong)) {
            danhSach[--soLuong] = null;
            return false;
        }
        return true;
    }

    public boolean capNhat(NhanVien nv) {
        if (nv == null || nv.getMaNV() == null || nv.getMaNV().trim().isEmpty()) {
            return false;
        }
        if (nv.getHoTen() == null || nv.getHoTen().trim().isEmpty()) {
            return false;
        }
        if (nv.getLuong() <= 0) {
            return false;
        }

        String ma = nv.getMaNV().trim();
        for (int i = 0; i < soLuong; i++) {
            NhanVien nhanVienCu = danhSach[i];
            if (nhanVienCu != null && nhanVienCu.getMaNV() != null
                    && nhanVienCu.getMaNV().equalsIgnoreCase(ma)) {
                danhSach[i] = nv;
                if (!QuanLyDuLieuFile.ghiNhanVien(danhSach, soLuong)) {
                    danhSach[i] = nhanVienCu;
                    return false;
                }
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
                QuanLyDuLieuFile.ghiNhanVien(danhSach, soLuong);
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

    private static void moRongMang() {
        danhSach = Arrays.copyOf(danhSach, danhSach.length * 2);
    }
}
