package service;

import dao.TaiKhoanDAO;
import model.TaiKhoan;
import java.util.Arrays;

public class QLTaiKhoan {
    private TaiKhoan[] dsTaiKhoan;
    private int soLuong;
    private TaiKhoanDAO tkDAO;

    public QLTaiKhoan() {
        this.tkDAO = new TaiKhoanDAO();
        TaiKhoan[] data = tkDAO.docFile();
        if (data != null) {
            this.dsTaiKhoan = data;
            this.soLuong = data.length;
        } else {
            this.dsTaiKhoan = new TaiKhoan[0];
            this.soLuong = 0;
        }
    }

    public TaiKhoan kiemTraDangNhap(String tenDangNhap, String matKhau) {
        for (int i = 0; i < soLuong; i++) {
            if (dsTaiKhoan[i].getTenDangNhap().equals(tenDangNhap) && 
                dsTaiKhoan[i].getMatKhau().equals(matKhau)) {
                return dsTaiKhoan[i];
            }
        }
        return null;
    }

    public TaiKhoan timTheoTenDN(String tenDangNhap) {
        for (int i = 0; i < soLuong; i++) {
            if (dsTaiKhoan[i].getTenDangNhap().equalsIgnoreCase(tenDangNhap)) {
                return dsTaiKhoan[i];
            }
        }
        return null;
    }

    public boolean them(TaiKhoan tk) {
        if (timTheoTenDN(tk.getTenDangNhap()) != null) {
            return false;
        }
        dsTaiKhoan = Arrays.copyOf(dsTaiKhoan, soLuong + 1);
        dsTaiKhoan[soLuong] = tk;
        soLuong++;
        tkDAO.ghiFile(dsTaiKhoan);
        return true;
    }

    public boolean doiMatKhau(String tenDangNhap, String matKhauMoi) {
        TaiKhoan tk = timTheoTenDN(tenDangNhap);
        if (tk != null) {
            tk.setMatKhau(matKhauMoi);
            tkDAO.ghiFile(dsTaiKhoan);
            return true;
        }
        return false;
    }

    public boolean xoa(String tenDangNhap) {
        int index = -1;
        for (int i = 0; i < soLuong; i++) {
            if (dsTaiKhoan[i].getTenDangNhap().equalsIgnoreCase(tenDangNhap)) {
                index = i;
                break;
            }
        }
        if (index == -1) return false;

        for (int i = index; i < soLuong - 1; i++) {
            dsTaiKhoan[i] = dsTaiKhoan[i + 1];
        }
        soLuong--;
        dsTaiKhoan = Arrays.copyOf(dsTaiKhoan, soLuong);
        tkDAO.ghiFile(dsTaiKhoan);
        return true;
    }

    public TaiKhoan[] getDanhSach() {
        return Arrays.copyOf(dsTaiKhoan, soLuong);
    }
}