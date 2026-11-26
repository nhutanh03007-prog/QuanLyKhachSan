package service;

import DAO.DatPhongDAO;
import java.time.LocalDate;
import model.DatPhong;
import model.TrangThaiDatPhong;

public class QLDatPhong {
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();

    // ===== TAO =====
    public boolean taoDatPhong(DatPhong dp) {
        if (dp == null || !dp.isNgayHopLe()) {
            System.out.println("Loi: Ngay nhan hoac ngay tra phong khong hop le");
            return false;
        }

        if (!datPhongDAO.kiemTraPhongTrong(dp.getMaPhong(), dp.getNgayNhanPhong(), dp.getNgayTraPhong())) {
            System.out.println("Loi: Phong " + dp.getMaPhong() + " da co nguoi dat trong khoang thoi gian nay!");
            return false;
        }

        boolean kq = datPhongDAO.them(dp);
        if (!kq) {
            System.out.println("Dat phong that bai (Trung ma phieu hoac day danh sach)!");
        }
        return kq;
    }

    // ===== SUA =====
    public boolean suaDatPhong(DatPhong dp) {
        if (dp == null || !dp.isNgayHopLe()) {
            System.out.println("Loi: Ngay nhan hoac ngay tra phong khong hop le");
            return false;
        }

        // Bo qua chinh phieu dang sua khi kiem tra trung lich
        if (!datPhongDAO.kiemTraPhongTrong(dp.getMaPhong(), dp.getNgayNhanPhong(),
                dp.getNgayTraPhong(), dp.getMaDatPhong())) {
            System.out.println("Loi: Phong " + dp.getMaPhong() + " da co nguoi dat trong khoang thoi gian nay!");
            return false;
        }

        return datPhongDAO.sua(dp);
    }

    // ===== HUY =====
    public boolean huyDatPhong(String maDatPhong) {
        DatPhong dp = datPhongDAO.timTheoMa(maDatPhong);
        if (dp == null) {
            System.out.println("Khong tim thay ma dat phong!");
            return false;
        }
        if (dp.getTrangThai() != TrangThaiDatPhong.DA_DAT) {
            System.out.println("Chi huy duoc phieu o trang thai DA_DAT (hien tai: " + dp.getTrangThai() + ")!");
            return false;
        }

        dp.setTrangThai(TrangThaiDatPhong.DA_HUY);
        if (!datPhongDAO.sua(dp)) {       
            System.out.println("Loi: Khong luu duoc thay doi!");
            return false;
        }
        System.out.println("Da huy dat phong thanh cong!");
        return true;
    }

    // ===== NHAN PHONG =====
    public boolean nhanPhong(String maDatPhong, LocalDate ngayNhanThucTe) {
        DatPhong dp = datPhongDAO.timTheoMa(maDatPhong);
        if (dp == null || dp.getTrangThai() != TrangThaiDatPhong.DA_DAT) {
            System.out.println("Loi: Phieu dat phong khong hop le!");
            return false;
        }

        dp.setNgayNhanPhong(ngayNhanThucTe);
        dp.setTrangThai(TrangThaiDatPhong.DANG_SU_DUNG);
        return datPhongDAO.sua(dp);
    }

    // ===== LUU THAY DOI CHUNG =====
    public boolean capNhat(DatPhong dp) {
        return datPhongDAO.sua(dp);
    }

    // ===== TIM / LAY DANH SACH =====
    public DatPhong timDatPhong(String maDatPhong) {
        return datPhongDAO.timTheoMa(maDatPhong);
    }

    public DatPhong[] layTatCa() {
        return datPhongDAO.layDanhSach();
    }

    /** Lay tat ca phieu cua mot lan dat: maGoc "DP01" -> DP01-1, DP01-2, ... */
    public DatPhong[] timTheoNhom(String maGoc) {
        DatPhong[] tatCa = datPhongDAO.layDanhSach();
        String tienTo = maGoc + "-";
        int dem = 0;
        for (DatPhong dp : tatCa) {
            if (dp.getMaDatPhong().startsWith(tienTo)) dem++;
        }
        DatPhong[] ketQua = new DatPhong[dem];
        int i = 0;
        for (DatPhong dp : tatCa) {
            if (dp.getMaDatPhong().startsWith(tienTo)) ketQua[i++] = dp;
        }
        return ketQua;
    }

    /** Lay tat ca phieu cua mot khach hang. */
    public DatPhong[] timTheoKhachHang(String maKH) {
        DatPhong[] tatCa = datPhongDAO.layDanhSach();
        int dem = 0;
        for (DatPhong dp : tatCa) {
            if (maKH.equalsIgnoreCase(dp.getMaKhachHang())) dem++;
        }
        DatPhong[] ketQua = new DatPhong[dem];
        int i = 0;
        for (DatPhong dp : tatCa) {
            if (maKH.equalsIgnoreCase(dp.getMaKhachHang())) ketQua[i++] = dp;
        }
        return ketQua;
    }
}