import core.BaseTest;
import core.ExcelUtils;
import core.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

public class HomePageTest extends BaseTest {

    @Test(dataProvider = "homePageTestData")
    public void homePageTest(String testCaseId, String url, String username, String password, String fname, String lname, String empId){
        // Ghi log vào Extent Report cho chuyên nghiệp
        ExtentManager.getTest().info("Bắt đầu chạy Test Case: " + testCaseId);

        LoginPage loginPage =new LoginPage(getDriver());

        ExtentManager.getTest().info("Điều hướng tới URL: " + url);
        loginPage.navigateToUrl(url);
        ExtentManager.getTest().info("Kiểm tra nút Login có hiển thị không");
        boolean isLoginBtnDisplayed = loginPage.isBtnDisplayed();
        Assert.assertTrue(isLoginBtnDisplayed);

        ExtentManager.getTest().info("Nhập Username và Password");
        loginPage.sendKeyToUsername(username);
        loginPage.sendKeyToPassword(password);

        ExtentManager.getTest().info("Nhấn nút Login");
        loginPage.login();

        ExtentManager.getTest().info("Kiểm tra tiêu đề Dashboard sau khi login");
        Assert.assertEquals(loginPage.getTitleText(), "Dashboard");


        String[] data = {testCaseId, url,String.valueOf(isLoginBtnDisplayed), };
        String[] headers = {"TestCaseID","URL", "isLoginBtnDisplayed"};
        String[][] allHeaders = {headers};

        ExcelUtils.writeDataToExcel("data/test-data-result.xlsx", "LoginPage", data, allHeaders);
    }
    @DataProvider(name = "homePageTestData", parallel = true)
    public Object[][] getData() {
        String path = "data" + java.io.File.separator + "test-data.xlsx";
        return ExcelUtils.getTableArray(path, "HomePageTest", false);
    }
}
