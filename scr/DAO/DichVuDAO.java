package DAO;

import model.DichVu;

/** DAO lưu dịch vụ bằng mảng có sức chứa cố định. */
public class DichVuDAO {
    public static DichVu[] danhSachDichVu = new DichVu[100];
    public static int soLuongDichVu = 0;

    public boolean them(DichVu dichVu) {
        if (dichVu == null || dichVu.getMaDichVu() == null
                || timTheoMa(dichVu.getMaDichVu()) != null
                || soLuongDichVu == danhSachDichVu.length) return false;
        danhSachDichVu[soLuongDichVu] = dichVu;
        soLuongDichVu++;
        return true;
    }

    public boolean sua(DichVu dichVu) {
        if (dichVu == null) return false;
        for (int i = 0; i < soLuongDichVu; i++) {
            if (danhSachDichVu[i].getMaDichVu().equals(dichVu.getMaDichVu())) {
                danhSachDichVu[i] = dichVu;
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maDichVu) {
        for (int i = 0; i < soLuongDichVu; i++) {
            if (danhSachDichVu[i].getMaDichVu().equals(maDichVu)) {
                for (int j = i; j < soLuongDichVu - 1; j++) {
                    danhSachDichVu[j] = danhSachDichVu[j + 1];
                }
                danhSachDichVu[--soLuongDichVu] = null;
                return true;
            }
        }
        return false;
    }

    public DichVu timTheoMa(String maDichVu) {
        for (int i = 0; i < soLuongDichVu; i++) {
            if (danhSachDichVu[i].getMaDichVu().equals(maDichVu)) return danhSachDichVu[i];
        }
        return null;
    }

    /** Trả về mảng chỉ có các phần tử đang được sử dụng. */
    public DichVu[] layDanhSach() {
        DichVu[] ketQua = new DichVu[soLuongDichVu];
        for (int i = 0; i < soLuongDichVu; i++) ketQua[i] = danhSachDichVu[i];
        return ketQua;
    }
}