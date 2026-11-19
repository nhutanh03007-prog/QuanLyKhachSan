package Main;
import Database.DatabaseManager;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import ui.HeThongUI;
import ui.PhongUI;

public class main {

    private static final Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);
    public static void main(String[] args) {

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
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