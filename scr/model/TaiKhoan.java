public class TaiKhoan {
    private String tenDangNhap;
    private String matKhau;
    private String maNV;
    private String vaiTro;
    private String trangThai;

    public TaiKhoan() {
    }

    public TaiKhoan(String tenDangNhap, String matKhau, String maNV,
                    String vaiTro, String trangThai) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.maNV = maNV;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
    }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }
    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }
    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
