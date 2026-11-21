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
        // Sua QLThanhToan() thanh QLHoaDon() cho dung thu tu tham so constructor
        this.qlThongKe = new QLThongKe(
                new QLHoaDon(),
                new QLPhong(),
                new QLKhachHang(),
                new QLDichVu()
        );
    }

    // Constructor mo rong neu muon truyen san doi tuong QLThongKe tu ngoai vao
    public ThongKeUI(QLThongKe qlThongKe) {
        this.qlThongKe = qlThongKe;
    }

    public void menuThongKe() {
        int chon;

        do {
            System.out.println("\n===== MENU THONG KE =====");
            System.out.println("1. Thong ke phong");
            System.out.println("2. Thong ke khach hang");
            System.out.println("3. Thong ke dich vu");
            System.out.println("4. Thong ke hoa don / doanh thu");
            System.out.println("5. Bao cao tong hop");
            System.out.println("0. Quay lai");
            System.out.print("Nhap lua chon: ");

            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so!");
                chon = -1;
                continue;
            }

            switch (chon) {
                case 1 -> thongKePhong();
                case 2 -> thongKeKhachHang();
                case 3 -> thongKeDichVu();
                case 4 -> thongKeHoaDon();
                case 5 -> baoCaoTongHop();
                case 0 -> System.out.println("Quay lai menu!");
                default -> System.out.println("Lua chon khong hop le!");
            }

        } while (chon != 0);
    }

    private void thongKePhong() {
        System.out.println("\n--- THONG KE PHONG ---");
        System.out.println("Tong so phong: " + qlThongKe.tongSoPhong());
        for (TinhTrangPhong tt : TinhTrangPhong.values()) {
            System.out.println("- Phong trang thai [" + tt + "]: " + qlThongKe.demPhongTheoTrangThai(tt));
        }
    }

    private void thongKeKhachHang() {
        System.out.println("\n--- THONG KE KHACH HANG ---");
        System.out.print("Nhap loai khach hang can thong ke (VD: VIP, Thuong): ");
        String loaiKhach = sc.nextLine();
        System.out.println("So luong khach hang loai [" + loaiKhach + "]: " + qlThongKe.demKhachHangTheoLoai(loaiKhach));
    }

    private void thongKeDichVu() {
        System.out.println("\n--- THONG KE DICH VU ---");
        System.out.println("Tong so dich vu dang hoat dong: " + qlThongKe.tongSoDichVuHoatDong());
    }

    private void thongKeHoaDon() {
        System.out.println("\n--- THONG KE HOA DON ---");
        for (TrangThaiThanhToan tt : TrangThaiThanhToan.values()) {
            System.out.println("- Hoa don [" + tt + "]: " + qlThongKe.demHoaDonTheoTrangThai(tt));
        }
    }

    private void baoCaoTongHop() {
        System.out.println("\n========= BAO CAO TONG HOP =========");
        thongKePhong();
        thongKeDichVu();
        thongKeHoaDon();
    }
}