package ui;

import java.io.BufferedReader;
import model.TinhTrangPhong;
import model.TrangThaiThanhToan;
import service.QLDichVu;
import service.QLHoaDon;
import service.QLKhachHang;
import service.QLPhong;
import service.QLThongKe;

public class ThongKeUI {

    private final BufferedReader reader;
    private final QLThongKe qlThongKe;

    public ThongKeUI() {
        this(ConsoleInput.getReader());
    }

    public ThongKeUI(BufferedReader reader) {
        this(reader, new QLThongKe(
                new QLHoaDon(),
                new QLPhong(),
                new QLKhachHang(),
                new QLDichVu()
        ));
    }

    public ThongKeUI(QLThongKe qlThongKe) {
        this(ConsoleInput.getReader(), qlThongKe);
    }

    public ThongKeUI(BufferedReader reader, QLThongKe qlThongKe) {
        this.reader = reader;
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
                chon = Integer.parseInt(ConsoleInput.readLine(reader).trim());
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
            System.out.println("- Phong trang thai [" + tt + "]: "
                    + qlThongKe.demPhongTheoTrangThai(tt));
        }
    }

    private void thongKeKhachHang() {
        System.out.println("\n--- THONG KE KHACH HANG ---");
        System.out.println("Tong so khach hang: " + qlThongKe.tongSoKhachHang());
        System.out.println("- Khach hang [Thuong]: " + qlThongKe.demKhachHangTheoLoai("Thuong"));
        System.out.println("- Khach hang [VIP]   : " + qlThongKe.demKhachHangTheoLoai("VIP"));
    }

    private void thongKeDichVu() {
        System.out.println("\n--- THONG KE DICH VU ---");
        System.out.println("Tong so dich vu dang hoat dong: "
                + qlThongKe.tongSoDichVuHoatDong());
    }

    private void thongKeHoaDon() {
        System.out.println("\n--- THONG KE HOA DON & DOANH THU ---");
        System.out.println("Tong so hoa don: " + qlThongKe.tongSoHoaDon());
        for (TrangThaiThanhToan tt : TrangThaiThanhToan.values()) {
            System.out.println("- Hoa don [" + tt + "]: "
                    + qlThongKe.demHoaDonTheoTrangThai(tt));
        }
        System.out.println("- Doanh thu phong (truoc giam gia)  : " + qlThongKe.tinhTongDoanhThuPhong().toPlainString() + " VND");
        System.out.println("- Doanh thu dich vu (truoc giam gia): " + qlThongKe.tinhTongDoanhThuDichVu().toPlainString() + " VND");
        System.out.println("- Tong giam gia                     : " + qlThongKe.tinhTongGiamGia().toPlainString() + " VND");
        System.out.println("- Tong doanh thu thuc thu           : " + qlThongKe.tinhTongDoanhThu().toPlainString() + " VND");
    }

    private void baoCaoTongHop() {
        System.out.println("\n========= BAO CAO TONG HOP =========");
        thongKePhong();
        thongKeKhachHang();
        thongKeDichVu();
        thongKeHoaDon();
    }
}
