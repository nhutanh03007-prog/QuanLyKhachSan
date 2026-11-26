package DAO;

import java.time.LocalDate;
import java.util.Arrays;
import model.DatPhong;
import model.TrangThaiDatPhong;

public class DatPhongDAO {
    public static DatPhong[] danhSachDatPhong = new DatPhong[100];
    public static int soLuongDatPhong = 0;

    public static DatPhong[] danhSachDaXoa = new DatPhong[100];
    public static int soLuongDaXoa = 0;

    static {
        QuanLyDuLieuFile.damBaoDaNap();
    }

    public static void napDuLieu(DatPhong[] dsChinh, DatPhong[] dsXoa) {
        if (dsChinh == null) {
            danhSachDatPhong = new DatPhong[100];
            soLuongDatPhong = 0;
        } else {
            danhSachDatPhong = Arrays.copyOf(dsChinh, Math.max(100, dsChinh.length));
            soLuongDatPhong = dsChinh.length;
        }
        if (dsXoa == null) {
            danhSachDaXoa = new DatPhong[100];
            soLuongDaXoa = 0;
        } else {
            danhSachDaXoa = Arrays.copyOf(dsXoa, Math.max(100, dsXoa.length));
            soLuongDaXoa = dsXoa.length;
        }
    }

    private static boolean ghiFile() {
        boolean ok1 = QuanLyDuLieuFile.ghiDatPhong("DatPhong.dat", danhSachDatPhong, soLuongDatPhong);
        boolean ok2 = QuanLyDuLieuFile.ghiDatPhong("DatPhongDaXoa.dat", danhSachDaXoa, soLuongDaXoa);
        return ok1 && ok2;
    }

    public boolean them(DatPhong datPhong) {
        if (datPhong == null || datPhong.getMaDatPhong() == null || datPhong.getMaDatPhong().trim().isEmpty()
                || datPhong.getSoLuongPhong() == 0
                || timTheoMa(datPhong.getMaDatPhong()) != null) {
            return false;
        }
        if (soLuongDatPhong >= danhSachDatPhong.length) {
            danhSachDatPhong = Arrays.copyOf(danhSachDatPhong, danhSachDatPhong.length * 2);
        }
        danhSachDatPhong[soLuongDatPhong++] = datPhong;
        if (!ghiFile()) {
            danhSachDatPhong[--soLuongDatPhong] = null;
            return false;
        }
        return true;
    }

    public boolean sua(DatPhong datPhong) {
        if (datPhong == null || datPhong.getMaDatPhong() == null) {
            return false;
        }
        String ma = datPhong.getMaDatPhong().trim();
        for (int i = 0; i < soLuongDatPhong; i++) {
            if (danhSachDatPhong[i] != null && danhSachDatPhong[i].getMaDatPhong().equalsIgnoreCase(ma)) {
                DatPhong cu = danhSachDatPhong[i];
                danhSachDatPhong[i] = datPhong;
                if (!ghiFile()) {
                    danhSachDatPhong[i] = cu;
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maDatPhong) {
        if (maDatPhong == null || maDatPhong.trim().isEmpty()) {
            return false;
        }
        String ma = maDatPhong.trim();
        for (int i = 0; i < soLuongDatPhong; i++) {
            if (danhSachDatPhong[i] != null && danhSachDatPhong[i].getMaDatPhong().equalsIgnoreCase(ma)) {
                if (soLuongDaXoa >= danhSachDaXoa.length) {
                    danhSachDaXoa = Arrays.copyOf(danhSachDaXoa, danhSachDaXoa.length * 2);
                }
                danhSachDaXoa[soLuongDaXoa++] = danhSachDatPhong[i];
                System.arraycopy(danhSachDatPhong, i + 1, danhSachDatPhong, i, soLuongDatPhong - i - 1);
                danhSachDatPhong[--soLuongDatPhong] = null;
                ghiFile();
                return true;
            }
        }
        return false;
    }

    public DatPhong timTheoMa(String maDatPhong) {
        if (maDatPhong == null || maDatPhong.trim().isEmpty()) {
            return null;
        }
        String ma = maDatPhong.trim();
        for (int i = 0; i < soLuongDatPhong; i++) {
            if (danhSachDatPhong[i] != null && danhSachDatPhong[i].getMaDatPhong().equalsIgnoreCase(ma)) {
                return danhSachDatPhong[i];
            }
        }
        return null;
    }

    public boolean kiemTraPhongTrong(String maPhong, LocalDate ngayNhan, LocalDate ngayTra) {
        return kiemTraPhongTrong(maPhong, ngayNhan, ngayTra, null);
    }

    public boolean kiemTraPhongTrong(String maPhong, LocalDate ngayNhan, LocalDate ngayTra, String maDatPhongBoQua) {
        if (maPhong == null || maPhong.trim().isEmpty()) {
            return false;
        }
        for (int i = 0; i < soLuongDatPhong; i++) {
            DatPhong dp = danhSachDatPhong[i];
            if (dp == null) {
                continue;
            }
            if (maDatPhongBoQua != null && dp.getMaDatPhong() != null
                    && dp.getMaDatPhong().equalsIgnoreCase(maDatPhongBoQua.trim())) {
                continue;
            }
            if (dp.coChuaPhong(maPhong)
                    && dp.getTrangThai() != TrangThaiDatPhong.DA_HUY
                    && dp.getTrangThai() != TrangThaiDatPhong.DA_TRA) {
                if (dp.isTrungLich(ngayNhan, ngayTra)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean coPhieuHoatDongChoPhong(String maPhong, String maDatPhongBoQua) {
        if (maPhong == null || maPhong.trim().isEmpty()) {
            return false;
        }
        for (int i = 0; i < soLuongDatPhong; i++) {
            DatPhong dp = danhSachDatPhong[i];
            if (dp == null) {
                continue;
            }
            if (maDatPhongBoQua != null && dp.getMaDatPhong() != null
                    && dp.getMaDatPhong().equalsIgnoreCase(maDatPhongBoQua.trim())) {
                continue;
            }
            if (dp.coChuaPhong(maPhong)
                    && dp.getTrangThai() != TrangThaiDatPhong.DA_HUY
                    && dp.getTrangThai() != TrangThaiDatPhong.DA_TRA) {
                return true;
            }
        }
        return false;
    }

    public boolean coPhieuHoatDongChoKhachHang(String maKH) {
        if (maKH == null || maKH.trim().isEmpty()) {
            return false;
        }
        String target = maKH.trim();
        for (int i = 0; i < soLuongDatPhong; i++) {
            DatPhong dp = danhSachDatPhong[i];
            if (dp != null && dp.getMaKhachHang() != null
                    && dp.getMaKhachHang().equalsIgnoreCase(target)
                    && dp.getTrangThai() != TrangThaiDatPhong.DA_HUY
                    && dp.getTrangThai() != TrangThaiDatPhong.DA_TRA) {
                return true;
            }
        }
        return false;
    }

    public DatPhong[] layDanhSach() {
        DatPhong[] ketQua = new DatPhong[soLuongDatPhong];
        System.arraycopy(danhSachDatPhong, 0, ketQua, 0, soLuongDatPhong);
        return ketQua;
    }

    public boolean khoiPhuc(String maDatPhong) {
        if (maDatPhong == null || maDatPhong.trim().isEmpty()) {
            return false;
        }
        String ma = maDatPhong.trim();
        for (int i = 0; i < soLuongDaXoa; i++) {
            if (danhSachDaXoa[i] != null && danhSachDaXoa[i].getMaDatPhong().equalsIgnoreCase(ma)) {
                if (soLuongDatPhong >= danhSachDatPhong.length) {
                    danhSachDatPhong = Arrays.copyOf(danhSachDatPhong, danhSachDatPhong.length * 2);
                }
                danhSachDatPhong[soLuongDatPhong++] = danhSachDaXoa[i];
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
}
