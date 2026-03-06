package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.yian.helpers.CaptureHelpers;
import core.DriverManager;
import core.ExtentManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        // Khởi tạo test và lưu vào ThreadLocal thông qua setTest
        ExtentTest test = ExtentManager.getInstance().createTest(result.getName());
        ExtentManager.setTest(test);
        System.out.println(">>> Đang bắt đầu chạy: " + result.getName());
    }
    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver driver = DriverManager.getDriver(); // Lấy driver từ ThreadLocal của bạn
        if (driver != null) {
            // Chỉ chụp ảnh khi driver tồn tại
            String screenshotPath = CaptureHelpers.captureScreenshot(driver, result.getName());

            // Gán ảnh vào Extent Report
            ExtentManager.getTest().fail("Test Case bị lỗi!",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else {
            // Nếu driver null, chỉ ghi log chữ để tránh bị crash
            ExtentManager.getTest().fail("Test Case bị lỗi nhưng không thể chụp ảnh vì Driver là NULL.");
        }
    }
    @Override
    public void onFinish(ITestContext context) {
        // Xuất báo cáo ra file HTML
        ExtentManager.getInstance().flush();
        System.out.println(">>> Đã xuất báo cáo Extent Report!");
    }
}