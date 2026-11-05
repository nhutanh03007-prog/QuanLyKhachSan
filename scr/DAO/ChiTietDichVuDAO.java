package DAO;

import model.ChiTietDichVu;

/** DAO lưu chi tiết dịch vụ bằng mảng. */
public class ChiTietDichVuDAO {
    public static ChiTietDichVu[] danhSachChiTiet = new ChiTietDichVu[100];
    public static int soLuongChiTiet = 0;

    public boolean them(ChiTietDichVu chiTiet) {
        if (chiTiet == null || timTheoMa(chiTiet.getMaDatPhong(), chiTiet.getMaDichVu()) != null
                || soLuongChiTiet == danhSachChiTiet.length) return false;
        chiTiet.tinhThanhTien();
        danhSachChiTiet[soLuongChiTiet] = chiTiet;
        soLuongChiTiet++;
        return true;
    }

    public boolean sua(ChiTietDichVu chiTiet) {
        if (chiTiet == null) return false;
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (laChiTietCanTim(danhSachChiTiet[i], chiTiet.getMaDatPhong(), chiTiet.getMaDichVu())) {
                chiTiet.tinhThanhTien();
                danhSachChiTiet[i] = chiTiet;
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maDatPhong, String maDichVu) {
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (laChiTietCanTim(danhSachChiTiet[i], maDatPhong, maDichVu)) {
                for (int j = i; j < soLuongChiTiet - 1; j++) {
                    danhSachChiTiet[j] = danhSachChiTiet[j + 1];
                }
                danhSachChiTiet[--soLuongChiTiet] = null;
                return true;
            }
        }
        return false;
    }

    public ChiTietDichVu timTheoMa(String maDatPhong, String maDichVu) {
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (laChiTietCanTim(danhSachChiTiet[i], maDatPhong, maDichVu)) return danhSachChiTiet[i];
        }
        return null;
    }

    public ChiTietDichVu[] layDanhSach() {
        ChiTietDichVu[] ketQua = new ChiTietDichVu[soLuongChiTiet];
        for (int i = 0; i < soLuongChiTiet; i++) ketQua[i] = danhSachChiTiet[i];
        return ketQua;
    }

    public ChiTietDichVu[] layTheoMaDatPhong(String maDatPhong) {
        int dem = 0;
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (maDatPhong.equals(danhSachChiTiet[i].getMaDatPhong())) dem++;
        }
        ChiTietDichVu[] ketQua = new ChiTietDichVu[dem];
        int viTri = 0;
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (maDatPhong.equals(danhSachChiTiet[i].getMaDatPhong())) ketQua[viTri++] = danhSachChiTiet[i];
        }
        return ketQua;
    }

    private boolean laChiTietCanTim(ChiTietDichVu chiTiet, String maDatPhong, String maDichVu) {
        return maDatPhong.equals(chiTiet.getMaDatPhong())
                && maDichVu.equals(chiTiet.getMaDichVu());
    }
}
