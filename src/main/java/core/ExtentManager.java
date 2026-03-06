package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
public class ExtentManager {
    // 1. Khởi tạo thông qua một hàm static trả về đối tượng
    private static final ExtentReports extentReports = createInstance("reports/extentReport/extentReport.html");
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentReports getInstance() {
        return extentReports;
    }

    public static ExtentReports createInstance(String fileName) {
        // KHÔNG dùng extentReports = new ExtentReports() ở đây
        ExtentReports extent = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(fileName);

        reporter.config().setTheme(Theme.DARK);
        reporter.config().setDocumentTitle("OrangeHRM Automation Report");
        reporter.config().setReportName("Duy - Regression Test Results");
        reporter.config().setEncoding("utf-8");

        extent.attachReporter(reporter);
        extent.setSystemInfo("Author", "Duy");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        return extent; // Trả về đối tượng để gán vào biến final ở trên
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void setTest(ExtentTest test) {
        extentTest.set(test);
    }

    public static void unload() {
        extentTest.remove();
    }
}