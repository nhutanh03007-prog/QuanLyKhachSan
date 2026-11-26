package service;

import DAO.HoaDonDAO;
import DAO.ThanhToanDAO;
import java.time.LocalDate;
import model.HoaDon;
import model.ThanhToan;
import model.TrangThaiThanhToan;

public class QLThanhToan {
    private final HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private final ThanhToanDAO thanhToanDAO = new ThanhToanDAO();
    private final QLDatPhong qlDatPhong = new QLDatPhong();

    public boolean thanhToan(String maHD, String maThanhToan, String phuongThuc) {
        HoaDon h = hoaDonDAO.timTheoMa(maHD);
        if (h == null || h.getTrangThai() == TrangThaiThanhToan.DA_THANH_TOAN || h.getThanhTien() == null) {
            System.out.println("Loi: Hoa don khong hop le hoac da duoc thanh toan!");
            return false;
        }
        LocalDate ngay = LocalDate.now();
        ThanhToan t = new ThanhToan(maThanhToan, h.getMaHD(), h.getThanhTien(), phuongThuc, ngay,
                TrangThaiThanhToan.DA_THANH_TOAN);
        if (!thanhToanDAO.them(t)) {
            System.out.println("Loi: That bai khi them thanh toan (trung ma giao dich hoac du lieu khong hop le)!");
            return false;
        }
        if (!hoaDonDAO.capNhatTrangThai(h.getMaHD(), TrangThaiThanhToan.DA_THANH_TOAN, phuongThuc, ngay)) {
            thanhToanDAO.xoa(maThanhToan);
            System.out.println("Loi: Khong the cap nhat trang thai hoa don!");
            return false;
        }
        // Tu dong tra phong va giai phong phong neu phieu dat phong chua tra
        qlDatPhong.traPhong(h.getMaDatPhong());
        return true;
    }

    public ThanhToan[] layDanhSachThanhToan() {
        return thanhToanDAO.layDanhSach();
    }

    public ThanhToan timThanhToan(String ma) {
        return thanhToanDAO.timTheoMa(ma);
    }

    public ThanhToan[] layThanhToanTheoHoaDon(String ma) {
        return thanhToanDAO.layTheoMaHD(ma);
    }
}
