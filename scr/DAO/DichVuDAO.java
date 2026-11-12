package DAO;

import model.DichVu;
import java.math.BigDecimal;

/** DAO lưu dịch vụ bằng mảng có sức chứa cố định. */
public class DichVuDAO {
    public static DichVu[] danhSachDichVu = new DichVu[100];
    public static int soLuongDichVu = 0;

    public DichVuDAO() {
        if (soLuongDichVu == 0) {
            them(new DichVu("DV01", "Massage thư giãn", "Làm đẹp", "1h30p", new BigDecimal("499000"), "DANG_HOAT_DONG", "Massage toàn thân"));
            them(new DichVu("DV02", "Giặt ủi", "Vệ sinh", "Lần", new BigDecimal("69000"), "DANG_HOAT_DONG", "Giặt sấy quần áo trong ngày"));
            them(new DichVu("DV03", "Đồ uống các loại", "Ăn uống", "Ly/Lon", new BigDecimal("25000"), "DANG_HOAT_DONG", "Đồng giá các loại nước giải khát"));
            them(new DichVu("DV04", "Vệ sinh phòng (Thêm)", "Vệ sinh", "Lần", new BigDecimal("100000"), "DANG_HOAT_DONG", "Yêu cầu dọn dẹp ngoài giờ"));
        }
    }

    public boolean them(DichVu dichVu) {
        if (dichVu == null || dichVu.getMaDichVu() == null || dichVu.getMaDichVu().trim().isEmpty()
                || timTheoMa(dichVu.getMaDichVu()) != null
                || soLuongDichVu == danhSachDichVu.length) return false;
        danhSachDichVu[soLuongDichVu] = dichVu;
        soLuongDichVu++;
        return true;
    }

    public boolean sua(DichVu dichVu) {
        if (dichVu == null || dichVu.getMaDichVu() == null || dichVu.getMaDichVu().trim().isEmpty()) return false;
        for (int i = 0; i < soLuongDichVu; i++) {
            if (danhSachDichVu[i] != null && dichVu.getMaDichVu().equals(danhSachDichVu[i].getMaDichVu())) {
                danhSachDichVu[i] = dichVu;
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maDichVu) {
        if (maDichVu == null || maDichVu.trim().isEmpty()) return false;
        for (int i = 0; i < soLuongDichVu; i++) {
            if (danhSachDichVu[i] != null && maDichVu.equals(danhSachDichVu[i].getMaDichVu())) {
               System.arraycopy(danhSachDichVu, i + 1, danhSachDichVu, i,
                        soLuongDichVu - i - 1);
                danhSachDichVu[--soLuongDichVu] = null;
                return true;
            }
        }
        return false;
    }

    public DichVu timTheoMa(String maDichVu) {
        if (maDichVu == null || maDichVu.trim().isEmpty()) return null;
        for (int i = 0; i < soLuongDichVu; i++) {
            if (danhSachDichVu[i] != null && maDichVu.equals(danhSachDichVu[i].getMaDichVu())) return danhSachDichVu[i];
        }
        return null;
    }
    public DichVu[] timTheoTen(String tuKhoa) {
        if (tuKhoa == null || tuKhoa.trim().isEmpty()) return new DichVu[0];
        java.util.List<DichVu> ketQua = new java.util.ArrayList<>();
        for (int i=0;i<soLuongDichVu;i++) if (danhSachDichVu[i] != null && danhSachDichVu[i].getTenDichVu()!=null && danhSachDichVu[i].getTenDichVu().toLowerCase().contains(tuKhoa.trim().toLowerCase())) ketQua.add(danhSachDichVu[i]);
        return ketQua.toArray(new DichVu[0]);
    }

    /** Trả về mảng chỉ có các phần tử đang được sử dụng. */
    public DichVu[] layDanhSach() {
        DichVu[] ketQua = new DichVu[soLuongDichVu];
       System.arraycopy(danhSachDichVu, 0, ketQua, 0, soLuongDichVu);
        return ketQua;
    }
}
