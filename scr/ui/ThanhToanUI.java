package ui;

import INTERFACE.IHoaDon;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;
import model.ChiTietDichVu;
import model.DichVu;
import model.HoaDon;
import model.ThanhToan;
import model.TrangThaiThanhToan;
import service.QLDichVu;
import service.QLThanhToan;
import service.QLHoaDon;

public class ThanhToanUI {
    private final Scanner sc = new Scanner(System.in);
    private final IHoaDon qlThanhToan = new QLHoaDon();
    private final QLHoaDon qlHoaDon = (QLHoaDon) qlThanhToan;
    private final QLThanhToan qlThanhToanService = new QLThanhToan();
    private final QLDichVu qlDichVu = new QLDichVu();

    public void menuThanhToan() {
        int chon;
        do {
            System.out.println("\n==============================================");
            System.out.println(" QUAN LY DICH VU, HOA DON VA THANH TOAN");
            System.out.println("==============================================");
            System.out.println("NHOM 1 - DANH MUC DICH VU");
            System.out.println(" 1. Xem danh sach dich vu");
            System.out.println(" 2. Them dich vu");
            System.out.println(" 3. Sua dich vu");
            System.out.println(" 4. Xoa dich vu");
            System.out.println(" 5. Tim dich vu theo ma/ten");
            System.out.println("NHOM 2 - DICH VU SU DUNG THEO PHIEU DAT PHONG");
            System.out.println(" 6. Them dich vu da su dung");
            System.out.println(" 7. Sua so luong dich vu da goi");
            System.out.println(" 8. Xoa dich vu da goi nham");
            System.out.println(" 9. Xem chi tiet dich vu theo dat phong");
            System.out.println("10. Xem toan bo chi tiet dich vu");
            System.out.println("NHOM 3 - HOA DON VA THANH TOAN");
            System.out.println("11. Lap hoa don tu dong tu dat phong");
            System.out.println("12. Them hoa don");
            System.out.println("13. Thanh toan hoa don");
            System.out.println("14. Xem danh sach hoa don");
            System.out.println("15. Tim hoa don va in phieu chi tiet");
            System.out.println("16. Sua thong tin hoa don");
            System.out.println("17. Xoa hoa don");
            System.out.println("18. Xem lich su giao dich");
            System.out.println(" 0. Quay lai");
            chon = nhapSoNguyen("Nhap lua chon: ");
            switch (chon) {
                case 1: hienThiDichVu(qlDichVu.layDanhSachDichVu()); break;
                case 2: themDichVu(); break;
                case 3: suaDichVu(); break;
                case 4: xoaDichVu(); break;
                case 5: timDichVu(); break;
                case 6: themChiTiet(); break;
                case 7: suaChiTiet(); break;
                case 8: xoaChiTiet(); break;
                case 9: xemChiTietTheoDatPhong(); break;
                case 10: inDanhSachChiTiet(qlDichVu.layDanhSachChiTiet()); break;
                case 11: lapHoaDonTuDatPhong(); break;
                case 12: themHoaDon(); break;
                case 13: thanhToanHoaDon(); break;
                case 14: hienThiDanhSachHoaDon(); break;
                case 15: timVaInHoaDon(); break;
                case 16: suaHoaDon(); break;
                case 17: xoaHoaDon(); break;
                case 18: xemLichSuThanhToan(); break;
                case 0: System.out.println("Quay lai menu truoc..."); break;
                default: System.out.println("Lua chon khong hop le!");
            }
        } while (chon != 0);
    }

    private void themDichVu() {
        System.out.println("\n--- THEM DICH VU ---");
        String ma = nhapChuoi("Ma dich vu: ");
        String ten = nhapChuoi("Ten dich vu: ");
        String loai = nhapChuoi("Loai dich vu: ");
        String donVi = nhapChuoi("Don vi tinh: ");
        BigDecimal gia = nhapTien("Gia dich vu: ", false);
        System.out.println("1. DANG_HOAT_DONG  2. NGUNG_HOAT_DONG");
        int chonTrangThai = nhapLuaChon("Chon trang thai: ", 1, 2);
        String trangThai = chonTrangThai == 1 ? "DANG_HOAT_DONG" : "NGUNG_HOAT_DONG";
        String moTa = nhapChuoiChoPhepTrong("Mo ta (Enter de bo qua): ");
        DichVu dv = new DichVu(ma, ten, loai, donVi, gia, trangThai, moTa);
        System.out.println(qlDichVu.themDichVu(dv) ? "Them dich vu thanh cong!" : "Them dich vu that bai!");
    }

    private void suaDichVu() {
        String ma = nhapChuoi("Ma dich vu can sua: ");
        DichVu cu = qlDichVu.timDichVuTheoMa(ma);
        if (cu == null) { System.out.println("Khong tim thay dich vu!"); return; }
        String ten = nhapChuoi("Ten moi: ");
        String loai = nhapChuoi("Loai moi: ");
        String donVi = nhapChuoi("Don vi tinh moi: ");
        BigDecimal gia = nhapTien("Gia moi: ", false);
        System.out.println("1. DANG_HOAT_DONG  2. NGUNG_HOAT_DONG");
        int chonTrangThai = nhapLuaChon("Chon trang thai: ", 1, 2);
        String trangThai = chonTrangThai == 1 ? "DANG_HOAT_DONG" : "NGUNG_HOAT_DONG";
        String moTa = nhapChuoiChoPhepTrong("Mo ta moi (Enter de bo qua): ");
        DichVu dv = new DichVu(ma, ten, loai, donVi, gia, trangThai, moTa);
        System.out.println(qlDichVu.suaDichVu(dv) ? "Cap nhat dich vu thanh cong!" : "Cap nhat dich vu that bai!");
    }

    private void xoaDichVu() {
        String ma = nhapChuoi("Ma dich vu can xoa: ");
        System.out.println(qlDichVu.xoaDichVu(ma) ? "Xoa dich vu thanh cong!" : "Xoa dich vu that bai!");
    }

    private void timDichVu() {
        String tuKhoa = nhapChuoi("Nhap ma hoac ten dich vu: ");
        hienThiDichVu(qlDichVu.timDichVu(tuKhoa));
    }

    private void hienThiDichVu(DichVu[] ds) {
        System.out.println("\n--- DANH SACH DICH VU ---");
        if (ds == null || ds.length == 0) { System.out.println("Danh sach dich vu trong!"); return; }
        System.out.printf("%-5s %-12s %-24s %-16s %-12s %-16s %-20s%n", "STT", "Ma DV", "Ten dich vu", "Loai", "Don vi", "Gia", "Trang thai");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        int stt = 0;
        for (DichVu dv : ds) if (dv != null) {
            System.out.printf("%-5d %-12s %-24s %-16s %-12s %-16s %-20s%n", ++stt, safe(dv.getMaDichVu()), safe(dv.getTenDichVu()), safe(dv.getLoaiDichVu()), safe(dv.getDonViTinh()), tien(dv.getGia()), safe(dv.getTrangThai()));
        }
    }

    private void themChiTiet() {
        String maDP = nhapChuoi("Ma dat phong: ");
        String maDV = nhapChuoi("Ma dich vu: ");
        int soLuong = nhapSoDuong("So luong: ");
        ChiTietDichVu ct = new ChiTietDichVu(maDP, maDV, LocalDate.now(), LocalDate.now(), soLuong, BigDecimal.ZERO);
        System.out.println(qlDichVu.themChiTiet(ct) ? "Ghi nhan dich vu thanh cong!" : "Ghi nhan dich vu that bai!");
    }

    private void suaChiTiet() {
        String maDP = nhapChuoi("Ma dat phong: ");
        String maDV = nhapChuoi("Ma dich vu can sua: ");
        ChiTietDichVu cu = qlDichVu.timChiTiet(maDP, maDV);
        if (cu == null) { System.out.println("Khong tim thay chi tiet dich vu!"); return; }
        cu.setSoLuong(nhapSoDuong("So luong moi: "));
        System.out.println(qlDichVu.suaChiTiet(cu) ? "Cap nhat chi tiet thanh cong!" : "Cap nhat chi tiet that bai!");
    }

    private void xoaChiTiet() {
        String maDP = nhapChuoi("Ma dat phong: ");
        String maDV = nhapChuoi("Ma dich vu can xoa: ");
        System.out.println(qlDichVu.xoaChiTiet(maDP, maDV) ? "Xoa chi tiet thanh cong!" : "Xoa chi tiet that bai!");
    }

    private void xemChiTietTheoDatPhong() {
        String maDP = nhapChuoi("Ma dat phong: ");
        inDanhSachChiTiet(qlDichVu.layChiTietTheoDatPhong(maDP));
        System.out.println("Tong tien dich vu: " + tien(qlDichVu.tinhTongTienDichVu(maDP)));
    }

    private void inDanhSachChiTiet(ChiTietDichVu[] ds) {
        System.out.println("\n--- CHI TIET DICH VU DA SU DUNG ---");
        if (ds == null || ds.length == 0) { System.out.println("Khong co chi tiet dich vu."); return; }
        System.out.printf("%-5s %-14s %-12s %-24s %-12s %-14s %-14s %-16s%n", "STT", "Ma dat phong", "Ma DV", "Ten dich vu", "So luong", "Don gia", "Thanh tien", "Ngay dat DV");
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        int stt = 0;
        for (ChiTietDichVu ct : ds) if (ct != null) {
            System.out.printf("%-5d %-14s %-12s %-24s %-12d %-14s %-14s %-16s%n", ++stt, safe(ct.getMaDatPhong()), safe(ct.getMaDichVu()), safe(qlDichVu.layTenDichVu(ct.getMaDichVu())), ct.getSoLuong(), tien(ct.getDonGia()), tien(ct.getThanhTien()), ct.getNgayDatDichVu());
        }
    }

    private void lapHoaDonTuDatPhong() {
        String maHD = nhapChuoi("Ma hoa don: ");
        String maDP = nhapChuoi("Ma dat phong: ");
        BigDecimal giamGia = nhapTien("Giam gia: ", true);
        HoaDon hd = qlHoaDon.lapHoaDonTuDatPhong(maHD, maDP, giamGia);
        if (hd == null) { System.out.println("Lap hoa don that bai!"); return; }
        System.out.println("Lap hoa don thanh cong!");
        inPhieuHoaDon(hd);
    }

    private void themHoaDon() {
        String maHD = nhapChuoi("Ma hoa don: ");
        String maDP = nhapChuoi("Ma dat phong: ");
        if (qlHoaDon.daCoHoaDon(maDP)) { System.out.println("Dat phong nay da co hoa don!"); return; }
        BigDecimal tongGia = nhapTien("Tong gia: ", true);
        BigDecimal giamGia = nhapTien("Giam gia: ", true);
        if (giamGia.compareTo(tongGia) > 0) { System.out.println("Giam gia khong duoc lon hon tong gia."); return; }
        HoaDon hd = new HoaDon(maHD, maDP, LocalDate.now(), tongGia, giamGia);
        System.out.println(qlThanhToan.themHoaDon(hd) ? "Them hoa don thanh cong!" : "Them hoa don that bai!");
    }

    private void thanhToanHoaDon() {
        String maHD = nhapChuoi("Ma hoa don can thanh toan: ");
        HoaDon hd = qlHoaDon.timHoaDon(maHD);
        if (hd == null) { System.out.println("Khong tim thay hoa don!"); return; }
        System.out.println("1. Tien mat  2. Chuyen khoan  3. The");
        int chon = nhapLuaChon("Chon phuong thuc: ", 1, 3);
        String phuongThuc = chon == 1 ? "Tien mat" : chon == 2 ? "Chuyen khoan" : "The";
        String maGD = nhapChuoi("Ma giao dich: ");
        System.out.println(qlThanhToanService.thanhToan(maHD, maGD, phuongThuc) ? "Thanh toan thanh cong!" : "Thanh toan that bai!");
    }

    private void hienThiDanhSachHoaDon() {
        HoaDon[] ds = qlThanhToan.layDanhSachHoaDon();
        System.out.println("\n--- DANH SACH HOA DON ---");
        if (ds == null || ds.length == 0) { System.out.println("Danh sach hoa don trong!"); return; }
        System.out.printf("%-5s %-14s %-14s %-14s %-14s %-14s %-20s%n", "STT", "Ma HD", "Ma dat phong", "Tong gia", "Giam gia", "Thanh tien", "Trang thai");
        System.out.println("--------------------------------------------------------------------------------------------------");
        int stt = 0;
        for (HoaDon hd : ds) if (hd != null) inDongHoaDon(++stt, hd);
    }

    private void inDongHoaDon(int stt, HoaDon hd) {
        System.out.printf("%-5d %-14s %-14s %-14s %-14s %-14s %-20s%n", stt, safe(hd.getMaHD()), safe(hd.getMaDatPhong()), tien(hd.getTongGia()), tien(hd.getGiamGia()), tien(hd.getThanhTien()), hd.getTrangThai());
    }

    private void timVaInHoaDon() {
        String maHD = nhapChuoi("Ma hoa don can tim: ");
        HoaDon hd = qlHoaDon.timHoaDon(maHD);
        if (hd == null) { System.out.println("Khong tim thay hoa don!"); return; }
        inPhieuHoaDon(hd);
    }

    private void inPhieuHoaDon(HoaDon hd) {
        System.out.println("\n================ PHIEU HOA DON ================");
        System.out.println("Ma hoa don: " + safe(hd.getMaHD()));
        System.out.println("Ma dat phong: " + safe(hd.getMaDatPhong()));
        System.out.println("Ngay lap: " + hd.getNgayLap());
        ChiTietDichVu[] ds = qlDichVu.layChiTietTheoDatPhong(hd.getMaDatPhong());
        inDanhSachChiTiet(ds);
        BigDecimal tienDichVu = qlDichVu.tinhTongTienDichVu(hd.getMaDatPhong());
        BigDecimal tongGia = hd.getTongGia() == null ? BigDecimal.ZERO : hd.getTongGia();
        BigDecimal giamGia = hd.getGiamGia() == null ? BigDecimal.ZERO : hd.getGiamGia();
        BigDecimal tienPhong = qlHoaDon.tinhTienPhongTrongHoaDon(hd);
        System.out.println("Tien phong: " + tien(tienPhong));
        System.out.println("Tien dich vu: " + tien(tienDichVu));
        System.out.println("Tong gia: " + tien(tongGia));
        System.out.println("Giam gia: " + tien(giamGia));
        System.out.println("Thanh tien: " + tien(hd.getThanhTien()));
        System.out.println("Trang thai: " + hd.getTrangThai());
        System.out.println("================================================");
    }

    private void suaHoaDon() {
        String maHD = nhapChuoi("Ma hoa don can sua: ");
        HoaDon cu = qlHoaDon.timHoaDon(maHD);
        if (cu == null) { System.out.println("Khong tim thay hoa don!"); return; }
        if (cu.getTrangThai() == TrangThaiThanhToan.DA_THANH_TOAN) { System.out.println("Khong the sua hoa don da thanh toan."); return; }
        BigDecimal tongGia = nhapTien("Tong gia moi: ", true);
        BigDecimal giamGia = nhapTien("Giam gia moi: ", true);
        if (giamGia.compareTo(tongGia) > 0) { System.out.println("Giam gia phai nam trong khoang 0 den tong gia."); return; }
        cu.setTongGia(tongGia);
        cu.setGiamGia(giamGia);
        System.out.println(qlThanhToan.suaHoaDon(cu) ? "Cap nhat hoa don thanh cong!" : "Cap nhat hoa don that bai!");
    }

    private void xoaHoaDon() {
        String maHD = nhapChuoi("Ma hoa don can xoa: ");
        HoaDon hd = qlHoaDon.timHoaDon(maHD);
        if (hd == null) { System.out.println("Khong tim thay hoa don!"); return; }
        if (hd.getTrangThai() == TrangThaiThanhToan.DA_THANH_TOAN) { System.out.println("Khong the xoa hoa don da thanh toan."); return; }
        System.out.println(qlThanhToan.xoaHoaDon(maHD) ? "Xoa hoa don thanh cong!" : "Xoa hoa don that bai!");
    }

    private void xemLichSuThanhToan() {
        ThanhToan[] ds = qlThanhToanService.layDanhSachThanhToan();
        System.out.println("\n--- LICH SU GIAO DICH ---");
        if (ds == null || ds.length == 0) { System.out.println("Chua co giao dich."); return; }
        System.out.printf("%-5s %-16s %-14s %-16s %-18s %-16s %-20s%n", "STT", "Ma giao dich", "Ma HD", "So tien", "Phuong thuc", "Ngay thanh toan", "Trang thai");
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        int stt = 0;
        for (ThanhToan gd : ds) if (gd != null) System.out.printf("%-5d %-16s %-14s %-16s %-18s %-16s %-20s%n", ++stt, safe(gd.getMaThanhToan()), safe(gd.getMaHD()), tien(gd.getSoTien()), safe(gd.getPhuongThuc()), gd.getNgayThanhToan(), gd.getTrangThai());
    }

    private String nhapChuoi(String thongBao) {
        while (true) { System.out.print(thongBao); String s = sc.nextLine().trim(); if (!s.isEmpty()) return s; System.out.println("Khong duoc de trong!"); }
    }

    private String nhapChuoiChoPhepTrong(String thongBao) { System.out.print(thongBao); return sc.nextLine().trim(); }

    private int nhapSoNguyen(String thongBao) {
        while (true) { try { System.out.print(thongBao); return Integer.parseInt(sc.nextLine().trim()); } catch (NumberFormatException e) { System.out.println("Vui long nhap so nguyen!"); } }
    }

    private int nhapSoDuong(String thongBao) {
        while (true) { int n = nhapSoNguyen(thongBao); if (n > 0) return n; System.out.println("Gia tri phai lon hon 0!"); }
    }

    private int nhapLuaChon(String thongBao, int min, int max) {
        while (true) { int n = nhapSoNguyen(thongBao); if (n >= min && n <= max) return n; System.out.println("Lua chon khong hop le!"); }
    }

    private BigDecimal nhapTien(String thongBao, boolean choPhepKhong) {
        while (true) {
            try {
                System.out.print(thongBao);
                BigDecimal tien = new BigDecimal(sc.nextLine().trim());
                if (choPhepKhong ? tien.signum() >= 0 : tien.signum() > 0) return tien;
                System.out.println(choPhepKhong ? "Gia tri khong duoc am!" : "Gia tri phai lon hon 0!");
            } catch (NumberFormatException e) { System.out.println("So tien khong hop le!"); }
        }
    }

    private String tien(BigDecimal giaTri) { return giaTri == null ? "0" : giaTri.toPlainString(); }
    private String safe(Object giaTri) { return giaTri == null ? "" : giaTri.toString(); }
}
