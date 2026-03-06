package com.yian.helpers;

import core.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureHelpers {

    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            // 1. Tạo tên file kèm ngày giờ để không bị trùng
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = screenshotName + "_" + timestamp + ".png";

            // 2. Xác định đường dẫn folder screenshots
            String directory = System.getProperty("user.dir") + "/reports/screenshots/";
            File folder = new File(directory);

            // Nếu folder chưa có thì tự tạo luôn
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // 3. Chụp ảnh
            File source = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
            String targetPath = directory + fileName;
            File destination = new File(targetPath);

            // 4. Copy file vào folder
            FileUtils.copyFile(source, destination);

            System.out.println(">>> Đã lưu ảnh màn hình tại: " + targetPath);
            return targetPath; // Trả về đường dẫn để Extent Report lấy dùng
        } catch (Exception e) {
            System.out.println("Lỗi khi chụp ảnh: " + e.getMessage());
            return "";
        }
    }
}