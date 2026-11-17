
package DAO;

import model.Phong;
import model.PhongThuong;
import model.PhongVIP;
import model.TinhTrangPhong;
import Database.DatabaseManager;

import java.io.*;
import java.math.BigDecimal;

public class PhongDAO {

    private static final String FILE_PATH =
            DatabaseManager.getDuongDanFile("phong.dat");

    private static final int MAX_PHONG = 100;

    private static Phong[] danhSachPhong = new Phong[MAX_PHONG];

    private static int soLuongPhong = 0;

    // Đọc dữ liệu từ file khi lớp được khởi tạo
    static {
        docFile();
    }

    // =========================
    // GHI FILE
    // =========================

    private static boolean ghiFile() {

        File file = new File(FILE_PATH);
        File folder = file.getParentFile();

        // Tạo thư mục nếu chưa tồn tại
        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }

        try (DataOutputStream outStream =
                     new DataOutputStream(
                             new FileOutputStream(FILE_PATH))) {

            for (int i = 0; i < soLuongPhong; i++) {

                Phong p = danhSachPhong[i];

                if (p instanceof PhongThuong) {

                    outStream.writeUTF("THUONG");

                } else if (p instanceof PhongVIP) {

                    outStream.writeUTF("VIP");

                } else {

                    throw new IOException("Loai phong khong hop le!");
                }

                // Ghi thông tin chung của phòng
                outStream.writeUTF(p.getMaPhong());
                outStream.writeUTF(p.getTenPhong());
                outStream.writeUTF(p.getLoaiPhong());
                outStream.writeUTF(p.getGiaPhong().toPlainString());
                outStream.writeUTF(p.getTinhTrang().name());

                // Ghi thông tin riêng của từng lớp con
                if (p instanceof PhongThuong) {

                    PhongThuong pt = (PhongThuong) p;

                    outStream.writeUTF(pt.getDichVuThuong());

                } else if (p instanceof PhongVIP) {

                    PhongVIP pv = (PhongVIP) p;

                    outStream.writeUTF(pv.getDichVuVIP());
                }
            }

            return true;

        } catch (IOException e) {

            System.out.println("Loi khi ghi file phong: " + e.getMessage());

            return false;
        }
    }

    // =========================
    // ĐỌC FILE
    // =========================

    private static void docFile() {

        File file = new File(FILE_PATH);

        // Nếu file chưa tồn tại thì chưa có dữ liệu để đọc
        if (!file.exists()) {
            return;
        }

        Phong[] dsTam = new Phong[MAX_PHONG];
        int dem = 0;

        try (DataInputStream inStream =
                     new DataInputStream(
                             new FileInputStream(FILE_PATH))) {

            while (inStream.available() > 0) {

                if (dem >= MAX_PHONG) {
                    System.out.println("Danh sach phong da day!");
                    break;
                }

                // Đọc loại phòng
                String loai = inStream.readUTF();

                // Đọc thông tin chung
                String maPhong = inStream.readUTF();
                String tenPhong = inStream.readUTF();
                String loaiPhong = inStream.readUTF();

                BigDecimal giaPhong =
                        new BigDecimal(inStream.readUTF());

                TinhTrangPhong tinhTrang =
                        TinhTrangPhong.valueOf(inStream.readUTF());

                // Đọc thông tin riêng và tạo đối tượng
                if (loai.equals("THUONG")) {

                    String dichVuThuong = inStream.readUTF();

                    dsTam[dem] = new PhongThuong(
                            maPhong,
                            tenPhong,
                            loaiPhong,
                            giaPhong,
                            tinhTrang,
                            dichVuThuong
                    );

                } else if (loai.equals("VIP")) {

                    String dichVuVIP = inStream.readUTF();

                    dsTam[dem] = new PhongVIP(
                            maPhong,
                            tenPhong,
                            loaiPhong,
                            giaPhong,
                            tinhTrang,
                            dichVuVIP
                    );

                } else {

                    throw new IOException("Loai phong trong file khong hop le!");
                }

                dem++;
            }

            // Chỉ cập nhật danh sách sau khi đọc thành công
            danhSachPhong = dsTam;
            soLuongPhong = dem;

            System.out.println("Doc file phong thanh cong!");

        } catch (IOException | IllegalArgumentException e) {

            System.out.println("Loi khi doc file phong: " + e.getMessage());
        }
    }

    // =========================
    // THÊM PHÒNG
    // =========================

    public boolean themPhong(Phong phong) {

        if (phong == null) {
            System.out.println("Phong khong hop le!");
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

        danhSachPhong[soLuongPhong] = phong;
        soLuongPhong++;

        // Nếu ghi file thất bại thì khôi phục mảng
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

            if (danhSachPhong[i].getMaPhong().equalsIgnoreCase(maPhong)) {
                viTri = i;
                break;
            }
        }

        if (viTri == -1) {
            System.out.println("Khong tim thay phong!");
            return false;
        }

        // Sao lưu danh sách để khôi phục nếu ghi file thất bại
        Phong[] backup = danhSachPhong.clone();
        int soLuongCu = soLuongPhong;

        // Dồn các phần tử phía sau lên trước
        for (int i = viTri; i < soLuongPhong - 1; i++) {
            danhSachPhong[i] = danhSachPhong[i + 1];
        }

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

    public boolean suaPhong(String maPhong, Phong phongMoi) {

        if (phongMoi == null) {
            return false;
        }

        int viTri = -1;

        for (int i = 0; i < soLuongPhong; i++) {

            if (danhSachPhong[i].getMaPhong().equalsIgnoreCase(maPhong)) {
                viTri = i;
                break;
            }
        }

        if (viTri == -1) {
            System.out.println("Khong tim thay phong!");
            return false;
        }

        // Không cho sửa thành mã phòng đã tồn tại ở vị trí khác
        Phong phongTrung = timPhongTheoMa(phongMoi.getMaPhong());

        if (phongTrung != null && phongTrung != danhSachPhong[viTri]) {
            System.out.println("Ma phong moi da ton tai!");
            return false;
        }

        // Lưu phòng cũ để khôi phục nếu ghi file thất bại
        Phong phongCu = danhSachPhong[viTri];

        danhSachPhong[viTri] = phongMoi;

        if (!ghiFile()) {

            danhSachPhong[viTri] = phongCu;

            return false;
        }

        return true;
    }

    // =========================
    // TÌM PHÒNG THEO MÃ
    // =========================

    public Phong timPhongTheoMa(String maPhong) {

        if (maPhong == null) {
            return null;
        }

        for (int i = 0; i < soLuongPhong; i++) {

            if (danhSachPhong[i].getMaPhong().equalsIgnoreCase(maPhong)) {
                return danhSachPhong[i];
            }
        }

        return null;
    }

    // =========================
    // LẤY DANH SÁCH PHÒNG
    // =========================

    public Phong[] layDanhSachPhong() {

        Phong[] ketQua = new Phong[soLuongPhong];

        for (int i = 0; i < soLuongPhong; i++) {
            ketQua[i] = danhSachPhong[i];
        }

        return ketQua;
    }

    // =========================
    // ĐẾM SỐ LƯỢNG PHÒNG
    // =========================

    public int demSoLuongPhong() {
        return soLuongPhong;
    }

    // =========================
    // LẤY DANH SÁCH THEO TÌNH TRẠNG
    // =========================

    public Phong[] layDanhSachPhongTheoTinhTrang(
            TinhTrangPhong tinhTrang) {

        int dem = 0;

        for (int i = 0; i < soLuongPhong; i++) {

            if (danhSachPhong[i].getTinhTrang() == tinhTrang) {
                dem++;
            }
        }

        Phong[] ketQua = new Phong[dem];

        int viTri = 0;

        for (int i = 0; i < soLuongPhong; i++) {

            if (danhSachPhong[i].getTinhTrang() == tinhTrang) {
                ketQua[viTri] = danhSachPhong[i];
                viTri++;
            }
        }

        return ketQua;
    }

    // =========================
    // ĐẾM PHÒNG THEO TÌNH TRẠNG
    // =========================

    public int demSoLuongPhongTheoTinhTrang(
            TinhTrangPhong tinhTrang) {

        int dem = 0;

        for (int i = 0; i < soLuongPhong; i++) {

            if (danhSachPhong[i].getTinhTrang() == tinhTrang) {
                dem++;
            }
        }

        return dem;
    }
}
