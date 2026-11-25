
package DAO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import model.TaiKhoan;

public final class TaiKhoanDAO {

    private static final String FILE_PATH ="DATABASEH/taiKhoan.dat";

    private TaiKhoan[] danhSach;
    private int soLuong;

    public TaiKhoanDAO() {
        danhSach = new TaiKhoan[10];
        soLuong = 0;

        docFile();

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

    public boolean ghiFile() {
        File file = new File(FILE_PATH);

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

        if (!ghiFile()) {
            soLuong--;
            danhSach[soLuong] = null;
            return false;
        }

        return true;
    }

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

    public TaiKhoan[] layTatCa() {

        TaiKhoan[] ketQua = new TaiKhoan[soLuong];

        System.arraycopy(danhSach, 0, ketQua, 0, soLuong);

        return ketQua;
    }

    public int getSoLuong() {
        return soLuong;
    }

    private void moRongMang() {

        TaiKhoan[] mangMoi = new TaiKhoan[danhSach.length * 2];

        System.arraycopy(danhSach, 0, mangMoi, 0, soLuong);

        danhSach = mangMoi;
    }
}