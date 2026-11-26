package INTERFACE;

import java.time.LocalDate;
import model.Phong;
import model.TinhTrangPhong;

public interface IPhong {

    boolean themPhong(Phong phong);

    Phong[] layDanhSachPhong();

    Phong[] layDanhSachPhongTheoTinhTrang(TinhTrangPhong tinhTrang);

    Phong[] layPhongTrong(LocalDate ngayNhan, LocalDate ngayTra);

    Phong timPhongTheoMa(String maPhong);

    boolean suaPhong(Phong phong);

    boolean capNhatTinhTrang(String maPhong, TinhTrangPhong tinhTrang);

    boolean xoaPhong(String maPhong);

    int demSoLuongPhong();

    int demSoLuongPhongTheoTinhTrang(TinhTrangPhong tinhTrang);

}
