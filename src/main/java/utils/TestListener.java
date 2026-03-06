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
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            String screenshotPath = CaptureHelpers.captureScreenshot(driver, result.getName());

            // KIỂM TRA: Nếu có ảnh thì đính kèm, không thì chỉ ghi log lỗi
            if (screenshotPath != null && !screenshotPath.isEmpty()) {
                ExtentManager.getTest().fail("Test Case FAILED: " + result.getThrowable(),
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } else {
                ExtentManager.getTest().fail("Test Case FAILED: " + result.getThrowable());
                ExtentManager.getTest().warning("Could not attach screenshot (Path is null or empty).");
            }
        } else {
            ExtentManager.getTest().fail("Test Case FAILED: " + result.getThrowable() + " (Driver is NULL)");
        }
    }
    @Override
    public void onFinish(ITestContext context) {
        // Xuất báo cáo ra file HTML
        ExtentManager.getInstance().flush();
        System.out.println(">>> Đã xuất báo cáo Extent Report!");
    }
}