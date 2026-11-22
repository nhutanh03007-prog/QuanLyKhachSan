package INTERFACE;

import java.time.LocalDate;
import model.Phong;
import model.TinhTrangPhong;

public interface IPhong {

    boolean themPhong(Phong phong);

    Phong[] layDanhSachPhong();

    Phong[] layDanhSachPhongTheoTinhTrang(TinhTrangPhong tinhTrang);

    /** Cac phong khong bao tri va khong trung lich trong khoang [ngayNhan, ngayTra). */
    Phong[] layPhongTrong(LocalDate ngayNhan, LocalDate ngayTra);

    Phong timPhongTheoMa(String maPhong);

    /** Sua thong tin phong (khong doi ma phong). */
    boolean suaPhong(Phong phong);

    /** Chi doi tinh trang phong; dung cho nghiep vu dat/nhan/tra phong. */
    boolean capNhatTinhTrang(String maPhong, TinhTrangPhong tinhTrang);

    boolean xoaPhong(String maPhong);

    int demSoLuongPhong();

    int demSoLuongPhongTheoTinhTrang(TinhTrangPhong tinhTrang);
}
