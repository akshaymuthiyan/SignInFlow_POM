package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By lnkSignIn = By.linkText("Sign In");
    private By txtEmailAddress = By.id("email");
    private By txtPassword = By.id("pass");
    private By btnSignIn = By.id("send2"); // Magento default
    private By welcomeDropdown = By.xpath("//button[@data-action='customer-menu-toggle']");
    private By lnkSignOut = By.linkText("Sign Out");

    // Success & error checks
    private By headerWelcome = By.xpath("//span[contains(text(),'Welcome,')]");
    private By errorMessage = By.cssSelector(".message-error");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Open login form
    public void openLoginForm() {
        wait.until(ExpectedConditions.elementToBeClickable(lnkSignIn)).click();
    }

    public void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(txtEmailAddress));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(txtPassword));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickSignInButton() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSignIn)).click();
    }


    public void clickSignOutLink() {
        // Step 1: Click Welcome dropdown
        WebElement welcomeDrop = wait.until(ExpectedConditions.elementToBeClickable(welcomeDropdown));
        welcomeDrop.click();

        // Step 2: Wait for Sign Out link in dropdown to be visible
        By signOutLocator = By.xpath("//div[@aria-hidden='false']//a[normalize-space()='Sign Out']");
        WebElement signOutLink = wait.until(ExpectedConditions.elementToBeClickable(signOutLocator));

        // Step 3: Click Sign Out
        signOutLink.click();
    }


    public boolean login(String email, String password) {
        openLoginForm();
        enterEmail(email);
        enterPassword(password);
        clickSignInButton();

        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(headerWelcome),
                    ExpectedConditions.visibilityOfElementLocated(errorMessage)
            ));

            // Return true if welcome message is present
            return driver.findElements(headerWelcome).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
