package core;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver(){
        return driverThreadLocal.get();
    }

    public static void setDriverThreadLocal(WebDriver driver){
        driverThreadLocal.set(driver);
    }

    public static void quitDriver() {
        if (getDriver() != null){
            getDriver().quit();
            driverThreadLocal.remove();
        }
    }
}
