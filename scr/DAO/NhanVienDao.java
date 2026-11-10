package DAO;

import java.util.ArrayList;
import java.util.List;
import model.NhanVien;

/**
 * Quản lý nhân viên tạm thời bằng ArrayList.
 * Chưa kết nối Database.
 */
public class NhanVienDAO {

    private final List<NhanVien> danhSach = new ArrayList<>();

    // ================= LẤY DANH SÁCH =================

    public List<NhanVien> layTatCa() {
        return new ArrayList<>(danhSach);
    }

    // ================= THÊM NHÂN VIÊN =================
    public boolean them(NhanVien nv) {

        if (nv == null) {
            return false;
        }

        if (nv.getMaNV() == null || nv.getMaNV().trim().isEmpty()) {
            return false;
        }

        if (nv.getHoTen() == null || nv.getHoTen().trim().isEmpty()) {
            return false;
        }

        // Kiểm tra mã nhân viên đã tồn tại chưa
        if (timTheoMa(nv.getMaNV()) != null) {
            return false;
        }

        danhSach.add(nv);

        return true;
    }

    // ================= CẬP NHẬT NHÂN VIÊN =================

    public boolean capNhat(NhanVien nv) {

        if (nv == null) {
            return false;
        }

        if (nv.getMaNV() == null || nv.getMaNV().trim().isEmpty()) {
            return false;
        }

        if (nv.getHoTen() == null || nv.getHoTen().trim().isEmpty()) {
            return false;
        }

        String ma = nv.getMaNV().trim();

        for (int i = 0; i < danhSach.size(); i++) {

            NhanVien nhanVienCu = danhSach.get(i);

            if (nhanVienCu.getMaNV() != null
                    && nhanVienCu.getMaNV().equalsIgnoreCase(ma)) {

                danhSach.set(i, nv);

                return true;
            }
        }

        return false;
    }

    // ================= XÓA NHÂN VIÊN =================

    public boolean xoa(String maNV) {

        if (maNV == null || maNV.trim().isEmpty()) {
            return false;
        }

        for (int i = 0; i < danhSach.size(); i++) {

            if (danhSach.get(i).getMaNV() != null
                    && danhSach.get(i).getMaNV().equalsIgnoreCase(maNV.trim())) {

                danhSach.remove(i);

                return true;
            }
        }

        return false;
    }

    // ================= TÌM NHÂN VIÊN THEO MÃ =================

    public NhanVien timTheoMa(String maNV) {

        if (maNV == null || maNV.trim().isEmpty()) {
            return null;
        }

        String maCanTim = maNV.trim();

        for (NhanVien nv : danhSach) {

            if (nv.getMaNV() != null
                    && nv.getMaNV().equalsIgnoreCase(maCanTim)) {

                return nv;
            }
        }

        return null;
    }

    // ================= KIỂM TRA MÃ NHÂN VIÊN =================

    public boolean kiemTraMaTonTai(String maNV) {

        return timTheoMa(maNV) != null;
    }

    // ================= ĐẾM SỐ LƯỢNG NHÂN VIÊN =================

    public int demSoLuong() {

        return danhSach.size();
    }
}
