package DAO;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import model.ChiTietDichVu;

public class ChiTietDichVuDAO {
    private static final String FILE_PATH = "DATABASEH/ChiTietDichVu.dat";
    public static ChiTietDichVu[] danhSachChiTiet = new ChiTietDichVu[100];
    public static int soLuongChiTiet = 0;
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
        soLuongChiTiet = 0;
        Arrays.fill(danhSachChiTiet, null);
        File f = new File(FILE_PATH);
        try {
            if (!f.exists() || f.length() == 0)
                return;
            try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(f)))) {
                int count = dis.readInt();
                if (count < 0 || count > 100)
                    throw new IOException("So luong khong hop le");
                for (int i = 0; i < count; i++) {
                    ChiTietDichVu c = new ChiTietDichVu(docChuoi(dis), docChuoi(dis), docNgay(dis), docNgay(dis),
                            dis.readInt(), new BigDecimal(dis.readUTF()));
                    BigDecimal total = new BigDecimal(dis.readUTF());
                    if (hopLe(c) && timNoiBo(c.getMaDatPhong(), c.getMaDichVu()) == null) {
                        c.setThanhTien(total);
                        danhSachChiTiet[soLuongChiTiet++] = c;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Khong doc duoc " + FILE_PATH + ": " + e.getMessage());
        }
    }

    private static ChiTietDichVu timNoiBo(String dp, String dv) {
        for (int i = 0; i < soLuongChiTiet; i++)
            if (cungKhoa(danhSachChiTiet[i], dp, dv))
                return danhSachChiTiet[i];
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
                dos.writeInt(soLuongChiTiet);
                for (int i = 0; i < soLuongChiTiet; i++) {
                    ChiTietDichVu c = danhSachChiTiet[i];
                    ghiChuoi(dos, c.getMaDatPhong());
                    ghiChuoi(dos, c.getMaDichVu());
                    ghiNgay(dos, c.getNgayDatDichVu());
                    ghiNgay(dos, c.getNgayNhan());
                    dos.writeInt(c.getSoLuong());
                    dos.writeUTF(c.getDonGia().toString());
                    dos.writeUTF(tinhThanhTienNoiBo(c).toString());
                }
            }
            return true;
        } catch (Exception e) {
            System.err.println("Khong ghi duoc " + FILE_PATH + ": " + e.getMessage());
            return false;
        }
    }

    public boolean them(ChiTietDichVu c) {
        if (!hopLe(c))
            return false;
        ChiTietDichVu cu = timTheoMa(c.getMaDatPhong(), c.getMaDichVu());
        if (cu != null) {
            int sl = cu.getSoLuong();
            BigDecimal dg = cu.getDonGia(), tt = cu.getThanhTien();
            cu.setSoLuong(sl + c.getSoLuong());
            cu.setDonGia(c.getDonGia());
            cu.setThanhTien(tinhThanhTien(cu));
            if (!ghiFile()) {
                cu.setSoLuong(sl);
                cu.setDonGia(dg);
                cu.setThanhTien(tt);
                return false;
            }
            return true;
        }
        if (soLuongChiTiet >= 100)
            return false;
        c.setThanhTien(tinhThanhTien(c));
        int old = soLuongChiTiet;
        danhSachChiTiet[soLuongChiTiet++] = c;
        if (!ghiFile()) {
            soLuongChiTiet = old;
            danhSachChiTiet[old] = null;
            return false;
        }
        return true;
    }

    public boolean sua(ChiTietDichVu c) {
        if (!hopLe(c))
            return false;
        for (int i = 0; i < soLuongChiTiet; i++)
            if (cungKhoa(danhSachChiTiet[i], c.getMaDatPhong(), c.getMaDichVu())) {
                ChiTietDichVu old = danhSachChiTiet[i];
                BigDecimal tt = c.getThanhTien();
                c.setThanhTien(tinhThanhTien(c));
                danhSachChiTiet[i] = c;
                if (!ghiFile()) {
                    danhSachChiTiet[i] = old;
                    c.setThanhTien(tt);
                    return false;
                }
                return true;
            }
        return false;
    }

    public BigDecimal tinhThanhTien(ChiTietDichVu c) {
        return tinhThanhTienNoiBo(c);
    }

    public boolean xoa(String dp, String dv) {
        for (int i = 0; i < soLuongChiTiet; i++)
            if (cungKhoa(danhSachChiTiet[i], dp, dv)) {
                ChiTietDichVu[] old = Arrays.copyOf(danhSachChiTiet, soLuongChiTiet);
                int n = soLuongChiTiet;
                System.arraycopy(danhSachChiTiet, i + 1, danhSachChiTiet, i, --soLuongChiTiet - i);
                danhSachChiTiet[soLuongChiTiet] = null;
                if (!ghiFile()) {
                    System.arraycopy(old, 0, danhSachChiTiet, 0, n);
                    soLuongChiTiet = n;
                    return false;
                }
                return true;
            }
        return false;
    }

    public ChiTietDichVu timTheoMa(String dp, String dv) {
        return timNoiBo(dp, dv);
    }

    public ChiTietDichVu[] layDanhSach() {
        return Arrays.copyOf(danhSachChiTiet, soLuongChiTiet);
    }

    public ChiTietDichVu[] layTheoMaDatPhong(String dp) {
        ArrayList<ChiTietDichVu> a = new ArrayList<>();
        for (int i = 0; i < soLuongChiTiet; i++)
            if (danhSachChiTiet[i] != null && dp != null && dp.equals(danhSachChiTiet[i].getMaDatPhong()))
                a.add(danhSachChiTiet[i]);
        return a.toArray(new ChiTietDichVu[0]);
    }

    public boolean kiemTraDichVuDangSuDung(String ma) {
        for (int i = 0; i < soLuongChiTiet; i++)
            if (danhSachChiTiet[i] != null && ma != null && ma.equals(danhSachChiTiet[i].getMaDichVu()))
                return true;
        return false;
    }

    public BigDecimal tinhTongTienTheoDatPhong(String dp) {
        BigDecimal sum = BigDecimal.ZERO;
        for (ChiTietDichVu c : layTheoMaDatPhong(dp))
            sum = sum.add(tinhThanhTien(c));
        return sum;
    }

    private static BigDecimal tinhThanhTienNoiBo(ChiTietDichVu c) {
        return c == null || c.getDonGia() == null ? BigDecimal.ZERO
                : c.getDonGia().multiply(BigDecimal.valueOf(c.getSoLuong()));
    }

    private static boolean hopLe(ChiTietDichVu c) {
        return c != null && !rong(c.getMaDatPhong()) && !rong(c.getMaDichVu()) && c.getSoLuong() > 0
                && c.getDonGia() != null && c.getDonGia().signum() >= 0;
    }

    private static boolean cungKhoa(ChiTietDichVu c, String dp, String dv) {
        return c != null && dp != null && dv != null && dp.equals(c.getMaDatPhong()) && dv.equals(c.getMaDichVu());
    }

    private static boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}
