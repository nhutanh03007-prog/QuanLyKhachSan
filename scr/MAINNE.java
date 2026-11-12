import ui.HeThongUI;

public class MAINNE{
    public static void main(String[] args) {
        // Khởi tạo giao diện quản lý hệ thống của bạn
        HeThongUI heThongUI = new HeThongUI();
        
        // 1. Yêu cầu đăng nhập đầu tiên
        boolean isLogged = heThongUI.dangNhap();
        
        // 2. Nếu đăng nhập thành công thì mới chia nhánh menu
        if (isLogged) {
            // Sau này bạn sẽ lấy quyền thực tế từ tài khoản đăng nhập
            // Ví dụ: String role = heThongUI.getTaiKhoanHienTai().getQuyen();
            String role = "ADMIN"; // Tạm thời gán cứng để test

            if (role.equals("ADMIN")) {
                // Mở menu Quản lý Nhân viên & Thống kê của bạn
                heThongUI.menuQuanLyHeThong(); 
            } else if (role.equals("NHAN_VIEN")) {
                // Chỗ này sau này đồng đội của bạn sẽ khởi tạo PhongUI
                System.out.println("Chuyển hướng sang giao diện Lễ tân / Phòng...");
                // PhongUI phongUI = new PhongUI();
                // phongUI.menuNhanVien();
            }
        } else {
            System.out.println("Đăng nhập thất bại. Chương trình kết thúc.");
        }
    }
}
