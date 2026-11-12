
package ui;

import java.math.BigDecimal;
import java.util.Scanner;

import model.Phong;
import model.PhongThuong;
import model.PhongVIP;
import model.TinhTrangPhong;

import INTERFACE.IPhong;
import service.QLPhong;

public class PhongUI {
    private final Scanner sc = new Scanner(System.in);
    private final IPhong qlPhong;
    public PhongUI() {
        qlPhong = new QLPhong();
    }

    public void menuPhong() {
        int chon;
        do {
            System.out.println("\n======================================");
            System.out.println("          QUAN LY PHONG KHACH SAN");
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
                case 1:
                    themPhong();
                    break;

                case 2:
                    hienThiDanhSachPhong();
                    break;

                case 3:
                    timPhongTheoMa();
                    break;

                case 4:
                    timPhongTheoTinhTrang();
                    break;

                case 5:
                    suaPhong();
                    break;

                case 6:
                    xoaPhong();
                    break;

                case 7:
                    demTongSoPhong();
                    break;

                case 8:
                    thongKePhongTheoTinhTrang();
                    break;

                case 0:
                    System.out.println("Thoat quan ly phong!");
                    break;

                default:
                    System.out.println("Lua chon khong hop le!");
            }

        } while (chon != 0);
    }

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
    private TinhTrangPhong nhapTinhTrang() {
        while (true) {
            System.out.println("\n--- TINH TRANG PHONG ---");
            System.out.println("1. TRONG");
            System.out.println("2. DANG_SU_DUNG");
            System.out.println("3. DANG_BAO_TRI");
            int chon = nhapSoNguyen("Chon tinh trang: ");
            switch (chon) {
                case 1:
                    return TinhTrangPhong.TRONG;

                case 2:
                    return TinhTrangPhong.DANG_SU_DUNG;

                case 3:
                    return TinhTrangPhong.DANG_BAO_TRI;

                default:
                    System.out.println("Lua chon khong hop le!");
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
    private Phong taoPhong(
            int loaiPhong,
            String maPhong,
            String tenPhong,
            BigDecimal giaPhong,
            TinhTrangPhong tinhTrang) {
        if (loaiPhong == 1) {
            return new PhongThuong(
                    maPhong, tenPhong, "Thuong",
                    giaPhong, tinhTrang, tenPhong);
        }
        return new PhongVIP(
                maPhong, tenPhong, "VIP",
                giaPhong, tinhTrang, tenPhong);
    }
    private int layLoaiPhong(Phong phong) {
        if (phong instanceof PhongVIP) {
            return 2;
        }
        return 1;
    }
    public void themPhong() {
        System.out.println("\n========== THEM PHONG ==========");
        String maPhong = nhapChuoi("Nhap ma phong: ");
        String tenPhong = nhapChuoi("Nhap ten phong: ");
        int loaiPhong = nhapLoaiPhong();
        BigDecimal giaPhong = nhapGiaPhong();
        TinhTrangPhong tinhTrang = nhapTinhTrang();
        Phong phong = taoPhong(
                loaiPhong,
                maPhong,
                tenPhong,
                giaPhong,
                tinhTrang);
        if (qlPhong.themPhong(phong)) {
            System.out.println("Them phong thanh cong!");
        } else {
            System.out.println("Them phong that bai!");
        }
    }
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
        System.out.println("==============================================================");
        System.out.println("Tong so phong: " + dsPhong.length);
    }
    private void inTieuDeBang() {
        System.out.println(
            "--------------------------------------------------------------------------");
        System.out.printf(
            "%-5s %-12s %-15s %-12s %-18s %-15s%n",
            "STT", "Ma phong", "Ten phong",
            "Loai phong", "Gia phong", "Tinh trang");
        System.out.println(
            "--------------------------------------------------------------------------");
    }

    private void inThongTinPhong(int stt, Phong phong) {
        String loaiPhong = phong instanceof PhongVIP
                ? "VIP" : "Thuong";
        System.out.printf(
            "%-5d %-12s %-15s %-12s %-18s %-15s%n",
            stt,
            phong.getMaPhong(),
            phong.getTenPhong(),
            loaiPhong,
            phong.getGiaPhong().toPlainString(),
            phong.getTinhTrang());
    }

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
            "--------------------------------------------------------------------------");
    }

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
            "--------------------------------------------------------------------------");
    }

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
        int loaiPhong = layLoaiPhong(phongCu);
        Phong phongMoi = taoPhong(
                loaiPhong,
                phongCu.getMaPhong(),
                tenPhong,
                giaPhong,
                tinhTrang);
        if (qlPhong.suaPhong(phongMoi)) {
            System.out.println("Cap nhat phong thanh cong!");
        } else {
            System.out.println("Cap nhat phong that bai!");
        }
    }

    public void xoaPhong() {
        System.out.println("\n========== XOA PHONG ==========");
        String maPhong = nhapChuoi("Nhap ma phong can xoa: ");
        Phong phong = qlPhong.timPhongTheoMa(maPhong);
        if (phong == null) {
            System.out.println("Khong tim thay phong!");
            return;
        }
        System.out.println("Thong tin phong can xoa:");
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

    public void demTongSoPhong() {
        int soLuong = qlPhong.demSoLuongPhong();
        System.out.println("\n========== THONG KE PHONG ==========");
        System.out.println("Tong so phong hien co: " + soLuong);
    }

    public void thongKePhongTheoTinhTrang() {
        System.out.println("\n========== THONG KE THEO TINH TRANG ==========");
        for (TinhTrangPhong tinhTrang : TinhTrangPhong.values()) {
            int soLuong =
                    qlPhong.demSoLuongPhongTheoTinhTrang(tinhTrang);
            System.out.println(tinhTrang + ": " + soLuong + " phong");
        }
    }
}
