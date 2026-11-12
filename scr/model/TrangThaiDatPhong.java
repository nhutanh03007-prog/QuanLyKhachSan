package model;
public enum TrangThaiDatPhong {
    DA_DAT("Đã đặt trước"),
    DANG_SU_DUNG("Đang sử dụng"),
    DA_TRA("Đã trả phòng"),
    DA_HUY("Đã hủy");

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