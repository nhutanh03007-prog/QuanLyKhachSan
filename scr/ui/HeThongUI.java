package ui;

import java.util.Scanner;
import model.NhanVien;
import model.TaiKhoan;
import service.QLDichVu;
import service.QLHoaDon;
import service.QLKhachHang;
import service.QLNhanVien;
import service.QLPhong;
import service.QLTaiKhoan;
import service.QLThongKe;

public class HeThongUI {
    private final Scanner scanner;
    private final QLTaiKhoan qlTaiKhoan;
    private final QLNhanVien qlNhanVien;
    private final QLThongKe qlThongKe;
    private TaiKhoan taiKhoanHienTai;

    public HeThongUI() {
        this.scanner = new Scanner(System.in);
        this.qlTaiKhoan = new QLTaiKhoan();
        this.qlNhanVien = new QLNhanVien();
        this.qlThongKe = new QLThongKe(
                new QLHoaDon(),
                new QLPhong(),
                new QLKhachHang(),
                new QLDichVu()
        );
    }

    public HeThongUI(QLTaiKhoan qlTaiKhoan, QLNhanVien qlNhanVien, QLThongKe qlThongKe) {
        this.scanner = new Scanner(System.in);
        this.qlTaiKhoan = qlTaiKhoan;
        this.qlNhanVien = qlNhanVien;
        this.qlThongKe = qlThongKe;
    }

    public boolean dangNhap() {
        System.out.println("=== DANG NHAP HE THONG KHACH SAN ===");
        System.out.print("Ten dang nhap: ");
        String username = scanner.nextLine();
        System.out.print("Mat khau: ");
        String password = scanner.nextLine();

        TaiKhoan tk = qlTaiKhoan.kiemTraDangNhap(username, password);

        if (tk != null) {
            if (!"HOAT_DONG".equalsIgnoreCase(tk.getTrangThai())) {
                System.out.println("Tai khoan nay da bi khoa! Vui long lien he Admin.");
                return false;
            }
            this.taiKhoanHienTai = tk;
            System.out.println("Dang nhap thanh cong! Xin chao: " + tk.getTenDangNhap() + " (" + tk.getVaiTro() + ")");
            return true;
        } else {
            System.out.println("Sai ten dang nhap hoac mat khau (hoac tai khoan da bi khoa).");
            return false;
        }
    }

    public void menuQuanLyHeThong() {
        int choice = -1;
        do {
            System.out.println("\n=== QUAN LY HE THONG & NHAN SU ===");
            System.out.println("1. Quan ly Nhan vien (Them/Sua/Xoa/Tim kiem)");
            System.out.println("2. Quan ly Tai khoan (Them/Doi MK/Xoa/Tim kiem)");
            System.out.println("3. Xem Bao cao Thong ke");
            System.out.println("0. Quay lai Menu chinh / Dang xuat");
            System.out.print("Chon chuc nang: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so hop le.");
                continue;
            }

            switch (choice) {
                case 1 -> menuNhanVien();
                case 2 -> menuTaiKhoan();
                case 3 -> menuThongKe();
                case 0 -> System.out.println("Dang thoat chuc nang Quan tri...");
                default -> System.out.println("Lua chon khong hop le.");
            }

        } while (choice != 0);
    }

    private void inTieuDeNhanVien() {
        System.out.printf("%-10s | %-24s | %-8s | %-12s | %-16s | %-16s | %-14s | %12s | %-12s%n",
                "Ma NV", "Ho Ten", "Gioi Tinh", "Ngay Sinh",
                "Dia Chi", "SDT", "Chuc Vu", "Luong", "Trang Thai");

        System.out.println("-".repeat(145));
    }

    private void menuNhanVien() {
        int choice = -1;

        do {
            System.out.println("\n--- QUAN LY NHAN VIEN ---");
            System.out.println("1. Xem danh sach nhan vien");
            System.out.println("2. Them nhan vien moi");
            System.out.println("3. Cap nhat thong tin nhan vien");
            System.out.println("4. Xoa nhan vien");
            System.out.println("5. Tim kiem nhan vien theo ma");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so hop le.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    NhanVien[] ds = qlNhanVien.getDanhSach();

                    if (ds.length == 0) {
                        System.out.println("Chua co nhan vien nao trong he thong.");
                    } else {
                        inTieuDeNhanVien();

                        for (NhanVien nv : ds) {
                            System.out.println(nv.toString());
                        }
                    }
                }

                case 2 -> {
                    System.out.print("Nhap ma nhan vien (VD: NV001): ");
                    String maNV = scanner.nextLine();

                    if (qlNhanVien.timTheoMa(maNV) != null) {
                        System.out.println("That bai: Ma nhan vien da ton tai!");
                        break;
                    }

                    System.out.print("Nhap ho ten: ");
                    String hoTen = scanner.nextLine();

                    System.out.print("Nhap gioi tinh (Nam/Nu): ");
                    String gioiTinh = scanner.nextLine();

                    System.out.print("Nhap ngay sinh (dd/MM/yyyy): ");
                    String ngaySinh = scanner.nextLine();

                    System.out.print("Nhap dia chi: ");
                    String diaChi = scanner.nextLine();

                    System.out.print("Nhap so dien thoai: ");
                    String sdt = scanner.nextLine();

                    System.out.print("Nhap chuc vu (Quan ly/Le tan/Phuc vu...): ");
                    String chucVu = scanner.nextLine();

                    double luong;

                    while (true) {
                        try {
                            System.out.print("Nhap luong co ban: ");
                            luong = Double.parseDouble(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Luong phai la so! Vui long nhap lai.");
                        }
                    }

                    NhanVien nvMoi = new NhanVien(
                            maNV, hoTen, gioiTinh, ngaySinh,
                            diaChi, sdt, chucVu, luong, "Dang lam"
                    );

                    if (qlNhanVien.them(nvMoi)) {
                        System.out.println("Them nhan vien thanh cong!");
                        System.out.println("\n--- TAO TAI KHOAN CHO NHAN VIEN " + maNV + " ---");
                        taoTaiKhoanChoNhanVien(maNV);
                    } else {
                        System.out.println("Them nhan vien that bai!");
                    }
                }

                case 3 -> {
                    System.out.print("Nhap ma NV can sua: ");
                    String maNV = scanner.nextLine();

                    NhanVien nvCu = qlNhanVien.timTheoMa(maNV);

                    if (nvCu == null) {
                        System.out.println("Khong tim thay nhan vien co ma: " + maNV);
                        break;
                    }

                    inTieuDeNhanVien();
                    System.out.println(nvCu.toString());

                    System.out.print("Nhap ho ten moi: ");
                    String hoTen = scanner.nextLine();

                    System.out.print("Nhap gioi tinh moi: ");
                    String gioiTinh = scanner.nextLine();

                    System.out.print("Nhap ngay sinh moi: ");
                    String ngaySinh = scanner.nextLine();

                    System.out.print("Nhap dia chi moi: ");
                    String diaChi = scanner.nextLine();

                    System.out.print("Nhap SDT moi: ");
                    String sdt = scanner.nextLine();

                    System.out.print("Nhap chuc vu moi: ");
                    String chucVu = scanner.nextLine();

                    double luong;

                    while (true) {
                        try {
                            System.out.print("Nhap luong moi: ");
                            luong = Double.parseDouble(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Luong phai la so! Vui long nhap lai.");
                        }
                    }

                    System.out.print("Nhap trang thai (Dang lam / Nghi viec): ");
                    String trangThai = scanner.nextLine();

                    NhanVien nvCapNhat = new NhanVien(
                            maNV, hoTen, gioiTinh, ngaySinh,
                            diaChi, sdt, chucVu, luong, trangThai
                    );

                    System.out.println(
                            qlNhanVien.sua(nvCapNhat)
                                    ? "Cap nhat thanh cong!"
                                    : "Cap nhat that bai!"
                    );
                }

                case 4 -> {
                    System.out.print("Nhap ma NV can xoa: ");
                    String maNV = scanner.nextLine();

                    System.out.println(
                            qlNhanVien.xoa(maNV)
                                    ? "Xoa nhan vien thanh cong!"
                                    : "Khong tim thay ma nhan vien can xoa!"
                    );
                }

                case 5 -> {
                    System.out.print("Nhap ma NV can tim: ");
                    String maNV = scanner.nextLine();

                    NhanVien kq = qlNhanVien.timTheoMa(maNV);

                    if (kq != null) {
                        inTieuDeNhanVien();
                        System.out.println(kq.toString());
                    } else {
                        System.out.println("Khong tim thay nhan vien co ma: " + maNV);
                    }
                }

                case 0 -> System.out.println("Quay lai menu Quan tri...");
                default -> System.out.println("Lua chon khong hop le.");
            }

        } while (choice != 0);
    }

    private void hienThiThongTinTaiKhoan(TaiKhoan tk) {
        System.out.printf("Ten DN: %-15s | Ma NV: %-8s | Vai tro: %-10s | Trang thai: %s%n",
                tk.getTenDangNhap(), tk.getMaNV(),
                tk.getVaiTro(), tk.getTrangThai());
    }

    private void menuTaiKhoan() {
        int choice = -1;

        do {
            System.out.println("\n--- QUAN LY TAI KHOAN ---");
            System.out.println("1. Xem danh sach tai khoan");
            System.out.println("2. Tao tai khoan moi");
            System.out.println("3. Doi mat khau");
            System.out.println("4. Xoa tai khoan");
            System.out.println("5. Tim kiem tai khoan");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so hop le.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    TaiKhoan[] ds = qlTaiKhoan.getDanhSach();

                    if (ds.length == 0) {
                        System.out.println("Chua co tai khoan nao trong he thong.");
                    } else {
                        for (TaiKhoan tk : ds) {
                            hienThiThongTinTaiKhoan(tk);
                        }
                    }
                }

                case 2 -> {
                    System.out.print("Nhap ma nhan vien (VD: NV01): ");
                    String maNV = scanner.nextLine();
                    taoTaiKhoanChoNhanVien(maNV);
                }

                case 3 -> {
                    System.out.print("Nhap ten dang nhap can doi mat khau: ");
                    String tenDN = scanner.nextLine();

                    System.out.print("Nhap mat khau moi: ");
                    String mkMoi = scanner.nextLine();

                    System.out.println(
                            qlTaiKhoan.doiMatKhau(tenDN, mkMoi)
                                    ? "Doi mat khau thanh cong!"
                                    : "Khong tim thay ten dang nhap!"
                    );
                }

                case 4 -> {
                    System.out.print("Nhap ten dang nhap can xoa: ");
                    String tenDN = scanner.nextLine();

                    if (taiKhoanHienTai != null
                            && taiKhoanHienTai.getTenDangNhap().equalsIgnoreCase(tenDN)) {

                        System.out.println("Khong the tu xoa tai khoan dang dang nhap!");

                    } else {
                        System.out.println(
                                qlTaiKhoan.xoa(tenDN)
                                        ? "Xoa tai khoan thanh cong!"
                                        : "Khong tim thay ten dang nhap can xoa!"
                        );
                    }
                }

                case 5 -> {
                    System.out.print("Nhap ten dang nhap can tim: ");
                    String tenDN = scanner.nextLine();

                    TaiKhoan kq = qlTaiKhoan.timTheoTenDN(tenDN);

                    if (kq != null) {
                        hienThiThongTinTaiKhoan(kq);
                    } else {
                        System.out.println("Khong tim thay tai khoan khop voi: " + tenDN);
                    }
                }

                case 0 -> System.out.println("Quay lai menu Quan tri...");
                default -> System.out.println("Lua chon khong hop le.");
            }

        } while (choice != 0);
    }

    private void menuThongKe() {
        int choice = -1;

        do {
            System.out.println("\n--- BAO CAO THONG KE ---");
            System.out.println("1. Thong ke tong quan (Phong & Dich vu)");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so hop le.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("- Tong so phong: " + qlThongKe.tongSoPhong());
                    System.out.println("- Dich vu dang hoat dong: "
                            + qlThongKe.tongSoDichVuHoatDong());
                }

                case 0 -> System.out.println("Quay lai menu Quan tri...");
                default -> System.out.println("Lua chon khong hop le.");
            }

        } while (choice != 0);
    }

    private void taoTaiKhoanChoNhanVien(String maNV) {
        if (qlNhanVien.timTheoMa(maNV) == null) {
            System.out.println("That bai: Khong tim thay nhan vien co ma " + maNV + "!");
            return;
        }

        String tenDN;
        while (true) {
            System.out.print("Nhap ten dang nhap: ");
            tenDN = scanner.nextLine().trim();

            if (tenDN.isEmpty()) {
                System.out.println("Ten dang nhap khong duoc de trong! Vui long nhap lai.");
                continue;
            }
            if (qlTaiKhoan.timTheoTenDN(tenDN) != null) {
                System.out.println("Ten dang nhap da ton tai! Vui long nhap ten khac.");
                continue;
            }
            break;
        }

        String matKhau;
        while (true) {
            System.out.print("Nhap mat khau: ");
            matKhau = scanner.nextLine();
            if (matKhau.trim().isEmpty()) {
                System.out.println("Mat khau khong duoc de trong! Vui long nhap lai.");
                continue;
            }
            break;
        }

        System.out.print("Nhap vai tro (VD: ADMIN, LE_TAN): ");
        String vaiTro = scanner.nextLine();

        TaiKhoan tkMoi = new TaiKhoan(
                tenDN, matKhau, maNV, vaiTro, "HOAT_DONG"
        );

        System.out.println(
                qlTaiKhoan.them(tkMoi)
                        ? "Them tai khoan thanh cong!"
                        : "Them tai khoan that bai!"
        );
    }
}
