package service;

import java.math.BigDecimal;
import model.DichVu;
import model.HoaDon;
import model.TinhTrangPhong;
import model.TrangThaiThanhToan;

public class QLThongKe {
    private final QLHoaDon qlHoaDon;
    private final QLPhong qlPhong;
    private final QLKhachHang qlKhachHang;
    private final QLDichVu qlDichVu;

    public QLThongKe(QLHoaDon qlHoaDon, QLPhong qlPhong, QLKhachHang qlKhachHang, QLDichVu qlDichVu) {
        this.qlHoaDon = qlHoaDon;
        this.qlPhong = qlPhong;
        this.qlKhachHang = qlKhachHang;
        this.qlDichVu = qlDichVu;
    }

    public BigDecimal tinhTongDoanhThu() {
        BigDecimal tong = BigDecimal.ZERO;
        HoaDon[] dsHoaDon = qlHoaDon.layDanhSachHoaDon();
        if (dsHoaDon != null) {
            for (HoaDon h : dsHoaDon) {
                if (h != null && h.getTrangThai() == TrangThaiThanhToan.DA_THANH_TOAN && h.getThanhTien() != null) {
                    tong = tong.add(h.getThanhTien());
                }
            }
        }

        return tong;
    }

    public BigDecimal tinhTongDoanhThuPhong() {
        BigDecimal tongPhong = BigDecimal.ZERO;
        HoaDon[] dsHoaDon = qlHoaDon.layDanhSachHoaDon();
        if (dsHoaDon != null) {
            for (HoaDon h : dsHoaDon) {
                if (h != null && h.getTrangThai() == TrangThaiThanhToan.DA_THANH_TOAN) {
                    tongPhong = tongPhong.add(qlHoaDon.tinhTienPhongTrongHoaDon(h));
                }
            }
        }
        return tongPhong;
    }

    public BigDecimal tinhTongDoanhThuDichVu() {
        BigDecimal tongDichVu = BigDecimal.ZERO;
        HoaDon[] dsHoaDon = qlHoaDon.layDanhSachHoaDon();
        if (dsHoaDon != null) {
            for (HoaDon h : dsHoaDon) {
                if (h != null && h.getTrangThai() == TrangThaiThanhToan.DA_THANH_TOAN && h.getMaDatPhong() != null) {
                    BigDecimal tienDV = qlDichVu.tinhTongTienDichVu(h.getMaDatPhong());
                    if (tienDV != null) {
                        tongDichVu = tongDichVu.add(tienDV);
                    }
                }
            }
        }
        return tongDichVu;
    }

    public int demHoaDonTheoTrangThai(TrangThaiThanhToan trangThai) {
        int count = 0;
        HoaDon[] dsHoaDon = qlHoaDon.layDanhSachHoaDon();
        if (dsHoaDon != null) {
            for (HoaDon h : dsHoaDon) {
                if (h != null && h.getTrangThai() == trangThai) {
                    count++;
                }
            }
        }
        return count;
    }

    public int tongSoPhong() {
        return qlPhong.demSoLuongPhong();
    }

    public int demPhongTheoTrangThai(TinhTrangPhong tinhTrang) {
        return qlPhong.demSoLuongPhongTheoTinhTrang(tinhTrang);
    }


    public int tongSoKhachHang() {
        return qlKhachHang.demSoLuongKhachHang();
    }

    public int demKhachHangTheoLoai(String loaiKhach) {
        return qlKhachHang.demSoLuongKhachHangTheoLoai(loaiKhach);
    }


    public int tongSoDichVuHoatDong() {
        int count = 0;
        DichVu[] dsDichVu = qlDichVu.layDanhSachDichVu();
        if (dsDichVu != null) {
            for (DichVu dv : dsDichVu) {
                if (dv != null && !"NGUNG_HOAT_DONG".equals(dv.getTrangThai())) {
                    count++;
                }
            }
        }
        return count;
    }
}