package testCase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import PageObjects.LoginPage;

public class TC_SignInData {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://magento.softwaretestingboard.com/");
        loginPage = new LoginPage(driver);
    }

    @Test
    public void verify_login_and_signout() {
        String email = "admin012@gmail.com"; // valid
        String password = "Test@12344"; // valid

        boolean loginSuccess = loginPage.login(email, password);
        Assert.assertTrue(loginSuccess, "Login failed — Welcome message not displayed!");

        loginPage.clickSignOutLink();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
