package service;

import DAO.DatPhongDAO;
import DAO.KhachHangDAO;
import DAO.PhongDAO;
import java.math.BigDecimal;
import java.time.LocalDate;
import model.DatPhong;
import model.Phong;
import model.TinhTrangPhong;
import model.TrangThaiDatPhong;

public class QLDatPhong {
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();
    private final PhongDAO phongDAO = new PhongDAO();
    private final KhachHangDAO khachHangDAO = new KhachHangDAO();

    public boolean taoDatPhong(DatPhong dp) {
        if (dp == null || dp.getMaDatPhong() == null || dp.getMaDatPhong().trim().isEmpty()) {
            System.out.println("Loi: Ma dat phong khong hop le!");
            return false;
        }
        if (dp.getMaKhachHang() == null || khachHangDAO.timTheoMa(dp.getMaKhachHang()) == null) {
            System.out.println("Loi: Ma khach hang '" + dp.getMaKhachHang() + "' khong ton tai!");
            return false;
        }
        if (!dp.isNgayHopLe()) {
            System.out.println("Loi: Ngay nhan hoac ngay tra phong khong hop le!");
            return false;
        }
        String[] dsPhong = dp.getDanhSachMaPhong();
        if (dsPhong.length == 0) {
            System.out.println("Loi: Phieu dat phong phai co it nhat 1 phong!");
            return false;
        }

        BigDecimal tongGiaNgay = BigDecimal.ZERO;
        for (String maPhong : dsPhong) {
            Phong p = phongDAO.timPhongTheoMa(maPhong);
            if (p == null) {
                System.out.println("Loi: Phong '" + maPhong + "' khong ton tai!");
                return false;
            }
            if (p.getTinhTrang() == TinhTrangPhong.DANG_BAO_TRI) {
                System.out.println("Loi: Phong '" + maPhong + "' dang bao tri!");
                return false;
            }
            if (!datPhongDAO.kiemTraPhongTrong(maPhong, dp.getNgayNhanPhong(), dp.getNgayTraPhong())) {
                System.out.println("Loi: Phong '" + maPhong + "' da co nguoi dat trong khoang thoi gian nay!");
                return false;
            }
            if (p.getGiaPhong() != null) {
                tongGiaNgay = tongGiaNgay.add(p.getGiaPhong());
            }
        }

        dp.setMaDatPhong(dp.getMaDatPhong().trim().toUpperCase());
        if (dp.getGiaPhongNgay() == null || dp.getGiaPhongNgay().signum() <= 0) {
            dp.setGiaPhongNgay(tongGiaNgay);
        }
        if (dp.getNgayDat() == null) {
            dp.setNgayDat(LocalDate.now());
        }
        if (dp.getTrangThai() == null) {
            dp.setTrangThai(TrangThaiDatPhong.DA_DAT);
        }

        boolean kq = datPhongDAO.them(dp);
        if (kq) {
            for (String maPhong : dsPhong) {
                Phong p = phongDAO.timPhongTheoMa(maPhong);
                if (p != null && p.getTinhTrang() == TinhTrangPhong.TRONG) {
                    p.setTinhTrang(TinhTrangPhong.DA_DAT);
                    phongDAO.suaPhong(p.getMaPhong(), p);
                }
            }
            System.out.println("Dat phong thanh cong! Tong tien du kien (" + dp.getSoLuongPhong()
                    + " phong, " + dp.getSoNgayO() + " ngay): " + dp.tinhTienPhong() + " VND");
        } else {
            System.out.println("Dat phong that bai (Trung ma phieu hoac loi luu file)!");
        }
        return kq;
    }

    public boolean suaDatPhong(DatPhong dpMoi) {
        if (dpMoi == null || dpMoi.getMaDatPhong() == null) {
            System.out.println("Loi: Thong tin dat phong khong hop le!");
            return false;
        }
        DatPhong dpCu = datPhongDAO.timTheoMa(dpMoi.getMaDatPhong());
        if (dpCu == null) {
            System.out.println("Loi: Khong tim thay ma dat phong!");
            return false;
        }
        if (dpCu.getTrangThai() == TrangThaiDatPhong.DA_HUY || dpCu.getTrangThai() == TrangThaiDatPhong.DA_TRA) {
            System.out.println("Loi: Khong the sua phieu dat phong da huy hoac da tra!");
            return false;
        }
        if (dpMoi.getMaKhachHang() == null || khachHangDAO.timTheoMa(dpMoi.getMaKhachHang()) == null) {
            System.out.println("Loi: Ma khach hang '" + dpMoi.getMaKhachHang() + "' khong ton tai!");
            return false;
        }
        if (!dpMoi.isNgayHopLe()) {
            System.out.println("Loi: Ngay nhan hoac ngay tra phong khong hop le!");
            return false;
        }
        String[] dsPhongMoi = dpMoi.getDanhSachMaPhong();
        if (dsPhongMoi.length == 0) {
            System.out.println("Loi: Phieu dat phong phai co it nhat 1 phong!");
            return false;
        }

        BigDecimal tongGiaNgay = BigDecimal.ZERO;
        for (String maPhong : dsPhongMoi) {
            Phong p = phongDAO.timPhongTheoMa(maPhong);
            if (p == null) {
                System.out.println("Loi: Phong '" + maPhong + "' khong ton tai!");
                return false;
            }
            if (p.getTinhTrang() == TinhTrangPhong.DANG_BAO_TRI) {
                System.out.println("Loi: Phong '" + maPhong + "' dang bao tri!");
                return false;
            }
            if (!datPhongDAO.kiemTraPhongTrong(maPhong, dpMoi.getNgayNhanPhong(), dpMoi.getNgayTraPhong(), dpMoi.getMaDatPhong())) {
                System.out.println("Loi: Phong '" + maPhong + "' bi trung lich trong khoang thoi gian moi!");
                return false;
            }
            if (p.getGiaPhong() != null) {
                tongGiaNgay = tongGiaNgay.add(p.getGiaPhong());
            }
        }

        String[] dsPhongCu = dpCu.getDanhSachMaPhong();
        dpMoi.setGiaPhongNgay(tongGiaNgay);
        boolean ok = datPhongDAO.sua(dpMoi);
        if (ok) {
            for (String maCu : dsPhongCu) {
                if (!dpMoi.coChuaPhong(maCu)) {
                    capNhatLaiTrangThaiPhong(maCu, dpMoi.getMaDatPhong());
                }
            }
            TinhTrangPhong ttMucTieu = dpMoi.getTrangThai() == TrangThaiDatPhong.DANG_SU_DUNG
                    ? TinhTrangPhong.DANG_SU_DUNG
                    : TinhTrangPhong.DA_DAT;
            for (String maMoi : dsPhongMoi) {
                Phong p = phongDAO.timPhongTheoMa(maMoi);
                if (p != null && (p.getTinhTrang() == TinhTrangPhong.TRONG || ttMucTieu == TinhTrangPhong.DANG_SU_DUNG)) {
                    p.setTinhTrang(ttMucTieu);
                    phongDAO.suaPhong(p.getMaPhong(), p);
                }
            }
        }
        return ok;
    }

    public boolean huyDatPhong(String maDatPhong) {
        DatPhong dp = datPhongDAO.timTheoMa(maDatPhong);
        if (dp == null) {
            System.out.println("Khong tim thay ma dat phong!");
            return false;
        }
        if (dp.getTrangThai() == TrangThaiDatPhong.DA_HUY) {
            System.out.println("Phieu dat phong nay da duoc huy truoc do!");
            return false;
        }
        if (dp.getTrangThai() == TrangThaiDatPhong.DA_TRA) {
            System.out.println("Khong the huy phieu dat phong da tra phong!");
            return false;
        }
        DatPhong capNhat = new DatPhong(dp);
        capNhat.setTrangThai(TrangThaiDatPhong.DA_HUY);
        if (!datPhongDAO.sua(capNhat)) {
            System.out.println("Huy dat phong that bai khi luu file!");
            return false;
        }
        for (String maPhong : capNhat.getDanhSachMaPhong()) {
            capNhatLaiTrangThaiPhong(maPhong, capNhat.getMaDatPhong());
        }
        System.out.println("Da huy dat phong thanh cong!");
        return true;
    }

    public boolean nhanPhong(String maDatPhong, LocalDate ngayNhanThucTe) {
        DatPhong dp = datPhongDAO.timTheoMa(maDatPhong);
        if (dp == null || dp.getTrangThai() != TrangThaiDatPhong.DA_DAT) {
            System.out.println("Loi: Phieu dat phong khong ton tai hoac khong o trang thai DA_DAT!");
            return false;
        }
        if (ngayNhanThucTe == null || (dp.getNgayTraPhong() != null && ngayNhanThucTe.isAfter(dp.getNgayTraPhong()))) {
            System.out.println("Loi: Ngay nhan phong thuc te khong hop le!");
            return false;
        }
        DatPhong capNhat = new DatPhong(dp);
        capNhat.setNgayNhanPhong(ngayNhanThucTe);
        capNhat.setTrangThai(TrangThaiDatPhong.DANG_SU_DUNG);
        if (!datPhongDAO.sua(capNhat)) {
            System.out.println("Loi: Khong the luu trang thai nhan phong!");
            return false;
        }
        for (String maPhong : capNhat.getDanhSachMaPhong()) {
            Phong p = phongDAO.timPhongTheoMa(maPhong);
            if (p != null) {
                p.setTinhTrang(TinhTrangPhong.DANG_SU_DUNG);
                phongDAO.suaPhong(p.getMaPhong(), p);
            }
        }
        return true;
    }

    public boolean traPhong(String maDatPhong) {
        DatPhong dp = datPhongDAO.timTheoMa(maDatPhong);
        if (dp == null) {
            return false;
        }
        if (dp.getTrangThai() == TrangThaiDatPhong.DA_HUY || dp.getTrangThai() == TrangThaiDatPhong.DA_TRA) {
            return false;
        }
        DatPhong capNhat = new DatPhong(dp);
        capNhat.setTrangThai(TrangThaiDatPhong.DA_TRA);
        if (!datPhongDAO.sua(capNhat)) {
            return false;
        }
        for (String maPhong : capNhat.getDanhSachMaPhong()) {
            capNhatLaiTrangThaiPhong(maPhong, capNhat.getMaDatPhong());
        }
        return true;
    }

    private void capNhatLaiTrangThaiPhong(String maPhong, String maDatPhongBoQua) {
        Phong p = phongDAO.timPhongTheoMa(maPhong);
        if (p == null || p.getTinhTrang() == TinhTrangPhong.DANG_BAO_TRI) {
            return;
        }
        boolean dangSuDung = false;
        boolean daDat = false;
        for (DatPhong other : datPhongDAO.layDanhSach()) {
            if (other == null) {
                continue;
            }
            if (maDatPhongBoQua != null && other.getMaDatPhong().equalsIgnoreCase(maDatPhongBoQua)) {
                continue;
            }
            if (other.coChuaPhong(maPhong)) {
                if (other.getTrangThai() == TrangThaiDatPhong.DANG_SU_DUNG) {
                    dangSuDung = true;
                } else if (other.getTrangThai() == TrangThaiDatPhong.DA_DAT) {
                    daDat = true;
                }
            }
        }
        if (dangSuDung) {
            p.setTinhTrang(TinhTrangPhong.DANG_SU_DUNG);
        } else if (daDat) {
            p.setTinhTrang(TinhTrangPhong.DA_DAT);
        } else {
            p.setTinhTrang(TinhTrangPhong.TRONG);
        }
        phongDAO.suaPhong(p.getMaPhong(), p);
    }

    public DatPhong timDatPhong(String maDatPhong) {
        return datPhongDAO.timTheoMa(maDatPhong);
    }

    public DatPhong[] layTatCa() {
        return datPhongDAO.layDanhSach();
    }
}
