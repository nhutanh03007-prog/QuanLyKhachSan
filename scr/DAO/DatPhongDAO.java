package DAO;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import model.DatPhong;
import model.TrangThaiDatPhong;

public class DatPhongDAO {
    private static final String FILE_PATH = "DATABASEH/DatPhong.dat";
    private static final String FILE_PATH_DA_XOA = "DATABASEH/DatPhongDaXoa.dat";

    public static DatPhong[] danhSachDatPhong = new DatPhong[100];
    public static int soLuongDatPhong = 0;

    public static DatPhong[] danhSachDaXoa = new DatPhong[100];
    public static int soLuongDaXoa = 0;

    static {
        docFile();
    }

    public boolean them(DatPhong datPhong) {
        if (datPhong == null || datPhong.getMaDatPhong() == null 
            || timTheoMa(datPhong.getMaDatPhong()) != null 
            || soLuongDatPhong == danhSachDatPhong.length) {
            return false;
        }
        danhSachDatPhong[soLuongDatPhong] = datPhong;
        soLuongDatPhong++;
        ghiFile();
        return true;
    }

    public boolean sua(DatPhong datPhong) {
        if (datPhong == null) return false;
        for (int i = 0; i < soLuongDatPhong; i++) {
            if (danhSachDatPhong[i].getMaDatPhong().equals(datPhong.getMaDatPhong())) {
                danhSachDatPhong[i] = datPhong;
                ghiFile();
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
                ghiFile();
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
                ghiFile();
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
    private static void docFile() {
        soLuongDatPhong = docDanhSachNoiBo(FILE_PATH, danhSachDatPhong);
        soLuongDaXoa = docDanhSachNoiBo(FILE_PATH_DA_XOA, danhSachDaXoa);
    }

    private static int docDanhSachNoiBo(String path, DatPhong[] mang) {
        int count = 0;
        Arrays.fill(mang, null);
        File f = new File(path);
        try {
            if (f.getParentFile() != null) {
                f.getParentFile().mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            try (DataInputStream dis = new DataInputStream(new FileInputStream(f))) {
    while (count < mang.length && dis.available() > 0) {
        String maDatPhong = dis.readUTF();
        String maKhachHang = dis.readUTF();
        String maPhong = dis.readUTF();
        String ngayDatStr = dis.readUTF();
        String ngayNhanStr = dis.readUTF();
        String ngayTraStr = dis.readUTF();
        String trangThaiStr = dis.readUTF();
        String giaPhongStr = dis.readUTF();

        DatPhong dp = new DatPhong();
        dp.setMaDatPhong(maDatPhong);
        dp.setMaKhachHang(maKhachHang);
        dp.setMaPhong(maPhong);
        dp.setNgayDat(ngayDatStr.isEmpty() ? null : LocalDate.parse(ngayDatStr));
        dp.setNgayNhanPhong(ngayNhanStr.isEmpty() ? null : LocalDate.parse(ngayNhanStr));
        dp.setNgayTraPhong(ngayTraStr.isEmpty() ? null : LocalDate.parse(ngayTraStr));
        dp.setTrangThai(trangThaiStr.isEmpty() ? TrangThaiDatPhong.DA_DAT : TrangThaiDatPhong.valueOf(trangThaiStr));
        dp.setGiaPhongNgay(giaPhongStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(giaPhongStr));
        mang[count++] = dp;
    }
            }
        } catch (IOException e) {
            System.err.println("Khong doc duoc " + path + ": " + e.getMessage());
        }
        return count;
    }

    private static boolean ghiFile() {
        boolean ok1 = ghiDanhSachNoiBo(FILE_PATH, danhSachDatPhong, soLuongDatPhong);
        boolean ok2 = ghiDanhSachNoiBo(FILE_PATH_DA_XOA, danhSachDaXoa, soLuongDaXoa);
        return ok1 && ok2;
    }

    private static boolean ghiDanhSachNoiBo(String path, DatPhong[] mang, int soLuong) {
        File f = new File(path);
        try {
            if (f.getParentFile() != null) {
                f.getParentFile().mkdirs();
            }
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(f))) {
    for (int i = 0; i < soLuong; i++) {
        DatPhong dp = mang[i];
        if (dp != null) {
            dos.writeUTF(giaTri(dp.getMaDatPhong()));
            dos.writeUTF(giaTri(dp.getMaKhachHang()));
            dos.writeUTF(giaTri(dp.getMaPhong()));
            dos.writeUTF(giaTri(dp.getNgayDat()));
            dos.writeUTF(giaTri(dp.getNgayNhanPhong()));
            dos.writeUTF(giaTri(dp.getNgayTraPhong()));
            dos.writeUTF(dp.getTrangThai() == null ? TrangThaiDatPhong.DA_DAT.name() : dp.getTrangThai().name());
            dos.writeUTF(giaTri(dp.getGiaPhongNgay()));
        }
    }
}
        } catch (IOException e) {
            System.err.println("Khong ghi duoc " + path + ": " + e.getMessage());
            return false;
        }
        return true;
    }

    private static String giaTri(Object o) {
        return o == null ? "" : o.toString();
    }
}