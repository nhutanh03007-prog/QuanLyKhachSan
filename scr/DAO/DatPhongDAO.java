package DAO;

import java.time.LocalDate;
import model.DatPhong;
import model.TrangThaiDatPhong;


public class DatPhongDAO {
    public static DatPhong[] danhSachDatPhong = new DatPhong[100];
    public static int soLuongDatPhong = 0;

    public static DatPhong[] danhSachDaXoa = new DatPhong[100];
    public static int soLuongDaXoa = 0;

    public boolean them(DatPhong datPhong) {
        if (datPhong == null || datPhong.getMaDatPhong() == null 
            || timTheoMa(datPhong.getMaDatPhong()) != null 
            || soLuongDatPhong == danhSachDatPhong.length) {
            return false;
        }
        danhSachDatPhong[soLuongDatPhong] = datPhong;
        soLuongDatPhong++;
        return true;
    }

    public boolean sua(DatPhong datPhong) {
        if (datPhong == null) return false;
        for (int i = 0; i < soLuongDatPhong; i++) {
            if (danhSachDatPhong[i].getMaDatPhong().equals(datPhong.getMaDatPhong())) {
                danhSachDatPhong[i] = datPhong;
                return true;
            }
        }
        return false;
    }

    public boolean xoa(String maDatPhong) {
        for (int i = 0; i < soLuongDatPhong; i++) {
            if (danhSachDatPhong[i].getMaDatPhong().equals(maDatPhong)) {
                if (soLuongDaXoa < danhSachDaXoa.length) {
                    danhSachDaXoa[soLuongDaXoa] = danhSachDatPhong[i];
                    soLuongDaXoa++;
                }
                System.arraycopy(danhSachDatPhong, i + 1, danhSachDatPhong, i, soLuongDatPhong - i - 1);
                danhSachDatPhong[--soLuongDatPhong] = null;
                return true;
            }
        }
        return false;
    }

    public DatPhong timTheoMa(String maDatPhong) {
        for (int i = 0; i < soLuongDatPhong; i++) {
            if (danhSachDatPhong[i].getMaDatPhong().equals(maDatPhong)) {
                return danhSachDatPhong[i];
            }
        }
        return null;
    }

    
    public boolean kiemTraPhongTrong(String maPhong, LocalDate ngayNhan, LocalDate ngayTra) {
        for (int i = 0; i < soLuongDatPhong; i++) {
            DatPhong dp = danhSachDatPhong[i];
            
            if (dp.getMaPhong().equalsIgnoreCase(maPhong) &&
                dp.getTrangThai() != TrangThaiDatPhong.DA_HUY &&
                dp.getTrangThai() != TrangThaiDatPhong.DA_TRA) {
                
                
                if (dp.isTrungLich(ngayNhan, ngayTra)) {
                    return false;
                }
            }
        }
        return true;
    }

    public DatPhong[] layDanhSach() {
        DatPhong[] ketQua = new DatPhong[soLuongDatPhong];
        System.arraycopy(danhSachDatPhong, 0, ketQua, 0, soLuongDatPhong);
        return ketQua;
    }
    
    public boolean khoiPhuc(String maDatPhong) {
        for (int i = 0; i < soLuongDaXoa; i++) {
            if (danhSachDaXoa[i].getMaDatPhong().equals(maDatPhong)) {
                if (soLuongDatPhong >= danhSachDatPhong.length) {
                    return false; // Danh sách chính đã đầy
                }
                
                danhSachDatPhong[soLuongDatPhong] = danhSachDaXoa[i];
                soLuongDatPhong++;

                
                System.arraycopy(danhSachDaXoa, i + 1, danhSachDaXoa, i, soLuongDaXoa - i - 1);
                danhSachDaXoa[--soLuongDaXoa] = null;
                return true;
            }
        }
        return false;
    }

    
    public DatPhong[] layDanhSachDaXoa() {
        DatPhong[] ketQua = new DatPhong[soLuongDaXoa];
        System.arraycopy(danhSachDaXoa, 0, ketQua, 0, soLuongDaXoa);
        return ketQua;
    }
}