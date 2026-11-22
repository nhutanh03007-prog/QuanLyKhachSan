package DAO;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import model.ThanhToan;
import model.TrangThaiThanhToan;

public class ThanhToanDAO {
    private static final String FILE_PATH = "DATABASEH/ThanhToan.dat";
    public static ThanhToan[] danhSachThanhToan = new ThanhToan[100];
    public static int soLuongThanhToan = 0;

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
        soLuongThanhToan = 0;
        Arrays.fill(danhSachThanhToan, null);
        File f = new File(FILE_PATH);
        try {
            if (!f.exists() || f.length() == 0)
                return;
            try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(f)))) {
                int count = dis.readInt();
                if (count < 0)
                    throw new IOException("So luong khong hop le");
                if (count > danhSachThanhToan.length) {
                    danhSachThanhToan = Arrays.copyOf(danhSachThanhToan, count * 2);
                }
                for (int i = 0; i < count; i++) {
                    String t = docChuoi(dis);
                    ThanhToan tToan = new ThanhToan(docChuoi(dis), docChuoi(dis), new BigDecimal(dis.readUTF()),
                            docChuoi(dis), docNgay(dis), t == null ? null : TrangThaiThanhToan.valueOf(t));
                    if (hopLe(tToan) && timNoiBo(tToan.getMaThanhToan()) == null)
                        danhSachThanhToan[soLuongThanhToan++] = tToan;
                }
            }
        } catch (IOException e) {
            System.err.println("Khong doc duoc " + FILE_PATH + ": " + e.getMessage());
        }
    }

    private static ThanhToan timNoiBo(String ma) {
        for (int i = 0; i < soLuongThanhToan; i++)
            if (ma.equals(danhSachThanhToan[i].getMaThanhToan()))
                return danhSachThanhToan[i];
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
                dos.writeInt(soLuongThanhToan);
                for (int i = 0; i < soLuongThanhToan; i++) {
                    ThanhToan t = danhSachThanhToan[i];
                    ghiChuoi(dos, t.getMaThanhToan());
                    ghiChuoi(dos, t.getMaHD());
                    dos.writeUTF(t.getSoTien().toString());
                    ghiChuoi(dos, t.getPhuongThuc());
                    ghiNgay(dos, t.getNgayThanhToan());
                    ghiChuoi(dos, t.getTrangThai() == null ? null : t.getTrangThai().name());
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Khong ghi duoc " + FILE_PATH + ": " + e.getMessage());
            return false;
        }
    }

    public boolean them(ThanhToan t) {
        if (!hopLe(t) || timTheoMa(t.getMaThanhToan()) != null)
            return false;
        if (soLuongThanhToan >= danhSachThanhToan.length) {
            danhSachThanhToan = Arrays.copyOf(danhSachThanhToan, danhSachThanhToan.length * 2);
        }
        int old = soLuongThanhToan;
        danhSachThanhToan[soLuongThanhToan++] = t;
        if (!ghiFile()) {
            soLuongThanhToan = old;
            danhSachThanhToan[old] = null;
            return false;
        }
        return true;
    }

    public boolean sua(ThanhToan t) {
        if (!hopLe(t))
            return false;
        for (int i = 0; i < soLuongThanhToan; i++)
            if (danhSachThanhToan[i] != null && t.getMaThanhToan().equals(danhSachThanhToan[i].getMaThanhToan())) {
                ThanhToan old = danhSachThanhToan[i];
                danhSachThanhToan[i] = t;
                if (!ghiFile()) {
                    danhSachThanhToan[i] = old;
                    return false;
                }
                return true;
            }
        return false;
    }

    public boolean xoa(String ma) {
        for (int i = 0; i < soLuongThanhToan; i++)
            if (danhSachThanhToan[i] != null && ma != null && ma.equals(danhSachThanhToan[i].getMaThanhToan())) {
                ThanhToan[] old = Arrays.copyOf(danhSachThanhToan, soLuongThanhToan);
                int n = soLuongThanhToan;
                System.arraycopy(danhSachThanhToan, i + 1, danhSachThanhToan, i, --soLuongThanhToan - i);
                danhSachThanhToan[soLuongThanhToan] = null;
                if (!ghiFile()) {
                    System.arraycopy(old, 0, danhSachThanhToan, 0, n);
                    soLuongThanhToan = n;
                    return false;
                }
                return true;
            }
        return false;
    }

    public ThanhToan timTheoMa(String ma) {
        if (rong(ma))
            return null;
        for (int i = 0; i < soLuongThanhToan; i++)
            if (danhSachThanhToan[i] != null && ma.equals(danhSachThanhToan[i].getMaThanhToan()))
                return danhSachThanhToan[i];
        return null;
    }

    public ThanhToan[] layTheoMaHD(String ma) {
        if (rong(ma)) {
            return new ThanhToan[0];
        }
        ThanhToan[] ketQuaTam = new ThanhToan[soLuongThanhToan];
        int dem = 0;
        for (int i = 0; i < soLuongThanhToan; i++) {
            if (danhSachThanhToan[i] != null && ma.equals(danhSachThanhToan[i].getMaHD())) {
                ketQuaTam[dem++] = danhSachThanhToan[i];
            }
        }
        return Arrays.copyOf(ketQuaTam, dem);
    }

    public ThanhToan[] layDanhSach() {
        return Arrays.copyOf(danhSachThanhToan, soLuongThanhToan);
    }

    private static boolean hopLe(ThanhToan t) {
        return t != null && !rong(t.getMaThanhToan()) && !rong(t.getMaHD()) && !rong(t.getPhuongThuc())
                && t.getSoTien() != null && t.getSoTien().signum() > 0;
    }

    private static boolean rong(String s) {
        return s == null || s.trim().isEmpty();
    }
}