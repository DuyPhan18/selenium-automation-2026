package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import utils.ConfigReader;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseTest {

    protected static ExtentReports extent;
    protected ExtentTest test;
    private Set<Cookie> cookies;

    @BeforeSuite
    public void beforeSuite() {
        // Khởi tạo báo cáo duy nhất 1 lần cho cả đợt chạy
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void initDriver() {

        // 1. Lấy tên trình duyệt từ file config (mặc định là chrome nếu file trống)
        String browserName = ConfigReader.getProperty("browser");
        if (browserName == null || browserName.isEmpty()) {
            browserName = "chrome";
        }
        WebDriver driver;
        // 2. Tắt log hệ thống chung
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        // 3. Switch-case để chọn Browser
        switch (browserName.toLowerCase().trim()) {
            case "chrome":
                System.setProperty("webdriver.chrome.silentOutput", "true");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--no-sandbox"); // Vượt qua rào cản bảo mật của OS
                chromeOptions.addArguments("--disable-dev-shm-usage"); // Tránh lỗi thiếu bộ nhớ đệm (/dev/shm)
                chromeOptions.addArguments("--window-size=1920,1080");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                // Firefox không dùng ChromeOptions mà dùng FirefoxOptions
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-headless");
                firefoxOptions.addArguments("--no-sandbox"); // Vượt qua rào cản bảo mật của OS
                firefoxOptions.addArguments("--disable-dev-shm-usage"); // Tránh lỗi thiếu bộ nhớ đệm (/dev/shm)
                firefoxOptions.addArguments("--window-size=1920,1080");
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--disable-gpu");
                edgeOptions.addArguments("-headless");
                edgeOptions.addArguments("--no-sandbox"); // Vượt qua rào cản bảo mật của OS
                edgeOptions.addArguments("--disable-dev-shm-usage"); // Tránh lỗi thiếu bộ nhớ đệm (/dev/shm)
                edgeOptions.addArguments("--window-size=1920,1080");
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                System.out.println("Browser: " + browserName + " không hợp lệ. Đang khởi tạo Chrome mặc định...");
                driver = new ChromeDriver();
                break;
        }
        driver.manage().window().maximize();

        // 2. Đẩy vào ThreadLocal ngay lập tức
        DriverManager.setDriverThreadLocal(driver);
    }
    // 3. Luôn lấy driver từ ThreadLocal thông qua hàm này
    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    @AfterMethod
    public void closeDriver() {
        // 4. Giải phóng driver và xóa ThreadLocal cho sạch ngăn kéo
        DriverManager.quitDriver();
    }

    // --- Phần Cookie giữ nguyên logic của ông ---
    public void saveCookies() {
        cookies = getDriver().manage().getCookies();
    }

    public void loadCookies(String currentDomain) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getDomain().equals(currentDomain)) {
                    getDriver().manage().addCookie(cookie);
                }
            }
        }
    }
}