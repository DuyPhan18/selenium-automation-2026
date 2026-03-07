package pages;

import core.BasePage;
import core.WebUI;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PIMPage extends BasePage {
    private WebUI ui; // Khai báo đối tượng WebUI để dùng trong Page

    public PIMPage(WebDriver driver) {
        super(driver);
        // Khởi tạo Page Factory (Nếu BasePage chưa làm việc này)
        PageFactory.initElements(driver, this);

        // Khởi tạo WebUI bằng chính cái driver này
        this.ui = new WebUI(driver);
    }

    @FindBy(how = How.XPATH, using = "//span[text()='PIM']")
    private WebElement pimMenu;

    @FindBy(how = How.XPATH, using = "//button[text()=' Add ']")
    private WebElement addBtn;

    @FindBy(how = How.NAME, using = "firstName")
    private WebElement firstNameInput;

    @FindBy(how = How.NAME, using = "lastName")
    private WebElement lastNameInput;

    @FindBy(how = How.XPATH, using = "//button[@type='submit']")
    private WebElement saveBtn;

    @FindBy(how = How.XPATH, using = "//h6[text()='Personal Details']")
    private WebElement pdTitle;

    @FindBy(how = How.XPATH, using = "//label[text()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement empIdInput;

    @FindBy(how = How.XPATH, using = "//i[contains(@class, 'bi-trash')]")
    private WebElement deleteEmpBtn;

    @FindBy(how = How.XPATH, using = "//div[@role='document']")
    private WebElement delPopup;

    @FindBy(how = How.XPATH, using = "//button[(text()= ' Yes, Delete ')]")
    private WebElement delBtn;

    @FindBy(how = How.XPATH, using = "//button[text()=' Search ']")
    private WebElement searchBtn;

    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'orangehrm-horizontal-padding orangehrm-vertical-padding')]/span")
    private WebElement recordFoundTitle;

    public void goToPIM() {
        ui.waitToDisplay(pimMenu);
        ui.waitToClick(pimMenu);
    }

    public void addEmployee(String fname, String lname,String empId) {
        ui.waitToClick(addBtn);
        ui.waitToDisplay(empIdInput);
        ui.clearInput(empIdInput);
        ui.sendKeysToElement(firstNameInput, fname);
        ui.sendKeysToElement(lastNameInput, lname);
        ui.sendKeysToElement(empIdInput, empId);
        ui.waitToClick(saveBtn);
    }

    public String getPdTitle(){
        ui.waitToDisplay(pdTitle);
        return pdTitle.getText() ;
    }

    public boolean searchAndDelEmp(String empId){
        // 1. Đợi ô input xuất hiện và dùng phím tắt để xóa sạch (Clear "bất tử")
        ui.waitToDisplay(empIdInput);
        ui.clearInput(empIdInput);
        ui.waitToDisplay(empIdInput);
        ui.sendKeysToElement(empIdInput, empId);

        // 2. Nhấn Search
        searchBtn.click();
        if (isRecordFound()){
            ui.scrollToElement(deleteEmpBtn);
            ui.waitToClick(deleteEmpBtn);
            ui.waitToDisplay(delPopup);
            ui.waitToClick(delBtn);
            return true;
        }
        return false;
    }

    public boolean isRecordFound(){
        ui.waitToDisplay(recordFoundTitle);
        if (recordFoundTitle.getText().contains("Record Found")){
           return true;
        }
        return false;
    }

    public boolean isDelSuccess(String empId){
        ui.waitToDisplay(empIdInput);
        ui.clearInput(empIdInput);
        ui.sendKeysToElement(empIdInput, empId);
        ui.waitToClick(searchBtn);
        ui.waitToDisplay(recordFoundTitle);
        if (recordFoundTitle.getText().contains("No Records Found")){
            return true;
        }
        return false;
    }


}
