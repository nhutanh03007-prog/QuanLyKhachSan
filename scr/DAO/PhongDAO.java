
package DAO;

import model.Phong;
import model.PhongThuong;
import model.PhongVIP;
import model.TinhTrangPhong;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class PhongDAO {

    // =====================================
    // KHAI BÁO FILE VÀ MẢNG
    // =====================================

    private static final String FILE_PATH =
            "DATABASEH/phong.dat";

    private static final String FILE_TEMP =
            "DATABASEH/phong_temp.dat";

    private static final int MAX_PHONG = 100;

    private static Phong[] danhSachPhong =
            new Phong[10];

    private static int soLuongPhong = 0;

    // Tự động đọc file khi khởi tạo DAO
    static {
        docFile();
    }

    // =====================================
    // TẠO THƯ MỤC NẾU CHƯA CÓ
    // =====================================

    private static void taoThuMucNeuChuaCo() {
        try {
            Files.createDirectories(
                    Path.of("DATABASEH")
            );
        } catch (IOException e) {
            System.out.println(
                    "Khong tao duoc thu muc DATABASEH: "
                    + e.getMessage()
            );
        }
    }

    // =====================================
    // GHI CHUỖI
    // =====================================

    private static void ghiChuoi(
            DataOutputStream dos,
            String s
    ) throws IOException {

        dos.writeUTF(s == null ? "" : s);
    }

    // =====================================
    // ĐỌC CHUỖI
    // =====================================

    private static String docChuoi(
            DataInputStream dis
    ) throws IOException {

        String s = dis.readUTF();

        return s.isEmpty() ? null : s;
    }

    // =====================================
    // GHI FILE
    // =====================================

    private static boolean ghiFile() {

        taoThuMucNeuChuaCo();

        // Ghi vào file tạm trước
        try (DataOutputStream dos =
                new DataOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream(FILE_TEMP)
                        )
                )) {

            // Ghi số lượng phòng
            dos.writeInt(soLuongPhong);

            for (int i = 0; i < soLuongPhong; i++) {

                Phong p = danhSachPhong[i];

                if (!hopLe(p)) {
                    throw new IOException(
                            "Thong tin phong khong hop le!"
                    );
                }

                String loai;
                String dichVu;

                // Xác định loại phòng
                if (p instanceof PhongThuong) {

                    loai = "THUONG";

                    PhongThuong pt =
                            (PhongThuong) p;

                    dichVu = pt.getDichVuThuong();

                } else if (p instanceof PhongVIP) {

                    loai = "VIP";

                    PhongVIP pv =
                            (PhongVIP) p;

                    dichVu = pv.getDichVuVIP();

                } else {

                    throw new IOException(
                            "Loai phong khong hop le!"
                    );
                }

                if (dichVu == null) {
                    throw new IOException(
                            "Dich vu phong khong duoc null!"
                    );
                }

                // Ghi loại phòng
                dos.writeUTF(loai);

                // Ghi thông tin chung
                ghiChuoi(dos, p.getMaPhong());

                ghiChuoi(dos, p.getTenPhong());

                ghiChuoi(dos, p.getLoaiPhong());

                // Ghi giá phòng
                dos.writeUTF(
                        p.getGiaPhong().toPlainString()
                );

                // Ghi tình trạng phòng
                dos.writeUTF(
                        p.getTinhTrang().name()
                );

                // Ghi dịch vụ riêng của từng loại phòng
                ghiChuoi(dos, dichVu);
            }

        } catch (IOException | RuntimeException e) {

            System.out.println(
                    "Loi ghi file phong: "
                    + e.getMessage()
            );

            return false;
        }

        // =====================================
        // THAY FILE TẠM BẰNG FILE CHÍNH
        // =====================================

        try {

            Files.move(
                    Path.of(FILE_TEMP),
                    Path.of(FILE_PATH),
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE
            );

        } catch (IOException e) {

            // Nếu không hỗ trợ ATOMIC_MOVE
            try {

                Files.move(
                        Path.of(FILE_TEMP),
                        Path.of(FILE_PATH),
                        StandardCopyOption.REPLACE_EXISTING
                );

            } catch (IOException ex) {

                System.out.println(
                        "Loi thay file phong: "
                        + ex.getMessage()
                );

                return false;
            }
        }

        System.out.println(
                "Ghi file phong thanh cong!"
        );

        return true;
    }

    // =====================================
    // ĐỌC FILE
    // =====================================

    private static void docFile() {

        File file = new File(FILE_PATH);

        if (!file.exists() || file.length() == 0) {
            return;
        }

        Phong[] dsTam = new Phong[10];

        int dem = 0;

        try (DataInputStream dis =
                new DataInputStream(
                        new BufferedInputStream(
                                new FileInputStream(file)
                        )
                )) {

            // Đọc số lượng phòng
            int count = dis.readInt();

            if (count < 0 || count > MAX_PHONG) {

                throw new IOException(
                        "So luong phong khong hop le!"
                );
            }

            for (int i = 0; i < count; i++) {

                // Đọc loại phòng
                String loai = dis.readUTF();

                // Đọc thông tin chung
                String maPhong = docChuoi(dis);

                String tenPhong = docChuoi(dis);

                String loaiPhong = docChuoi(dis);

                BigDecimal giaPhong =
                        new BigDecimal(dis.readUTF());

                TinhTrangPhong tinhTrang =
                        TinhTrangPhong.valueOf(
                                dis.readUTF()
                        );

                // Đọc dịch vụ
                String dichVu = docChuoi(dis);

                Phong phong;

                // =====================================
                // KHỞI TẠO PHÒNG THƯỜNG
                // =====================================

                if (loai.equals("THUONG")) {

                    phong = new PhongThuong(
                            maPhong,
                            tenPhong,
                            loaiPhong,
                            giaPhong,
                            tinhTrang,
                            dichVu
                    );

                }

                // =====================================
                // KHỞI TẠO PHÒNG VIP
                // =====================================

                else if (loai.equals("VIP")) {

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
                            "Loai phong khong hop le: "
                            + loai
                    );
                }

                // Kiểm tra dữ liệu
                if (!hopLe(phong)) {

                    throw new IOException(
                            "Du lieu phong khong hop le: "
                            + maPhong
                    );
                }

                // Kiểm tra mã phòng trùng
                for (int j = 0; j < dem; j++) {

                    if (dsTam[j].getMaPhong()
                            .equalsIgnoreCase(maPhong)) {

                        throw new IOException(
                                "Ma phong bi trung: "
                                + maPhong
                        );
                    }
                }

                // Mở rộng mảng khi cần
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

            // Kiểm tra dữ liệu thừa phía cuối file
            if (dis.read() != -1) {

                throw new IOException(
                        "File phong co du lieu thua!"
                );
            }

            // Chỉ cập nhật khi đọc thành công
            danhSachPhong = dsTam;

            soLuongPhong = dem;

            System.out.println(
                    "Doc file phong thanh cong!"
            );

            System.out.println(
                    "So luong phong: "
                    + soLuongPhong
            );

        } catch (IOException |
                 IllegalArgumentException |
                 NullPointerException e) {

            System.out.println(
                    "Loi doc file phong: "
                    + e.getMessage()
            );
        }
    }

    // =====================================
    // KIỂM TRA PHÒNG HỢP LỆ
    // =====================================

    private static boolean hopLe(Phong p) {

        if (p == null
                || p.getMaPhong() == null
                || p.getMaPhong().trim().isEmpty()
                || p.getTenPhong() == null
                || p.getTenPhong().trim().isEmpty()
                || p.getLoaiPhong() == null
                || p.getLoaiPhong().trim().isEmpty()
                || p.getGiaPhong() == null
                || p.getGiaPhong().signum() <= 0
                || p.getTinhTrang() == null) {

            return false;
        }

        if (p instanceof PhongThuong) {

            return ((PhongThuong) p)
                    .getDichVuThuong() != null;

        } else if (p instanceof PhongVIP) {

            return ((PhongVIP) p)
                    .getDichVuVIP() != null;
        }

        return false;
    }

    // =====================================
    // TÌM PHÒNG THEO MÃ
    // =====================================

    public Phong timPhongTheoMa(String maPhong) {

        if (maPhong == null
                || maPhong.trim().isEmpty()) {

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

    // =====================================
    // THÊM PHÒNG
    // =====================================

    public boolean themPhong(Phong phong) {

        if (!hopLe(phong)) {
            return false;
        }

        if (soLuongPhong >= MAX_PHONG) {

            System.out.println(
                    "Danh sach phong da day!"
            );

            return false;
        }

        if (timPhongTheoMa(phong.getMaPhong()) != null) {

            System.out.println(
                    "Ma phong da ton tai!"
            );

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

        int viTriCu = soLuongPhong;

        danhSachPhong[soLuongPhong++] = phong;

        // Ghi file
        if (!ghiFile()) {

            soLuongPhong--;

            danhSachPhong[viTriCu] = null;

            return false;
        }

        return true;
    }

    // =====================================
    // XÓA PHÒNG
    // =====================================

    public boolean xoaPhong(String maPhong) {

        if (maPhong == null
                || maPhong.trim().isEmpty()) {

            return false;
        }

        int viTri = -1;

        for (int i = 0; i < soLuongPhong; i++) {

            if (danhSachPhong[i].getMaPhong()
                    .equalsIgnoreCase(maPhong.trim())) {

                viTri = i;

                break;
            }
        }

        if (viTri == -1) {

            System.out.println(
                    "Khong tim thay phong!"
            );

            return false;
        }

        // Sao lưu danh sách
        Phong[] backup = danhSachPhong.clone();

        int soLuongCu = soLuongPhong;

        // Dồn phần tử
        System.arraycopy(
                danhSachPhong,
                viTri + 1,
                danhSachPhong,
                viTri,
                soLuongPhong - viTri - 1
        );

        soLuongPhong--;

        danhSachPhong[soLuongPhong] = null;

        // Ghi file
        if (!ghiFile()) {

            danhSachPhong = backup;

            soLuongPhong = soLuongCu;

            return false;
        }

        return true;
    }

    // =====================================
    // SỬA PHÒNG
    // =====================================

    public boolean suaPhong(
            String maPhong,
            Phong phongMoi
    ) {

        if (maPhong == null
                || maPhong.trim().isEmpty()
                || !hopLe(phongMoi)) {

            return false;
        }

        int viTri = -1;

        for (int i = 0; i < soLuongPhong; i++) {

            if (danhSachPhong[i].getMaPhong()
                    .equalsIgnoreCase(maPhong.trim())) {

                viTri = i;

                break;
            }
        }

        if (viTri == -1) {

            System.out.println(
                    "Khong tim thay phong!"
            );

            return false;
        }

        // Kiểm tra mã phòng trùng
        Phong phongTrung =
                timPhongTheoMa(phongMoi.getMaPhong());

        if (phongTrung != null
                && phongTrung != danhSachPhong[viTri]) {

            System.out.println(
                    "Ma phong da ton tai!"
            );

            return false;
        }

        // Sao lưu phòng cũ
        Phong phongCu = danhSachPhong[viTri];

        danhSachPhong[viTri] = phongMoi;

        // Ghi file
        if (!ghiFile()) {

            danhSachPhong[viTri] = phongCu;

            return false;
        }

        return true;
    }

    // =====================================
    // LẤY DANH SÁCH PHÒNG
    // =====================================

    public Phong[] layDanhSachPhong() {

        return Arrays.copyOf(
                danhSachPhong,
                soLuongPhong
        );
    }

    // =====================================
    // ĐẾM SỐ LƯỢNG PHÒNG
    // =====================================

    public int demSoLuongPhong() {

        return soLuongPhong;
    }

    // =====================================
    // LẤY DANH SÁCH THEO TÌNH TRẠNG
    // =====================================

    public Phong[] layDanhSachPhongTheoTinhTrang(
            TinhTrangPhong tinhTrang
    ) {

        if (tinhTrang == null) {

            return new Phong[0];
        }

        int dem =
                demSoLuongPhongTheoTinhTrang(tinhTrang);

        Phong[] ketQua = new Phong[dem];

        int viTri = 0;

        for (int i = 0; i < soLuongPhong; i++) {

            if (danhSachPhong[i].getTinhTrang()
                    == tinhTrang) {

                ketQua[viTri++] = danhSachPhong[i];
            }
        }

        return ketQua;
    }

    // =====================================
    // ĐẾM PHÒNG THEO TÌNH TRẠNG
    // =====================================

    public int demSoLuongPhongTheoTinhTrang(
            TinhTrangPhong tinhTrang
    ) {

        if (tinhTrang == null) {

            return 0;
        }

        int dem = 0;

        for (int i = 0; i < soLuongPhong; i++) {

            if (danhSachPhong[i].getTinhTrang()
                    == tinhTrang) {

                dem++;
            }
        }

        return dem;
    }
}
