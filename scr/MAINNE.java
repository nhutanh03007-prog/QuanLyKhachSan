import ui.HeThongUI;
import ui.PhongUI;

public class MAINNE {
    public static void main(String[] args) {
        HeThongUI heThongUI = new HeThongUI();

        // 1. Yêu cầu đăng nhập đầu tiên
        boolean isLogged = heThongUI.dangNhap();

        // 2. Nếu đăng nhập thành công thì chia nhánh menu theo quyền
        if (isLogged) {
            // Tạm thời gán cứng "ADMIN" để test (hoặc lấy từ heThongUI nếu đã thêm getter)
            String role = "ADMIN";

            if (role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("QuanLy")) {
                heThongUI.menuQuanLyHeThong();
            } else {
                System.out.println("Chuyển hướng sang giao diện Lễ tân / Phòng...");
                PhongUI phongUI = new PhongUI();
                phongUI.menuPhong();
            }
        } else {
            System.out.println("Đăng nhập thất bại. Chương trình kết thúc.");
        }
    }
}