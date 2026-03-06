package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    private static final String URL ="";
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.TIMEOUT), Duration.ofSeconds(Constants.POLLING));
        PageFactory.initElements(driver, this);
    }

    public void navigateToUrl(String url){
        driver.navigate().to(url);
    }



}
