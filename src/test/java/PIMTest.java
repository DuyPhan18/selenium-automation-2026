import core.BaseTest;
import core.ExcelUtils;
import core.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PIMPage;

public class PIMTest extends BaseTest {
    @Test(priority = 1, dataProvider = "homePageTestData")
    public void testAddEmp(String testCaseId, String url, String username, String password, String fname, String lname, String empId){
        // 1. Đăng nhập trước (Dùng luôn LoginPage đã có)
        LoginPage loginPage = new LoginPage(getDriver());
        ExtentManager.getTest().info("Điều hướng tới URL: " + url);
        loginPage.navigateToUrl(url);
        ExtentManager.getTest().info("Kiểm tra nút Login có hiển thị không");
        boolean isLoginBtnDisplayed = loginPage.isBtnDisplayed();
        Assert.assertTrue(isLoginBtnDisplayed);
        ExtentManager.getTest().info("Nhập Username và Password");
        loginPage.sendKeyToUsername(username);
        loginPage.sendKeyToPassword(password);
        loginPage.login();

        // 2. Vào PIM và thêm nhân viên
        PIMPage pimPage = new PIMPage(getDriver());
        pimPage.goToPIM();
        ExtentManager.getTest().info("Đã vào trang PIM");

        pimPage.addEmployee(fname, lname, empId);
        ExtentManager.getTest().info("Đã nhập thông tin nhân viên");

        Assert.assertEquals(pimPage.getPdTitle(), "Personal Details");
        ExtentManager.getTest().info("Add new employee successfuly!");

        pimPage.goToPIM();
        ExtentManager.getTest().info("Chuyển hướng vào trang PIM");

        pimPage.searchAndDelEmp(empId);
        ExtentManager.getTest().info("Tìm và xóa nhân viên ID: " + empId);

        Assert.assertTrue(pimPage.isDelSuccess(empId));
        ExtentManager.getTest().pass("Đẫ xóa thành công với ID: " + empId
        );
    }

//    @Test(priority = 2, dependsOnMethods = "testAddEmp")
//    public void testDelEmp() {
//        createExtentTest("Xóa nhân viên khỏi hệ thống");
//
//        try {
//            PIMPage pimPage = new PIMPage(getDriver());
//
//            pimPage.goToPIM();
//            test.info("Chuyển hướng vào trang PIM");
//            boolean isDel = pimPage.searchAndDelEmp("0312");
//            Assert.assertTrue(isDel);
//            test.info("Đẫ xóa thành công với ID: " + "0312");
//
//        } catch (Exception | AssertionError e) {
//            try {
//                Thread.sleep(1000); // Đợi 1s cho trang kịp hiện hình
//            } catch (InterruptedException ie) {
//            }
//            // Nếu lỗi, chụp ảnh và đính vào Report
//            String path = captureScreenshot("Add_Employee_Error");
//            test.fail("Bài test bị lỗi: " + e.getMessage())
//                    .addScreenCaptureFromPath(path);
//            Assert.fail("Kết thúc test do lỗi.");
//        }
//        }

    @DataProvider(name = "homePageTestData", parallel = true)
    public Object[][] getData () {
        return ExcelUtils.getTableArray("data\\test-data.xlsx", "HomePageTest", false);
    }
}
