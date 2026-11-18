<<<<<<< HEAD
import ui.PhongUI;
    public class Main {
import ui.HeThongUI;
import ui.PhongUI;

        public static void main(String[] args) {

            PhongUI phongUI = new PhongUI();

            phongUI.menuPhong();
        }
    }

public class MAINNE{
    public static void main(String[] args) {
        // Khởi tạo giao diện quản lý hệ thống của bạn
        HeThongUI heThongUI = new HeThongUI();
        
        // 1. Yêu cầu đăng nhập đầu tiên
        boolean isLogged = heThongUI.dangNhap();
        
        // 2. Nếu đăng nhập thành công thì mới chia nhánh menu
        if (isLogged) {
            // Sau này bạn sẽ lấy quyền thực tế từ tài khoản đăng nhập
            // Ví dụ: String role = heThongUI.getTaiKhoanHienTai().getQuyen();
            String role = "ADMIN"; // Tạm thời gán cứng để test

            if (role.equals("ADMIN")) {
                // Mở menu Quản lý Nhân viên & Thống kê của bạn
                heThongUI.menuQuanLyHeThong(); 
            } else if (role.equals("NHAN_VIEN")) {
                // Chỗ này sau sẽ khởi tạo PhongUI
                System.out.println("Chuyển hướng sang giao diện Lễ tân / Phòng...");
                PhongUI phongUI = new PhongUI();
                phongUI.menuPhong();
            }
        } else {
            System.out.println("Đăng nhập thất bại. Chương trình kết thúc.");
        }
    }
}
=======
import ui.HeThongUI;
import ui.PhongUI;
import Database.DatabaseManager;

import java.util.Scanner;

public class MAINNE {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // 1. Khoi tao thu muc du lieu
        DatabaseManager.khoiTaoDatabase();

        // 2. Khoi tao UI
        HeThongUI heThongUI = new HeThongUI();

        // 3. Dang nhap
        boolean dangNhap = heThongUI.dangNhap();

        if (!dangNhap) {
            System.out.println("Dang nhap that bai!");
            return;
        }

        // 4. Lay quyen (tam thoi hard-code de test)
        String role = "ADMIN";

        // 5. Chuyen den menu theo quyen
        switch (role) {

            case "ADMIN":
                menuAdmin(heThongUI);
                break;

            case "NHAN_VIEN":
                menuNhanVien();
                break;

            default:
                System.out.println("Quyen khong hop le!");
        }

        System.out.println("Ket thuc chuong trinh.");
    }

    // =============================
    // MENU ADMIN
    // =============================
    private static void menuAdmin(HeThongUI heThongUI) {

        int chon;

        do {
            System.out.println("\n========== MENU ADMIN ==========");
            System.out.println("1. Quan ly he thong");
            System.out.println("2. Quan ly phong");
            System.out.println("0. Dang xuat");
            System.out.println("==============================");

            chon = nhapLuaChon(0, 2);

            switch (chon) {

                case 1:
                    heThongUI.menuQuanLyHeThong();
                    break;

                case 2:
                    PhongUI phongUI = new PhongUI();
                    phongUI.menuPhong();
                    break;

                case 0:
                    System.out.println("Dang xuat ADMIN...");
                    break;
            }

        } while (chon != 0);
    }

    // =============================
    // MENU NHAN VIEN
    // =============================
    private static void menuNhanVien() {

        int chon;

        do {
            System.out.println("\n======= MENU NHAN VIEN =======");
            System.out.println("1. Quan ly phong");
            System.out.println("0. Dang xuat");
            System.out.println("==============================");

            chon = nhapLuaChon(0, 1);

            switch (chon) {

                case 1:
                    PhongUI phongUI = new PhongUI();
                    phongUI.menuPhong();
                    break;

                case 0:
                    System.out.println("Dang xuat...");
                    break;
            }

        } while (chon != 0);
    }

    // =============================
    // NHAP LUA CHON MENU
    // =============================
    private static int nhapLuaChon(int min, int max) {

        int chon;

        while (true) {
            System.out.print("Nhap lua chon: ");

            try {
                chon = Integer.parseInt(sc.nextLine());

                if (chon >= min && chon <= max) {
                    return chon;
                }

                System.out.println("Lua chon khong hop le!");

            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so!");
            }
        }
    }
}
>>>>>>> 0b24b6cdd36638f96925fad2bde0c092ec4d6cf0
