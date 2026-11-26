
package ui;

import INTERFACE.IPhong;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.io.BufferedReader;

import model.Phong;
import model.PhongThuong;
import model.PhongVIP;
import model.TinhTrangPhong;

import service.QLPhong;

public class PhongUI {

    private static final String DUONG_KE = "-".repeat(100);

    private final BufferedReader reader;
    private final IPhong qlPhong;

    public PhongUI(BufferedReader reader) {
        this.reader = reader;
        this.qlPhong = new QLPhong();
    }

    public PhongUI() {
        this(ConsoleInput.getReader());
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

            String s = ConsoleInput.readLine(reader).trim();

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
                return Integer.parseInt(ConsoleInput.readLine(reader).trim());

            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so nguyen!");
            }
        }
    }

    private int nhapSoNguyenDuong(String thongBao) {
        while (true) {
            int so = nhapSoNguyen(thongBao);

            if (so > 0) {
                return so;
            }

            System.out.println("Gia tri phai lon hon 0!");
        }
    }

    private boolean nhapCoKhong(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = ConsoleInput.readLine(reader).trim();

            if (s.equalsIgnoreCase("Y")) {
                return true;
            }

            if (s.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Chi nhap Y hoac N!");
        }
    }

    private BigDecimal nhapGia(String thongBao, BigDecimal macDinh) {
        while (true) {
            System.out.print(thongBao);

            String s = ConsoleInput.readLine(reader).trim();

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
                return LocalDate.parse(ConsoleInput.readLine(reader).trim());

            } catch (DateTimeParseException e) {
                System.out.println(
                        "Sai dinh dang ngay (yyyy-MM-dd, VD: 2026-11-30)!"
                );
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

    private TinhTrangPhong nhapTinhTrang(TinhTrangPhong... choPhep) {

        TinhTrangPhong[] ds =
                (choPhep == null || choPhep.length == 0)
                        ? TinhTrangPhong.values()
                        : choPhep;

        while (true) {
            System.out.println("\n--- TINH TRANG PHONG ---");

            for (int i = 0; i < ds.length; i++) {
                System.out.println((i + 1) + ". " + ds[i]);
            }

            int chon = nhapSoNguyen("Chon tinh trang: ");

            if (chon >= 1 && chon <= ds.length) {
                return ds[chon - 1];
            }

            System.out.println("Lua chon khong hop le!");
        }
    }

    // ================= GIA MAC DINH =================

    private BigDecimal giaMacDinh(int loaiPhong) {

        if (loaiPhong == 1) {
            return PhongThuong.getGiaMacDinh();
        }

        return PhongVIP.getGiaMacDinh();
    }

    // ================= TAO PHONG =================

    private Phong taoPhong(
            int loaiPhong,
            String maPhong,
            String tenPhong,
            BigDecimal giaPhong,
            TinhTrangPhong tinhTrang) {

        if (loaiPhong == 1) {

            System.out.println("\n--- THONG TIN PHONG THUONG ---");

            int soGiuong = nhapSoNguyenDuong("Nhap so giuong: ");

            boolean coDieuHoa =
                    nhapCoKhong("Phong co dieu hoa? (Y/N): ");

            return new PhongThuong(
                    maPhong,
                    tenPhong,
                    giaPhong,
                    tinhTrang,
                    soGiuong,
                    coDieuHoa
            );

        } else {

            System.out.println("\n--- THONG TIN PHONG VIP ---");

            int dienTich =
                    nhapSoNguyenDuong("Nhap dien tich (m2): ");

            boolean coBonTam =
                    nhapCoKhong("Phong co bon tam? (Y/N): ");

            return new PhongVIP(
                    maPhong,
                    tenPhong,
                    giaPhong,
                    tinhTrang,
                    dienTich,
                    coBonTam
            );
        }
    }

    // ================= IN BANG =================

    private void inBang(Phong[] ds) {

    System.out.println(DUONG_KE);

    System.out.printf(
            "%-5s %-10s %-15s %-8s %-14s %-18s %-12s %-12s%n",
            "STT",
            "Ma phong",
            "Ten phong",
            "Loai",
            "Gia phong",
            "Tinh trang",
            "Giuong/Dien tich",
            "Tien ich"
    );

    System.out.println(DUONG_KE);

    for (int i = 0; i < ds.length; i++) {

        if (ds[i] != null) {
            inThongTinPhong(i + 1, ds[i]);
        }
    }

    System.out.println(DUONG_KE);
}

    private void inThongTinPhong(int stt, Phong p) {

    String thongTinRieng = "";
    String tienIch = "";

    if (p instanceof PhongThuong pt) {

        thongTinRieng = pt.getSoGiuong() + " giuong";

        tienIch = pt.isCoDieuHoa()
                ? "Co dieu hoa"
                : "Khong";

    } else if (p instanceof PhongVIP pv) {

        thongTinRieng = pv.getDienTich() + " m2";

        tienIch = pv.isCoBonTam()
                ? "Co bon tam"
                : "Khong";
    }

    System.out.printf(
            "%-5d %-10s %-15s %-8s %-14s %-18s %-12s %-12s%n",
            stt,
            p.getMaPhong(),
            p.getTenPhong(),
            p.getLoaiPhong(),
            p.getGiaPhong().toPlainString(),
            p.getTinhTrang(),
            thongTinRieng,
            tienIch
    );
}

    // ================= THEM PHONG =================

    public void themPhong() {

        System.out.println("\n========== THEM PHONG ==========");

        String maPhong = nhapChuoi("Nhap ma phong: ");

        while (qlPhong.timPhongTheoMa(maPhong) != null) {

            System.out.println("Ma phong da ton tai!");

            maPhong = nhapChuoi("Nhap ma phong khac: ");
        }
        
        String tenPhong = nhapChuoi("Nhap ten phong: ");
        while (qlPhong.timPhongTheoTen(tenPhong)) {
            System.out.println("Ten phong da ton tai!");
            tenPhong = nhapChuoi("Nhap ten phong khac: ");
        }

        int loaiPhong = nhapLoaiPhong();

        BigDecimal macDinh = giaMacDinh(loaiPhong);

        BigDecimal giaPhong = nhapGia(
                "Nhap gia phong (Enter de dung gia mac dinh "
                        + macDinh.toPlainString() + " VND): ",
                macDinh
        );

        Phong phong = taoPhong(
                loaiPhong,
                maPhong,
                tenPhong,
                giaPhong,
                TinhTrangPhong.TRONG
        );

        if (qlPhong.themPhong(phong)) {
            System.out.println("Them phong thanh cong!");
        } else {
            System.out.println("Them phong that bai!");
        }
    }

    // ================= HIEN THI =================

    public void hienThiDanhSachPhong() {

        System.out.println("\n========== DANH SACH PHONG ==========");

        Phong[] ds = qlPhong.layDanhSachPhong();

        if (ds == null || ds.length == 0) {
            System.out.println("Danh sach phong trong!");
            return;
        }

        inBang(ds);

        System.out.println("Tong so phong: " + ds.length);
    }

    // ================= TIM PHONG THEO MA =================

    public void timPhongTheoMa() {

        System.out.println("\n========== TIM PHONG THEO MA ==========");

        String ma = nhapChuoi("Nhap ma phong can tim: ");

        Phong phong = qlPhong.timPhongTheoMa(ma);

        if (phong == null) {
            System.out.println("Khong tim thay phong!");
            return;
        }

        inBang(new Phong[]{phong});

        System.out.println("Thong tin chi tiet: " + phong);
    }

    // ================= TIM THEO TINH TRANG =================

    public void timPhongTheoTinhTrang() {

        System.out.println(
                "\n========== TIM PHONG THEO TINH TRANG =========="
        );

        TinhTrangPhong tt = nhapTinhTrang();

        Phong[] ds = qlPhong.layDanhSachPhongTheoTinhTrang(tt);

        if (ds == null || ds.length == 0) {
            System.out.println("Khong co phong nao co tinh trang nay!");
            return;
        }

        inBang(ds);

        System.out.println("So phong tim thay: " + ds.length);
    }

    // ================= TIM PHONG TRONG =================

    public void timPhongTrong() {

        System.out.println(
                "\n========== TIM PHONG TRONG THEO NGAY =========="
        );

        LocalDate nhan = nhapNgay(
                "Nhap ngay nhan phong (yyyy-MM-dd): "
        );

        LocalDate tra = nhapNgay(
                "Nhap ngay tra phong (yyyy-MM-dd): "
        );

        if (tra.isBefore(nhan)) {
            System.out.println("Ngay tra khong duoc truoc ngay nhan!");
            return;
        }

        Phong[] ds = qlPhong.layPhongTrong(nhan, tra);

        if (ds == null || ds.length == 0) {
            System.out.println(
                    "Khong co phong trong trong khoang thoi gian nay!"
            );
            return;
        }

        inBang(ds);

        System.out.println("So phong trong: " + ds.length);
    }

    // ================= SAO CHEP PHONG =================

    private Phong saoChepPhong(Phong cu) {

        if (cu instanceof PhongThuong pt) {

            return new PhongThuong(
                    pt.getMaPhong(),
                    pt.getTenPhong(),
                    pt.getGiaPhong(),
                    pt.getTinhTrang(),
                    pt.getSoGiuong(),
                    pt.isCoDieuHoa()
            );

        } else if (cu instanceof PhongVIP pv) {

            return new PhongVIP(
                    pv.getMaPhong(),
                    pv.getTenPhong(),
                    pv.getGiaPhong(),
                    pv.getTinhTrang(),
                    pv.getDienTich(),
                    pv.isCoBonTam()
            );
        }

        return null;
    }

    // ================= SUA PHONG =================

    public void suaPhong() {

        System.out.println("\n========== SUA THONG TIN PHONG ==========");

        String ma = nhapChuoi("Nhap ma phong can sua: ");

        Phong cu = qlPhong.timPhongTheoMa(ma);

        if (cu == null) {
            System.out.println("Khong tim thay phong!");
            return;
        }

        System.out.println("\nThong tin phong hien tai:");

        inBang(new Phong[]{cu});

        boolean choDoiTinhTrang =
                cu.getTinhTrang() == TinhTrangPhong.TRONG
                || cu.getTinhTrang() == TinhTrangPhong.DANG_BAO_TRI;

        // Tao ban sao de tranh thay doi truc tiep phong goc
        Phong moi = saoChepPhong(cu);

        if (moi == null) {
            System.out.println("Khong the sao chep loai phong nay!");
            return;
        }

        while (true) {

            System.out.println("\n--- CHON MUC CAN SUA ---");
            System.out.println("1. Ten phong");
            System.out.println("2. Gia phong");
            System.out.println("3. Tinh trang");
            System.out.println("4. Luu thay doi");
            System.out.println("0. Huy");

            int chon = nhapSoNguyen("Chon: ");

            switch (chon) {

                case 1 -> {
                    moi.setTenPhong(
                            nhapChuoi("Nhap ten phong moi: ")
                    );
                }

                case 2 -> {
                    moi.setGiaPhong(
                            nhapGia("Nhap gia phong moi: ", null)
                    );
                }

                case 3 -> {

                    if (choDoiTinhTrang) {

                        moi.setTinhTrang(
                                nhapTinhTrang(
                                        TinhTrangPhong.TRONG,
                                        TinhTrangPhong.DANG_BAO_TRI
                                )
                        );

                    } else {

                        System.out.println(
                                "Phong dang " + cu.getTinhTrang()
                                + ": tinh trang do nghiep vu dat phong quan ly!"
                        );
                    }
                }

                case 4 -> {

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

        String ma = nhapChuoi("Nhap ma phong can xoa: ");

        Phong phong = qlPhong.timPhongTheoMa(ma);

        if (phong == null) {
            System.out.println("Khong tim thay phong!");
            return;
        }

        System.out.println("\nThong tin phong can xoa:");

        inBang(new Phong[]{phong});

        System.out.print("\nBan co chac chan muon xoa? (Y/N): ");

        String xacNhan = ConsoleInput.readLine(reader).trim();

        if (xacNhan.equalsIgnoreCase("Y")) {

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

        System.out.println(
                "Tong so phong hien co: "
                        + qlPhong.demSoLuongPhong()
        );
    }

    public void thongKePhongTheoTinhTrang() {

        System.out.println(
                "\n========== THONG KE THEO TINH TRANG =========="
        );

        for (TinhTrangPhong tt : TinhTrangPhong.values()) {

            System.out.println(
                    tt + ": "
                            + qlPhong.demSoLuongPhongTheoTinhTrang(tt)
                            + " phong"
            );
        }

        System.out.println("--------------------------------------");

        System.out.println(
                "Tong so phong: " + qlPhong.demSoLuongPhong()
        );
    }
}
