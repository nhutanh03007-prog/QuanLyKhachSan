package ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import model.DatPhong;
import service.QLDatPhong;

public class DatPhongUI {
    private final Scanner scanner;
    private final QLDatPhong qlDatPhong;
    private final DateTimeFormatter dateFormatter;

    public DatPhongUI() {
        this.scanner = new Scanner(System.in);
        this.qlDatPhong = new QLDatPhong();
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public void menuDatPhong() {
        int choice = -1;
        do {
            System.out.println("\n===== QUAN LY DAT PHONG =====");
            System.out.println("1. Tao phieu dat phong moi");
            System.out.println("2. Xem danh sach phieu dat phong");
            System.out.println("3. TTim kiem phieu dat phong theo ma");
            System.out.println("4. HHuy phieu dat phong");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap mot so nguyen hop le!");
                continue;
            }

            switch (choice) {
                case 1 -> taoDatPhongMoi();
                case 2 -> hienThiDanhSach();
                case 3 -> timKiemDatPhong();
                case 4 -> huyDatPhong();
                case 0 -> System.out.println("Thoat quan ly dat phong.");
                default -> System.out.println("Loi: Lua chon khong hop le (chi chon tu 0 đen 4)!");
            }
        } while (choice != 0);
    }

    private void taoDatPhongMoi() {
        System.out.println("\n--- TAO PHIEU DAT PHONG MOI ---");

        
        String maDatPhong = nhapChuoiKhongRong("Nhap ma dat phong (VD: DP01): ");
        if (qlDatPhong.timDatPhong(maDatPhong) != null) {
            System.out.println("Loi: Ma dat phong '" + maDatPhong + "' đa ton tai!");
            return;
        }

        String maKhachHang = nhapChuoiKhongRong("Nhap ma khach hang: ");
        String maPhong = nhapChuoiKhongRong("Nhap ma phong: ");

        
        double giaPhong = nhapSoThucDuong("Nhap gia phong/ngay: ");

        
        LocalDate ngayNhan;
        while (true) {
            ngayNhan = nhapNgay("Nhap ngay nhan phong (yyyy-MM-dd): ");
            if (ngayNhan.isBefore(LocalDate.now())) {
                System.out.println("Loi: Ngay nhan phong khong đuoc truoc ngay hom nay (" + LocalDate.now() + ")!");
            } else {
                break;
            }
        }

        
        LocalDate ngayTra;
        while (true) {
            ngayTra = nhapNgay("Nhap ngay tra phong (yyyy-MM-dd): ");
            if (ngayTra.isBefore(ngayNhan)) {
                System.out.println("Loi: Ngay tra phong khong duoc truoc ngay nhan phong (" + ngayNhan + ")!");
            } else {
                break;
            }
        }

        
        DatPhong dp = new DatPhong(maDatPhong, maKhachHang, maPhong, ngayNhan, ngayTra, giaPhong);
        qlDatPhong.taoDatPhong(dp);
    }

    private void hienThiDanhSach() {
        System.out.println("\n--- DANH SACH PHIEU DAT PHONG ---");
        DatPhong[] danhSach = qlDatPhong.layTatCa();
        boolean coDuLieu = false;

        if (danhSach != null) {
            for (DatPhong dp : danhSach) {
                if (dp != null) {
                    System.out.println(dp.toString());
                    coDuLieu = true;
                }
            }
        }

        if (!coDuLieu) {
            System.out.println("Chua co phieu dat phong nao.");
        }
    }

    private void timKiemDatPhong() {
        String ma = nhapChuoiKhongRong("\nNhap ma dat phong can tim: ");
        DatPhong dp = qlDatPhong.timDatPhong(ma);

        if (dp != null) {
            System.out.println("Thong tin phieu dat:");
            System.out.println(dp.toString());
        } else {
            System.out.println("Khong tim thay ma dat phong: " + ma);
        }
    }

    private void huyDatPhong() {
        String ma = nhapChuoiKhongRong("\nNhap ma dat phong can huy: ");
        DatPhong dp = qlDatPhong.timDatPhong(ma);

        if (dp == null) {
            System.out.println("Loi: Khong tim thay phieu dat phong co ma '" + ma + "'!");
            return;
        }

        System.out.print("Ban co chac chan muon huy phieu '" + ma + "' khong? (Y/N): ");
        String xacNhan = scanner.nextLine().trim();
        if (xacNhan.equalsIgnoreCase("Y")) {
            qlDatPhong.huyDatPhong(ma);
        } else {
            System.out.println("Da huy thao tac.");
        }
    }

    // --- CÁC HÀM HỖ TRỢ KIỂM TRA DỮ LIỆU ĐẦU VÀO ---

    private String nhapChuoiKhongRong(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Loi: Du lieu khong duoc de trong! Vui long nhap lai.");
            } else {
                return input;
            }
        }
    }

    private double nhapSoThucDuong(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            try {
                double so = Double.parseDouble(scanner.nextLine().trim());
                if (so <= 0) {
                    System.out.println("Loi: Gia tri phai lon hon 0! Vui long nhap lai.");
                } else {
                    return so;
                }
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap mot so hop le!");
            }
        }
    }

    private LocalDate nhapNgay(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Loi: Sai dinh dang ngay (yeu cau yyyy-MM-dd, VD: 2026-11-20)! Vui long nhap lai.");
            }
        }
    }
}