package INTERFACE;
import model.Phong;
import model.TinhTrangPhong;
public interface IPhong{

    boolean themPhong(Phong phong);

    Phong[] layDanhSachPhong();

    Phong[] layDanhSachPhongTheoTinhTrang(TinhTrangPhong tinhTrang);

    Phong timPhongTheoMa(String maPhong);

    boolean suaPhong(Phong phong);

    boolean xoaPhong(String maPhong);

    int demSoLuongPhong();

    int demSoLuongPhongTheoTinhTrang(TinhTrangPhong tinhTrang);
}
