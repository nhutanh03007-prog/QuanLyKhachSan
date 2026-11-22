package DAO;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import model.HoaDon;
import model.TrangThaiThanhToan;

public class HoaDonDAO {
    private static final String FILE_PATH = "DATABASEH/HoaDon.txt";
    public static HoaDon[] danhSachHoaDon = new HoaDon[100];
    public static int soLuongHoaDon = 0;
    static {
        docFile();
    }

    private static void ghiChuoi(DataOutputStream dos, String s) throws IOException {
        dos.writeUTF(s == null ? "" : s);
    }

    private static String docChuoi(DataInputStream dis) throws IOException {
        String s = dis.readUTF();
        return s.isEmpty() ? null : s;
    }

    private static void ghiNgay(DataOutputStream dos, LocalDate d) throws IOException {
        dos.writeLong(d == null ? -1L : d.toEpochDay());
    }

    private static LocalDate docNgay(DataInputStream dis) throws IOException {
        long v = dis.readLong();
        return v == -1L ? null : LocalDate.ofEpochDay(v);
    }

    private static void docFile() {
        soLuongHoaDon = 0;
        Arrays.fill(danhSachHoaDon, null);
        File f = new File(FILE_PATH);
        try {
            if (!f.exists() || f.length() == 0)
                return;
            try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(f)))) {
                int count = dis.readInt();
                if (count < 0 || count > 100)
                    throw new IOException("So luong khong hop le");
                for (int i = 0; i < count; i++) {
                    HoaDon h = new HoaDon();
                    h.setMaHD(docChuoi(dis));
                    h.setMaDatPhong(docChuoi(dis));
                    h.setNgayLap(docNgay(dis));
                    h.setNgayThanhToan(docNgay(dis));
                    h.setTongGia(new BigDecimal(dis.readUTF()));
                    h.setGiamGia(new BigDecimal(dis.readUTF()));
                    h.setThanhTien(new BigDecimal(dis.readUTF()));
                    h.setPhuongThucTT(docChuoi(dis));
                    String t = docChuoi(dis);
                    h.setTrangThai(t == null ? null : TrangThaiThanhToan.valueOf(t));
                    if (hopLe(h) && timNoiBo(h.getMaHD()) == null && timNoiBoDatPhong(h.getMaDatPhong()) == null) {
                        h.setThanhTien(tinhThanhTienNoiBo(h));
                        danhSachHoaDon[soLuongHoaDon++] = h;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Khong doc duoc " + FILE_PATH + ": " + e.getMessage());
        }
    }

    private static HoaDon timNoiBo(String ma) {
        for (int i = 0; i < soLuongHoaDon; i++)
            if (ma.equals(danhSachHoaDon[i].getMaHD()))
                return danhSachHoaDon[i];
        return null;
    }

    private static HoaDon timNoiBoDatPhong(String ma) {
        for (int i = 0; i < soLuongHoaDon; i++)
            if (ma.equals(danhSachHoaDon[i].getMaDatPhong()))
                return danhSachHoaDon[i];
        return null;
    }

    private static boolean ghiFile() {
        File f = new File(FILE_PATH);
        if (!f.exists()) {
            System.err.println("Khong tim thay file: " + FILE_PATH);
            return false;
        }
        try {
            try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)))) {
                dos.writeInt(soLuongHoaDon);
                for (int i = 0; i < soLuongHoaDon; i++) {
                    HoaDon h = danhSachHoaDon[i];
                    ghiChuoi(dos, h.getMaHD());
                    ghiChuoi(dos, h.getMaDatPhong());
                    ghiNgay(dos, h.getNgayLap());
                    ghiNgay(dos, h.getNgayThanhToan());
                    dos.writeUTF(h.getTongGia().toString());
                    dos.writeUTF(h.getGiamGia().toString());
                    dos.writeUTF(tinhThanhTienNoiBo(h).toString());
                    ghiChuoi(dos, h.getPhuongThucTT());
                    ghiChuoi(dos, h.getTrangThai() == null ? null : h.getTrangThai().name());
                }
            }
            return true;
        } catch (Exception e) {
            System.err.println("Khong ghi duoc " + FILE_PATH + ": " + e.getMessage());
            return false;
        }
    }

    public boolean them(HoaDon h) {
        if (!hopLe(h) || timTheoMa(h.getMaHD()) != null || timTheoMaDatPhong(h.getMaDatPhong()) != null
                || soLuongHoaDon >= 100)
            return false;
        if (h.getNgayLap() == null)
            h.setNgayLap(LocalDate.now());
        if (h.getTrangThai() == null)
            h.setTrangThai(TrangThaiThanhToan.CHUA_THANH_TOAN);
        h.setThanhTien(tinhThanhTien(h));
        int old = soLuongHoaDon;
        danhSachHoaDon[soLuongHoaDon++] = h;
        if (!ghiFile()) {
            soLuongHoaDon = old;
            danhSachHoaDon[old] = null;
            return false;
        }
        return true;
    }

    public boolean sua(HoaDon h) {
        if (!hopLe(h))
            return false;
        for (int i = 0; i < soLuongHoaDon; i++)
            if (danhSachHoaDon[i] != null && h.getMaHD().equals(danhSachHoaDon[i].getMaHD())) {
                if (danhSachHoaDon[i].getTrangThai() == TrangThaiThanhToan.DA_THANH_TOAN)
                    return false;
                HoaDon old = danhSachHoaDon[i];
                h.setThanhTien(tinhThanhTien(h));
                danhSachHoaDon[i] = h;
                if (!ghiFile()) {
                    danhSachHoaDon[i] = old;
                    return false;
                }
                return true;
            }
        return false;
    }

    public boolean xoa(String ma) {
        for (int i = 0; i < soLuongHoaDon; i++)
            if (danhSachHoaDon[i] != null && ma != null && ma.equals(danhSachHoaDon[i].getMaHD())) {
                if (danhSachHoaDon[i].getTrangThai() == TrangThaiThanhToan.DA_THANH_TOAN)
                    return false;
                HoaDon[] old = Arrays.copyOf(danhSachHoaDon, soLuongHoaDon);
                int n = soLuongHoaDon;
                System.arraycopy(danhSachHoaDon, i + 1, danhSachHoaDon, i, --soLuongHoaDon - i);
                danhSachHoaDon[soLuongHoaDon] = null;
                if (!ghiFile()) {
                    System.arraycopy(old, 0, danhSachHoaDon, 0, n);
                    soLuongHoaDon = n;
                    return false;
                }
                return true;
            }
        return false;
    }

    public BigDecimal tinhThanhTien(HoaDon h) {
        return tinhThanhTienNoiBo(h);
    }

    public boolean capNhatTrangThai(String ma, TrangThaiThanhToan t, String p, LocalDate d) {
        HoaDon h = timTheoMa(ma);
        if (h == null || t == null)
            return false;
        TrangThaiThanhToan oldT = h.getTrangThai();
        String oldP = h.getPhuongThucTT();
        LocalDate oldD = h.getNgayThanhToan();
        h.setTrangThai(t);
        h.setPhuongThucTT(p);
        h.setNgayThanhToan(d);
        if (!ghiFile()) {
            h.setTrangThai(oldT);
            h.setPhuongThucTT(oldP);
            h.setNgayThanhToan(oldD);
            return false;
        }
        return true;
    }

    public HoaDon timTheoMa(String ma) {
        if (rong(ma))
            return null;
        for (int i = 0; i < soLuongHoaDon; i++)
            if (danhSachHoaDon[i] != null && ma.equals(danhSachHoaDon[i].getMaHD()))
                return danhSachHoaDon[i];
        return null;
    }

    public HoaDon timTheoMaDatPhong(String dp) {
        if (rong(dp))
            return null;
        for (int i = 0; i < soLuongHoaDon; i++)
            if (danhSachHoaDon[i] != null && dp.equals(danhSachHoaDon[i].getMaDatPhong()))
                return danhSachHoaDon[i];
        return null;
    }

    public HoaDon[] layDanhSach() {
        return Arrays.copyOf(danhSachHoaDon, soLuongHoaDon);
    }

    private static BigDecimal tinhThanhTienNoiBo(HoaDon h) {
        return h == null || h.getTongGia() == null || h.getGiamGia() == null ? BigDecimal.ZERO
                : h.getTongGia().subtract(h.getGiamGia());
    }

    private static boolean hopLe(HoaDon h) {
        return h != null && !rong(h.getMaHD()) && !rong(h.getMaDatPhong()) && h.getTongGia() != null
                && h.getGiamGia() != null && h.getTongGia().signum() >= 0 && h.getGiamGia().signum() >= 0
                && h.getGiamGia().compareTo(h.getTongGia()) <= 0;
    }

    private static boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}
