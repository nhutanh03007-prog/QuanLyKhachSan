package ui;

import service.QLThongKe;
import service.QLThanhToan;
import service.QLPhong;
import service.QLKhachHang;
import service.QLDichVu;

import java.util.Scanner;

public class ThongKeUI {

    private final Scanner sc = new Scanner(System.in);

    private final QLThongKe qlThongKe;

    public ThongKeUI() {

        qlThongKe = new QLThongKe(
                new QLThanhToan(),
                new QLPhong(),
                new QLKhachHang(),
                new QLDichVu()
        );
    }

    public void menuThongKe() {

        int chon;

        do {
            System.out.println("\n===== MENU THONG KE =====");
            System.out.println("1. Thong ke phong");
            System.out.println("2. Thong ke khach hang");
            System.out.println("3. Thong ke dich vu");
            System.out.println("4. Thong ke doanh thu");
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

                case 1:
                    qlThongKe.thongKePhong();
                    break;

                case 2:
                    qlThongKe.thongKeKhachHang();
                    break;

                case 3:
                    qlThongKe.thongKeDichVu();
                    break;

                case 4:
                    qlThongKe.thongKeDoanhThu();
                    break;

                case 5:
                    qlThongKe.baoCaoTongHop();
                    break;

                case 0:
                    System.out.println("Quay lai menu!");
                    break;

                default:
                    System.out.println("Lua chon khong hop le!");
            }

        } while (chon != 0);
    }
}