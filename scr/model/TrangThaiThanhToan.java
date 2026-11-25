package model;

public enum TrangThaiThanhToan {
    CHUA_THANH_TOAN("Chua thanh toan"), 
    DA_THANH_TOAN("Da thanh toan");

    private final String tenTrangThai;
    TrangThaiThanhToan(String tenTrangThai) {
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
