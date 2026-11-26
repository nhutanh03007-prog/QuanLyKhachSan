package ui;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import model.DatPhong;
import model.Phong;
import model.TinhTrangPhong;
import model.TrangThaiDatPhong;
import service.QLDatPhong;
import service.QLKhachHang;
import service.QLPhong;

public class DatPhongUI {
    private final BufferedReader reader;
    private final QLDatPhong qlDatPhong;
    private final QLPhong qlPhong;
    private final QLKhachHang qlKhachHang;
    private final DateTimeFormatter dateFormatter;

    public DatPhongUI() {
        this(ConsoleInput.getReader());
    }

    public DatPhongUI(BufferedReader reader) {
        this.reader = reader;
        this.qlDatPhong = new QLDatPhong();
        this.qlPhong = new QLPhong();
        this.qlKhachHang = new QLKhachHang();
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public void menuDatPhong() {
        int choice = -1;
        do {
            System.out.println("\n===== QUAN LY DAT PHONG =====");
            System.out.println("1. Tao phieu dat phong moi (ho tro dat nhieu phong)");
            System.out.println("2. Sua phieu dat phong");
            System.out.println("3. Xem danh sach phieu dat phong");
            System.out.println("4. Tim kiem phieu dat phong theo ma");
            System.out.println("5. Huy phieu dat phong");
            System.out.println("6. Nhan phong (Check-in)");
            System.out.println("7. Tra phong (Check-out)");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            try {
                choice = Integer.parseInt(ConsoleInput.readLine(reader).trim());
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap mot so nguyen hop le!");
                continue;
            }

            switch (choice) {
                case 1 -> taoDatPhongMoi(null);
                case 2 -> suaDatPhong();
                case 3 -> hienThiDanhSach();
                case 4 -> timKiemDatPhong();
                case 5 -> huyDatPhong();
                case 6 -> nhanPhong();
                case 7 -> traPhong();
                case 0 -> System.out.println("Thoat quan ly dat phong.");
                default -> System.out.println("Loi: Lua chon khong hop le (chi chon tu 0 den 7)!");
            }
        } while (choice != 0);
    }

    public void taoDatPhongMoi(boolean chuyenSangKhachHang) {
        taoDatPhongMoi(null);
    }

    public void taoDatPhongMoi(String maKhachHangCoSan) {
        System.out.println("\n--- TAO PHIEU DAT PHONG MOI ---");

        String maDatPhong;
        while (true) {
            maDatPhong = nhapChuoiKhongRong("Nhap ma dat phong (VD: DP25): ").toUpperCase();
            if (qlDatPhong.timDatPhong(maDatPhong) != null) {
                System.out.println("Loi: Ma dat phong '" + maDatPhong + "' da ton tai! Vui long nhap ma khac.");
            } else {
                break;
            }
        }

        String maKhachHang = maKhachHangCoSan;
        while (maKhachHang == null || qlKhachHang.timKhachHangTheoMa(maKhachHang) == null) {
            maKhachHang = nhapChuoiKhongRong("Nhap ma khach hang (VD: KH01): ").toUpperCase();
            if (qlKhachHang.timKhachHangTheoMa(maKhachHang) == null) {
                System.out.print("Khach hang '" + maKhachHang + "' chua ton tai. Tao moi khach hang? (Y/N): ");
                if (ConsoleInput.readLine(reader).trim().equalsIgnoreCase("Y")) {
                    maKhachHang = new KhachHangUI(reader).themKhachHang(false);
                } else {
                    maKhachHang = null;
                }
            }
        }

        LocalDate ngayNhan;
        while (true) {
            ngayNhan = nhapNgay("Nhap ngay nhan phong du kien (yyyy-MM-dd): ");
            if (ngayNhan.isBefore(LocalDate.now())) {
                System.out.println("Loi: Ngay nhan phong khong duoc truoc ngay hom nay (" + LocalDate.now() + ")!");
            } else {
                break;
            }
        }

        LocalDate ngayTra;
        while (true) {
            ngayTra = nhapNgay("Nhap ngay tra phong du kien (yyyy-MM-dd): ");
            if (ngayTra.isBefore(ngayNhan)) {
                System.out.println("Loi: Ngay tra phong khong duoc truoc ngay nhan phong (" + ngayNhan + ")!");
            } else {
                break;
            }
        }

        Phong[] phongTrong = qlPhong.layPhongTrong(ngayNhan, ngayTra);
        if (phongTrong.length == 0) {
            System.out.println("Khong con phong trong trong khoang " + ngayNhan + " den " + ngayTra + "!");
            return;
        }
        System.out.println("\nDanh sach phong trong tu " + ngayNhan + " den " + ngayTra + ":");
        for (Phong p : phongTrong) {
            System.out.printf("  - %-6s | %-10s | %-6s | %s VND/ngay | %s%n",
                    p.getMaPhong(), p.getTenPhong(), p.getLoaiPhong(),
                    p.getGiaPhong().toPlainString(), p.getTinhTrang());
        }

        String[] dsMaPhong = new String[phongTrong.length];
        int soPhongChon = 0;
        BigDecimal tongGiaNgay = BigDecimal.ZERO;

        while (true) {
            String maPhong = nhapChuoiKhongRong("Nhap ma phong muon dat: ").toUpperCase();
            if (daCoTrongMang(dsMaPhong, soPhongChon, maPhong)) {
                System.out.println("Loi: Phong '" + maPhong + "' da duoc chon trong phieu nay!");
            } else {
                Phong phongChon = qlPhong.timPhongTheoMa(maPhong);
                if (phongChon == null) {
                    System.out.println("Loi: Ma phong '" + maPhong + "' khong ton tai!");
                } else if (phongChon.getTinhTrang() == TinhTrangPhong.DANG_BAO_TRI) {
                    System.out.println("Loi: Phong '" + maPhong + "' dang bao tri!");
                } else if (!phongCoTrongDanhSach(phongTrong, phongChon.getMaPhong())) {
                    System.out.println("Loi: Phong '" + maPhong + "' da co lich dat trong khoang thoi gian nay!");
                } else {
                    if (soPhongChon >= dsMaPhong.length) {
                        dsMaPhong = Arrays.copyOf(dsMaPhong, dsMaPhong.length * 2 + 1);
                    }
                    dsMaPhong[soPhongChon++] = phongChon.getMaPhong();
                    tongGiaNgay = tongGiaNgay.add(phongChon.getGiaPhong());
                    System.out.println("Da them phong " + phongChon.getMaPhong() + " ("
                            + phongChon.getGiaPhong().toPlainString() + " VND/ngay).");
                    System.out.println("Danh sach phong da chon (" + soPhongChon + " phong): "
                            + Arrays.toString(Arrays.copyOf(dsMaPhong, soPhongChon))
                            + " | Tong gia/ngay: " + tongGiaNgay.toPlainString() + " VND");
                }
            }

            if (soPhongChon > 0) {
                System.out.print("Ban co muon dat them phong khac vao phieu nay khong? (Y/N): ");
                if (!ConsoleInput.readLine(reader).trim().equalsIgnoreCase("Y")) {
                    break;
                }
            }
        }

        String[] ketQuaPhong = Arrays.copyOf(dsMaPhong, soPhongChon);
        DatPhong dp = new DatPhong(maDatPhong, maKhachHang, ketQuaPhong, ngayNhan, ngayTra, tongGiaNgay);
        qlDatPhong.taoDatPhong(dp);
    }

    private boolean daCoTrongMang(String[] mang, int soLuong, String ma) {
        for (int i = 0; i < soLuong; i++) {
            if (mang[i] != null && mang[i].equalsIgnoreCase(ma)) {
                return true;
            }
        }
        return false;
    }

    private boolean phongCoTrongDanhSach(Phong[] ds, String maPhong) {
        for (Phong p : ds) {
            if (p != null && p.getMaPhong().equalsIgnoreCase(maPhong)) {
                return true;
            }
        }
        return false;
    }

    private void hienThiDanhSach() {
        System.out.println("\n--- DANH SACH PHIEU DAT PHONG ---");
        DatPhong[] danhSach = qlDatPhong.layTatCa();
        if (danhSach == null || danhSach.length == 0) {
            System.out.println("Chua co phieu dat phong nao.");
            return;
        }
        System.out.printf("%-8s %-8s %-20s %-12s %-12s %-14s %-14s %-15s%n",
                "Ma DP", "Ma KH", "Danh sach phong", "Ngay nhan", "Ngay tra", "Gia/ngay", "Tien phong", "Trang thai");
        System.out.println("-".repeat(110));
        for (DatPhong dp : danhSach) {
            if (dp != null) {
                System.out.printf("%-8s %-8s %-20s %-12s %-12s %-14s %-14s %-15s%n",
                        dp.getMaDatPhong(),
                        dp.getMaKhachHang(),
                        dp.getMaPhong(),
                        dp.getNgayNhanPhong(),
                        dp.getNgayTraPhong(),
                        dp.getGiaPhongNgay() == null ? "0" : dp.getGiaPhongNgay().toPlainString(),
                        dp.tinhTienPhong().toPlainString(),
                        dp.getTrangThai());
            }
        }
        System.out.println("Tong so phieu dat phong: " + danhSach.length);
    }

    private void timKiemDatPhong() {
        String ma = nhapChuoiKhongRong("\nNhap ma dat phong can tim: ");
        DatPhong dp = qlDatPhong.timDatPhong(ma);

        if (dp != null) {
            System.out.println("Thong tin phieu dat:");
            System.out.println(dp.toString());
            System.out.println("So phong: " + dp.getSoLuongPhong()
                    + " | So ngay o: " + dp.getSoNgayO()
                    + " | Tong tien phong: " + dp.tinhTienPhong().toPlainString() + " VND");
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

        System.out.println("Thong tin phieu: " + dp);
        System.out.print("Ban co chac chan muon huy phieu '" + dp.getMaDatPhong() + "' khong? (Y/N): ");
        String xacNhan = ConsoleInput.readLine(reader).trim();
        if (xacNhan.equalsIgnoreCase("Y")) {
            qlDatPhong.huyDatPhong(dp.getMaDatPhong());
        } else {
            System.out.println("Da huy thao tac.");
        }
    }

    private void nhanPhong() {
        String ma = nhapChuoiKhongRong("\nNhap ma dat phong can nhan: ");
        DatPhong dp = qlDatPhong.timDatPhong(ma);
        if (dp == null || dp.getTrangThai() != TrangThaiDatPhong.DA_DAT) {
            System.out.println("Loi: Phieu dat phong khong hop le hoac khong o trang thai DA_DAT!");
            return;
        }

        LocalDate ngayNhanThucTe;
        while (true) {
            ngayNhanThucTe = nhapNgay("Nhap ngay nhan phong thuc te (yyyy-MM-dd, Enter de lay hom nay): ", true);
            if (ngayNhanThucTe == null) {
                ngayNhanThucTe = LocalDate.now();
            }
            if (dp.getNgayTraPhong() != null && ngayNhanThucTe.isAfter(dp.getNgayTraPhong())) {
                System.out.println("Loi: Ngay nhan phong khong duoc sau ngay tra phong (" + dp.getNgayTraPhong() + ")!");
            } else {
                break;
            }
        }

        if (qlDatPhong.nhanPhong(dp.getMaDatPhong(), ngayNhanThucTe)) {
            DatPhong sau = qlDatPhong.timDatPhong(dp.getMaDatPhong());
            System.out.println("Nhan phong thanh cong! Danh sach phong: " + Arrays.toString(sau.getDanhSachMaPhong())
                    + " | Tien phong (" + sau.getSoNgayO() + " ngay): "
                    + sau.tinhTienPhong().toPlainString() + " VND");
        }
    }

    private void traPhong() {
        String ma = nhapChuoiKhongRong("\nNhap ma dat phong can tra: ");
        DatPhong dp = qlDatPhong.timDatPhong(ma);
        if (dp == null) {
            System.out.println("Loi: Khong tim thay phieu dat phong co ma '" + ma + "'!");
            return;
        }
        if (qlDatPhong.traPhong(dp.getMaDatPhong())) {
            System.out.println("Tra phong thanh cong! Cac phong " + Arrays.toString(dp.getDanhSachMaPhong()) + " da duoc cap nhat.");
        } else {
            System.out.println("Tra phong that bai (phieu da tra hoac da huy)!");
        }
    }

    private void suaDatPhong() {
        String ma = nhapChuoiKhongRong("\nNhap ma dat phong can sua: ");
        DatPhong dpGoc = qlDatPhong.timDatPhong(ma);
        if (dpGoc == null) {
            System.out.println("Loi: Khong tim thay phieu dat phong co ma '" + ma + "'!");
            return;
        }
        if (dpGoc.getTrangThai() == TrangThaiDatPhong.DA_HUY || dpGoc.getTrangThai() == TrangThaiDatPhong.DA_TRA) {
            System.out.println("Loi: Khong the sua phieu dat phong da huy hoac da tra!");
            return;
        }

        DatPhong dp = new DatPhong(dpGoc);
        System.out.println("\n--- SUA PHIEU DAT PHONG ---");
        boolean daSua = false;
        int chon;

        do {
            System.out.println("\nThong tin hien tai: " + dp);
            System.out.println("1. Sua ma khach hang   : " + dp.getMaKhachHang());
            System.out.println("2. Sua danh sach phong : " + Arrays.toString(dp.getDanhSachMaPhong()));
            System.out.println("3. Sua ngay nhan phong : " + dp.getNgayNhanPhong());
            System.out.println("4. Sua ngay tra phong  : " + dp.getNgayTraPhong());
            System.out.println("0. Luu va thoat");

            chon = -1;
            while (chon < 0 || chon > 4) {
                try {
                    System.out.print("Chon thong tin can sua (0-4): ");
                    chon = Integer.parseInt(ConsoleInput.readLine(reader).trim());
                    if (chon < 0 || chon > 4) {
                        System.out.println("Loi: Lua chon khong hop le! Vui long chon tu 0 den 4.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Loi: Vui long nhap mot so nguyen hop le!");
                }
            }

            switch (chon) {
                case 1 -> {
                    String maKH = nhapChuoiKhongRong("Nhap ma khach hang moi: ").toUpperCase();
                    if (qlKhachHang.timKhachHangTheoMa(maKH) == null) {
                        System.out.println("Loi: Ma khach hang '" + maKH + "' khong ton tai!");
                    } else {
                        dp.setMaKhachHang(maKH);
                        daSua = true;
                        System.out.println("Da cap nhat ma khach hang.");
                    }
                }
                case 2 -> {
                    String dsStr = nhapChuoiKhongRong("Nhap danh sach ma phong moi (cach nhau bang dau phay, VD: P101,P102): ");
                    String[] tach = dsStr.split(",");
                    String[] dsMoi = new String[tach.length];
                    int dem = 0;
                    for (String s : tach) {
                        if (!s.trim().isEmpty()) {
                            dsMoi[dem++] = s.trim().toUpperCase();
                        }
                    }
                    if (dem > 0) {
                        dp.setDanhSachMaPhong(Arrays.copyOf(dsMoi, dem));
                        daSua = true;
                        System.out.println("Da cap nhat danh sach phong tam thoi: " + Arrays.toString(dp.getDanhSachMaPhong()));
                    }
                }
                case 3 -> {
                    LocalDate ngayNhan = nhapNgay("Nhap ngay nhan phong moi (yyyy-MM-dd, de trong de giu nguyen): ", true);
                    if (ngayNhan != null) {
                        if (dp.getNgayTraPhong() != null && ngayNhan.isAfter(dp.getNgayTraPhong())) {
                            System.out.println("Loi: Ngay nhan phong khong duoc sau ngay tra phong (" + dp.getNgayTraPhong() + ")!");
                        } else {
                            dp.setNgayNhanPhong(ngayNhan);
                            daSua = true;
                            System.out.println("Da cap nhat ngay nhan phong.");
                        }
                    }
                }
                case 4 -> {
                    LocalDate ngayTra = nhapNgay("Nhap ngay tra phong moi (yyyy-MM-dd, de trong de giu nguyen): ", true);
                    if (ngayTra != null) {
                        LocalDate moc = (dp.getNgayNhanPhong() != null) ? dp.getNgayNhanPhong() : LocalDate.now();
                        if (ngayTra.isBefore(moc)) {
                            System.out.println("Loi: Ngay tra phong khong duoc truoc " + moc + "!");
                        } else {
                            dp.setNgayTraPhong(ngayTra);
                            daSua = true;
                            System.out.println("Da cap nhat ngay tra phong.");
                        }
                    }
                }
                case 0 -> { /* thoat */ }
            }
        } while (chon != 0);

        if (!daSua) {
            System.out.println("Khong co thay doi nao duoc thuc hien.");
            return;
        }

        if (qlDatPhong.suaDatPhong(dp)) {
            System.out.println("Sua phieu dat phong thanh cong!");
        } else {
            System.out.println("Sua phieu dat phong that bai!");
        }
    }

    private String nhapChuoiKhongRong(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String input = ConsoleInput.readLine(reader).trim();
            if (input.isEmpty()) {
                System.out.println("Loi: Du lieu khong duoc de trong! Vui long nhap lai.");
            } else {
                return input;
            }
        }
    }

    private LocalDate nhapNgay(String thongBao) {
        return nhapNgay(thongBao, false);
    }

    private LocalDate nhapNgay(String thongBao, boolean choPhepRong) {
        while (true) {
            System.out.print(thongBao);
            String input = ConsoleInput.readLine(reader).trim();
            if (input.isEmpty()) {
                if (choPhepRong) {
                    return null;
                }
                System.out.println("Loi: Ngay khong duoc de trong! Vui long nhap lai.");
                continue;
            }
            try {
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Loi: Sai dinh dang ngay (yeu cau yyyy-MM-dd, VD: 2026-11-26)! Vui long nhap lai.");
            }
        }
    }
}
