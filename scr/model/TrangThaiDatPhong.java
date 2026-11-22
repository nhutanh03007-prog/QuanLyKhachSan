package model;
public enum TrangThaiDatPhong {
    DA_DAT("Da dat"),
    DANG_SU_DUNG("Dang su dung"),
    DA_TRA("Da tra phong"),
    DA_HUY("Da huy");

    private final String tenTrangThai;

    TrangThaiDatPhong(String tenTrangThai) {
        this.tenTrangThai = tenTrangThai;
    }

    public String getTenTrangThai() {
        return tenTrangThai;
    }

    @Override
    public String toString() {
        return this.tenTrangThai;
    }
}