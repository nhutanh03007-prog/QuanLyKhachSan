package service;

import DAO.DatPhongDAO;
import model.DatPhong;
import model.TrangThaiDatPhong;

public class QLDatPhong {
    private DatPhongDAO datPhongDAO = new DatPhongDAO();

    public boolean taoDatPhong(DatPhong dp) {
        if (dp == null || !dp.isNgayHopLe()) {
            System.out.println("Lỗi: Ngày nhận hoặc ngày trả phòng không hợp lệ!");
            return false;
        }
        // Kiểm tra phòng có trống trong lịch đặt không
        if (!datPhongDAO.kiemTraPhongTrong(dp.getMaPhong(), dp.getNgayNhanPhong(), dp.getNgayTraPhong())) {
            System.out.println("Lỗi: Phòng " + dp.getMaPhong() + " đã có người đặt trong thời gian này!");
            return false;
        }

        boolean kq = datPhongDAO.them(dp);
        if (kq) System.out.println("Đặt phòng thành công! Tổng tiền dự kiến: " + dp.tinhTienPhong());
        else System.out.println("Đặt phòng thất bại (Trùng mã phiếu hoặc đầy danh sách)!");
        return kq;
    }

    public boolean huyDatPhong(String maDatPhong) {
        DatPhong dp = datPhongDAO.timTheoMa(maDatPhong);
        if (dp == null) {
            System.out.println("Không tìm thấy mã đặt phòng!");
            return false;
        }
        dp.setTrangThai(TrangThaiDatPhong.DA_HUY);
        System.out.println("Đã hủy đặt phòng thành công!");
        return true;
    }

    public DatPhong timDatPhong(String maDatPhong) {
        return datPhongDAO.timTheoMa(maDatPhong);
    }

    public DatPhong[] layTatCa() {
        return datPhongDAO.layDanhSach();
    }
}