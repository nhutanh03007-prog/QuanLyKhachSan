package Main;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import ui.DatPhongUI;
import ui.HeThongUI;
import ui.KhachHangUI;
import ui.PhongUI;
import ui.ThanhToanUI;
import ui.ThongKeUI;

public class main {

    private static final Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);

    public static void main(String[] args) {

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        // 2. Khoi tao UI
        HeThongUI heThongUI = new HeThongUI();

        // 3. Dang nhap
        boolean dangNhap = heThongUI.dangNhap();

        if (!dangNhap) {
            System.out.println("Dang nhap that bai!");
            return;
        }

        String role = "ADMIN";
        // 5. Chuyen den menu theo quyen
        switch (role) {

            case "ADMIN" -> menuAdmin(heThongUI);

            case "NHAN_VIEN" -> menuNhanVien();

            default -> System.out.println("Quyen khong hop le!");
        }

        System.out.println("Ket thuc chuong trinh.");
    }

    private static void menuAdmin(HeThongUI heThongUI) {

        int chon;

        do {
            System.out.println("\n========== MENU ADMIN ==========");
            System.out.println("1. Quan ly he thong");
            System.out.println("2. Quan ly phong");
            System.out.println("3. Quan ly khach hang");
            System.out.println("4. Quan ly dat phong");
            System.out.println("5. Quan ly he thong thanh toan va dich vu");
            System.out.println("6. Quan ly thong ke");
            System.out.println("0. Dang xuat");
            System.out.println("==============================");

            chon = nhapLuaChon(0, 6);

            switch (chon) {

                case 1 -> heThongUI.menuQuanLyHeThong();

                case 2 -> {
                    PhongUI phongUI = new PhongUI();
                    phongUI.menuPhong();
                    break;
                }
                case 3 -> {
                    KhachHangUI khachHangUI = new KhachHangUI();
                    khachHangUI.menuKhachHang();
                    break;
                }
                case 4 -> {
                    DatPhongUI datPhongUI = new DatPhongUI();
                    datPhongUI.menuDatPhong();
                    break;
                }
                case 5 ->{
                    ThanhToanUI thanhToanUI = new ThanhToanUI();
                    thanhToanUI.menuThanhToan();
                    break;
                }
                case 6 -> {
                    ThongKeUI thongKeUI = new ThongKeUI();
                    thongKeUI.menuThongKe();
                    break;
                }

                case 0 -> System.out.println("Dang xuat ADMIN...");
            }

        } while (chon != 0);
    }

    private static void menuNhanVien() {

        int chon;

        do {
            System.out.println("\n======= MENU NHAN VIEN =======");
            System.out.println("1. Quan ly khach hang");
            System.out.println("2. Quan ly dat phong");
            System.out.println("3. Quan ly thanh toan");
            System.out.println("4. Quan ly thong ke");
            System.out.println("0. Dang xuat");
            System.out.println("==============================");

            chon = nhapLuaChon(0, 4);

            switch (chon) {

                case 1 -> {
                    KhachHangUI khachHangUI = new KhachHangUI();
                    khachHangUI.menuKhachHang();
                }
                case 2 -> {
                    DatPhongUI datPhongUI = new DatPhongUI();
                    datPhongUI.menuDatPhong();
                }
                case 3 -> {
                    ThanhToanUI thanhToanUI = new ThanhToanUI();
                    thanhToanUI.menuThanhToan();
                }
                case 4 -> {
                    ThongKeUI thongKeUI = new ThongKeUI();
                    thongKeUI.menuThongKe();
                } 

                case 0 -> System.out.println("Dang xuat...");
            }

        } while (chon != 0);
    }

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