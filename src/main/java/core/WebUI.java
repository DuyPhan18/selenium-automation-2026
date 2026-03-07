package core;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebUI {
    private WebDriver driver;
    private WebDriverWait wait;

    public WebUI(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitToDisplay(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    public void waitToClick(WebElement element){
        waitToDisplay(element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
    public void sendKeysToElement(WebElement element, String value){
        element.sendKeys(value);
    }
    public void clearInput(WebElement element) {
        waitToDisplay(element);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.BACK_SPACE);
    }
    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
