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
            System.out.println("\n===== QUẢN LÝ ĐẶT PHÒNG =====");
            System.out.println("1. Tạo phiếu đặt phòng mới");
            System.out.println("2. Xem danh sách phiếu đặt phòng");
            System.out.println("3. Tìm kiếm phiếu đặt phòng theo mã");
            System.out.println("4. Hủy phiếu đặt phòng");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1 -> taoDatPhongMoi();
                case 2 -> hienThiDanhSach();
                case 3 -> timKiemDatPhong();
                case 4 -> huyDatPhong();
                case 0 -> System.out.println("Thoát quản lý đặt phòng.");
                default -> System.out.println("Lỗi: Lựa chọn không hợp lệ (chỉ chọn từ 0 đến 4)!");
            }
        } while (choice != 0);
    }

    private void taoDatPhongMoi() {
        System.out.println("\n--- TẠO PHIẾU ĐẶT PHÒNG MỚI ---");

        
        String maDatPhong = nhapChuoiKhongRong("Nhập mã đặt phòng (VD: DP01): ");
        if (qlDatPhong.timDatPhong(maDatPhong) != null) {
            System.out.println("Lỗi: Mã đặt phòng '" + maDatPhong + "' đã tồn tại!");
            return;
        }

        String maKhachHang = nhapChuoiKhongRong("Nhập mã khách hàng: ");
        String maPhong = nhapChuoiKhongRong("Nhập mã phòng: ");

        
        double giaPhong = nhapSoThucDuong("Nhập giá phòng/ngày: ");

        
        LocalDate ngayNhan;
        while (true) {
            ngayNhan = nhapNgay("Nhập ngày nhận phòng (yyyy-MM-dd): ");
            if (ngayNhan.isBefore(LocalDate.now())) {
                System.out.println("Lỗi: Ngày nhận phòng không được trước ngày hôm nay (" + LocalDate.now() + ")!");
            } else {
                break;
            }
        }

        
        LocalDate ngayTra;
        while (true) {
            ngayTra = nhapNgay("Nhập ngày trả phòng (yyyy-MM-dd): ");
            if (ngayTra.isBefore(ngayNhan)) {
                System.out.println("Lỗi: Ngày trả phòng không được trước ngày nhận phòng (" + ngayNhan + ")!");
            } else {
                break;
            }
        }

        
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
        String ma = nhapChuoiKhongRong("\nNhập mã đặt phòng cần tìm: ");
        DatPhong dp = qlDatPhong.timDatPhong(ma);

        if (dp != null) {
            System.out.println("Thông tin phiếu đặt:");
            System.out.println(dp.toString());
        } else {
            System.out.println("Không tìm thấy mã đặt phòng: " + ma);
        }
    }

    private void huyDatPhong() {
        String ma = nhapChuoiKhongRong("\nNhập mã đặt phòng cần hủy: ");
        DatPhong dp = qlDatPhong.timDatPhong(ma);

        if (dp == null) {
            System.out.println("Lỗi: Không tìm thấy phiếu đặt phòng có mã '" + ma + "'!");
            return;
        }

        System.out.print("Bạn có chắc chắn muốn hủy phiếu '" + ma + "' không? (Y/N): ");
        String xacNhan = scanner.nextLine().trim();
        if (xacNhan.equalsIgnoreCase("Y")) {
            qlDatPhong.huyDatPhong(ma);
        } else {
            System.out.println("Đã hủy thao tác.");
        }
    }

    // --- CÁC HÀM HỖ TRỢ KIỂM TRA DỮ LIỆU ĐẦU VÀO ---

    private String nhapChuoiKhongRong(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Lỗi: Dữ liệu không được để trống! Vui lòng nhập lại.");
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
                    System.out.println("Lỗi: Giá trị phải lớn hơn 0! Vui lòng nhập lại.");
                } else {
                    return so;
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số hợp lệ!");
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
                System.out.println("Lỗi: Sai định dạng ngày (yêu cầu yyyy-MM-dd, VD: 2026-11-20)! Vui lòng nhập lại.");
            }
        }
    }
}