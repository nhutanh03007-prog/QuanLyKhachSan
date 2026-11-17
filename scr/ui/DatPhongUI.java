package ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import model.DatPhong;
import service.QLDatPhong;

public class DatPhongUI {
    private Scanner scanner;
    private QLDatPhong qlDatPhong;
    private DateTimeFormatter dateFormatter;

    public DatPhongUI() {
        this.scanner = new Scanner(System.in);
        this.qlDatPhong = new QLDatPhong();
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public void menuDatPhong() {
        int choice = -1;
        do {
            System.out.println("\n===== QUẢN LÝ ĐẶT PHÒNG =====");
            System.out.println("1. Tạo phiếu đặt phòng mới");
            System.out.println("2. Xem danh sách phiếu đặt phòng");
            System.out.println("3. Tìm kiếm phiếu đặt phòng theo mã");
            System.out.println("4. Hủy phiếu đặt phòng");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                    taoDatPhongMoi();
                    break;
                case 2:
                    hienThiDanhSach();
                    break;
                case 3:
                    timKiemDatPhong();
                    break;
                case 4:
                    huyDatPhong();
                    break;
                case 0:
                    System.out.println("Thoát quản lý đặt phòng.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choice != 0);
    }

    private void taoDatPhongMoi() {
        System.out.println("\n--- TẠO PHIẾU ĐẶT PHÒNG MỚI ---");
        System.out.print("Nhập mã đặt phòng (VD: DP01): ");
        String maDatPhong = scanner.nextLine();

        System.out.print("Nhập mã khách hàng: ");
        String maKhachHang = scanner.nextLine();

        System.out.print("Nhập mã phòng: ");
        String maPhong = scanner.nextLine();

        System.out.print("Nhập giá phòng/ngày: ");
        double giaPhong;
        try {
            giaPhong = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Lỗi: Giá phòng phải là số!");
            return;
        }

        LocalDate ngayNhan = nhapNgay("Nhập ngày nhận phòng (yyyy-MM-dd): ");
        if (ngayNhan == null) return;

        LocalDate ngayTra = nhapNgay("Nhập ngày trả phòng (yyyy-MM-dd): ");
        if (ngayTra == null) return;

        
        DatPhong dp = new DatPhong(maDatPhong, maKhachHang, maPhong, ngayNhan, ngayTra, giaPhong);
        qlDatPhong.taoDatPhong(dp);
    }

    private void hienThiDanhSach() {
        System.out.println("\n--- DANH SÁCH PHIẾU ĐẶT PHÒNG ---");
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
            System.out.println("Chưa có phiếu đặt phòng nào.");
        }
    }

    private void timKiemDatPhong() {
        System.out.print("\nNhập mã đặt phòng cần tìm: ");
        String ma = scanner.nextLine();

        DatPhong dp = qlDatPhong.timDatPhong(ma);
        if (dp != null) {
            System.out.println("Thông tin phiếu đặt:");
            System.out.println(dp.toString());
        } else {
            System.out.println("Không tìm thấy mã đặt phòng: " + ma);
        }
    }

    private void huyDatPhong() {
        System.out.print("\nNhập mã đặt phòng cần hủy: ");
        String ma = scanner.nextLine();
        qlDatPhong.huyDatPhong(ma);
    }

    private LocalDate nhapNgay(String thongBao) {
        System.out.print(thongBao);
        String input = scanner.nextLine();
        try {
            return LocalDate.parse(input, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Lỗi: Sai định dạng ngày (yêu cầu yyyy-MM-dd)!");
            return null;
        }
    }
}