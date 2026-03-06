package pages;

import core.BasePage;
import core.WebUI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    private WebUI ui;

    public LoginPage(WebDriver driver) {
        super(driver);
        // Khởi tạo Page Factory (Nếu BasePage chưa làm việc này)
        PageFactory.initElements(driver, this);

        // Khởi tạo WebUI bằng chính cái driver này
        this.ui = new WebUI(driver);
    }

    @FindBy(how = How.NAME, using = "username")
    private WebElement usernameInput;
    @FindBy(how = How.NAME, using = "password")
    private WebElement passInput;
    @FindBy(how = How.XPATH, using = "//button[@type='submit']")
    private WebElement loginBtn;
    @FindBy(how = How.XPATH, using = "//h6[text()='Dashboard']")
    private WebElement titleDashboard;

    public boolean isBtnDisplayed(){
        ui.waitToDisplay(loginBtn);
        return loginBtn.isDisplayed();
    }
    public void sendKeyToUsername(String value){
        ui.sendKeysToElement(usernameInput, value);
    }
    public void sendKeyToPassword(String value){
        ui.sendKeysToElement(passInput, value);
    }
    public void login(){
        ui.waitToClick(loginBtn);
    }

    public String getTitleText(){
        ui.waitToDisplay(titleDashboard);
        String titleDisplayed = titleDashboard.getText();
        return titleDisplayed;
    }
}
