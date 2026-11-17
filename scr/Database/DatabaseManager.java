
package Database;

import java.io.File;

public class DatabaseManager {

    private static final String DATA_FOLDER = "data";

    // Khoi tao thu muc du lieu
    public static void khoiTaoDatabase() {

        File folder = new File(DATA_FOLDER);

        if (!folder.exists()) {

            if (!folder.mkdirs()) {
                throw new RuntimeException(
                    "Khong the tao thu muc data!"
                );
            }

            System.out.println("Khoi tao data thanh cong!");

        } else {
            System.out.println("Thu muc data da ton tai!");
        }
    }

    // Lay duong dan file theo ten module
    public static String getDuongDanFile(String tenFile) {

        return DATA_FOLDER + File.separator + tenFile;
    }
}
