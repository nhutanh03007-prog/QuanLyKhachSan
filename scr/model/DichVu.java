public class DichVu {
    private String maDichVu;
    private String tenDichVu;
    private String loaiDichVu;
    private String donViTinh;
    private double gia;
    private String trangThai;
    private String moTa;

    public DichVu() { }

    public DichVu(String maDichVu, String tenDichVu, String loaiDichVu,
                  String donViTinh, double gia, String trangThai, String moTa) {
        this.maDichVu = maDichVu;
        this.tenDichVu = tenDichVu;
        this.loaiDichVu = loaiDichVu;
        this.donViTinh = donViTinh;
        this.gia = gia;
        this.trangThai = trangThai;
        this.moTa = moTa;
    }

    public void nhapThongTin() { }
    public void xuatThongTin() { System.out.println(this); }
    public void themDichVu() { System.out.println("Da them dich vu: " + tenDichVu); }
    public void suaDichVu() { System.out.println("Da cap nhat dich vu: " + tenDichVu); }
    public void xoaDichVu() { System.out.println("Da xoa dich vu: " + tenDichVu); }
    public boolean kiemTra(String tuKhoa) {
        if (tuKhoa == null) return false;
        return maDichVu.contains(tuKhoa) || tenDichVu.contains(tuKhoa);
    }
    public double thongKeDoanhThu(int soLuong) { return gia * soLuong; }

    public String getMaDichVu() { return maDichVu; }
    public void setMaDichVu(String maDichVu) { this.maDichVu = maDichVu; }
    public String getTenDichVu() { return tenDichVu; }
    public void setTenDichVu(String tenDichVu) { this.tenDichVu = tenDichVu; }
    public String getLoaiDichVu() { return loaiDichVu; }
    public void setLoaiDichVu(String loaiDichVu) { this.loaiDichVu = loaiDichVu; }
    public String getDonViTinh() { return donViTinh; }
    public void setDonViTinh(String donViTinh) { this.donViTinh = donViTinh; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    @Override
    public String toString() {
        return "Ma dich vu: " + maDichVu + ", ten: " + tenDichVu + ", loai: " + loaiDichVu
                + ", don vi: " + donViTinh + ", gia: " + gia + ", trang thai: " + trangThai;
    }
}