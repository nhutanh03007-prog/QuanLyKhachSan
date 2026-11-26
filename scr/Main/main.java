package Main;

import DAO.QuanLyDuLieuFile;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
<<<<<<< HEAD
import java.util.Scanner;
=======
import ui.ConsoleInput;
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6
import ui.DatPhongUI;
import ui.HeThongUI;
import ui.KhachHangUI;
import ui.PhongUI;
import ui.ThanhToanUI;
import ui.ThongKeUI;

public class main {

    private static final BufferedReader reader = ConsoleInput.getReader();

    public static void main(String[] args) {

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        // 1. Doc toan bo du lieu tu DATABASEH va nap vao cac DAO
        QuanLyDuLieuFile.napTatCaDuLieu();

        // 2. Khoi tao UI
        HeThongUI heThongUI = new HeThongUI(reader);

        // 3. Dang nhap
        boolean dangNhap = heThongUI.dangNhap();

        if (!dangNhap) {
            System.out.println("Dang nhap that bai!");
            return;
        }

        String role = chuanHoaVaiTro(heThongUI.getVaiTroHienTai());

        switch (role) {
            case "ADMIN" -> menuAdmin(heThongUI);
            case "NHAN_VIEN" -> menuNhanVien();
            default -> System.out.println("Quyen khong hop le: " + heThongUI.getVaiTroHienTai());
        }

        System.out.println("Ket thuc chuong trinh.");
    }

    private static String chuanHoaVaiTro(String vaiTro) {
        if (vaiTro == null) {
            return "";
        }
        String r = vaiTro.trim().toUpperCase().replace(" ", "_");
        if (r.equals("ADMIN") || r.equals("QUAN_LY")) {
            return "ADMIN";
        }
        if (r.equals("NHAN_VIEN") || r.equals("NHANVIEN") || r.equals("LE_TAN") || r.equals("LETAN")) {
            return "NHAN_VIEN";
        }
        return r;
    }

    private static void menuAdmin(HeThongUI heThongUI) {

        int chon;

        do {
            System.out.println("\n========== MENU ADMIN ==========");
            System.out.println("1. Quan ly he thong");
            System.out.println("2. Quan ly phong");
            System.out.println("3. Quan ly khach hang");
            System.out.println("4. Quan ly dat phong");
            System.out.println("5. Quan ly dich vu, hoa don va thanh toan");
            System.out.println("6. Quan ly thong ke");
            System.out.println("0. Dang xuat");
            System.out.println("================================");

            chon = nhapLuaChon(0, 6);

            switch (chon) {
                case 1 -> heThongUI.menuQuanLyHeThong();
                case 2 -> new PhongUI(reader).menuPhong();
                case 3 -> new KhachHangUI(reader).menuKhachHang();
                case 4 -> new DatPhongUI(reader).menuDatPhong();
                case 5 -> new ThanhToanUI(reader).menuThanhToan();
                case 6 -> new ThongKeUI(reader).menuThongKe();
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
            System.out.println("3. Quan ly dich vu, hoa don va thanh toan");
            System.out.println("4. Quan ly thong ke");
            System.out.println("0. Dang xuat");
            System.out.println("==============================");

            chon = nhapLuaChon(0, 4);

            switch (chon) {
                case 1 -> new KhachHangUI(reader).menuKhachHang();
                case 2 -> new DatPhongUI(reader).menuDatPhong();
                case 3 -> new ThanhToanUI(reader).menuThanhToan();
                case 4 -> new ThongKeUI(reader).menuThongKe();
                case 0 -> System.out.println("Dang xuat...");
            }

        } while (chon != 0);
    }

    private static int nhapLuaChon(int min, int max) {

        int chon;

        while (true) {
            System.out.print("Nhap lua chon: ");

            try {
                chon = Integer.parseInt(ConsoleInput.readLine(reader).trim());

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
