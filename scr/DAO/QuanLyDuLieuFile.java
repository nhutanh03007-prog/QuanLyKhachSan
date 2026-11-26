package DAO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import model.ChiTietDichVu;
import model.DatPhong;
import model.DichVu;
import model.HoaDon;
import model.KhachHang;
import model.NhanVien;
import model.Phong;
import model.PhongThuong;
import model.PhongVIP;
import model.TaiKhoan;
import model.ThanhToan;
import model.TinhTrangPhong;
import model.TrangThaiDatPhong;
import model.TrangThaiThanhToan;

/**
 * Lop trung tam doc va ghi toan bo du lieu trong thu muc DATABASEH.
 * Moi cap doc/ghi dat canh nhau theo dung cung thu tu truong de tranh lech du lieu.
 */
public final class QuanLyDuLieuFile {

    private static final String TEN_THU_MUC = "DATABASEH";
    private static boolean daNap = false;

    private QuanLyDuLieuFile() {
    }

    // =========================================================================
    // TIM THU MUC DATABASEH
    // =========================================================================

    public static File layThuMucDatabase() {
        File cwd = new File(System.getProperty("user.dir")).getAbsoluteFile();

        // 1. Uu tien tim thu muc goc du an (phai co ca thu muc scr/ va DATABASEH/)
        File found = timThuMucDuAnLenCha(cwd, 4);
        if (found != null) {
            return found;
        }

        // 2. Tim tu vi tri file .class dang chay
        try {
            URI uri = QuanLyDuLieuFile.class.getProtectionDomain().getCodeSource().getLocation().toURI();
            File codeLoc = new File(uri).getAbsoluteFile();
            File startDir = codeLoc.isFile() ? codeLoc.getParentFile() : codeLoc;
            found = timThuMucDuAnLenCha(startDir, 6);
            if (found != null) {
                return found;
            }
        } catch (Exception ignored) {
        }

        // 3. Tim thu muc du an trong cwd hoac user.home (xu ly truong hop Oracle Java nbcode chay tu .vscode/extensions/.../nbcode)
        File[] ungVien = {
            new File(cwd, "QuanLyKhachSan"),
            timTuHome("QuanLyKhachSan"),
            timTuHome("Desktop" + File.separator + "QuanLyKhachSan"),
            timTuHome("Documents" + File.separator + "QuanLyKhachSan")
        };
        for (File dir : ungVien) {
            if (laThuMucDuAn(dir)) {
                return new File(dir, TEN_THU_MUC);
            }
        }

        // 4. Fallback: neu khong co scr/, tim DATABASEH binh thuong (phuc vu moi truong test doc lap)
        found = timTrongThuMucCha(cwd, 4);
        if (found != null) {
            return found;
        }

        return new File(cwd, TEN_THU_MUC);
    }

    private static File timTuHome(String duongDanCon) {
        String home = System.getProperty("user.home");
        if (home == null || home.isEmpty()) {
            return null;
        }
        return new File(home, duongDanCon);
    }

    private static boolean laThuMucDuAn(File dir) {
        return dir != null
                && new File(dir, "scr").isDirectory()
                && new File(dir, TEN_THU_MUC).isDirectory();
    }

    private static File timThuMucDuAnLenCha(File dir, int maxDepth) {
        File cur = dir;
        for (int i = 0; i <= maxDepth && cur != null; i++) {
            if (laThuMucDuAn(cur)) {
                return new File(cur, TEN_THU_MUC);
            }
            cur = cur.getParentFile();
        }
        return null;
    }

    private static File timTrongThuMucCha(File dir, int maxDepth) {
        File cur = dir;
        for (int i = 0; i <= maxDepth && cur != null; i++) {
            File candidate = new File(cur, TEN_THU_MUC);
            if (candidate.exists() && candidate.isDirectory()) {
                return candidate;
            }
            cur = cur.getParentFile();
        }
        return null;
    }

    private static File layFile(String tenFile) {
        return new File(layThuMucDatabase(), tenFile);
    }

    // =========================================================================
    // NAP TOAN BO DU LIEU VAO CAC DAO
    // =========================================================================

    public static synchronized void damBaoDaNap() {
        if (!daNap) {
            napTatCaDuLieu(false);
        }
    }

    public static synchronized void napTatCaDuLieu() {
        napTatCaDuLieu(true);
    }

    public static synchronized void napTatCaDuLieu(boolean inThongTin) {
        daNap = true;

        Phong[] dsPhong = docPhong();
        PhongDAO.napDuLieu(dsPhong);

        KhachHang[] dsKH = docKhachHang();
        KhachHangDAO.napDuLieu(dsKH);

        DatPhong[] dsDP = docDatPhong("DatPhong.dat");
        DatPhong[] dsDPDaXoa = docDatPhong("DatPhongDaXoa.dat");
        DatPhongDAO.napDuLieu(dsDP, dsDPDaXoa);

        DichVu[] dsDV = docDichVu();
        DichVuDAO.napDuLieu(dsDV);

        ChiTietDichVu[] dsCT = docChiTietDichVu();
        ChiTietDichVuDAO.napDuLieu(dsCT);

        HoaDon[] dsHD = docHoaDon();
        HoaDonDAO.napDuLieu(dsHD);

        ThanhToan[] dsTT = docThanhToan();
        ThanhToanDAO.napDuLieu(dsTT);

        NhanVien[] dsNV = docNhanVien();
        NhanVienDAO.napDuLieu(dsNV);

        TaiKhoan[] dsTK = docTaiKhoan();
        if (dsTK.length == 0) {
            dsTK = new TaiKhoan[]{ new TaiKhoan("admin", "admin123", "NV001", "ADMIN", "HOAT_DONG") };
            ghiTaiKhoan(dsTK, dsTK.length);
        }
        TaiKhoanDAO.napDuLieu(dsTK);

        if (inThongTin) {
            System.out.println("[DATABASEH] Thu muc du lieu: " + layThuMucDatabase().getAbsolutePath());
            System.out.println("[DATABASEH] Da nap: "
                    + dsPhong.length + " phong, "
                    + dsNV.length + " nhan vien, "
                    + dsTK.length + " tai khoan, "
                    + dsKH.length + " khach hang, "
                    + dsDV.length + " dich vu, "
                    + dsDP.length + " dat phong, "
                    + dsHD.length + " hoa don, "
                    + dsTT.length + " thanh toan.");
        }
    }

    // =========================================================================
    // HAM HO TRO DOC / GHI NGUYEN THUY
    // =========================================================================

    private static void ghiChuoi(DataOutputStream dos, String s) throws IOException {
        dos.writeUTF(s == null ? "" : s);
    }

    private static String docChuoi(DataInputStream dis) throws IOException {
        String s = dis.readUTF();
        return s.isEmpty() ? null : s;
    }

    private static void ghiNgayLong(DataOutputStream dos, LocalDate d) throws IOException {
        dos.writeLong(d == null ? -1L : d.toEpochDay());
    }

    private static LocalDate docNgayLong(DataInputStream dis) throws IOException {
        long v = dis.readLong();
        return v == -1L ? null : LocalDate.ofEpochDay(v);
    }

    private static String giaTriChuoi(Object o) {
        return o == null ? "" : o.toString();
    }

    private static boolean damBaoThuMucCha(File f) {
        File cha = f.getParentFile();
        return cha == null || cha.exists() || cha.mkdirs() || cha.exists();
    }

    // =========================================================================
    // 1. PHONG (phong.dat)
    // Quy trinh ghi/doc: loai -> ma -> ten -> gia -> tinhTrang -> (thuoc tinh rieng)
    // =========================================================================

    public static Phong[] docPhong() {
        File file = layFile("phong.dat");
        if (!file.exists() || file.length() == 0) {
            return new Phong[0];
        }
        Phong[] ds = new Phong[20];
        int dem = 0;
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            while (true) {
                String loai = dis.readUTF();
                String ma = dis.readUTF();
                String ten = dis.readUTF();
                BigDecimal gia = new BigDecimal(dis.readUTF());
                TinhTrangPhong tinhTrang = TinhTrangPhong.valueOf(dis.readUTF());

                Phong p;
                if ("THUONG".equals(loai)) {
                    int soGiuong = dis.readInt();
                    boolean coDieuHoa = dis.readBoolean();
                    p = new PhongThuong(ma, ten, gia, tinhTrang, soGiuong, coDieuHoa);
                } else if ("VIP".equals(loai)) {
                    int dienTich = dis.readInt();
                    boolean coBonTam = dis.readBoolean();
                    p = new PhongVIP(ma, ten, gia, tinhTrang, dienTich, coBonTam);
                } else {
                    throw new IOException("Loai phong khong hop le: " + loai);
                }

                if (dem >= ds.length) {
                    ds = Arrays.copyOf(ds, ds.length * 2);
                }
                ds[dem++] = p;
            }
        } catch (EOFException ignored) {
            // Doc het file binh thuong
        } catch (IOException | RuntimeException e) {
            System.err.println("Loi doc phong.dat: " + e.getMessage());
        }
        return Arrays.copyOf(ds, dem);
    }

    public static boolean ghiPhong(Phong[] danhSach, int soLuong) {
        File file = layFile("phong.dat");
        if (!damBaoThuMucCha(file)) {
            return false;
        }
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            for (int i = 0; i < soLuong; i++) {
                Phong p = danhSach[i];
                if (p instanceof PhongThuong t) {
                    dos.writeUTF("THUONG");
                    dos.writeUTF(t.getMaPhong());
                    dos.writeUTF(t.getTenPhong());
                    dos.writeUTF(t.getGiaPhong().toPlainString());
                    dos.writeUTF(t.getTinhTrang().name());
                    dos.writeInt(t.getSoGiuong());
                    dos.writeBoolean(t.isCoDieuHoa());
                } else if (p instanceof PhongVIP v) {
                    dos.writeUTF("VIP");
                    dos.writeUTF(v.getMaPhong());
                    dos.writeUTF(v.getTenPhong());
                    dos.writeUTF(v.getGiaPhong().toPlainString());
                    dos.writeUTF(v.getTinhTrang().name());
                    dos.writeInt(v.getDienTich());
                    dos.writeBoolean(v.isCoBonTam());
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Loi ghi phong.dat: " + e.getMessage());
            return false;
        }
    }

    // =========================================================================
    // 2. DAT PHONG (DatPhong.dat & DatPhongDaXoa.dat)
    // Quy trinh ghi/doc: maDP -> maKH -> maPhong -> ngayDat -> ngayNhan -> ngayTra -> trangThai -> giaPhongNgay
    // =========================================================================

    public static DatPhong[] docDatPhong(String tenFile) {
        File file = layFile(tenFile);
        if (!file.exists() || file.length() == 0) {
            return new DatPhong[0];
        }
        DatPhong[] ds = new DatPhong[20];
        int dem = 0;
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            while (true) {
                String maDP = dis.readUTF();
                String maKH = dis.readUTF();
                String maPhong = dis.readUTF();
                String ngayDatStr = dis.readUTF();
                String ngayNhanStr = dis.readUTF();
                String ngayTraStr = dis.readUTF();
                String trangThaiStr = dis.readUTF();
                String giaStr = dis.readUTF();

                DatPhong dp = new DatPhong();
                dp.setMaDatPhong(maDP);
                dp.setMaKhachHang(maKH);
                dp.setMaPhong(maPhong);
                dp.setNgayDat(ngayDatStr.isEmpty() ? null : LocalDate.parse(ngayDatStr));
                dp.setNgayNhanPhong(ngayNhanStr.isEmpty() ? null : LocalDate.parse(ngayNhanStr));
                dp.setNgayTraPhong(ngayTraStr.isEmpty() ? null : LocalDate.parse(ngayTraStr));
                dp.setTrangThai(trangThaiStr.isEmpty() ? TrangThaiDatPhong.DA_DAT : TrangThaiDatPhong.valueOf(trangThaiStr));
                dp.setGiaPhongNgay(giaStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(giaStr));

                if (dem >= ds.length) {
                    ds = Arrays.copyOf(ds, ds.length * 2);
                }
                ds[dem++] = dp;
            }
        } catch (EOFException ignored) {
            // Doc het file binh thuong
        } catch (IOException | RuntimeException e) {
            System.err.println("Loi doc " + tenFile + ": " + e.getMessage());
        }
        return Arrays.copyOf(ds, dem);
    }

    public static boolean ghiDatPhong(String tenFile, DatPhong[] danhSach, int soLuong) {
        File file = layFile(tenFile);
        if (!damBaoThuMucCha(file)) {
            return false;
        }
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            for (int i = 0; i < soLuong; i++) {
                DatPhong dp = danhSach[i];
                if (dp != null) {
                    dos.writeUTF(giaTriChuoi(dp.getMaDatPhong()));
                    dos.writeUTF(giaTriChuoi(dp.getMaKhachHang()));
                    dos.writeUTF(giaTriChuoi(dp.getMaPhong()));
                    dos.writeUTF(giaTriChuoi(dp.getNgayDat()));
                    dos.writeUTF(giaTriChuoi(dp.getNgayNhanPhong()));
                    dos.writeUTF(giaTriChuoi(dp.getNgayTraPhong()));
                    dos.writeUTF(dp.getTrangThai() == null ? TrangThaiDatPhong.DA_DAT.name() : dp.getTrangThai().name());
                    dos.writeUTF(giaTriChuoi(dp.getGiaPhongNgay()));
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Loi ghi " + tenFile + ": " + e.getMessage());
            return false;
        }
    }

    // =========================================================================
    // 3. KHACH HANG (KhachHang.dat)
    // Quy trinh ghi/doc: maKH -> hoTen -> gioiTinh -> ngaySinh -> diaChi -> sdt -> email -> loaiKhach -> ghiChu
    // =========================================================================

    public static KhachHang[] docKhachHang() {
        File file = layFile("KhachHang.dat");
        if (!file.exists() || file.length() == 0) {
            return new KhachHang[0];
        }
        KhachHang[] ds = new KhachHang[20];
        int dem = 0;
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            while (true) {
                String maKH = dis.readUTF();
                String hoTen = dis.readUTF();
                String gioiTinh = dis.readUTF();
                String ngaySinhStr = dis.readUTF();
                String diaChi = dis.readUTF();
                String sdt = dis.readUTF();
                String email = dis.readUTF();
                String loaiKhach = dis.readUTF();
                String ghiChu = dis.readUTF();

                LocalDate ngaySinh = ngaySinhStr.isEmpty() ? null : LocalDate.parse(ngaySinhStr);
                if (dem >= ds.length) {
                    ds = Arrays.copyOf(ds, ds.length * 2);
                }
                ds[dem++] = new KhachHang(maKH, hoTen, gioiTinh, ngaySinh, diaChi, sdt, email, loaiKhach, ghiChu);
            }
        } catch (EOFException ignored) {
            // Doc het file binh thuong
        } catch (IOException | RuntimeException e) {
            System.err.println("Loi doc KhachHang.dat: " + e.getMessage());
        }
        return Arrays.copyOf(ds, dem);
    }

    public static boolean ghiKhachHang(KhachHang[] danhSach, int soLuong) {
        File file = layFile("KhachHang.dat");
        if (!damBaoThuMucCha(file)) {
            return false;
        }
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            for (int i = 0; i < soLuong; i++) {
                KhachHang kh = danhSach[i];
                if (kh != null) {
                    dos.writeUTF(giaTriChuoi(kh.getMaKH()));
                    dos.writeUTF(giaTriChuoi(kh.getHoTen()));
                    dos.writeUTF(giaTriChuoi(kh.getGioiTinh()));
                    dos.writeUTF(giaTriChuoi(kh.getNgaySinh()));
                    dos.writeUTF(giaTriChuoi(kh.getDiaChi()));
                    dos.writeUTF(giaTriChuoi(kh.getSdt()));
                    dos.writeUTF(giaTriChuoi(kh.getEmail()));
                    dos.writeUTF(giaTriChuoi(kh.getLoaiKhach()));
                    dos.writeUTF(giaTriChuoi(kh.getGhiChu()));
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Loi ghi KhachHang.dat: " + e.getMessage());
            return false;
        }
    }

    // =========================================================================
    // 4. DICH VU (DichVu.dat)
    // Quy trinh ghi/doc: count -> [maDV -> tenDV -> loaiDV -> donVi -> gia -> trangThai -> moTa]
    // =========================================================================

    public static DichVu[] docDichVu() {
        File file = layFile("DichVu.dat");
        if (!file.exists() || file.length() == 0) {
            return new DichVu[0];
        }
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            int count = dis.readInt();
            if (count < 0 || count > 100000) {
                throw new IOException("So luong dich vu khong hop le: " + count);
            }
            DichVu[] ds = new DichVu[count];
            int dem = 0;
            for (int i = 0; i < count; i++) {
                String ma = docChuoi(dis);
                String ten = docChuoi(dis);
                String loai = docChuoi(dis);
                String donVi = docChuoi(dis);
                BigDecimal gia = new BigDecimal(dis.readUTF());
                String trangThai = docChuoi(dis);
                String moTa = dis.readUTF();
                ds[dem++] = new DichVu(ma, ten, loai, donVi, gia, trangThai, moTa);
            }
            return Arrays.copyOf(ds, dem);
        } catch (Exception e) {
            System.err.println("Loi doc DichVu.dat: " + e.getMessage());
            return new DichVu[0];
        }
    }

    public static boolean ghiDichVu(DichVu[] danhSach, int soLuong) {
        File file = layFile("DichVu.dat");
        if (!damBaoThuMucCha(file)) {
            return false;
        }
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            dos.writeInt(soLuong);
            for (int i = 0; i < soLuong; i++) {
                DichVu d = danhSach[i];
                ghiChuoi(dos, d.getMaDichVu());
                ghiChuoi(dos, d.getTenDichVu());
                ghiChuoi(dos, d.getLoaiDichVu());
                ghiChuoi(dos, d.getDonViTinh());
                dos.writeUTF(d.getGia().toString());
                ghiChuoi(dos, d.getTrangThai());
                dos.writeUTF(d.getMoTa() == null ? "" : d.getMoTa());
            }
            return true;
        } catch (Exception e) {
            System.err.println("Loi ghi DichVu.dat: " + e.getMessage());
            return false;
        }
    }

    // =========================================================================
    // 5. CHI TIET DICH VU (ChiTietDichVu.dat)
    // Quy trinh ghi/doc: count -> [maDP -> maDV -> ngayDat(long) -> ngayNhan(long) -> soLuong(int) -> donGia -> thanhTien]
    // =========================================================================

    public static ChiTietDichVu[] docChiTietDichVu() {
        File file = layFile("ChiTietDichVu.dat");
        if (!file.exists() || file.length() == 0) {
            return new ChiTietDichVu[0];
        }
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            int count = dis.readInt();
            if (count < 0 || count > 100000) {
                throw new IOException("So luong chi tiet dich vu khong hop le: " + count);
            }
            ChiTietDichVu[] ds = new ChiTietDichVu[count];
            int dem = 0;
            for (int i = 0; i < count; i++) {
                String maDP = docChuoi(dis);
                String maDV = docChuoi(dis);
                LocalDate ngayDat = docNgayLong(dis);
                LocalDate ngayNhan = docNgayLong(dis);
                int sl = dis.readInt();
                BigDecimal donGia = new BigDecimal(dis.readUTF());
                BigDecimal thanhTien = new BigDecimal(dis.readUTF());
                ChiTietDichVu c = new ChiTietDichVu(maDP, maDV, ngayDat, ngayNhan, sl, donGia);
                c.setThanhTien(thanhTien);
                ds[dem++] = c;
            }
            return Arrays.copyOf(ds, dem);
        } catch (Exception e) {
            System.err.println("Loi doc ChiTietDichVu.dat: " + e.getMessage());
            return new ChiTietDichVu[0];
        }
    }

    public static boolean ghiChiTietDichVu(ChiTietDichVu[] danhSach, int soLuong) {
        File file = layFile("ChiTietDichVu.dat");
        if (!damBaoThuMucCha(file)) {
            return false;
        }
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            dos.writeInt(soLuong);
            for (int i = 0; i < soLuong; i++) {
                ChiTietDichVu c = danhSach[i];
                ghiChuoi(dos, c.getMaDatPhong());
                ghiChuoi(dos, c.getMaDichVu());
                ghiNgayLong(dos, c.getNgayDatDichVu());
                ghiNgayLong(dos, c.getNgayNhan());
                dos.writeInt(c.getSoLuong());
                dos.writeUTF(c.getDonGia().toString());
                BigDecimal thanhTien = c.getDonGia().multiply(BigDecimal.valueOf(c.getSoLuong()));
                dos.writeUTF(thanhTien.toString());
            }
            return true;
        } catch (Exception e) {
            System.err.println("Loi ghi ChiTietDichVu.dat: " + e.getMessage());
            return false;
        }
    }

    // =========================================================================
    // 6. HOA DON (HoaDon.dat)
    // Quy trinh ghi/doc: count -> [maHD -> maDP -> ngayLap(long) -> ngayTT(long) -> tongGia -> giamGia -> thanhTien -> phuongThuc -> trangThai]
    // =========================================================================

    public static HoaDon[] docHoaDon() {
        File file = layFile("HoaDon.dat");
        if (!file.exists() || file.length() == 0) {
            return new HoaDon[0];
        }
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            int count = dis.readInt();
            if (count < 0 || count > 100000) {
                throw new IOException("So luong hoa don khong hop le: " + count);
            }
            HoaDon[] ds = new HoaDon[count];
            int dem = 0;
            for (int i = 0; i < count; i++) {
                HoaDon h = new HoaDon();
                h.setMaHD(docChuoi(dis));
                h.setMaDatPhong(docChuoi(dis));
                h.setNgayLap(docNgayLong(dis));
                h.setNgayThanhToan(docNgayLong(dis));
                h.setTongGia(new BigDecimal(dis.readUTF()));
                h.setGiamGia(new BigDecimal(dis.readUTF()));
                h.setThanhTien(new BigDecimal(dis.readUTF()));
                h.setPhuongThucTT(docChuoi(dis));
                String trangThaiStr = docChuoi(dis);
                h.setTrangThai(trangThaiStr == null ? null : TrangThaiThanhToan.valueOf(trangThaiStr));
                ds[dem++] = h;
            }
            return Arrays.copyOf(ds, dem);
        } catch (Exception e) {
            System.err.println("Loi doc HoaDon.dat: " + e.getMessage());
            return new HoaDon[0];
        }
    }

    public static boolean ghiHoaDon(HoaDon[] danhSach, int soLuong) {
        File file = layFile("HoaDon.dat");
        if (!damBaoThuMucCha(file)) {
            return false;
        }
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            dos.writeInt(soLuong);
            for (int i = 0; i < soLuong; i++) {
                HoaDon h = danhSach[i];
                ghiChuoi(dos, h.getMaHD());
                ghiChuoi(dos, h.getMaDatPhong());
                ghiNgayLong(dos, h.getNgayLap());
                ghiNgayLong(dos, h.getNgayThanhToan());
                dos.writeUTF(h.getTongGia().toString());
                dos.writeUTF(h.getGiamGia().toString());
                BigDecimal thanhTien = h.getTongGia().subtract(h.getGiamGia());
                dos.writeUTF(thanhTien.toString());
                ghiChuoi(dos, h.getPhuongThucTT());
                ghiChuoi(dos, h.getTrangThai() == null ? null : h.getTrangThai().name());
            }
            return true;
        } catch (Exception e) {
            System.err.println("Loi ghi HoaDon.dat: " + e.getMessage());
            return false;
        }
    }

    // =========================================================================
    // 7. THANH TOAN (ThanhToan.dat)
    // Quy trinh ghi/doc: count -> [maTT -> maHD -> soTien -> phuongThuc -> ngayTT(long) -> trangThai]
    // =========================================================================

    public static ThanhToan[] docThanhToan() {
        File file = layFile("ThanhToan.dat");
        if (!file.exists() || file.length() == 0) {
            return new ThanhToan[0];
        }
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            int count = dis.readInt();
            if (count < 0 || count > 100000) {
                throw new IOException("So luong thanh toan khong hop le: " + count);
            }
            ThanhToan[] ds = new ThanhToan[count];
            int dem = 0;
            for (int i = 0; i < count; i++) {
                String maTT = docChuoi(dis);
                String maHD = docChuoi(dis);
                BigDecimal soTien = new BigDecimal(dis.readUTF());
                String phuongThuc = docChuoi(dis);
                LocalDate ngayTT = docNgayLong(dis);
                String trangThaiStr = docChuoi(dis);
                TrangThaiThanhToan tt = trangThaiStr == null ? null : TrangThaiThanhToan.valueOf(trangThaiStr);
                ds[dem++] = new ThanhToan(maTT, maHD, soTien, phuongThuc, ngayTT, tt);
            }
            return Arrays.copyOf(ds, dem);
        } catch (Exception e) {
            System.err.println("Loi doc ThanhToan.dat: " + e.getMessage());
            return new ThanhToan[0];
        }
    }

    public static boolean ghiThanhToan(ThanhToan[] danhSach, int soLuong) {
        File file = layFile("ThanhToan.dat");
        if (!damBaoThuMucCha(file)) {
            return false;
        }
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            dos.writeInt(soLuong);
            for (int i = 0; i < soLuong; i++) {
                ThanhToan t = danhSach[i];
                ghiChuoi(dos, t.getMaThanhToan());
                ghiChuoi(dos, t.getMaHD());
                dos.writeUTF(t.getSoTien().toString());
                ghiChuoi(dos, t.getPhuongThuc());
                ghiNgayLong(dos, t.getNgayThanhToan());
                ghiChuoi(dos, t.getTrangThai() == null ? null : t.getTrangThai().name());
            }
            return true;
        } catch (IOException e) {
            System.err.println("Loi ghi ThanhToan.dat: " + e.getMessage());
            return false;
        }
    }

    // =========================================================================
    // 8. NHAN VIEN (NhanVien.dat)
    // Quy trinh ghi/doc: count -> [maNV -> hoTen -> gioiTinh -> ngaySinh -> diaChi -> sdt -> chucVu -> luong(double) -> trangThai]
    // =========================================================================

    public static NhanVien[] docNhanVien() {
        File file = layFile("NhanVien.dat");
        if (!file.exists() || file.length() == 0) {
            return new NhanVien[0];
        }
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            int tongSo = dis.readInt();
            if (tongSo < 0 || tongSo > 100000) {
                throw new IOException("So luong nhan vien khong hop le: " + tongSo);
            }
            NhanVien[] ds = new NhanVien[tongSo];
            int dem = 0;
            for (int i = 0; i < tongSo; i++) {
                String maNV = dis.readUTF();
                String hoTen = dis.readUTF();
                String gioiTinh = dis.readUTF();
                String ngaySinh = dis.readUTF();
                String diaChi = dis.readUTF();
                String sdt = dis.readUTF();
                String chucVu = dis.readUTF();
                double luong = dis.readDouble();
                String trangThai = dis.readUTF();
                ds[dem++] = new NhanVien(maNV, hoTen, gioiTinh, ngaySinh, diaChi, sdt, chucVu, luong, trangThai);
            }
            return Arrays.copyOf(ds, dem);
        } catch (Exception e) {
            System.err.println("Loi doc NhanVien.dat: " + e.getMessage());
            return new NhanVien[0];
        }
    }

    public static boolean ghiNhanVien(NhanVien[] danhSach, int soLuong) {
        File file = layFile("NhanVien.dat");
        if (!damBaoThuMucCha(file)) {
            return false;
        }
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            dos.writeInt(soLuong);
            for (int i = 0; i < soLuong; i++) {
                NhanVien nv = danhSach[i];
                if (nv != null) {
                    dos.writeUTF(nv.getMaNV() != null ? nv.getMaNV() : "");
                    dos.writeUTF(nv.getHoTen() != null ? nv.getHoTen() : "");
                    dos.writeUTF(nv.getGioiTinh() != null ? nv.getGioiTinh() : "");
                    dos.writeUTF(nv.getNgaySinh() != null ? nv.getNgaySinh() : "");
                    dos.writeUTF(nv.getDiaChi() != null ? nv.getDiaChi() : "");
                    dos.writeUTF(nv.getSoDienThoai() != null ? nv.getSoDienThoai() : "");
                    dos.writeUTF(nv.getChucVu() != null ? nv.getChucVu() : "");
                    dos.writeDouble(nv.getLuong());
                    dos.writeUTF(nv.getTrangThai() != null ? nv.getTrangThai() : "");
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Loi ghi NhanVien.dat: " + e.getMessage());
            return false;
        }
    }

    // =========================================================================
    // 9. TAI KHOAN (taiKhoan.dat)
    // Quy trinh ghi/doc: count -> [tenDangNhap -> matKhau -> maNV -> vaiTro -> trangThai]
    // =========================================================================

    public static TaiKhoan[] docTaiKhoan() {
        File file = layFile("taiKhoan.dat");
        if (!file.exists() || file.length() == 0) {
            return new TaiKhoan[0];
        }
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            int n = dis.readInt();
            if (n < 0 || n > 100000) {
                throw new IOException("So luong tai khoan khong hop le: " + n);
            }
            TaiKhoan[] ds = new TaiKhoan[n];
            int dem = 0;
            for (int i = 0; i < n; i++) {
                String tenDN = dis.readUTF();
                String matKhau = dis.readUTF();
                String maNV = dis.readUTF();
                String vaiTro = dis.readUTF();
                String trangThai = dis.readUTF();
                ds[dem++] = new TaiKhoan(tenDN, matKhau, maNV, vaiTro, trangThai);
            }
            return Arrays.copyOf(ds, dem);
        } catch (Exception e) {
            System.err.println("Loi doc taiKhoan.dat: " + e.getMessage());
            return new TaiKhoan[0];
        }
    }

    public static boolean ghiTaiKhoan(TaiKhoan[] danhSach, int soLuong) {
        File file = layFile("taiKhoan.dat");
        if (!damBaoThuMucCha(file)) {
            return false;
        }
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            dos.writeInt(soLuong);
            for (int i = 0; i < soLuong; i++) {
                TaiKhoan tk = danhSach[i];
                dos.writeUTF(tk.getTenDangNhap() == null ? "" : tk.getTenDangNhap());
                dos.writeUTF(tk.getMatKhau() == null ? "" : tk.getMatKhau());
                dos.writeUTF(tk.getMaNV() == null ? "" : tk.getMaNV());
                dos.writeUTF(tk.getVaiTro() == null ? "" : tk.getVaiTro());
                dos.writeUTF(tk.getTrangThai() == null ? "" : tk.getTrangThai());
            }
            return true;
        } catch (IOException e) {
            System.err.println("Loi ghi taiKhoan.dat: " + e.getMessage());
            return false;
        }
    }
}
