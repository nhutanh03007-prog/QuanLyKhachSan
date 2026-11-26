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

    boolean timPhongTheoTen(String tenPhong);

<<<<<<< HEAD
    int demSoLuongPhongTheoTinhTrang(TinhTrangPhong tinhTrang);
=======
    Phong[] layDanhSachPhongVIP();

    Phong[] layDanhSachPhongThuong();
>>>>>>> c985e6d03220d3dd61236d9d6291fe3051cfb2ac

}
