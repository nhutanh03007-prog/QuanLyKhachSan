package ui;

import java.util.Scanner;
import model.TinhTrangPhong;
import model.TrangThaiThanhToan;
import service.QLDichVu;
import service.QLHoaDon;
import service.QLKhachHang;
import service.QLPhong;
import service.QLThongKe;

public class ThongKeUI {

    private final Scanner sc = new Scanner(System.in);
    private final QLThongKe qlThongKe;

    public ThongKeUI() {
        // Sửa QLThanhToan() thành QLHoaDon() cho đúng thứ tự tham số constructor
        this.qlThongKe = new QLThongKe(
                new QLHoaDon(),
                new QLPhong(),
                new QLKhachHang(),
                new QLDichVu()
        );
    }

    // Constructor mở rộng nếu muốn truyền sẵn đối tượng QLThongKe từ ngoài vào
    public ThongKeUI(QLThongKe qlThongKe) {
        this.qlThongKe = qlThongKe;
    }

    public void menuThongKe() {
        int chon;

        do {
            System.out.println("\n===== MENU THỐNG KÊ =====");
            System.out.println("1. Thống kê phòng");
            System.out.println("2. Thống kê khách hàng");
            System.out.println("3. Thống kê dịch vụ");
            System.out.println("4. Thống kê hóa đơn / doanh thu");
            System.out.println("5. Báo cáo tổng hợp");
            System.out.println("0. Quay lại");
            System.out.print("Nhập lựa chọn: ");

            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
                chon = -1;
                continue;
            }

            switch (chon) {
                case 1 -> thongKePhong();
                case 2 -> thongKeKhachHang();
                case 3 -> thongKeDichVu();
                case 4 -> thongKeHoaDon();
                case 5 -> baoCaoTongHop();
                case 0 -> System.out.println("Quay lại menu!");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }

        } while (chon != 0);
    }

    private void thongKePhong() {
        System.out.println("\n--- THỐNG KÊ PHÒNG ---");
        System.out.println("Tổng số phòng: " + qlThongKe.tongSoPhong());
        for (TinhTrangPhong tt : TinhTrangPhong.values()) {
            System.out.println("- Phòng trạng thái [" + tt + "]: " + qlThongKe.demPhongTheoTrangThai(tt));
        }
    }

    private void thongKeKhachHang() {
        System.out.println("\n--- THỐNG KÊ KHÁCH HÀNG ---");
        System.out.print("Nhập loại khách hàng cần thống kê (VD: VIP, Thuong): ");
        String loaiKhach = sc.nextLine();
        System.out.println("Số lượng khách hàng loại [" + loaiKhach + "]: " + qlThongKe.demKhachHangTheoLoai(loaiKhach));
    }

    private void thongKeDichVu() {
        System.out.println("\n--- THỐNG KÊ DỊCH VỤ ---");
        System.out.println("Tổng số dịch vụ đang hoạt động: " + qlThongKe.tongSoDichVuHoatDong());
    }

    private void thongKeHoaDon() {
        System.out.println("\n--- THỐNG KÊ HÓA ĐƠN ---");
        for (TrangThaiThanhToan tt : TrangThaiThanhToan.values()) {
            System.out.println("- Hóa đơn [" + tt + "]: " + qlThongKe.demHoaDonTheoTrangThai(tt));
        }
    }

    private void baoCaoTongHop() {
        System.out.println("\n========= BÁO CÁO TỔNG HỢP =========");
        thongKePhong();
        thongKeDichVu();
        thongKeHoaDon();
    }
}