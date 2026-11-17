package ui;

import java.util.Scanner;

public class HeThongUI {

    public HeThongUI() {
        this.scanner = new Scanner(System.in);
    }

    public boolean dangNhap() {
        System.out.println("=== ĐĂNG NHẬP HỆ THỐNG KHÁCH SẠN ===");
        System.out.print("Tên đăng nhập: ");
        String username = scanner.nextLine();
        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine();

        boolean dangNhapThanhCong = true; // Thay bằng: taiKhoanHienTai != null;
        
        if (dangNhapThanhCong) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Sai tên đăng nhập hoặc mật khẩu.");
        }
        return dangNhapThanhCong;
    }

    // 2. Menu chính dành cho Quản lý (Gom Nhân viên & Thống kê)
    public void menuQuanLyHeThong() {
        int choice = -1;
        do {
            System.out.println("\n=== QUẢN LÝ HỆ THỐNG & NHÂN SỰ ===");
            System.out.println("1. Quản lý Nhân viên (Thêm/Sửa/Xóa/Tìm kiếm)");
            System.out.println("2. Quản lý Tài khoản (Cấp quyền/Khóa tài khoản)");
            System.out.println("3. Xem Báo cáo Thống kê");
            System.out.println("0. Quay lại Menu chính / Đăng xuất");
            System.out.print("Chọn chức năng: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ.");
                continue;
            }

            switch (choice) {
                case 1 -> menuNhanVien();
                case 2 -> System.out.println("Đang mở chức năng Quản lý Tài khoản...");
                // Gọi hàm xử lý Tài khoản
                case 3 -> System.out.println("Đang mở chức năng Thống kê...");
                // Gọi hàm hiển thị Thống kê
                case 0 -> System.out.println("Đang thoát chức năng Quản trị...");
                default -> System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    // 3. Sub-menu cho module Nhân Viên
    private void menuNhanVien() {
        System.out.println("\n--- QUẢN LÝ NHÂN VIÊN ---");
        System.out.println("1. Thêm nhân viên mới");
        System.out.println("2. Xem danh sách nhân viên");
        System.out.println("3. Cập nhật thông tin nhân viên");
        // Gọi các hàm tương ứng từ NhanVienDAO
        System.out.println("(Các chức năng đang được xây dựng...)");
    }
}