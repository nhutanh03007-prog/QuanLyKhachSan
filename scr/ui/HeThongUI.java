package ui;

import java.util.Scanner;
import model.NhanVien;
import model.TaiKhoan;
import service.QLDichVu;
import service.QLHoaDon;
import service.QLKhachHang;
import service.QLNhanVien;
import service.QLPhong;
import service.QLTaiKhoan;
import service.QLThongKe;

public class HeThongUI {
    private final Scanner scanner;
    private final QLTaiKhoan qlTaiKhoan;
    private final QLNhanVien qlNhanVien;
    private final QLThongKe qlThongKe;
    
    // Lưu thông tin người dùng đang đăng nhập
    private TaiKhoan taiKhoanHienTai; 

    public HeThongUI() {
        this.scanner = new Scanner(System.in);
        this.qlTaiKhoan = new QLTaiKhoan();
        this.qlNhanVien = new QLNhanVien();
        
        // Khởi tạo 4 service thành phần để truyền đúng vào constructor của QLThongKe
        QLHoaDon qlHoaDon = new QLHoaDon();
        QLPhong qlPhong = new QLPhong();
        QLKhachHang qlKhachHang = new QLKhachHang();
        QLDichVu qlDichVu = new QLDichVu();
        this.qlThongKe = new QLThongKe(qlHoaDon, qlPhong, qlKhachHang, qlDichVu);
    }

    // Constructor mở rộng nếu MAINNE.java đã khởi tạo sẵn các service dùng chung
    public HeThongUI(QLTaiKhoan qlTaiKhoan, QLNhanVien qlNhanVien, QLThongKe qlThongKe) {
        this.scanner = new Scanner(System.in);
        this.qlTaiKhoan = qlTaiKhoan;
        this.qlNhanVien = qlNhanVien;
        this.qlThongKe = qlThongKe;
    }

    // 1. Module Tài Khoản: Màn hình đăng nhập
    public boolean dangNhap() {
        System.out.println("=== ĐĂNG NHẬP HỆ THỐNG KHÁCH SẠN ===");
        System.out.print("Tên đăng nhập: ");
        String username = scanner.nextLine();
        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine();

        TaiKhoan tk = qlTaiKhoan.kiemTraDangNhap(username, password);
        
        if (tk != null) {
            if ("Khóa".equalsIgnoreCase(tk.getTrangThai()) || "Khoa".equalsIgnoreCase(tk.getTrangThai())) {
                System.out.println("Tài khoản này đã bị khóa! Vui lòng liên hệ Admin.");
                return false;
            }
            this.taiKhoanHienTai = tk;
            System.out.println("Đăng nhập thành công! Xin chào: " + tk.getTenDangNhap() + " (" + tk.getVaiTro() + ")");
            return true;
        } else {
            System.out.println("Sai tên đăng nhập hoặc mật khẩu.");
            return false;
        }
    }

    // 2. Menu Quản lý hệ thống chính
    public void menuQuanLyHeThong() {
        int choice = -1;
        do {
            System.out.println("\n=== QUẢN LÝ HỆ THỐNG & NHÂN SỰ ===");
            System.out.println("1. Quản lý Nhân viên (Thêm/Sửa/Xóa/Tìm kiếm)");
            System.out.println("2. Quản lý Tài khoản (Thêm/Đổi MK/Xóa/Tìm kiếm)");
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
                case 2 -> menuTaiKhoan(); 
                case 3 -> menuThongKe();  
                case 0 -> System.out.println("Đang thoát chức năng Quản trị...");
                default -> System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    private void inTieuDeNhanVien() {
        System.out.printf("%-10s | %-24s | %-8s | %-12s | %-16s | %-16s | %-14s | %12s | %-12s%n",
                "Mã NV", "Họ Tên", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "SĐT", "Chức Vụ", "Lương", "Trạng Thái");
        System.out.println("-".repeat(145));
    }

    // 3. Menu con: Quản lý Nhân viên
    private void menuNhanVien() {
        int choice = -1;
        do {
            System.out.println("\n--- QUẢN LÝ NHÂN VIÊN ---");
            System.out.println("1. Xem danh sách nhân viên");
            System.out.println("2. Thêm nhân viên mới");
            System.out.println("3. Cập nhật thông tin nhân viên");
            System.out.println("4. Xóa nhân viên");
            System.out.println("5. Tìm kiếm nhân viên theo mã");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println(">> Danh sách nhân viên:");
                    NhanVien[] ds = qlNhanVien.getDanhSach();
                    if (ds.length == 0) {
                        System.out.println("Chưa có nhân viên nào trong hệ thống.");
                    } else {
                        inTieuDeNhanVien();
                        for (NhanVien nv : ds) {
                            System.out.println(nv.toString());
                        }
                    }
                }
                case 2 -> {
                    System.out.println(">> Thêm nhân viên mới:");
                    System.out.print("Nhập mã NV (VD: NV01): ");
                    String maNV = scanner.nextLine();
                    if (qlNhanVien.timTheoMa(maNV) != null) {
                        System.out.println("Thất bại: Mã nhân viên đã tồn tại!");
                        break;
                    }

                    System.out.print("Nhập họ tên: ");
                    String hoTen = scanner.nextLine();
                    System.out.print("Nhập giới tính (Nam/Nữ): ");
                    String gioiTinh = scanner.nextLine();
                    System.out.print("Nhập ngày sinh (dd/MM/yyyy): ");
                    String ngaySinh = scanner.nextLine();
                    System.out.print("Nhập địa chỉ: ");
                    String diaChi = scanner.nextLine();
                    System.out.print("Nhập số điện thoại: ");
                    String sdt = scanner.nextLine();
                    System.out.print("Nhập chức vụ (Quản lý/Lễ tân/Phục vụ...): ");
                    String chucVu = scanner.nextLine();
                    
                    double luong = 0;
                    while (true) {
                        try {
                            System.out.print("Nhập lương cơ bản: ");
                            luong = Double.parseDouble(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Lương phải là số! Vui lòng nhập lại.");
                        }
                    }
                    String trangThai = "Đang làm";

                    NhanVien nvMoi = new NhanVien(maNV, hoTen, gioiTinh, ngaySinh, diaChi, sdt, chucVu, luong, trangThai);
                    if (qlNhanVien.them(nvMoi)) {
                        System.out.println("Thêm nhân viên thành công!");
                    } else {
                        System.out.println("Thêm nhân viên thất bại!");
                    }
                }
                case 3 -> {
                    System.out.println(">> Cập nhật thông tin nhân viên:");
                    System.out.print("Nhập mã NV cần sửa: ");
                    String maNV = scanner.nextLine();
                    NhanVien nvCu = qlNhanVien.timTheoMa(maNV);

                    if (nvCu == null) {
                        System.out.println("Không tìm thấy nhân viên có mã: " + maNV);
                        break;
                    }

                    System.out.println("Thông tin hiện tại:");
                    inTieuDeNhanVien();
                    System.out.println(nvCu.toString());

                    System.out.print("Nhập họ tên mới: ");
                    String hoTen = scanner.nextLine();
                    System.out.print("Nhập giới tính mới: ");
                    String gioiTinh = scanner.nextLine();
                    System.out.print("Nhập ngày sinh mới: ");
                    String ngaySinh = scanner.nextLine();
                    System.out.print("Nhập địa chỉ mới: ");
                    String diaChi = scanner.nextLine();
                    System.out.print("Nhập SĐT mới: ");
                    String sdt = scanner.nextLine();
                    System.out.print("Nhập chức vụ mới: ");
                    String chucVu = scanner.nextLine();

                    double luong = 0;
                    while (true) {
                        try {
                            System.out.print("Nhập lương mới: ");
                            luong = Double.parseDouble(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Lương phải là số! Vui lòng nhập lại.");
                        }
                    }
                    System.out.print("Nhập trạng thái (Đang làm / Nghỉ việc): ");
                    String trangThai = scanner.nextLine();

                    NhanVien nvCapNhat = new NhanVien(maNV, hoTen, gioiTinh, ngaySinh, diaChi, sdt, chucVu, luong, trangThai);
                    if (qlNhanVien.sua(nvCapNhat)) {
                        System.out.println("Cập nhật thông tin nhân viên thành công!");
                    } else {
                        System.out.println("Cập nhật thất bại!");
                    }
                }
                case 4 -> {
                    System.out.println(">> Xóa nhân viên:");
                    System.out.print("Nhập mã NV cần xóa: ");
                    String maNV = scanner.nextLine();

                    if (qlNhanVien.xoa(maNV)) {
                        System.out.println("Xóa nhân viên thành công!");
                    } else {
                        System.out.println("Không tìm thấy mã nhân viên cần xóa!");
                    }
                }
                case 5 -> {
                    System.out.println(">> Tìm kiếm nhân viên:");
                    System.out.print("Nhập mã NV cần tìm: ");
                    String maNV = scanner.nextLine();
                    NhanVien kq = qlNhanVien.timTheoMa(maNV);

                    if (kq != null) {
                        inTieuDeNhanVien();
                        System.out.println(kq.toString());
                    } else {
                        System.out.println("Không tìm thấy nhân viên có mã: " + maNV);
                    }
                }
                case 0 -> System.out.println("Quay lại menu Quản trị...");
                default -> System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    private void hienThiThongTinTaiKhoan(TaiKhoan tk) {
        System.out.printf("Tên ĐN: %-15s | Mã NV: %-8s | Vai trò: %-10s | Trạng thái: %s%n",
                tk.getTenDangNhap(), tk.getMaNV(), tk.getVaiTro(), tk.getTrangThai());
    }

    // 4. Menu con: Quản lý Tài khoản
    private void menuTaiKhoan() {
        int choice = -1;
        do {
            System.out.println("\n--- QUẢN LÝ TÀI KHOẢN ---");
            System.out.println("1. Xem danh sách tài khoản");
            System.out.println("2. Tạo tài khoản mới");
            System.out.println("3. Đổi mật khẩu");
            System.out.println("4. Xóa tài khoản");
            System.out.println("5. Tìm kiếm tài khoản");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println(">> Danh sách tài khoản:");
                    TaiKhoan[] ds = qlTaiKhoan.getDanhSach();
                    if (ds.length == 0) {
                        System.out.println("Chưa có tài khoản nào trong hệ thống.");
                    } else {
                        for (TaiKhoan tk : ds) {
                            hienThiThongTinTaiKhoan(tk);
                        }
                    }
                }
                case 2 -> {
                    System.out.println(">> Tạo tài khoản mới:");
                    System.out.print("Nhập tên đăng nhập: ");
                    String tenDN = scanner.nextLine();
                    System.out.print("Nhập mật khẩu: ");
                    String matKhau = scanner.nextLine();
                    System.out.print("Nhập mã nhân viên (VD: NV01): ");
                    String maNV = scanner.nextLine();
                    System.out.print("Nhập vai trò (VD: Admin, LeTan): ");
                    String vaiTro = scanner.nextLine();
                    String trangThai = "Hoạt động";

                    TaiKhoan tkMoi = new TaiKhoan(tenDN, matKhau, maNV, vaiTro, trangThai);
                    if (qlTaiKhoan.them(tkMoi)) {
                        System.out.println("Thêm tài khoản thành công!");
                    } else {
                        System.out.println("Thất bại: Tên đăng nhập đã tồn tại!");
                    }
                }
                case 3 -> {
                    System.out.println(">> Đổi mật khẩu:");
                    System.out.print("Nhập tên đăng nhập cần đổi mật khẩu: ");
                    String tenDN = scanner.nextLine();
                    System.out.print("Nhập mật khẩu mới: ");
                    String mkMoi = scanner.nextLine();

                    if (qlTaiKhoan.doiMatKhau(tenDN, mkMoi)) {
                        System.out.println("Đổi mật khẩu thành công!");
                    } else {
                        System.out.println("Không tìm thấy tên đăng nhập!");
                    }
                }
                case 4 -> {
                    System.out.println(">> Xóa tài khoản:");
                    System.out.print("Nhập tên đăng nhập cần xóa: ");
                    String tenDN = scanner.nextLine();

                    if (taiKhoanHienTai != null && taiKhoanHienTai.getTenDangNhap().equalsIgnoreCase(tenDN)) {
                        System.out.println("Không thể tự xóa tài khoản đang đăng nhập!");
                    } else if (qlTaiKhoan.xoa(tenDN)) {
                        System.out.println("Xóa tài khoản thành công!");
                    } else {
                        System.out.println("Không tìm thấy tên đăng nhập cần xóa!");
                    }
                }
                case 5 -> {
                    System.out.println(">> Tìm kiếm tài khoản:");
                    System.out.print("Nhập tên đăng nhập cần tìm: ");
                    String tenDN = scanner.nextLine();
                    TaiKhoan kq = qlTaiKhoan.timTheoTenDN(tenDN);

                    if (kq != null) {
                        System.out.println("Tìm thấy tài khoản:");
                        hienThiThongTinTaiKhoan(kq);
                    } else {
                        System.out.println("Không tìm thấy tài khoản khớp với: " + tenDN);
                    }
                }
                case 0 -> System.out.println("Quay lại menu Quản trị...");
                default -> System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    // 5. Menu con: Báo cáo Thống kê
    private void menuThongKe() {
        int choice = -1;
        do {
            System.out.println("\n--- BÁO CÁO THỐNG KÊ ---");
            System.out.println("1. Thống kê tổng quan (Phòng & Dịch vụ)");
            System.out.println("2. Thống kê phòng theo tình trạng");
            System.out.println("3. Thống kê hóa đơn theo trạng thái");
            System.out.println("4. Thống kê khách hàng theo loại");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println(">> Thống kê tổng quan:");
                    System.out.println("- Tổng số phòng: " + qlThongKe.tongSoPhong());
                    System.out.println("- Dịch vụ đang hoạt động: " + qlThongKe.tongSoDichVuHoatDong());
                }
                case 2 -> System.out.println(">> Thống kê phòng theo tình trạng (Đang cập nhật)...");
                case 3 -> System.out.println(">> Thống kê hóa đơn theo trạng thái (Đang cập nhật)...");
                case 4 -> System.out.println(">> Thống kê khách hàng theo loại (Đang cập nhật)...");
                case 0 -> System.out.println("Quay lại menu Quản trị...");
                default -> System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }
}