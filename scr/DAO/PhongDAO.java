
package DAO;

import model.Phong;
import model.PhongThuong;
import model.PhongVIP;
import model.TinhTrangPhong;
import Database.DatabaseManager;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class PhongDAO {

    private static final String FILE_PATH =
            DatabaseManager.getDuongDanFile("phong.dat");

    private static final String FILE_TEMP =
            FILE_PATH + ".tmp";

    private static final int MAX_PHONG = 100;

    private static Phong[] danhSachPhong = new Phong[10];

    private static int soLuongPhong = 0;

    static {
        docFile();
    }

    // =========================
    // GHI FILE
    // =========================

    private static boolean ghiFile() {

        File file = new File(FILE_PATH);
        File folder = file.getParentFile();

        if (folder != null && !folder.exists()) {
            if (!folder.mkdirs()) {
                System.out.println("Khong tao duoc thu muc!");
                return false;
            }
        }

        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(FILE_TEMP)))) {

            for (int i = 0; i < soLuongPhong; i++) {

                Phong p = danhSachPhong[i];

                if (p == null) {
                    throw new IOException("Du lieu phong bi null!");
                }

                // Ghi loại đối tượng
                if (p instanceof PhongThuong) {
                    out.writeUTF("THUONG");

                } else if (p instanceof PhongVIP) {
                    out.writeUTF("VIP");

                } else {
                    throw new IOException("Loai phong khong hop le!");
                }

                // Ghi thông tin chung
                out.writeUTF(p.getMaPhong());
                out.writeUTF(p.getTenPhong());
                out.writeUTF(p.getLoaiPhong());
                out.writeUTF(p.getGiaPhong().toPlainString());
                out.writeUTF(p.getTinhTrang().name());

                // Ghi thông tin riêng
                if (p instanceof PhongThuong) {

                    PhongThuong pt = (PhongThuong) p;
                    out.writeUTF(pt.getDichVuThuong());

                } else {

                    PhongVIP pv = (PhongVIP) p;
                    out.writeUTF(pv.getDichVuVIP());
                }
            }

        } catch (IOException | RuntimeException e) {

            System.out.println("Loi ghi file: " + e.getMessage());
            return false;
        }

        // Chỉ thay file chính sau khi ghi file tạm thành công
        try {

            Path fileTam = Path.of(FILE_TEMP);
            Path fileChinh = Path.of(FILE_PATH);

            try {
                Files.move(
                        fileTam,
                        fileChinh,
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE
                );

            } catch (IOException e) {

                // Nếu hệ thống không hỗ trợ ATOMIC_MOVE
                Files.move(
                        fileTam,
                        fileChinh,
                        StandardCopyOption.REPLACE_EXISTING
                );
            }

            return true;

        } catch (IOException e) {

            System.out.println("Loi thay file: " + e.getMessage());
            return false;
        }
    }

    // =========================
    // ĐỌC FILE
    // =========================

    private static void docFile() {

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return;
        }

        Phong[] dsTam = new Phong[10];
        int dem = 0;

        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(FILE_PATH)))) {

            while (true) {

                String loai;

                try {
                    loai = in.readUTF();
                } catch (EOFException e) {
                    // Đã đọc hết file
                    break;
                }

                String maPhong = in.readUTF();
                String tenPhong = in.readUTF();
                String loaiPhong = in.readUTF();

                BigDecimal giaPhong =
                        new BigDecimal(in.readUTF());

                TinhTrangPhong tinhTrang =
                        TinhTrangPhong.valueOf(in.readUTF());

                Phong phong;

                if (loai.equals("THUONG")) {

                    String dichVu =
                            in.readUTF();

                    phong = new PhongThuong(
                            maPhong,
                            tenPhong,
                            loaiPhong,
                            giaPhong,
                            tinhTrang,
                            dichVu
                    );

                } else if (loai.equals("VIP")) {

                    String dichVu =
                            in.readUTF();

                    phong = new PhongVIP(
                            maPhong,
                            tenPhong,
                            loaiPhong,
                            giaPhong,
                            tinhTrang,
                            dichVu
                    );

                } else {

                    throw new IOException(
                            "Loai phong khong hop le: " + loai
                    );
                }

                if (dem >= MAX_PHONG) {
                    throw new IOException(
                            "So luong phong vuot gioi han!"
                    );
                }

                // Mở rộng mảng tạm khi cần
                if (dem >= dsTam.length) {

                    int kichThuocMoi = Math.min(
                            dsTam.length * 2,
                            MAX_PHONG
                    );

                    dsTam = Arrays.copyOf(
                            dsTam,
                            kichThuocMoi
                    );
                }

                dsTam[dem++] = phong;
            }

            // Chỉ cập nhật dữ liệu khi đọc thành công
            danhSachPhong = dsTam;
            soLuongPhong = dem;

            System.out.println("Doc file phong thanh cong!");

        } catch (IOException | IllegalArgumentException e) {

            System.out.println(
                    "Loi doc file phong: " + e.getMessage()
            );
        }
    }

    // =========================
    // TÌM PHÒNG THEO MÃ
    // =========================

    public Phong timPhongTheoMa(String maPhong) {

        if (maPhong == null) {
            return null;
        }

        maPhong = maPhong.trim();

        for (int i = 0; i < soLuongPhong; i++) {

            if (danhSachPhong[i].getMaPhong()
                    .equalsIgnoreCase(maPhong)) {

                return danhSachPhong[i];
            }
        }

        return null;
    }

    // =========================
    // THÊM PHÒNG
    // =========================

    public boolean themPhong(Phong phong) {

        if (phong == null) {
            return false;
        }

        if (soLuongPhong >= MAX_PHONG) {
            System.out.println("Danh sach phong da day!");
            return false;
        }

        if (timPhongTheoMa(phong.getMaPhong()) != null) {
            System.out.println("Ma phong da ton tai!");
            return false;
        }

        // Mở rộng mảng nếu đầy
        if (soLuongPhong >= danhSachPhong.length) {

            int kichThuocMoi = Math.min(
                    danhSachPhong.length * 2,
                    MAX_PHONG
            );

            danhSachPhong = Arrays.copyOf(
                    danhSachPhong,
                    kichThuocMoi
            );
        }

        danhSachPhong[soLuongPhong] = phong;
        soLuongPhong++;

        if (!ghiFile()) {

            soLuongPhong--;

            danhSachPhong[soLuongPhong] = null;

            return false;
        }

        return true;
    }

    // =========================
    // XÓA PHÒNG
    // =========================

    public boolean xoaPhong(String maPhong) {

        int viTri = -1;

        for (int i = 0; i < soLuongPhong; i++) {

            if (danhSachPhong[i].getMaPhong()
                    .equalsIgnoreCase(maPhong)) {

                viTri = i;
                break;
            }
        }

        if (viTri == -1) {
            System.out.println("Khong tim thay phong!");
            return false;
        }

        Phong[] backup = danhSachPhong.clone();
        int soLuongCu = soLuongPhong;

        System.arraycopy(
                danhSachPhong,
                viTri + 1,
                danhSachPhong,
                viTri,
                soLuongPhong - viTri - 1
        );

        soLuongPhong--;

        danhSachPhong[soLuongPhong] = null;

        if (!ghiFile()) {

            danhSachPhong = backup;
            soLuongPhong = soLuongCu;

            return false;
        }

        return true;
    }

    // =========================
    // SỬA PHÒNG
    // =========================

    public boolean suaPhong(
            String maPhong,
            Phong phongMoi) {

        if (maPhong == null || phongMoi == null) {
            return false;
        }

        int viTri = -1;

        for (int i = 0; i < soLuongPhong; i++) {

            if (danhSachPhong[i].getMaPhong()
                    .equalsIgnoreCase(maPhong)) {

                viTri = i;
                break;
            }
        }

        if (viTri == -1) {
            System.out.println("Khong tim thay phong!");
            return false;
        }

        Phong phongTrung =
                timPhongTheoMa(phongMoi.getMaPhong());

        if (phongTrung != null
                && phongTrung != danhSachPhong[viTri]) {

            System.out.println("Ma phong da ton tai!");
            return false;
        }

        Phong phongCu = danhSachPhong[viTri];

        danhSachPhong[viTri] = phongMoi;

        if (!ghiFile()) {

            danhSachPhong[viTri] = phongCu;

            return false;
        }

        return true;
    }

    // =========================
    // LẤY DANH SÁCH PHÒNG
    // =========================

    public Phong[] layDanhSachPhong() {

        return Arrays.copyOf(
                danhSachPhong,
                soLuongPhong
        );
    }

    // =========================
    // ĐẾM SỐ LƯỢNG
    // =========================

    public int demSoLuongPhong() {
        return soLuongPhong;
    }

    // =========================
    // LẤY DANH SÁCH THEO TÌNH TRẠNG
    // =========================

    public Phong[] layDanhSachPhongTheoTinhTrang(
            TinhTrangPhong tinhTrang) {

        if (tinhTrang == null) {
            return new Phong[0];
        }

        int dem = demSoLuongPhongTheoTinhTrang(tinhTrang);

        Phong[] ketQua = new Phong[dem];

        int viTri = 0;

        for (int i = 0; i < soLuongPhong; i++) {

            if (danhSachPhong[i].getTinhTrang() == tinhTrang) {
                ketQua[viTri++] = danhSachPhong[i];
            }
        }

        return ketQua;
    }

    // =========================
    // ĐẾM THEO TÌNH TRẠNG
    // =========================

    public int demSoLuongPhongTheoTinhTrang(
            TinhTrangPhong tinhTrang) {

        if (tinhTrang == null) {
            return 0;
        }

        int dem = 0;

        for (int i = 0; i < soLuongPhong; i++) {

            if (danhSachPhong[i].getTinhTrang() == tinhTrang) {
                dem++;
            }
        }

        return dem;
    }
}
