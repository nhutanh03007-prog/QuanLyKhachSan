package ui;

import java.io.BufferedReader;
<<<<<<< HEAD
import java.io.IOException;
import java.io.InputStreamReader;
=======
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6
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
    private final BufferedReader reader;
    private final QLTaiKhoan qlTaiKhoan;
    private final QLNhanVien qlNhanVien;
    private final QLThongKe qlThongKe;
    private TaiKhoan taiKhoanHienTai;

    public HeThongUI() {
<<<<<<< HEAD
        this.reader = new BufferedReader(new InputStreamReader(System.in));
=======
        this(ConsoleInput.getReader());
    }

    public HeThongUI(BufferedReader reader) {
        this.reader = reader;
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6
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
<<<<<<< HEAD
        this.reader = new BufferedReader(new InputStreamReader(System.in));
=======
        this.reader = ConsoleInput.getReader();
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6
        this.qlTaiKhoan = qlTaiKhoan;
        this.qlNhanVien = qlNhanVien;
        this.qlThongKe = qlThongKe;
    }

<<<<<<< HEAD
    private String nhapChuoi() {
        try {
            String line = reader.readLine();
            return line != null ? line : "";
        } catch (IOException e) {
            System.out.println("Loi nhap du lieu: " + e.getMessage());
            return "";
        }
=======
    public TaiKhoan getTaiKhoanHienTai() {
        return taiKhoanHienTai;
    }

    public String getVaiTroHienTai() {
        return taiKhoanHienTai == null ? null : taiKhoanHienTai.getVaiTro();
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6
    }

    public boolean dangNhap() {
        System.out.println("=== DANG NHAP HE THONG KHACH SAN ===");
        System.out.print("Ten dang nhap: ");
<<<<<<< HEAD
        String username = nhapChuoi();
        System.out.print("Mat khau: ");
        String password = nhapChuoi();
=======
        String username = ConsoleInput.readLine(reader);
        System.out.print("Mat khau: ");
        String password = ConsoleInput.readLine(reader);
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6

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
            System.out.println("Sai ten dang nhap hoac mat khau.");
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
            System.out.println("0. Quay lai Menu chinh");
            System.out.print("Chon chuc nang: ");

            try {
<<<<<<< HEAD
                choice = Integer.parseInt(nhapChuoi());
=======
                choice = Integer.parseInt(ConsoleInput.readLine(reader).trim());
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so hop le.");
                continue;
            }

            switch (choice) {
                case 1 -> menuNhanVien();
                case 2 -> menuTaiKhoan();
                case 3 -> new ThongKeUI(reader, qlThongKe).menuThongKe();
                case 0 -> System.out.println("Dang thoat chuc nang Quan tri...");
                default -> System.out.println("Lua chon khong hop le.");
            }

        } while (choice != 0);
    }

    private String nhapChuoiKhongRong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = ConsoleInput.readLine(reader).trim();
            if (!s.isEmpty()) {
                return s;
            }
            System.out.println("Khong duoc de trong! Vui long nhap lai.");
        }
    }

    private double nhapLuongDuong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double luong = Double.parseDouble(ConsoleInput.readLine(reader).trim());
                if (luong > 0) {
                    return luong;
                }
                System.out.println("Luong phai lon hon 0!");
            } catch (NumberFormatException e) {
                System.out.println("Luong phai la so! Vui long nhap lai.");
            }
        }
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
<<<<<<< HEAD
                choice = Integer.parseInt(nhapChuoi());
=======
                choice = Integer.parseInt(ConsoleInput.readLine(reader).trim());
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6
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
                        System.out.println("Tong so nhan vien: " + ds.length);
                    }
                }

                case 2 -> {
<<<<<<< HEAD
                    System.out.print("Nhap ma nhan vien (VD: NV001): ");
                    String maNV = nhapChuoi();
=======
                    String maNV = nhapChuoiKhongRong("Nhap ma nhan vien (VD: NV016): ").toUpperCase();
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6

                    if (qlNhanVien.timTheoMa(maNV) != null) {
                        System.out.println("That bai: Ma nhan vien da ton tai!");
                        break;
                    }

<<<<<<< HEAD
                    System.out.print("Nhap ho ten: ");
                    String hoTen = nhapChuoi();

                    System.out.print("Nhap gioi tinh (Nam/Nu): ");
                    String gioiTinh = nhapChuoi();

                    System.out.print("Nhap ngay sinh (dd/MM/yyyy): ");
                    String ngaySinh = nhapChuoi();

                    System.out.print("Nhap dia chi: ");
                    String diaChi = nhapChuoi();

                    System.out.print("Nhap so dien thoai: ");
                    String sdt = nhapChuoi();

                    System.out.print("Nhap chuc vu (Quan ly/Le tan/Phuc vu...): ");
                    String chucVu = nhapChuoi();

                    double luong;

                    while (true) {
                        try {
                            System.out.print("Nhap luong co ban: ");
                            luong = Double.parseDouble(nhapChuoi());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Luong phai la so! Vui long nhap lai.");
                        }
                    }
=======
                    String hoTen = nhapChuoiKhongRong("Nhap ho ten: ");
                    String gioiTinh = nhapChuoiKhongRong("Nhap gioi tinh (Nam/Nu): ");
                    String ngaySinh = nhapChuoiKhongRong("Nhap ngay sinh (dd/MM/yyyy): ");
                    String diaChi = nhapChuoiKhongRong("Nhap dia chi: ");
                    String sdt = nhapChuoiKhongRong("Nhap so dien thoai: ");
                    String chucVu = nhapChuoiKhongRong("Nhap chuc vu (Quan ly/Le tan/Phuc vu...): ");
                    double luong = nhapLuongDuong("Nhap luong co ban: ");
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6

                    NhanVien nvMoi = new NhanVien(
                            maNV, hoTen, gioiTinh, ngaySinh,
                            diaChi, sdt, chucVu, luong, "Dang lam"
                    );

                    if (qlNhanVien.them(nvMoi)) {
                        System.out.println("Them nhan vien thanh cong!");
                        System.out.print("Ban co muon tao tai khoan cho nhan vien nay? (Y/N): ");
                        if (ConsoleInput.readLine(reader).trim().equalsIgnoreCase("Y")) {
                            System.out.println("\n--- TAO TAI KHOAN CHO NHAN VIEN " + maNV + " ---");
                            taoTaiKhoanChoNhanVien(maNV);
                        }
                    } else {
                        System.out.println("Them nhan vien that bai!");
                    }
                }

                case 3 -> {
<<<<<<< HEAD
                    System.out.print("Nhap ma NV can sua: ");
                    String maNV = nhapChuoi();
=======
                    String maNV = nhapChuoiKhongRong("Nhap ma NV can sua: ");
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6

                    NhanVien nvCu = qlNhanVien.timTheoMa(maNV);

                    if (nvCu == null) {
                        System.out.println("Khong tim thay nhan vien co ma: " + maNV);
                        break;
                    }

                    inTieuDeNhanVien();
                    System.out.println(nvCu.toString());

<<<<<<< HEAD
                    System.out.print("Nhap ho ten moi: ");
                    String hoTen = nhapChuoi();

                    System.out.print("Nhap gioi tinh moi: ");
                    String gioiTinh = nhapChuoi();

                    System.out.print("Nhap ngay sinh moi: ");
                    String ngaySinh = nhapChuoi();

                    System.out.print("Nhap dia chi moi: ");
                    String diaChi = nhapChuoi();

                    System.out.print("Nhap SDT moi: ");
                    String sdt = nhapChuoi();

                    System.out.print("Nhap chuc vu moi: ");
                    String chucVu = nhapChuoi();

                    double luong;

                    while (true) {
                        try {
                            System.out.print("Nhap luong moi: ");
                            luong = Double.parseDouble(nhapChuoi());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Luong phai la so! Vui long nhap lai.");
                        }
                    }

                    System.out.print("Nhap trang thai (Dang lam / Nghi viec): ");
                    String trangThai = nhapChuoi();
=======
                    String hoTen = nhapChuoiKhongRong("Nhap ho ten moi: ");
                    String gioiTinh = nhapChuoiKhongRong("Nhap gioi tinh moi (Nam/Nu): ");
                    String ngaySinh = nhapChuoiKhongRong("Nhap ngay sinh moi (dd/MM/yyyy): ");
                    String diaChi = nhapChuoiKhongRong("Nhap dia chi moi: ");
                    String sdt = nhapChuoiKhongRong("Nhap SDT moi: ");
                    String chucVu = nhapChuoiKhongRong("Nhap chuc vu moi: ");
                    double luong = nhapLuongDuong("Nhap luong moi: ");
                    String trangThai = nhapChuoiKhongRong("Nhap trang thai (Dang lam / Nghi viec): ");
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6

                    NhanVien nvCapNhat = new NhanVien(
                            nvCu.getMaNV(), hoTen, gioiTinh, ngaySinh,
                            diaChi, sdt, chucVu, luong, trangThai
                    );

                    System.out.println(
                            qlNhanVien.sua(nvCapNhat)
                                    ? "Cap nhat thanh cong!"
                                    : "Cap nhat that bai!"
                    );
                }

                case 4 -> {
<<<<<<< HEAD
                    System.out.print("Nhap ma NV can xoa: ");
                    String maNV = nhapChuoi();
=======
                    String maNV = nhapChuoiKhongRong("Nhap ma NV can xoa: ");
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6

                    System.out.println(
                            qlNhanVien.xoa(maNV)
                                    ? "Xoa nhan vien thanh cong!"
                                    : "Khong tim thay ma nhan vien can xoa!"
                    );
                }

                case 5 -> {
<<<<<<< HEAD
                    System.out.print("Nhap ma NV can tim: ");
                    String maNV = nhapChuoi();
=======
                    String maNV = nhapChuoiKhongRong("Nhap ma NV can tim: ");
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6

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
<<<<<<< HEAD
                choice = Integer.parseInt(nhapChuoi());
=======
                choice = Integer.parseInt(ConsoleInput.readLine(reader).trim());
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6
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
<<<<<<< HEAD
                    System.out.print("Nhap ma nhan vien (VD: NV001): ");
                    String maNV = nhapChuoi();
=======
                    String maNV = nhapChuoiKhongRong("Nhap ma nhan vien (VD: NV001): ");
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6
                    taoTaiKhoanChoNhanVien(maNV);
                }

                case 3 -> {
<<<<<<< HEAD
                    System.out.print("Nhap ten dang nhap can doi mat khau: ");
                    String tenDN = nhapChuoi();

                    System.out.print("Nhap mat khau moi: ");
                    String mkMoi = nhapChuoi();
=======
                    String tenDN = nhapChuoiKhongRong("Nhap ten dang nhap can doi mat khau: ");
                    String mkMoi = nhapChuoiKhongRong("Nhap mat khau moi: ");
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6

                    System.out.println(
                            qlTaiKhoan.doiMatKhau(tenDN, mkMoi)
                                    ? "Doi mat khau thanh cong!"
                                    : "Khong tim thay ten dang nhap!"
                    );
                }

                case 4 -> {
<<<<<<< HEAD
                    System.out.print("Nhap ten dang nhap can xoa: ");
                    String tenDN = nhapChuoi();
=======
                    String tenDN = nhapChuoiKhongRong("Nhap ten dang nhap can xoa: ");
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6

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
<<<<<<< HEAD
                    System.out.print("Nhap ten dang nhap can tim: ");
                    String tenDN = nhapChuoi();
=======
                    String tenDN = nhapChuoiKhongRong("Nhap ten dang nhap can tim: ");
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6

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

<<<<<<< HEAD
    private void menuThongKe() {
        int choice = -1;

        do {
            System.out.println("\n--- BAO CAO THONG KE ---");
            System.out.println("1. Thong ke tong quan (Phong & Dich vu)");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            try {
                choice = Integer.parseInt(nhapChuoi());
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

=======
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6
    private void taoTaiKhoanChoNhanVien(String maNV) {
        NhanVien nv = qlNhanVien.timTheoMa(maNV);
        if (nv == null) {
            System.out.println("That bai: Khong tim thay nhan vien co ma " + maNV + "!");
            return;
        }

        String tenDN;
        while (true) {
<<<<<<< HEAD
            System.out.print("Nhap ten dang nhap: ");
            tenDN = nhapChuoi().trim();

            if (tenDN.isEmpty()) {
                System.out.println("Ten dang nhap khong duoc de trong! Vui long nhap lai.");
                continue;
            }
=======
            tenDN = nhapChuoiKhongRong("Nhap ten dang nhap: ");
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6
            if (qlTaiKhoan.timTheoTenDN(tenDN) != null) {
                System.out.println("Ten dang nhap da ton tai! Vui long nhap ten khac.");
                continue;
            }
            break;
        }

<<<<<<< HEAD
        String matKhau;
        while (true) {
            System.out.print("Nhap mat khau: ");
            matKhau = nhapChuoi();
            if (matKhau.trim().isEmpty()) {
                System.out.println("Mat khau khong duoc de trong! Vui long nhap lai.");
                continue;
            }
            break;
        }

        System.out.print("Nhap vai tro (VD: ADMIN, LE_TAN): ");
        String vaiTro = nhapChuoi();
=======
        String matKhau = nhapChuoiKhongRong("Nhap mat khau: ");
        String vaiTro = nhapChuoiKhongRong("Nhap vai tro (ADMIN / NHAN_VIEN): ").toUpperCase();
>>>>>>> 1406952588fed68ed202a5284963bbe81fd8aab6

        TaiKhoan tkMoi = new TaiKhoan(
                tenDN, matKhau, nv.getMaNV(), vaiTro, "HOAT_DONG"
        );

        System.out.println(
                qlTaiKhoan.them(tkMoi)
                        ? "Them tai khoan thanh cong!"
                        : "Them tai khoan that bai!"
        );
    }
}