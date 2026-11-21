package model;
public enum TinhTrangPhong{
    TRONG("Trong"),
    DANG_SU_DUNG("Dang su dung"),
    DANG_BAO_TRI("Dang bao tri");   

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
