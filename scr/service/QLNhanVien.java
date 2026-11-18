package service;

import DAO.NhanVienDAO;
import model.NhanVien;

public class QLNhanVien {
    private final NhanVienDAO nvDAO;

    public QLNhanVien() {
        this.nvDAO = new NhanVienDAO();
    }

    public NhanVien[] getDanhSach() {
        return nvDAO.layTatCa();
    }

    public NhanVien timTheoMa(String maNV) {
        return nvDAO.timTheoMa(maNV);
    }

    public boolean them(NhanVien nv) {
        return nvDAO.them(nv);
    }

    public boolean sua(NhanVien nvMoi) {
        return nvDAO.capNhat(nvMoi);
    }

    public boolean xoa(String maNV) {
        return nvDAO.xoa(maNV);
    }

    public boolean kiemTraMaTonTai(String maNV) {
        return nvDAO.kiemTraMaTonTai(maNV);
    }

    public int demSoLuong() {
        return nvDAO.demSoLuong();
    }
}