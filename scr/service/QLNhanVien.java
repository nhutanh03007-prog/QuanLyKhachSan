package service;

import dao.nhanvienDAO;
import model.nhanvien;
import java.util.Arrays;

public class QLNhanVien {
    private nhanvien[] dsNhanVien;
    private int soLuong;
    private nhanvienDAO nvDAO;

    public QLNhanVien() {
        this.nvDAO = new nhanvienDAO();
        // Giả sử nvDAO có hàm đọc danh sách từ file trả về mảng nhanvien[]
        nhanvien[] data = nvDAO.docFile(); 
        if (data != null) {
            this.dsNhanVien = data;
            this.soLuong = data.length;
        } else {
            this.dsNhanVien = new nhanvien[0];
            this.soLuong = 0;
        }
    }

    public nhanvien[] getDanhSach() {
        return Arrays.copyOf(dsNhanVien, soLuong);
    }

    public nhanvien timTheoMa(String maNV) {
        for (int i = 0; i < soLuong; i++) {
            if (dsNhanVien[i].getMaNV().equalsIgnoreCase(maNV)) {
                return dsNhanVien[i];
            }
        }
        return null;
    }

    public boolean them(nhanvien nv) {
        if (timTheoMa(nv.getMaNV()) != null) {
            return false; // Trùng mã nhân viên
        }
        dsNhanVien = Arrays.copyOf(dsNhanVien, soLuong + 1);
        dsNhanVien[soLuong] = nv;
        soLuong++;
        nvDAO.ghiFile(dsNhanVien);
        return true;
    }

    public boolean sua(nhanvien nvMoi) {
        for (int i = 0; i < soLuong; i++) {
            if (dsNhanVien[i].getMaNV().equalsIgnoreCase(nvMoi.getMaNV())) {
                dsNhanVien[i] = nvMoi;
                nvDAO.ghiFile(dsNhanVien);
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maNV) {
        int index = -1;
        for (int i = 0; i < soLuong; i++) {
            if (dsNhanVien[i].getMaNV().equalsIgnoreCase(maNV)) {
                index = i;
                break;
            }
        }
        if (index == -1) return false;

        for (int i = index; i < soLuong - 1; i++) {
            dsNhanVien[i] = dsNhanVien[i + 1];
        }
        soLuong--;
        dsNhanVien = Arrays.copyOf(dsNhanVien, soLuong);
        nvDAO.ghiFile(dsNhanVien);
        return true;
    }
}