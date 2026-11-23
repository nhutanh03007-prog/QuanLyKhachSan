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
                System.out.println("Loi: Vui long nhap so hop le!");
                continue;
            }

            switch (choice) {
                case 1 -> themKhachHang(true);
                case 2 -> hienThiDanhSach(qlKhachHang.layDanhSachKhachHang());
                case 3 -> timTheoMa();
                case 4 -> timTheoTen();
                case 5 -> locTheoLoai();
                case 6 -> suaKhachHang();
                case 7 -> xoaKhachHang();
                case 8 -> thongKe();
                case 0 -> System.out.println("Quay lai menu chinh...");
                default -> System.out.println("Loi: Lua chon khong hop le (chi chon tu 0 den 8)!");
            }
        } while (choice != 0);
    }

    public void themKhachHang(boolean chuyenSangDatPhong) {
        System.out.println("\n--- THEM KHACH HANG MOI ---");
        String maKH;
     while (true) {
       maKH = nhapChuoi("Nhap ma KH (VD: KH01): ");
       if (qlKhachHang.timKhachHangTheoMa(maKH) != null) {
        System.out.println("Loi: Ma khach hang '" + maKH + "' da ton tai! Vui long nhap ma khac.");
      } else {
        break;
    }
    }

        String hoTen = nhapChuoi("Nhap ho ten: ");
        String gioiTinh = nhapGioiTinh("Nhap gioi tinh (Nam/Nu): ");
        LocalDate ngaySinh = nhapNgaySinh("Nhap ngay sinh (yyyy-MM-dd): ");
        String diaChi = nhapChuoi("Nhap dia chi: ");
        String sdt = nhapSDT("Nhap so dien thoai (10-11 so): ");
        String email = nhapEmail("Nhap email: ");
        String loaiKhach = nhapLoaiKhach("Nhap loai khach (Thuong/VIP): ");

        System.out.print("Nhap ghi chu (Enter de bo qua): ");
        String ghiChu = scanner.nextLine().trim();

        KhachHang kh = new KhachHang(maKH, hoTen, gioiTinh, ngaySinh, diaChi, sdt, email, loaiKhach, ghiChu);
        if (qlKhachHang.themKhachHang(kh)) {
            System.out.println("Them khach hang thanh cong!");
        if (qlKhachHang.themKhachHang(kh)) {
          System.out.println("Them khach hang thanh cong!");

         if (chuyenSangDatPhong) {
           System.out.println("\n>>> Chuyen sang tao phieu dat phong...");
            new DatPhongUI().taoDatPhongMoi(false);
          }
       } 
    
        else {
            System.out.println("Them khach hang that bai (Danh sach da day)!");
        }
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
                        kh.getMaKH(), kh.getHoTen(), kh.getGioiTinh(), kh.getNgaySinh(), kh.getSdt(), kh.getLoaiKhach());
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
        String loai = nhapLoaiKhach("Nhap loai khach (Thuong/VIP): ");
        KhachHang[] ketQua = qlKhachHang.layDanhSachKhachHangTheoLoai(loai);
        hienThiDanhSach(ketQua);
    }

    private void suaKhachHang() {
        String maKH = nhapChuoi("Nhap ma KH can sua: ");
        if (qlKhachHang.timKhachHangTheoMa(maKH) == null) {
            System.out.println("Loi: Khong tim thay ma khach hang '" + maKH + "'!");
            return;
        }

        String hoTen = nhapChuoi("Nhap ho ten moi: ");
        String gioiTinh = nhapGioiTinh("Nhap gioi tinh moi (Nam/Nu): ");
        LocalDate ngaySinh = nhapNgaySinh("Nhap ngay sinh moi (yyyy-MM-dd): ");
        String diaChi = nhapChuoi("Nhap dia chi moi: ");
        String sdt = nhapSDT("Nhap SDT moi (10-11 so): ");
        String email = nhapEmail("Nhap email moi: ");
        String loaiKhach = nhapLoaiKhach("Nhap loai khach moi (Thuong/VIP): ");

        System.out.print("Nhap ghi chu moi (Enter de bo qua): ");
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
        KhachHang kh = qlKhachHang.timKhachHangTheoMa(maKH);
        if (kh == null) {
            System.out.println("Loi: Khong tim thay ma khach hang '" + maKH + "' de xoa!");
            return;
        }

        System.out.print("Ban co chac chan muon xoa khach hang '" + kh.getHoTen() + "'? (Y/N): ");
        String xacNhan = scanner.nextLine().trim();
        if (xacNhan.equalsIgnoreCase("Y")) {
            if (qlKhachHang.xoaKhachHang(maKH)) {
                System.out.println("Xoa khach hang thanh cong!");
            } else {
                System.out.println("Xoa khach hang that bai!");
            }
        } else {
            System.out.println("Da huy thao tac xoa.");
        }
    }

    private void thongKe() {
        System.out.println("\n--- THONG KE KHACH HANG ---");
        System.out.println("Tong so khach hang: " + qlKhachHang.demSoLuongKhachHang());
        String loai = nhapLoaiKhach("Nhap loai khach muon dem (Thuong/VIP): ");
        System.out.println("So luong khach loai " + loai + ": " + qlKhachHang.demSoLuongKhachHangTheoLoai(loai));
    }

    // --- CÁC HÀM HỖ TRỢ KIỂM TRA DỮ LIỆU ĐẦU VÀO ---

    private String nhapChuoi(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Loi: Khong duoc de trong! Vui long nhap lai.");
        }
    }

    private String nhapGioiTinh(String thongBao) {
        while (true) {
            String gt = nhapChuoi(thongBao);
            if (gt.equalsIgnoreCase("Nam")) return "Nam";
            if (gt.equalsIgnoreCase("Nu")) return "Nu";
            System.out.println("Loi: Chi nhap 'Nam' hoac 'Nu'! Vui long nhap lai.");
        }
    }

    private String nhapLoaiKhach(String thongBao) {
        while (true) {
            String loai = nhapChuoi(thongBao);
            if (loai.equalsIgnoreCase("Thuong")) return "Thuong";
            if (loai.equalsIgnoreCase("VIP")) return "VIP";
            System.out.println("Loi: Chi nhap 'Thuong' hoac 'VIP'! Vui long nhap lai.");
        }
    }

    private LocalDate nhapNgaySinh(String thongBao) {
        while (true) {
            try {
                String nsStr = nhapChuoi(thongBao);
                LocalDate ngaySinh = LocalDate.parse(nsStr);
                if (ngaySinh.isAfter(LocalDate.now())) {
                    System.out.println("Loi: Ngay sinh khong duoc lon hon ngay hien tai!");
                } else {
                    return ngaySinh;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Loi: Sai dinh dang ngay (VD: 2000-05-20). Nhap lai!");
            }
        }
    }

    private String nhapSDT(String thongBao) {
        while (true) {
            String sdt = nhapChuoi(thongBao);
            if (sdt.matches("^0\\d{9,10}$")) {
                return sdt;
            }
            System.out.println("Loi: SDT phai bat dau bang so 0 va gom 10-11 chu so! Nhap lai.");
        }
    }

    private String nhapEmail(String thongBao) {
        while (true) {
            String email = nhapChuoi(thongBao);
            if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                return email;
            }
            System.out.println("Loi: Dinh dang email khong hop le (VD: ten@gmail.com)! Nhap lai.");
        }
    }
}