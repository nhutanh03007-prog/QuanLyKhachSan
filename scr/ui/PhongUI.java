
package ui;


import DAO.DichVuDAO;
import INTERFACE.IPhong;
import java.math.BigDecimal;
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

    private static final DichVuDAO dichVuDAO = new DichVuDAO();
    private final Scanner sc = new Scanner(System.in);
    private final IPhong qlPhong;

    public PhongUI() {
        qlPhong = new QLPhong();
    }

    // ================= MENU PHONG =================

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

                case 0 -> System.out.println("Thoat quan ly phong!");

                default -> System.out.println("Lua chon khong hop le!");
            }

        } while (chon != 0);
    }

    // ================= NHAP DU LIEU =================

    private String nhapChuoi(String thongBao) {
        String ketQua;

        do {
            System.out.print(thongBao);
            ketQua = sc.nextLine().trim();

            if (ketQua.isEmpty()) {
                System.out.println("Khong duoc de trong!");
            }

        } while (ketQua.isEmpty());

        return ketQua;
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

    private BigDecimal nhapGiaPhong() {
        while (true) {
            try {
                System.out.print("Nhap gia phong: ");

                BigDecimal gia = new BigDecimal(sc.nextLine().trim());

                if (gia.compareTo(BigDecimal.ZERO) > 0) {
                    return gia;
                }

                System.out.println("Gia phong phai lon hon 0!");

            } catch (NumberFormatException e) {
                System.out.println("Gia phong khong hop le!");
            }
        }
    }

    // ================= NHAP LOAI PHONG =================

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

    // ================= TAO DOI TUONG PHONG =================

    private Phong taoPhong(
            int loaiPhong,
            String maPhong,
            String tenPhong,
            BigDecimal giaPhong,
            TinhTrangPhong tinhTrang,
            String dichVu) {
        if (loaiPhong == 1) {
            return new PhongThuong(
                    maPhong,
                    tenPhong,
                    "Thuong",
                    giaPhong,
                    tinhTrang,
                    dichVu);
        }

        return new PhongVIP(
                maPhong,
                tenPhong,
                "VIP",
                giaPhong,
                tinhTrang,
                dichVu);
    }

    // ================= LAY LOAI PHONG =================

    private int layLoaiPhong(Phong phong) {
        if (phong instanceof PhongVIP) {
            return 2;
        }

        return 1;
    }
    // ================= LAY DICH VU =================

private String layDichVu(Phong phong) {

        if (phong instanceof PhongVIP vip) {
            return vip.getDichVuVIP();
        }
        
        PhongThuong thuong = (PhongThuong) phong;
        return thuong.getDichVuThuong();
    }

    // ================= CHON DICH VU PHONG =================

    
private String chonNhieuDichVu() {

    DichVu[] dsDichVu = dichVuDAO.layDanhSach();

    if (dsDichVu.length == 0) {
        System.out.println("Chua co dich vu nao!");
        return "";
    }

    String[] dsMaDichVu = new String[dsDichVu.length];
    int soLuongChon = 0;

    while (true) {

        System.out.println("\n========== DANH SACH DICH VU ==========");

        for (int i = 0; i < dsDichVu.length; i++) {

            System.out.printf(
                "%d. %s - %s - %s VND - %s%n",
                i + 1,
                dsDichVu[i].getMaDichVu(),
                dsDichVu[i].getTenDichVu(),
                dsDichVu[i].getGia(),
                dsDichVu[i].getTrangThai()
            );
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

        if (dv.getTrangThai() == null
                || !dv.getTrangThai().equalsIgnoreCase("Hoat dong")) {
            System.out.println("Dich vu khong hoat dong!");
            continue;
        }

        boolean daChon = false;

        for (int i = 0; i < soLuongChon; i++) {
            if (dsMaDichVu[i].equals(dv.getMaDichVu())) {
                daChon = true;
                break;
            }
        }

        if (daChon) {
            System.out.println("Dich vu da duoc chon!");
            continue;
        }

        dsMaDichVu[soLuongChon] = dv.getMaDichVu();
        soLuongChon++;

        System.out.println("Da chon: " + dv.getTenDichVu());
    }

    String ketQua = "";

    for (int i = 0; i < soLuongChon; i++) {

        if (i > 0) {
            ketQua += ",";
        }

        ketQua += dsMaDichVu[i];
    }

    return ketQua;
}


    // ================= THEM PHONG =================

    public void themPhong() {
        System.out.println("\n========== THEM PHONG ==========");

        String maPhong = nhapChuoi("Nhap ma phong: ");

        // Kiem tra ma phong da ton tai
        if (qlPhong.timPhongTheoMa(maPhong) != null) {
            System.out.println("Ma phong da ton tai!");
            return;
        }

        String tenPhong = nhapChuoi("Nhap ten phong: ");

        int loaiPhong = nhapLoaiPhong();

        BigDecimal giaPhong = nhapGiaPhong();

        TinhTrangPhong tinhTrang = TinhTrangPhong.TRONG; // Khi thêm phòng mới, mặc định tình trạng là TRONG

        String dichVu = chonNhieuDichVu();

        Phong phong = taoPhong(
                loaiPhong,
                maPhong,
                tenPhong,
                giaPhong,
                tinhTrang,
                dichVu);

        if (qlPhong.themPhong(phong)) {
            System.out.println("Them phong thanh cong!");
        } else {
            System.out.println("Them phong that bai!");
        }
    }

    // ================= HIEN THI DANH SACH =================

    public void hienThiDanhSachPhong() {
        System.out.println("\n========== DANH SACH PHONG ==========");

        Phong[] dsPhong = qlPhong.layDanhSachPhong();

        if (dsPhong.length == 0) {
            System.out.println("Danh sach phong trong!");
            return;
        }

        inTieuDeBang();

        for (int i = 0; i < dsPhong.length; i++) {
            inThongTinPhong(i + 1, dsPhong[i]);
        }

        System.out.println("================================================================================================");
        System.out.println("Tong so phong: " + dsPhong.length);
    }

    // ================= IN TIEU DE BANG =================

    private void inTieuDeBang() {
        System.out.println(
                "------------------------------------------------------------------------------------------------");

        System.out.printf(
                "%-5s %-12s %-18s %-12s %-18s %-18s %-20s%n",
                "STT",
                "Ma phong",
                "Ten phong",
                "Loai phong",
                "Gia phong",
                "Tinh trang",
                "Dich vu");

        System.out.println(
                "------------------------------------------------------------------------------------------------");
    }

    // ================= IN THONG TIN PHONG =================

    private void inThongTinPhong(int stt, Phong phong) {

        String loaiPhong = phong instanceof PhongVIP
                ? "VIP"
                : "Thuong";

        String dichVu = layDichVu(phong);

        System.out.printf(
                "%-5d %-12s %-18s %-12s %-18s %-18s %-20s%n",
                stt,
                phong.getMaPhong(),
                phong.getTenPhong(),
                loaiPhong,
                phong.getGiaPhong().toPlainString(),
                phong.getTinhTrang(),
                dichVu);
    }

    // ================= TIM PHONG THEO MA =================

    public void timPhongTheoMa() {
        System.out.println("\n========== TIM PHONG THEO MA ==========");

        String maPhong = nhapChuoi("Nhap ma phong can tim: ");

        Phong phong = qlPhong.timPhongTheoMa(maPhong);

        if (phong == null) {
            System.out.println("Khong tim thay phong!");
            return;
        }

        inTieuDeBang();
        inThongTinPhong(1, phong);

        System.out.println(
                "------------------------------------------------------------------------------------------------");
    }

    // ================= TIM PHONG THEO TINH TRANG =================

    public void timPhongTheoTinhTrang() {
        System.out.println("\n========== TIM PHONG THEO TINH TRANG ==========");

        TinhTrangPhong tinhTrang = nhapTinhTrang();

        Phong[] dsPhong =
                qlPhong.layDanhSachPhongTheoTinhTrang(tinhTrang);

        if (dsPhong.length == 0) {
            System.out.println("Khong co phong nao co tinh trang nay!");
            return;
        }

        inTieuDeBang();

        for (int i = 0; i < dsPhong.length; i++) {
            inThongTinPhong(i + 1, dsPhong[i]);
        }

        System.out.println(
                "------------------------------------------------------------------------------------------------");

        System.out.println("So phong tim thay: " + dsPhong.length);
    }

    // ================= SUA THONG TIN PHONG =================

    public void suaPhong() {
        System.out.println("\n========== SUA THONG TIN PHONG ==========");

        String maPhong = nhapChuoi("Nhap ma phong can sua: ");

        Phong phongCu = qlPhong.timPhongTheoMa(maPhong);

        if (phongCu == null) {
            System.out.println("Khong tim thay phong!");
            return;
        }

        System.out.println("\nThong tin phong hien tai:");

        inTieuDeBang();
        inThongTinPhong(1, phongCu);

        System.out.println("\nNhap thong tin moi:");

        String tenPhong = nhapChuoi("Nhap ten phong moi: ");

        BigDecimal giaPhong = nhapGiaPhong();

        TinhTrangPhong tinhTrang = nhapTinhTrang();

        // Giu nguyen loai phong cu
        int loaiPhong = layLoaiPhong(phongCu);

        // Giu nguyen dich vu cu
        String dichVu = layDichVu(phongCu);

        Phong phongMoi = taoPhong(
                loaiPhong,
                phongCu.getMaPhong(),
                tenPhong,
                giaPhong,
                tinhTrang,
                dichVu);

        if (qlPhong.suaPhong(phongMoi)) {
            System.out.println("Cap nhat phong thanh cong!");
        } else {
            System.out.println("Cap nhat phong that bai!");
        }
    }

    // ================= XOA PHONG =================

    public void xoaPhong() {
        System.out.println("\n========== XOA PHONG ==========");

        String maPhong = nhapChuoi("Nhap ma phong can xoa: ");

        Phong phong = qlPhong.timPhongTheoMa(maPhong);

        if (phong == null) {
            System.out.println("Khong tim thay phong!");
            return;
        }

        System.out.println("\nThong tin phong can xoa:");

        inTieuDeBang();
        inThongTinPhong(1, phong);

        System.out.print("\nBan co chac chan muon xoa? (Y/N): ");

        String xacNhan = sc.nextLine().trim();

        if (xacNhan.equalsIgnoreCase("Y")) {

            if (qlPhong.xoaPhong(maPhong)) {
                System.out.println("Xoa phong thanh cong!");
            } else {
                System.out.println("Xoa phong that bai!");
            }

        } else {
            System.out.println("Da huy thao tac xoa!");
        }
    }

    // ================= DEM TONG SO PHONG =================

    public void demTongSoPhong() {

        int soLuong = qlPhong.demSoLuongPhong();

        System.out.println("\n========== THONG KE PHONG ==========");

        System.out.println("Tong so phong hien co: " + soLuong);
    }

    // ================= THONG KE THEO TINH TRANG =================

    public void thongKePhongTheoTinhTrang() {

        System.out.println("\n========== THONG KE THEO TINH TRANG ==========");

        for (TinhTrangPhong tinhTrang : TinhTrangPhong.values()) {

            int soLuong =
                    qlPhong.demSoLuongPhongTheoTinhTrang(tinhTrang);

            System.out.println(
                    tinhTrang + ": " + soLuong + " phong");
        }

        System.out.println("--------------------------------------");

        System.out.println(
                "Tong so phong: " + qlPhong.demSoLuongPhong());
    }
    private TinhTrangPhong nhapTinhTrang() {
    while (true) {
        System.out.println("\n--- TINH TRANG PHONG ---");
        System.out.println("1. " + TinhTrangPhong.TRONG);
        System.out.println("2. " + TinhTrangPhong.DANG_SU_DUNG);
        System.out.println("3. " + TinhTrangPhong.DANG_BAO_TRI);

        int chon = nhapSoNguyen("Chon tinh trang: ");

        switch (chon) {
            case 1 -> {
                return TinhTrangPhong.TRONG;
            }
            case 2 -> {
                return TinhTrangPhong.DANG_SU_DUNG;
            }
            case 3 -> {
                return TinhTrangPhong.DANG_BAO_TRI;
            }
            default -> System.out.println("Lua chon khong hop le! Vui long nhap lai.");
        }
    }
}
}
