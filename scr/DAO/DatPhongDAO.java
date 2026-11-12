package DAO;

import model.DatPhong;
import model.TrangThaiDatPhong;
import java.time.LocalDate;


public class DatPhongDAO {
    public static DatPhong[] danhSachDatPhong = new DatPhong[100];
    public static int soLuongDatPhong = 0;

    public boolean them(DatPhong datPhong) {
        if (datPhong == null || datPhong.getMaDatPhong() == null 
            || timTheoMa(datPhong.getMaDatPhong()) != null 
            || soLuongDatPhong == danhSachDatPhong.length) {
            return false;
        }
        danhSachDatPhong[soLuongDatPhong] = datPhong;
        soLuongDatPhong++;
        return true;
    }

    public boolean sua(DatPhong datPhong) {
        if (datPhong == null) return false;
        for (int i = 0; i < soLuongDatPhong; i++) {
            if (danhSachDatPhong[i].getMaDatPhong().equals(datPhong.getMaDatPhong())) {
                danhSachDatPhong[i] = datPhong;
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maDatPhong) {
        for (int i = 0; i < soLuongDatPhong; i++) {
            if (danhSachDatPhong[i].getMaDatPhong().equals(maDatPhong)) {
                System.arraycopy(danhSachDatPhong, i + 1, danhSachDatPhong, i, soLuongDatPhong - i - 1);
                danhSachDatPhong[--soLuongDatPhong] = null;
                return true;
            }
        }
        return false;
    }

    public DatPhong timTheoMa(String maDatPhong) {
        for (int i = 0; i < soLuongDatPhong; i++) {
            if (danhSachDatPhong[i].getMaDatPhong().equals(maDatPhong)) {
                return danhSachDatPhong[i];
            }
        }
        return null;
    }

    /** Kiểm tra xem phòng có trống trong khoảng thời gian dự định đặt hay không */
    public boolean kiemTraPhongTrong(String maPhong, LocalDate ngayNhan, LocalDate ngayTra) {
        for (int i = 0; i < soLuongDatPhong; i++) {
            DatPhong dp = danhSachDatPhong[i];
            // Chỉ kiểm tra các phòng trùng mã và phiếu đặt chưa bị hủy hoặc trả phòng
            if (dp.getMaPhong().equalsIgnoreCase(maPhong) &&
                dp.getTrangThai() != TrangThaiDatPhong.DA_HUY &&
                dp.getTrangThai() != TrangThaiDatPhong.DA_TRA) {
                
                // Nếu khoảng thời gian bị chồng lấp -> Phòng không trống
                if (dp.isTrungLich(ngayNhan, ngayTra)) {
                    return false;
                }
            }
        }
        return true;
    }

    public DatPhong[] layDanhSach() {
        DatPhong[] ketQua = new DatPhong[soLuongDatPhong];
        System.arraycopy(danhSachDatPhong, 0, ketQua, 0, soLuongDatPhong);
        return ketQua;
    }
}