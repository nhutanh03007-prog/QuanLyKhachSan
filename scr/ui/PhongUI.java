package ui;

import DAO.DichVuDAO;
import INTERFACE.IPhong;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.DichVu;
import model.Phong;
import model.PhongThuong;
import model.PhongVIP;
import model.TinhTrangPhong;
import service.QLPhong;

public class PhongUI {

    private static final int DO_RONG_DICH_VU = 30;
    private static final String DUONG_KE = "-".repeat(106);

    private final Scanner sc;
    private final IPhong qlPhong;
    private final DichVuDAO dichVuDAO;

    /** Dung Scanner chung: truyen cung mot Scanner cho moi lop UI. */
    public PhongUI(Scanner sc) {
        this.sc = sc;
        this.qlPhong = new QLPhong();
        this.dichVuDAO = new DichVuDAO();
    }

    public PhongUI() {
        this(new Scanner(System.in));
    }

    // ================= MENU =================

    public void menuPhong() {
        int chon;
        do {
            System.out.println("\n======================================");
            System.out.println("       QUAN LY PHONG KHACH SAN");
            System.out.println("======================================");
            System.out.println("1. Them phong");
            System.out.println("2. Hien thi danh sach phong");
            System.out.println("3. Tim phong theo ma");
            System.out.println("4. Tim phong theo tinh trang");
            System.out.println("5. Sua thong tin phong");
            System.out.println("6. Xoa phong");
            System.out.println("7. Dem tong so phong");
            System.out.println("8. Thong ke phong theo tinh trang");
            System.out.println("9. Tim phong trong theo ngay");
            System.out.println("0. Thoat");
            System.out.println("======================================");

            chon = nhapSoNguyen("Nhap lua chon: ");

            switch (chon) {
                case 1 -> themPhong();
                case 2 -> hienThiDanhSachPhong();
                case 3 -> timPhongTheoMa();
                case 4 -> timPhongTheoTinhTrang();
                case 5 -> suaPhong();
                case 6 -> xoaPhong();
                case 7 -> demTongSoPhong();
                case 8 -> thongKePhongTheoTinhTrang();
                case 9 -> timPhongTrong();
                case 0 -> System.out.println("Thoat quan ly phong!");
                default -> System.out.println("Lua chon khong hop le!");
            }
        } while (chon != 0);
    }

    // ================= NHAP DU LIEU =================

    private String nhapChuoi(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) {
                return s;
            }
            System.out.println("Khong duoc de trong!");
        }
    }

    private int nhapSoNguyen(String thongBao) {
        while (true) {
            try {
                System.out.print(thongBao);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so nguyen!");
            }
        }
    }

    /** Nhap gia > 0. Neu macDinh != null thi de trong (Enter) se dung gia mac dinh. */
    private BigDecimal nhapGia(String thongBao, BigDecimal macDinh) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            if (s.isEmpty() && macDinh != null) {
                return macDinh;
            }
            try {
                BigDecimal gia = new BigDecimal(s);
                if (gia.signum() > 0) {
                    return gia;
                }
                System.out.println("Gia phai lon hon 0!");
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so hop le!");
            }
        }
    }

    private LocalDate nhapNgay(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            try {
                return LocalDate.parse(sc.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Sai dinh dang ngay (yyyy-MM-dd, VD: 2026-11-30)!");
            }
        }
    }

    private int nhapLoaiPhong() {
        while (true) {
            System.out.println("\n--- LOAI PHONG ---");
            System.out.println("1. Phong thuong");
            System.out.println("2. Phong VIP");
            int chon = nhapSoNguyen("Chon loai phong: ");
            if (chon == 1 || chon == 2) {
                return chon;
            }
            System.out.println("Lua chon khong hop le!");
        }
    }

    /** Hien menu tu cac gia tri cho phep (rong = tat ca gia tri cua enum). */
    private TinhTrangPhong nhapTinhTrang(TinhTrangPhong... choPhep) {
        TinhTrangPhong[] ds = (choPhep == null || choPhep.length == 0)
                ? TinhTrangPhong.values() : choPhep;
        while (true) {
            System.out.println("\n--- TINH TRANG PHONG ---");
            for (int i = 0; i < ds.length; i++) {
                System.out.println((i + 1) + ". " + ds[i]);
            }
            int chon = nhapSoNguyen("Chon tinh trang: ");
            if (chon >= 1 && chon <= ds.length) {
                return ds[chon - 1];
            }
            System.out.println("Lua chon khong hop le! Vui long nhap lai.");
        }
    }

    // ================= TAO PHONG / GIA =================

    private BigDecimal giaMacDinh(int loaiPhong) {
        return loaiPhong == 1 ? PhongThuong.getGiaMacDinh() : PhongVIP.getGiaMacDinh();
    }

    private Phong taoPhong(int loaiPhong, String maPhong, String tenPhong,
                           BigDecimal giaPhong, TinhTrangPhong tinhTrang, String dichVu) {
        if (loaiPhong == 1) {
            return new PhongThuong(maPhong, tenPhong, giaPhong, tinhTrang, dichVu);
        }
        return new PhongVIP(maPhong, tenPhong, giaPhong, tinhTrang, dichVu);
    }

    // ================= DICH VU =================

    private boolean dichVuHoatDong(DichVu dv) {
        String tt = dv.getTrangThai();
        return tt != null
                && (tt.equalsIgnoreCase("DANG_HOAT_DONG") || tt.equalsIgnoreCase("HOAT_DONG"));
    }

    private String chonNhieuDichVu() {
        DichVu[] dsDichVu = dichVuDAO.layDanhSach();
        if (dsDichVu.length == 0) {
            System.out.println("Chua co dich vu nao!");
            return "";
        }

        List<String> daChon = new ArrayList<>();

        while (true) {
            System.out.println("\n========== DANH SACH DICH VU ==========");
            for (int i = 0; i < dsDichVu.length; i++) {
                System.out.printf("%d. %s - %s - %s VND - %s%n",
                        i + 1,
                        dsDichVu[i].getMaDichVu(),
                        dsDichVu[i].getTenDichVu(),
                        dsDichVu[i].getGia(),
                        dsDichVu[i].getTrangThai());
            }
            System.out.println("0. Hoan tat");

            int chon = nhapSoNguyen("Chon dich vu: ");
            if (chon == 0) {
                break;
            }
            if (chon < 1 || chon > dsDichVu.length) {
                System.out.println("Lua chon khong hop le!");
                continue;
            }

            DichVu dv = dsDichVu[chon - 1];
            if (!dichVuHoatDong(dv)) {
                System.out.println("Dich vu khong hoat dong!");
                continue;
            }
            if (daChon.contains(dv.getMaDichVu())) {
                System.out.println("Dich vu da duoc chon!");
                continue;
            }
            daChon.add(dv.getMaDichVu());
            System.out.println("Da chon: " + dv.getTenDichVu());
        }
        return String.join(",", daChon);
    }

    /** Doi chuoi ma "DV01,DV02" thanh chuoi ten de hien thi. */
    private String tenDichVu(String dsMa) {
        if (dsMa == null || dsMa.trim().isEmpty()) {
            return "(khong co)";
        }
        DichVu[] ds = dichVuDAO.layDanhSach();
        List<String> ten = new ArrayList<>();
        for (String ma : dsMa.split(",")) {
            ma = ma.trim();
            if (ma.isEmpty()) {
                continue;
            }
            String hienThi = ma + " (da xoa)";
            for (DichVu dv : ds) {
                if (dv != null && ma.equals(dv.getMaDichVu())) {
                    hienThi = dv.getTenDichVu();
                    break;
                }
            }
            ten.add(hienThi);
        }
        return ten.isEmpty() ? "(khong co)" : String.join(", ", ten);
    }

    // ================= IN BANG =================

    private void inBang(Phong[] ds) {
        System.out.println(DUONG_KE);
        System.out.printf("%-5s %-10s %-18s %-8s %-14s %-15s %-" + DO_RONG_DICH_VU + "s%n",
                "STT", "Ma phong", "Ten phong", "Loai", "Gia phong", "Tinh trang", "Dich vu");
        System.out.println(DUONG_KE);
        for (int i = 0; i < ds.length; i++) {
            inThongTinPhong(i + 1, ds[i]);
        }
        System.out.println(DUONG_KE);
    }

    private void inThongTinPhong(int stt, Phong p) {
        System.out.printf("%-5d %-10s %-18s %-8s %-14s %-15s %-" + DO_RONG_DICH_VU + "s%n",
                stt,
                p.getMaPhong(),
                p.getTenPhong(),
                p.getLoaiPhong(),
                p.getGiaPhong().toPlainString(),
                p.getTinhTrang(),
                catChuoi(tenDichVu(p.getDichVu()), DO_RONG_DICH_VU));
    }

    /** Cat chuoi qua dai va them "..." de khong lam vo bang. */
    private String catChuoi(String s, int doDaiToiDa) {
        if (s.length() <= doDaiToiDa) {
            return s;
        }
        return s.substring(0, doDaiToiDa - 3) + "...";
    }

    // ================= THEM PHONG =================

    public void themPhong() {
        System.out.println("\n========== THEM PHONG ==========");

        String maPhong = nhapChuoi("Nhap ma phong: ");
        while (qlPhong.timPhongTheoMa(maPhong) != null) {
            System.out.println("Ma phong da ton tai! Vui long nhap ma khac.");
            maPhong = nhapChuoi("Nhap ma phong: ");
        }

        String tenPhong = nhapChuoi("Nhap ten phong: ");
        int loaiPhong = nhapLoaiPhong();

        BigDecimal macDinh = giaMacDinh(loaiPhong);
        BigDecimal giaPhong = nhapGia(
                "Nhap gia phong (Enter de dung gia mac dinh " + macDinh.toPlainString() + " VND): ",
                macDinh);

        String dichVu = chonNhieuDichVu();

        Phong phong = taoPhong(loaiPhong, maPhong, tenPhong, giaPhong,
                TinhTrangPhong.TRONG, dichVu);

        if (qlPhong.themPhong(phong)) {
            System.out.println("Them phong thanh cong!");
        } else {
            System.out.println("Them phong that bai!");
        }
    }

    // ================= HIEN THI / TIM =================

    public void hienThiDanhSachPhong() {
        System.out.println("\n========== DANH SACH PHONG ==========");
        Phong[] ds = qlPhong.layDanhSachPhong();
        if (ds.length == 0) {
            System.out.println("Danh sach phong trong!");
            return;
        }
        inBang(ds);
        System.out.println("Tong so phong: " + ds.length);
    }

    public void timPhongTheoMa() {
        System.out.println("\n========== TIM PHONG THEO MA ==========");
        Phong phong = qlPhong.timPhongTheoMa(nhapChuoi("Nhap ma phong can tim: "));
        if (phong == null) {
            System.out.println("Khong tim thay phong!");
            return;
        }
        inBang(new Phong[] { phong });
        System.out.println("Dich vu day du: " + tenDichVu(phong.getDichVu()));
    }

    public void timPhongTheoTinhTrang() {
        System.out.println("\n========== TIM PHONG THEO TINH TRANG ==========");
        Phong[] ds = qlPhong.layDanhSachPhongTheoTinhTrang(nhapTinhTrang());
        if (ds.length == 0) {
            System.out.println("Khong co phong nao co tinh trang nay!");
            return;
        }
        inBang(ds);
        System.out.println("So phong tim thay: " + ds.length);
    }

    public void timPhongTrong() {
        System.out.println("\n========== TIM PHONG TRONG THEO NGAY ==========");
        LocalDate nhan = nhapNgay("Nhap ngay nhan phong (yyyy-MM-dd): ");
        LocalDate tra = nhapNgay("Nhap ngay tra phong (yyyy-MM-dd): ");
        if (!tra.isAfter(nhan)) {
            System.out.println("Ngay tra phai sau ngay nhan!");
            return;
        }
        Phong[] ds = qlPhong.layPhongTrong(nhan, tra);
        if (ds.length == 0) {
            System.out.println("Khong co phong trong trong khoang thoi gian nay!");
            return;
        }
        inBang(ds);
        System.out.println("So phong trong: " + ds.length);
    }

    // ================= SUA PHONG =================

    public void suaPhong() {
        System.out.println("\n========== SUA THONG TIN PHONG ==========");

        Phong cu = qlPhong.timPhongTheoMa(nhapChuoi("Nhap ma phong can sua: "));
        if (cu == null) {
            System.out.println("Khong tim thay phong!");
            return;
        }

        System.out.println("\nThong tin phong hien tai:");
        inBang(new Phong[] { cu });

        // Phong DA_DAT / DANG_SU_DUNG do nghiep vu dat phong quan ly, khong sua tay
        boolean choDoiTinhTrang = cu.getTinhTrang() == TinhTrangPhong.TRONG
                || cu.getTinhTrang() == TinhTrangPhong.DANG_BAO_TRI;

        Phong moi = cu.taoBanSao();

        while (true) {
            System.out.println("\n--- CHON MUC CAN SUA ---");
            System.out.println("1. Ten phong");
            System.out.println("2. Gia phong");
            System.out.println("3. Dich vu");
            System.out.println("4. Tinh trang (chi Trong / Dang bao tri)");
            System.out.println("5. Luu thay doi");
            System.out.println("0. Huy");

            int chon = nhapSoNguyen("Chon: ");
            switch (chon) {
                case 1 -> moi.setTenPhong(nhapChuoi("Nhap ten phong moi: "));
                case 2 -> moi.setGiaPhong(nhapGia("Nhap gia phong moi: ", null));
                case 3 -> moi.setDichVu(chonNhieuDichVu());
                case 4 -> {
                    if (choDoiTinhTrang) {
                        moi.setTinhTrang(nhapTinhTrang(
                                TinhTrangPhong.TRONG, TinhTrangPhong.DANG_BAO_TRI));
                    } else {
                        System.out.println("Phong dang " + cu.getTinhTrang()
                                + ": tinh trang do nghiep vu dat phong quan ly, khong sua tay!");
                    }
                }
                case 5 -> {
                    if (qlPhong.suaPhong(moi)) {
                        System.out.println("Cap nhat phong thanh cong!");
                    } else {
                        System.out.println("Cap nhat phong that bai!");
                    }
                    return;
                }
                case 0 -> {
                    System.out.println("Da huy thao tac sua!");
                    return;
                }
                default -> System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // ================= XOA PHONG =================

    public void xoaPhong() {
        System.out.println("\n========== XOA PHONG ==========");

        Phong phong = qlPhong.timPhongTheoMa(nhapChuoi("Nhap ma phong can xoa: "));
        if (phong == null) {
            System.out.println("Khong tim thay phong!");
            return;
        }

        System.out.println("\nThong tin phong can xoa:");
        inBang(new Phong[] { phong });

        System.out.print("\nBan co chac chan muon xoa? (Y/N): ");
        if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
            if (qlPhong.xoaPhong(phong.getMaPhong())) {
                System.out.println("Xoa phong thanh cong!");
            } else {
                System.out.println("Xoa phong that bai!");
            }
        } else {
            System.out.println("Da huy thao tac xoa!");
        }
    }

    // ================= THONG KE =================

    public void demTongSoPhong() {
        System.out.println("\n========== THONG KE PHONG ==========");
        System.out.println("Tong so phong hien co: " + qlPhong.demSoLuongPhong());
    }

    public void thongKePhongTheoTinhTrang() {
        System.out.println("\n========== THONG KE THEO TINH TRANG ==========");
        for (TinhTrangPhong tt : TinhTrangPhong.values()) {
            System.out.println(tt + ": " + qlPhong.demSoLuongPhongTheoTinhTrang(tt) + " phong");
        }
        System.out.println("--------------------------------------");
        System.out.println("Tong so phong: " + qlPhong.demSoLuongPhong());
    }
}