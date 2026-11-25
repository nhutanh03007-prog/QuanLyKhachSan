package ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import model.DatPhong;
import model.Phong;
import model.TinhTrangPhong;
import model.TrangThaiDatPhong;
import service.QLDatPhong;
import service.QLPhong;


public class DatPhongUI {
    private final Scanner scanner;
    private final QLDatPhong qlDatPhong;
    private final QLPhong qlPhong;
    private final DateTimeFormatter dateFormatter;

    public DatPhongUI() {
        this.scanner = new Scanner(System.in);
        this.qlDatPhong = new QLDatPhong();
        this.qlPhong = new QLPhong();
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public void menuDatPhong() {
        int choice = -1;
        do {
            System.out.println("\n===== QUAN LY DAT PHONG =====");
            System.out.println("1. Tao phieu dat phong moi");
            System.out.println("2. Sua phieu dat phong");
            System.out.println("3. Xem danh sach phieu dat phong");
            System.out.println("4. TTim kiem phieu dat phong theo ma");
            System.out.println("5. Huy phieu dat phong");
            System.out.println("6. Nhan phong");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap mot so nguyen hop le!");
                continue;
            }

            switch (choice) {
                case 1 -> taoDatPhongMoi(true);
                case 2 -> suaDatPhong();
                case 3 -> hienThiDanhSach();
                case 4 -> timKiemDatPhong();
                case 5 -> huyDatPhong();
                case 6 -> nhanPhong();
                case 0 -> System.out.println("Thoat quan ly dat phong.");
                default -> System.out.println("Loi: Lua chon khong hop le (chi chon tu 0 đen 6)!");
            }
        } while (choice != 0);
    }

    public void taoDatPhongMoi(boolean chuyenSangKhachHang) {
        System.out.println("\n--- TAO PHIEU DAT PHONG MOI ---");

        
        String maDatPhong;
   while (true) {
      maDatPhong = nhapChuoiKhongRong("Nhap ma dat phong (VD: DP01): ");
     if (qlDatPhong.timDatPhong(maDatPhong) != null) {
        System.out.println("Loi: Ma dat phong '" + maDatPhong + "' da ton tai! Vui long nhap ma khac.");
          } else {
         break;
       }
        }

        String maKhachHang = nhapChuoiKhongRong("Nhap ma khach hang: ");
        Phong phongChon;
        while (true) {
            String maPhong = nhapChuoiKhongRong("Nhap ma phong: ");
            phongChon = qlPhong.timPhongTheoMa(maPhong);
            if (phongChon == null) {
                System.out.println("Loi: Ma phong '" + maPhong + "' khong ton tai! Nhap lai.");
            } else if (phongChon.getTinhTrang() != TinhTrangPhong.TRONG) {
                System.out.println("Loi: Phong '" + maPhong + "' khong trong!");
            } else {
                break;
            }
        }
        BigDecimal giaPhong = phongChon.getGiaPhong();
        System.out.println("Gia phong tu dong lay: " + giaPhong + " VND/ngay");

        LocalDate ngayDat;
        while (true) {
            ngayDat = nhapNgay("Nhap ngay dat phong (yyyy-MM-dd): ");
            if (ngayDat.isBefore(LocalDate.now())) {
                System.out.println("Loi: Ngay dat phong khong duoc truoc ngay hom nay (" + LocalDate.now() + ")!");
            } else {
                break;
            }
        }

        LocalDate ngayTra;
        while (true) {
            ngayTra = nhapNgay("Nhap ngay tra phong (yyyy-MM-dd): ");
            if (ngayTra.isBefore(ngayDat)) {
                System.out.println("Loi: Ngay tra phong khong duoc truoc ngay dat phong (" + ngayDat + ")!");
            } else {
                break;
            }
        } 
        LocalDate ngayNhan = null;
        DatPhong dp = new DatPhong(maDatPhong, maKhachHang, phongChon.getMaPhong(), ngayNhan, ngayTra, giaPhong);
        if (qlDatPhong.taoDatPhong(dp)) {
            phongChon.setTinhTrang(TinhTrangPhong.DA_DAT);
            qlPhong.suaPhong(phongChon);
            System.out.println("Tong tien phong (" + dp.getSoNgayO() + " ngay): " + dp.tinhTienPhong() + " VND");
        }
        
      if (chuyenSangKhachHang) {
       System.out.println("\n>>> Chuyen sang nhap thong tin khach hang...");
         new KhachHangUI().themKhachHang(false);
       }

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
            
            qlPhong.capNhatTinhTrang(dp.getMaPhong(), TinhTrangPhong.TRONG);
        }
        else {
            System.out.println("Da huy thao tac.");
        }
    }
    private void nhanPhong() {
        String ma = nhapChuoiKhongRong("\nNhap ma dat phong can nhan: ");
        DatPhong dp = qlDatPhong.timDatPhong(ma);
        if (dp == null || dp.getTrangThai() != TrangThaiDatPhong.DA_DAT) {
            System.out.println("Loi: Phieu dat phong khong hop le!");
            return;
        }

        LocalDate ngayNhanThucTe;
while (true) {
    ngayNhanThucTe = nhapNgay("Nhap ngay nhan phong thuc te (yyyy-MM-dd): ");
    if (ngayNhanThucTe.isBefore(LocalDate.now())) {
        System.out.println("Loi: Ngay nhan phong khong duoc truoc ngay hom nay (" + LocalDate.now() + ")!");
    } else if (ngayNhanThucTe.isAfter(dp.getNgayTraPhong())) {
        System.out.println("Loi: Ngay nhan phong khong duoc sau ngay tra phong (" + dp.getNgayTraPhong() + ")!");
    } else {
        break;
    }
}
        dp.setNgayNhanPhong(ngayNhanThucTe);
        dp.setTrangThai(TrangThaiDatPhong.DANG_SU_DUNG);

        Phong phong = qlPhong.timPhongTheoMa(dp.getMaPhong());
        if (phong != null) {
            phong.setTinhTrang(TinhTrangPhong.DANG_SU_DUNG);
            qlPhong.suaPhong(phong);
        }

        System.out.println("Nhan phong thanh cong! Tien phong (" + dp.getSoNgayO() + " ngay): " + dp.tinhTienPhong() + " VND");
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
    private void suaDatPhong() {
    String ma = nhapChuoiKhongRong("\nNhap ma dat phong can sua: ");
    DatPhong dp = qlDatPhong.timDatPhong(ma);
    if (dp == null) {
        System.out.println("Loi: Khong tim thay phieu dat phong co ma '" + ma + "'!");
        return;
    }

    System.out.println("\n--- SUA PHIEU DAT PHONG ---");
    boolean daSua = false;
    int chon = -1;

    do {
        System.out.println("\nThong tin hien tai: " + dp);
        System.out.println("1. Ma khach hang : " + dp.getMaKhachHang());
        System.out.println("2. Ngay nhan phong: " + dp.getNgayNhanPhong());
        System.out.println("3. Ngay tra phong : " + dp.getNgayTraPhong());
        System.out.println("0. Thoat");

        // Nhap lua chon
        chon = -1;
        while (chon < 0 || chon > 3) {
            try {
                System.out.print("Chon thong tin can sua (0-3): ");
                chon = Integer.parseInt(scanner.nextLine().trim());
                if (chon < 0 || chon > 3) {
                    System.out.println("Loi: Lua chon khong hop le! Vui long chon tu 0 den 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap mot so nguyen hop le!");
            }
        }

        switch (chon) {
            case 1 -> {
                String maKH = nhapChuoiKhongRong("Nhap ma khach hang moi: ");
                dp.setMaKhachHang(maKH);
                daSua = true;
                System.out.println("Da cap nhat ma khach hang.");
            }
            case 2 -> {
                LocalDate ngayNhan = nhapNgay("Nhap ngay nhan phong moi (yyyy-MM-dd, de trong de giu nguyen): ");
                if (ngayNhan != null) {
                    dp.setNgayNhanPhong(ngayNhan);
                    daSua = true;
                    System.out.println("Da cap nhat ngay nhan phong.");
                }
            }
            case 3 -> {
                LocalDate ngayTra = nhapNgay("Nhap ngay tra phong moi (yyyy-MM-dd, de trong de giu nguyen): ");
                if (ngayTra != null) {
                    dp.setNgayTraPhong(ngayTra);
                    daSua = true;
                    System.out.println("Da cap nhat ngay tra phong.");
                }
            }
            case 0 -> { /* thoat */ }
        }

        if (chon != 0) {
            System.out.println("\nBan con muon sua gi them khong? Neu khong thi chon 0 de thoat.");
        }
    } while (chon != 0);

    if (!daSua) {
        System.out.println("Khong co thay doi nao duoc thuc hien.");
        return;
    }

    if (qlDatPhong.taoDatPhong(dp)) {
        System.out.println("Sua phieu dat phong thanh cong!");
    } else {
        System.out.println("Sua phieu dat phong that bai!");
    }
}
}