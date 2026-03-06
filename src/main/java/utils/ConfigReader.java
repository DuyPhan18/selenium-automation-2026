package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    // Tạo đường dẫn động dựa trên thư mục gốc
    private static final String CONFIG_PATH = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "test"
            + File.separator + "resources"
            + File.separator + "config.properties";

    static {
        try (FileInputStream file = new FileInputStream(CONFIG_PATH)) { // Dùng try-with-resources để tự đóng file
            properties = new Properties();
            properties.load(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Không tìm thấy file config tại: " + CONFIG_PATH);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}