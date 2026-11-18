
package DAO;

import model.TaiKhoan;
import Database.DatabaseManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TaiKhoanDao {

    private static final String FILE_PATH =
            DatabaseManager.getDuongDanFile("taiKhoan.dat");

    private TaiKhoan[] danhSach;
    private int soLuong;

    public TaiKhoanDao() {
        danhSach = new TaiKhoan[10];
        soLuong = 0;

        docFile();

        // Tạo tài khoản admin mặc định nếu chưa có tài khoản nào
        if (soLuong == 0) {
            TaiKhoan admin = new TaiKhoan(
                    "admin",
                    "admin123",
                    "NV001",
                    "ADMIN",
                    "HOAT_DONG"
            );

            them(admin);
        }
    }

    // =========================
    // ĐỌC FILE
    // =========================
    public boolean docFile() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            soLuong = 0;
            return true;
        }

        try (DataInputStream in = new DataInputStream(
                new FileInputStream(file))) {

            int n = in.readInt();

            if (n < 0 || n > 10000) {
                throw new IOException("So luong tai khoan khong hop le!");
            }

            danhSach = new TaiKhoan[Math.max(10, n)];
            soLuong = 0;

            for (int i = 0; i < n; i++) {
                String tenDangNhap = in.readUTF();
                String matKhau = in.readUTF();
                String maNV = in.readUTF();
                String vaiTro = in.readUTF();
                String trangThai = in.readUTF();

                TaiKhoan tk = new TaiKhoan(
                        tenDangNhap,
                        matKhau,
                        maNV,
                        vaiTro,
                        trangThai
                );

                danhSach[soLuong++] = tk;
            }

            return true;

        } catch (IOException e) {
            System.out.println("Loi doc file tai khoan: " + e.getMessage());
            return false;
        }
    }

    // =========================
    // GHI FILE
    // =========================
    public boolean ghiFile() {
        File file = new File(FILE_PATH);

        // Tạo thư mục nếu chưa tồn tại
        File thuMuc = file.getParentFile();

        if (thuMuc != null && !thuMuc.exists()) {
            thuMuc.mkdirs();
        }

        try (DataOutputStream out = new DataOutputStream(
                new FileOutputStream(file))) {

            out.writeInt(soLuong);

            for (int i = 0; i < soLuong; i++) {
                TaiKhoan tk = danhSach[i];

                out.writeUTF(tk.getTenDangNhap());
                out.writeUTF(tk.getMatKhau());
                out.writeUTF(tk.getMaNV());
                out.writeUTF(tk.getVaiTro());
                out.writeUTF(tk.getTrangThai());
            }

            return true;

        } catch (IOException e) {
            System.out.println("Loi ghi file tai khoan: " + e.getMessage());
            return false;
        }
    }

    // =========================
    // ĐĂNG NHẬP
    // =========================
    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {

        if (tenDangNhap == null || matKhau == null) {
            return null;
        }

        String tenCanTim = tenDangNhap.trim();

        for (int i = 0; i < soLuong; i++) {
            TaiKhoan tk = danhSach[i];

            if (tk != null
                    && tk.getTenDangNhap() != null
                    && tk.getTenDangNhap().equalsIgnoreCase(tenCanTim)
                    && tk.getMatKhau() != null
                    && tk.getMatKhau().equals(matKhau)
                    && "HOAT_DONG".equalsIgnoreCase(tk.getTrangThai())) {

                return tk;
            }
        }

        return null;
    }

    // =========================
    // THÊM TÀI KHOẢN
    // =========================
    public boolean them(TaiKhoan tk) {

        if (tk == null
                || tk.getTenDangNhap() == null
                || tk.getTenDangNhap().trim().isEmpty()
                || tk.getMatKhau() == null
                || tk.getMatKhau().isEmpty()) {

            return false;
        }

        if (timTheoTen(tk.getTenDangNhap()) != null) {
            return false;
        }

        if (soLuong == danhSach.length) {
            moRongMang();
        }

        danhSach[soLuong] = tk;
        soLuong++;

        // Nếu ghi file thất bại thì hoàn tác
        if (!ghiFile()) {
            soLuong--;
            danhSach[soLuong] = null;
            return false;
        }

        return true;
    }

    // =========================
    // ĐỔI MẬT KHẨU
    // =========================
    public boolean doiMatKhau(String tenDangNhap, String matKhauMoi) {

        if (tenDangNhap == null
                || matKhauMoi == null
                || matKhauMoi.trim().isEmpty()) {

            return false;
        }

        TaiKhoan tk = timTheoTen(tenDangNhap);

        if (tk == null) {
            return false;
        }

        String matKhauCu = tk.getMatKhau();

        tk.setMatKhau(matKhauMoi);

        if (!ghiFile()) {
            tk.setMatKhau(matKhauCu);
            return false;
        }

        return true;
    }

    // =========================
    // XÓA TÀI KHOẢN
    // =========================
    public boolean xoa(String tenDangNhap) {

        TaiKhoan tk = timTheoTen(tenDangNhap);

        if (tk == null) {
            return false;
        }

        int viTri = -1;

        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i] == tk) {
                viTri = i;
                break;
            }
        }

        if (viTri == -1) {
            return false;
        }

        // Lưu lại để hoàn tác nếu ghi file lỗi
        TaiKhoan[] mangCu = danhSach.clone();
        int soLuongCu = soLuong;

        for (int i = viTri; i < soLuong - 1; i++) {
            danhSach[i] = danhSach[i + 1];
        }

        danhSach[soLuong - 1] = null;
        soLuong--;

        if (!ghiFile()) {
            danhSach = mangCu;
            soLuong = soLuongCu;
            return false;
        }

        return true;
    }

    // =========================
    // TÌM THEO TÊN ĐĂNG NHẬP
    // =========================
    public TaiKhoan timTheoTen(String ten) {

        if (ten == null || ten.trim().isEmpty()) {
            return null;
        }

        String tenCanTim = ten.trim();

        for (int i = 0; i < soLuong; i++) {
            TaiKhoan tk = danhSach[i];

            if (tk != null
                    && tk.getTenDangNhap() != null
                    && tk.getTenDangNhap().equalsIgnoreCase(tenCanTim)) {

                return tk;
            }
        }

        return null;
    }

    // =========================
    // LẤY DANH SÁCH
    // =========================
    public TaiKhoan[] layTatCa() {

        TaiKhoan[] ketQua = new TaiKhoan[soLuong];

        for (int i = 0; i < soLuong; i++) {
            ketQua[i] = danhSach[i];
        }

        return ketQua;
    }

    // =========================
    // ĐẾM SỐ LƯỢNG
    // =========================
    public int getSoLuong() {
        return soLuong;
    }

    // =========================
    // MỞ RỘNG MẢNG
    // =========================
    private void moRongMang() {

        TaiKhoan[] mangMoi = new TaiKhoan[danhSach.length * 2];

        for (int i = 0; i < soLuong; i++) {
            mangMoi[i] = danhSach[i];
        }

        danhSach = mangMoi;
    }
}
