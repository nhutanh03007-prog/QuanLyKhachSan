package DAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import model.DichVu;

public class DichVuDAO {
    public static DichVu[] danhSachDichVu = new DichVu[100];
    public static int soLuongDichVu = 0;

    public DichVuDAO() {
    }

    public boolean kiemTraTenDichVuKhongRong(String s) {
        return !rong(s);
    }

    public boolean kiemTraSoTienKhongAm(BigDecimal n) {
        return n != null && n.signum() >= 0;
    }

    private boolean hopLe(DichVu d) {
        return d != null && !rong(d.getMaDichVu()) && !rong(d.getTenDichVu()) && !rong(d.getLoaiDichVu())
                && !rong(d.getDonViTinh()) && d.getGia() != null && d.getGia().signum() > 0;
    }

    public boolean them(DichVu d) {
        if (!hopLe(d) || timTheoMa(d.getMaDichVu()) != null || soLuongDichVu >= 100)
            return false;
        danhSachDichVu[soLuongDichVu++] = d;
        return true;
    }

    public boolean sua(DichVu d) {
        if (!hopLe(d))
            return false;
        for (int i = 0; i < soLuongDichVu; i++)
            if (danhSachDichVu[i] != null && d.getMaDichVu().equals(danhSachDichVu[i].getMaDichVu())) {
                danhSachDichVu[i] = d;
                return true;
            }
        return false;
    }

    public boolean xoa(String ma) {
        for (int i = 0; i < soLuongDichVu; i++)
            if (danhSachDichVu[i] != null && ma != null && ma.equals(danhSachDichVu[i].getMaDichVu())) {
                System.arraycopy(danhSachDichVu, i + 1, danhSachDichVu, i, --soLuongDichVu - i);
                danhSachDichVu[soLuongDichVu] = null;
                return true;
            }
        return false;
    }

    public DichVu timTheoMa(String ma) {
        if (rong(ma))
            return null;
        for (int i = 0; i < soLuongDichVu; i++)
            if (danhSachDichVu[i] != null && ma.equals(danhSachDichVu[i].getMaDichVu()))
                return danhSachDichVu[i];
        return null;
    }

    public DichVu[] timKiem(String key) {
        List<DichVu> out = new ArrayList<>();
        if (!rong(key))
            for (int i = 0; i < soLuongDichVu; i++) {
                DichVu d = danhSachDichVu[i];
                if (d != null && ((d.getMaDichVu() != null
                        && d.getMaDichVu().toLowerCase().contains(key.trim().toLowerCase()))
                        || (d.getTenDichVu() != null
                                && d.getTenDichVu().toLowerCase().contains(key.trim().toLowerCase()))))
                    out.add(d);
            }
        return out.toArray(new DichVu[0]);
    }

    public DichVu[] timTheoTen(String key) {
        return timKiem(key);
    }

    public DichVu[] layDanhSach() {
        return java.util.Arrays.copyOf(danhSachDichVu, soLuongDichVu);
    }

    private boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}
