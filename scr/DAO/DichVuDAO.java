package DAO;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import model.DichVu;

public class DichVuDAO {
    private static final String FILE_PATH = "DATABASEH/DichVu.dat";
    public static DichVu[] danhSachDichVu = new DichVu[100];
    public static int soLuongDichVu = 0;
    static {
        docFile();
    }

    public DichVuDAO() {
    }

    private static void ghiChuoi(DataOutputStream dos, String s) throws IOException {
        dos.writeUTF(s == null ? "" : s);
    }

    private static String docChuoi(DataInputStream dis) throws IOException {
        String s = dis.readUTF();
        return s.isEmpty() ? null : s;
    }

    private static void docFile() {
        soLuongDichVu = 0;
        Arrays.fill(danhSachDichVu, null);
        File f = new File(FILE_PATH);
        try {
            if (!f.exists() || f.length() == 0)
                return;
            try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(f)))) {
                int count = dis.readInt();
                if (count < 0 || count > 100)
                    throw new IOException("So luong khong hop le");
                for (int i = 0; i < count; i++) {
                    DichVu d = new DichVu(docChuoi(dis), docChuoi(dis), docChuoi(dis), docChuoi(dis),
                            new BigDecimal(dis.readUTF()), docChuoi(dis), dis.readUTF());
                    if (hopLe(d) && timNoiBo(d.getMaDichVu()) == null)
                        danhSachDichVu[soLuongDichVu++] = d;
                }
            }
        } catch (Exception e) {
            System.err.println("Khong doc duoc " + FILE_PATH + ": " + e.getMessage());
        }
    }

    private static DichVu timNoiBo(String ma) {
        for (int i = 0; i < soLuongDichVu; i++)
            if (danhSachDichVu[i] != null && ma.equals(danhSachDichVu[i].getMaDichVu()))
                return danhSachDichVu[i];
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
                dos.writeInt(soLuongDichVu);
                for (int i = 0; i < soLuongDichVu; i++) {
                    DichVu d = danhSachDichVu[i];
                    ghiChuoi(dos, d.getMaDichVu());
                    ghiChuoi(dos, d.getTenDichVu());
                    ghiChuoi(dos, d.getLoaiDichVu());
                    ghiChuoi(dos, d.getDonViTinh());
                    dos.writeUTF(d.getGia().toString());
                    ghiChuoi(dos, d.getTrangThai());
                    dos.writeUTF(d.getMoTa() == null ? "" : d.getMoTa());
                }
            }
            return true;
        } catch (Exception e) {
            System.err.println("Khong ghi duoc " + FILE_PATH + ": " + e.getMessage());
            return false;
        }
    }

    public boolean kiemTraTenDichVuKhongRong(String s) {
        return !rong(s);
    }

    public boolean kiemTraSoTienKhongAm(BigDecimal n) {
        return n != null && n.signum() >= 0;
    }

    public boolean them(DichVu d) {
        if (!hopLe(d) || timTheoMa(d.getMaDichVu()) != null || soLuongDichVu >= 100)
            return false;
        int old = soLuongDichVu;
        danhSachDichVu[soLuongDichVu++] = d;
        if (!ghiFile()) {
            soLuongDichVu = old;
            danhSachDichVu[old] = null;
            return false;
        }
        return true;
    }

    public boolean sua(DichVu d) {
        if (!hopLe(d))
            return false;
        for (int i = 0; i < soLuongDichVu; i++)
            if (danhSachDichVu[i] != null && d.getMaDichVu().equals(danhSachDichVu[i].getMaDichVu())) {
                DichVu old = danhSachDichVu[i];
                danhSachDichVu[i] = d;
                if (!ghiFile()) {
                    danhSachDichVu[i] = old;
                    return false;
                }
                return true;
            }
        return false;
    }

    public boolean xoa(String ma) {
        for (int i = 0; i < soLuongDichVu; i++)
            if (danhSachDichVu[i] != null && ma != null && ma.equals(danhSachDichVu[i].getMaDichVu())) {
                DichVu[] old = Arrays.copyOf(danhSachDichVu, soLuongDichVu);
                int n = soLuongDichVu;
                System.arraycopy(danhSachDichVu, i + 1, danhSachDichVu, i, --soLuongDichVu - i);
                danhSachDichVu[soLuongDichVu] = null;
                if (!ghiFile()) {
                    System.arraycopy(old, 0, danhSachDichVu, 0, n);
                    soLuongDichVu = n;
                    return false;
                }
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
        return Arrays.copyOf(danhSachDichVu, soLuongDichVu);
    }

    private static boolean hopLe(DichVu d) {
        return d != null && !rong(d.getMaDichVu()) && !rong(d.getTenDichVu()) && !rong(d.getLoaiDichVu())
                && !rong(d.getDonViTinh()) && d.getGia() != null && d.getGia().signum() > 0;
    }

    private static boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}
