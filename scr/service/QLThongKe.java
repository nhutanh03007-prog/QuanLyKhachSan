package service;

import java.math.BigDecimal;

import model.Phong;
import model.KhachHang;
import model.DichVu;
import model.ThanhToan;
import model.TinhTrangPhong;
import model.TrangThaiThanhToan;

public class QLThongKe {

    private final QLThanhToan qlThanhToan;
    private final QLPhong qlPhong;
    private final QLKhachHang qlKhachHang;
    private final QLDichVu qlDichVu;

    public QLThongKe(
            QLThanhToan qlThanhToan,
            QLPhong qlPhong,
            QLKhachHang qlKhachHang,
            QLDichVu qlDichVu) {

        this.qlThanhToan = qlThanhToan;
        this.qlPhong = qlPhong;
        this.qlKhachHang = qlKhachHang;
        this.qlDichVu = qlDichVu;
    }

    // =====================================
    // 1. TINH TONG DOANH THU
    // =====================================

    public BigDecimal tinhTongDoanhThu() {

        BigDecimal tong = BigDecimal.ZERO;

        ThanhToan[] dsThanhToan =
                qlThanhToan.layDanhSachThanhToan();

        if (dsThanhToan == null) {
            return tong;
        }

        for (ThanhToan tt : dsThanhToan) {

            if (tt != null
                    && tt.getTrangThai()
                    == TrangThaiThanhToan.DA_THANH_TOAN
                    && tt.getSoTien() != null) {

                tong = tong.add(tt.getSoTien());
            }
        }

        return tong;
    }

    // =====================================
    // 2. DEM PHONG THEO TINH TRANG
    // =====================================

    public int demPhongTheoTrangThai(
            TinhTrangPhong tinhTrang) {

        if (tinhTrang == null) {
            return 0;
        }

        return qlPhong.demSoLuongPhongTheoTinhTrang(
                tinhTrang
        );
    }

    // =====================================
    // 3. TONG SO PHONG
    // =====================================

    public int tongSoPhong() {

        return qlPhong.demSoLuongPhong();
    }

    // =====================================
    // 4. TONG SO KHACH HANG
    // =====================================

    public int tongSoKhachHang() {

        KhachHang[] dsKhachHang =
                qlKhachHang.layDanhSachKhachHang();

        return dsKhachHang == null
                ? 0
                : dsKhachHang.length;
    }

    // =====================================
    // 5. TONG SO DICH VU
    // =====================================

    public int tongSoDichVu() {

        DichVu[] dsDichVu =
                qlDichVu.layDanhSachDichVu();

        return dsDichVu == null
                ? 0
                : dsDichVu.length;
    }

    // =====================================
    // 6. THONG KE TAT CA TRANG THAI PHONG
    // =====================================

    public void thongKePhong() {

        System.out.println("\n===== THONG KE PHONG =====");

        for (TinhTrangPhong tt : TinhTrangPhong.values()) {

            int soLuong = demPhongTheoTrangThai(tt);

            System.out.println(
                    tt + ": " + soLuong + " phong"
            );
        }

        System.out.println(
                "Tong so phong: " + tongSoPhong()
        );
    }

    // =====================================
    // 7. THONG KE KHACH HANG
    // =====================================

    public void thongKeKhachHang() {

        System.out.println("\n===== THONG KE KHACH HANG =====");

        System.out.println(
                "Tong so khach hang: "
                + tongSoKhachHang()
        );
    }

    // =====================================
    // 8. THONG KE DICH VU
    // =====================================

    public void thongKeDichVu() {

        System.out.println("\n===== THONG KE DICH VU =====");

        System.out.println(
                "Tong so dich vu: "
                + tongSoDichVu()
        );
    }

    // =====================================
    // 9. THONG KE DOANH THU
    // =====================================

    public void thongKeDoanhThu() {

        System.out.println("\n===== THONG KE DOANH THU =====");

        System.out.println(
                "Tong doanh thu: "
                + tinhTongDoanhThu()
                + " VND"
        );
    }

    // =====================================
    // 10. BAO CAO TONG HOP
    // =====================================

    public void baoCaoTongHop() {

        System.out.println("\n================================");
        System.out.println("       BAO CAO TONG HOP");
        System.out.println("================================");

        System.out.println(
                "Tong so phong: "
                + tongSoPhong()
        );

        System.out.println(
                "Tong so khach hang: "
                + tongSoKhachHang()
        );

        System.out.println(
                "Tong so dich vu: "
                + tongSoDichVu()
        );

        System.out.println(
                "Tong doanh thu: "
                + tinhTongDoanhThu()
                + " VND"
        );

        System.out.println("================================");
    }
}