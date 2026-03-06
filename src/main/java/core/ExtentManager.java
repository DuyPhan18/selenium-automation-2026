package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extentReports;
    // THÊM DÒNG NÀY: Để quản lý bài test riêng biệt cho từng luồng
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentReports getInstance() {
        if (extentReports == null) {
            createInstance("reports/extentReport/extentReport.html");
        }
        return extentReports;
    }

    public static ExtentReports createInstance(String fileName){
        extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(fileName);

        reporter.config().setTheme(Theme.DARK); // Chuyển sang Dark Mode
        reporter.config().setDocumentTitle("OrangeHRM Automation Report"); // Tiêu đề tab trình duyệt
        reporter.config().setReportName("Duy - Regression Test Results"); // Tên báo cáo hiển thị ở góc
        reporter.config().setEncoding("utf-8");

        extentReports.attachReporter(reporter);

        extentReports.setSystemInfo("Framework Name", "Final Assignment");
        extentReports.setSystemInfo("Author", "Duy");
        extentReports.setSystemInfo("Environment", "Production");
        extentReports.setSystemInfo("OS", System.getProperty("os.name")); // Tự động lấy tên Win/Mac
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("Browser", "Chrome Latest");

        return extentReports;
    }
    // --- QUAN TRỌNG: Thêm hàm này để TestListener gọi được ---
    public ExtentTest createTest(String testName) {
        return extentReports.createTest(testName);
    }

    // THÊM 2 HÀM NÀY: Để TestListener gọi tới
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void setTest(ExtentTest test) {
        extentTest.set(test);
    }
}