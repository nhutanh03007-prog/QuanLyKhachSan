package service;

public class QLThongKe {
    private QLThanhToan qlThanhToan;
    private QLPhong qlPhong;
    private QLKhachHang qlKhachHang;
    private QLDichVu qlDichVu;

    public QLThongKe(QLThanhToan qlThanhToan, QLPhong qlPhong, QLKhachHang qlKhachHang, QLDichVu qlDichVu) {
        this.qlThanhToan = qlThanhToan;
        this.qlPhong = qlPhong;
        this.qlKhachHang = qlKhachHang;
        this.qlDichVu = qlDichVu;
    }

    // Tổng hợp doanh thu từ danh sách hóa đơn thanh toán
    public double tinhTongDoanhThu() {
        double tong = 0;
        // Gọi phương thức lấy danh sách hóa đơn từ QLThanhToan của thành viên trong nhóm
        var dsHoaDon = qlThanhToan.getDanhSach(); 
        if (dsHoaDon != null) {
            for (var hd : dsHoaDon) {
                if (hd != null) {
                    tong += hd.getTongTien();
                }
            }
        }
        return tong;
    }

    // Thống kê số lượng phòng theo trạng thái (VD: "Trống", "Đang thuê", "Bảo trì")
    public int demPhongTheoTrangThai(String trangThai) {
        int count = 0;
        var dsPhong = qlPhong.getDanhSach();
        if (dsPhong != null) {
            for (var p : dsPhong) {
                if (p != null && p.getTrangThai().equalsIgnoreCase(trangThai)) {
                    count++;
                }
            }
        }
        return count;
    }

    public int tongSoKhachHang() {
        var dsKH = qlKhachHang.getDanhSach();
        return dsKH != null ? dsKH.length : 0;
    }
}