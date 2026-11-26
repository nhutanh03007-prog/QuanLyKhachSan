package service;

import DAO.DatPhongDAO;
import model.DatPhong;
import model.TrangThaiDatPhong;

public class QLDatPhong {
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();

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
        if (kq) System.out.println("Dat phong thanh cong Tong tien du kien: " + dp.tinhTienPhong());
        else System.out.println("Dat phong that bai (Trung ma phieu hoac day danh sach)!");
        return kq;
    }

    public boolean huyDatPhong(String maDatPhong) {
        DatPhong dp = datPhongDAO.timTheoMa(maDatPhong);
        if (dp == null) {
            System.out.println("Khong tim thay ma dat phong!");
            return false;
        }
        dp.setTrangThai(TrangThaiDatPhong.DA_HUY);
        System.out.println("Da huy dat phong thanh cong!");
        return true;
    }

    public DatPhong timDatPhong(String maDatPhong) {
        return datPhongDAO.timTheoMa(maDatPhong);
    }

    public DatPhong[] layTatCa() {
        return datPhongDAO.layDanhSach();
    }

    public void suaDatPhong(DatPhong dp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}