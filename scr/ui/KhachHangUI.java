package ui;

import INTERFACE.IKhachHang;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import model.KhachHang;
import service.QLKhachHang;

public class KhachHangUI {
    private final Scanner scanner;
    private final IKhachHang qlKhachHang;

    public KhachHangUI() {
        this.scanner = new Scanner(System.in);
        this.qlKhachHang = new QLKhachHang();
    }

    public void menuKhachHang() {
        int choice = -1;
        do {
            System.out.println("\n=== QUAN LY KHACH HANG ===");
            System.out.println("1. Them khach hang");
            System.out.println("2. Hien thi danh sach khach hang");
            System.out.println("3. Tim khach hang theo ma");
            System.out.println("4. Tim khach hang theo ten");
            System.out.println("5. Loc khach hang theo loai");
            System.out.println("6. Sua thong tin khach hang");
            System.out.println("7. Xoa khach hang");
            System.out.println("8. Thong ke so luong khach hang");
            System.out.println("0. Quay lai");
            System.out.print("Nhap lua chon: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so hop le!");
                continue;
            }

            switch (choice) {
                case 1:
                    themKhachHang();
                    break;
                case 2:
                    hienThiDanhSach(qlKhachHang.layDanhSachKhachHang());
                    break;
                case 3:
                    timTheoMa();
                    break;
                case 4:
                    timTheoTen();
                    break;
                case 5:
                    locTheoLoai();
                    break;
                case 6:
                    suaKhachHang();
                    break;
                case 7:
                    xoaKhachHang();
                    break;
                case 8:
                    thongKe();
                    break;
                case 0:
                    System.out.println("Quay lai menu chinh...");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (choice != 0);
    }

    private void themKhachHang() {
        System.out.println("\n--- THEM KHACH HANG MOI ---");
        String maKH = nhapChuoi("Nhap ma KH: ");
        String hoTen = nhapChuoi("Nhap ho ten: ");
        String gioiTinh = nhapChuoi("Nhap gioi tinh (Nam/Nu): ");

        LocalDate ngaySinh = null;
        while (ngaySinh == null) {
            try {
                String nsStr = nhapChuoi("Nhap ngay sinh (yyyy-MM-dd): ");
                ngaySinh = LocalDate.parse(nsStr);
            } catch (DateTimeParseException e) {
                System.out.println("Sai dinh dang ngay (VD: 2000-05-20). Nhap lai!");
            }
        }

        String diaChi = nhapChuoi("Nhap dia chi: ");
        String sdt = nhapChuoi("Nhap so dien thoai: ");
        String email = nhapChuoi("Nhap email: ");
        String loaiKhach = nhapChuoi("Nhap loai khach (Thuong/VIP): ");
        System.out.print("Nhap ghi chu (Enter de bo qua): ");
        String ghiChu = scanner.nextLine().trim();

        KhachHang kh = new KhachHang(maKH, hoTen, gioiTinh, ngaySinh, diaChi, sdt, email, loaiKhach, ghiChu);
        if (qlKhachHang.themKhachHang(kh)) {
            System.out.println("Them khach hang thanh cong!");
        } else {
            System.out.println("Them khach hang that bai (Trung ma hoac danh sach day)!");
        }
    }

    private void hienThiDanhSach(KhachHang[] danhSach) {
        System.out.println("\n--- DANH SACH KHACH HANG ---");
        if (danhSach == null || danhSach.length == 0) {
            System.out.println("Danh sach khach hang trong!");
            return;
        }
        System.out.printf("%-10s %-22s %-10s %-12s %-15s %-12s%n",
                "Ma KH", "Ho Ten", "Gioi Tinh", "Ngay Sinh", "SDT", "Loai Khach");
        System.out.println("--------------------------------------------------------------------------------------");
        for (KhachHang kh : danhSach) {
            if (kh != null) {
                System.out.printf("%-10s %-22s %-10s %-12s %-15s %-12s%n",
                        kh.getMaKH(),
                        kh.getHoTen(),
                        kh.getGioiTinh(),
                        kh.getNgaySinh(),
                        kh.getSdt(),
                        kh.getLoaiKhach());
            }
        }
    }

    private void timTheoMa() {
        String maKH = nhapChuoi("Nhap ma KH can tim: ");
        KhachHang kh = qlKhachHang.timKhachHangTheoMa(maKH);
        if (kh != null) {
            hienThiDanhSach(new KhachHang[]{kh});
        } else {
            System.out.println("Khong tim thay khach hang co ma: " + maKH);
        }
    }

    private void timTheoTen() {
        String ten = nhapChuoi("Nhap ten khach hang can tim: ");
        KhachHang[] ketQua = qlKhachHang.timKhachHangTheoTen(ten);
        hienThiDanhSach(ketQua);
    }

    private void locTheoLoai() {
        String loai = nhapChuoi("Nhap loai khach (Thuong/VIP): ");
        KhachHang[] ketQua = qlKhachHang.layDanhSachKhachHangTheoLoai(loai);
        hienThiDanhSach(ketQua);
    }

    private void suaKhachHang() {
        String maKH = nhapChuoi("Nhap ma KH can sua: ");
        if (qlKhachHang.timKhachHangTheoMa(maKH) == null) {
            System.out.println("Khong tim thay ma khach hang!");
            return;
        }

        String hoTen = nhapChuoi("Nhap ho ten moi: ");
        String gioiTinh = nhapChuoi("Nhap gioi tinh moi: ");

        LocalDate ngaySinh = null;
        while (ngaySinh == null) {
            try {
                ngaySinh = LocalDate.parse(nhapChuoi("Nhap ngay sinh moi (yyyy-MM-dd): "));
            } catch (DateTimeParseException e) {
                System.out.println("Sai dinh dang ngay! Nhap lai.");
            }
        }

        String diaChi = nhapChuoi("Nhap dia chi moi: ");
        String sdt = nhapChuoi("Nhap SDT moi: ");
        String email = nhapChuoi("Nhap email moi: ");
        String loaiKhach = nhapChuoi("Nhap loai khach moi: ");
        System.out.print("Nhap ghi chu moi: ");
        String ghiChu = scanner.nextLine().trim();

        KhachHang khMoi = new KhachHang(maKH, hoTen, gioiTinh, ngaySinh, diaChi, sdt, email, loaiKhach, ghiChu);
        if (qlKhachHang.suaKhachHang(khMoi)) {
            System.out.println("Cap nhat thong tin khach hang thanh cong!");
        } else {
            System.out.println("Cap nhat that bai!");
        }
    }

    private void xoaKhachHang() {
        String maKH = nhapChuoi("Nhap ma KH can xoa: ");
        if (qlKhachHang.xoaKhachHang(maKH)) {
            System.out.println("Xoa khach hang thanh cong!");
        } else {
            System.out.println("Khong tim thay ma khach hang de xoa!");
        }
    }

    private void thongKe() {
        System.out.println("\n--- THONG KE KHACH HANG ---");
        System.out.println("Tong so khach hang: " + qlKhachHang.demSoLuongKhachHang());
        String loai = nhapChuoi("Nhap loai khach muon dem (Thuong/VIP): ");
        System.out.println("So luong khach loai " + loai + ": " + qlKhachHang.demSoLuongKhachHangTheoLoai(loai));
    }

    private String nhapChuoi(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Khong duoc de trong! Vui long nhap lai.");
        }
    }
}