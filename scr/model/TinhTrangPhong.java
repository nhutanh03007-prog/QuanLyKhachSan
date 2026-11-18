package model;
public enum TinhTrangPhong{
    TRONG("Trống"),
    DANG_SU_DUNG("Đang sử dụng"),
    DANG_BAO_TRI("Đang bảo trì");   

    private final String tenTinhTrang;

    TinhTrangPhong(String tenTinhTrang) {
        this.tenTinhTrang = tenTinhTrang;
    }
    public String getTenTinhTrang() {
        return tenTinhTrang;
    }
    @Override
    public String toString() {
        return this.tenTinhTrang;
    }
}
