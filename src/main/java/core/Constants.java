package core;

public class Constants {
    public static final int TIMEOUT = 10;
    public static final int POLLING = 15;
    public static final String userId = "mngr581439";
    public static String password = "test112$";
    public static final String TEST_DATA_FOLDER_PATH = "data/";
    //MSG
    public static final String IS_NOT_DISPLAYED = " is NOT displayed";
    public static final String IS_DISPLAYED = " is displayed";

    public static final String NO_BLANK = " must not be blank";
    //URL
    public static final String  LOGINPAGE_URL = "http://www.demo.guru99.com/v4";

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String newPassword) {
        password = newPassword;
    }
}
