package ui;

import java.util.Scanner;
// Import các class của bạn (Giả sử các package tương ứng)
// import model.TaiKhoan;
// import dao.TaiKhoanDAO;
// import dao.NhanVienDAO;
// import model.ThongKe;

public class HeThongUI {
    private Scanner scanner;
    // private TaiKhoanDAO taiKhoanDAO;
    // private NhanVienDAO nhanVienDAO;
    
    // Lưu thông tin người dùng đang đăng nhập
    // private TaiKhoan taiKhoanHienTai; 

    public HeThongUI() {
        this.scanner = new Scanner(System.in);
        // Khởi tạo các DAO của bạn ở đây
        // this.taiKhoanDAO = new TaiKhoanDAO();
        // this.nhanVienDAO = new NhanVienDAO();
    }

    // 1. Module Tài Khoản: Màn hình đăng nhập (Dùng chung cho cả hệ thống)
    public boolean dangNhap() {
        System.out.println("=== ĐĂNG NHẬP HỆ THỐNG KHÁCH SẠN ===");
        System.out.print("Tên đăng nhập: ");
        String username = scanner.nextLine();
        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine();

        // taiKhoanHienTai = taiKhoanDAO.kiemTraDangNhap(username, password);
        
        // Giả lập logic kiểm tra:
        boolean dangNhapThanhCong = true; // Thay bằng: taiKhoanHienTai != null;
        
        if (dangNhapThanhCong) {
            System.out.println("Đăng nhập thành công!");
            // return true;
        } else {
            System.out.println("Sai tên đăng nhập hoặc mật khẩu.");
            // return false;
        }
        return dangNhapThanhCong;
    }

    // 2. Menu chính dành cho Quản lý (Gom Nhân viên & Thống kê của bạn)
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
                case 1:
                    menuNhanVien();
                    break;
                case 2:
                    System.out.println("Đang mở chức năng Quản lý Tài khoản...");
                    // Gọi hàm xử lý Tài khoản
                    break;
                case 3:
                    System.out.println("Đang mở chức năng Thống kê...");
                    // Gọi hàm hiển thị Thống kê
                    break;
                case 0:
                    System.out.println("Đang thoát chức năng Quản trị...");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    // 3. Sub-menu cho module Nhân Viên
    private void menuNhanVien() {
        System.out.println("\n--- QUẢN LÝ NHÂN VIÊN ---");
        System.out.println("1. Thêm nhân viên mới");
        System.out.println("2. Xem danh sách nhân viên");
        System.out.println("3. Cập nhật thông tin nhân viên");
        // Gọi các hàm tương ứng từ NhanVienDAO của bạn
        System.out.println("(Các chức năng đang được xây dựng...)");
    }
}