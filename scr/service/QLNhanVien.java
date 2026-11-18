package service;

import dao.NhanVienDAO;
import model.NhanVien;
import java.util.Arrays;

public class QLNhanVien {
    private NhanVien[] dsNhanVien;
    private int soLuong;
    private NhanVienDAO nvDAO;

    public QLNhanVien() {
        this.nvDAO = new NhanVienDAO();
        NhanVien[] data = nvDAO.docFile(); 
        if (data != null) {
            this.dsNhanVien = data;
            this.soLuong = data.length;
        } else {
            this.dsNhanVien = new NhanVien[0];
            this.soLuong = 0;
        }

        return nvDAO.timTheoMa(maNV.trim());
    }

    public NhanVien[] getDanhSach() {
        return Arrays.copyOf(dsNhanVien, soLuong);
    }

    public NhanVien timTheoMa(String maNV) {
        for (int i = 0; i < soLuong; i++) {
            if (dsNhanVien[i].getMaNV().equalsIgnoreCase(maNV)) {
                return dsNhanVien[i];
            }
        }

    public boolean them(NhanVien nv) {
        if (timTheoMa(nv.getMaNV()) != null) {
            return false;
        }

        return nvDAO.them(nv);
    }

    public boolean sua(NhanVien nvMoi) {
        for (int i = 0; i < soLuong; i++) {
            if (dsNhanVien[i].getMaNV().equalsIgnoreCase(nvMoi.getMaNV())) {
                dsNhanVien[i] = nvMoi;
                nvDAO.ghiFile(dsNhanVien);
                return true;
            }
        }

        if (nv.getHoTen() == null || nv.getHoTen().trim().isEmpty()) {
            return false;
        }

        if (timTheoMa(nv.getMaNV()) == null) {
            return false;
        }

        return nvDAO.capNhat(nv);
    }

    // Xóa nhân viên
    public boolean xoa(String maNV) {

        if (maNV == null || maNV.trim().isEmpty()) {
            return false;
        }

        if (timTheoMa(maNV) == null) {
            return false;
        }

        return nvDAO.xoa(maNV);
    }

    // Kiểm tra mã nhân viên
    public boolean kiemTraMaTonTai(String maNV) {
        return timTheoMa(maNV) != null;
    }

    // Đếm nhân viên
    public int demSoLuong() {
        return nvDAO.demSoLuong();
    }
}