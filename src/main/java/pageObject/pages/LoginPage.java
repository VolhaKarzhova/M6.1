package pageObject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractPage {

    private static final By LOGIN_INPUT_LOCATOR = By.id("mailbox__login");
    private static final By PASSWORD_INPUT_LOCATOR = By.cssSelector("#mailbox__password");
    private static final By AUTHENTICATION_FAIL_MESSAGE_LOCATOR = By.xpath("//*[@id='mailbox:authfail']");
    static final By LOGIN_BUTTON_LOCATOR = By.className("mailbox__auth__button");
    private static final String URL = "https://mail.ru";
    public static final String USER_LOGIN = "volhakarzhova";
    public static final String USER_PASSWORD = "1584624Qwe";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(URL);
        return this;
    }

    public LoginPage fillLoginInput(String login) {
        driver.findElement(LOGIN_INPUT_LOCATOR).sendKeys(login);
        return this;
    }

    public LoginPage fillPasswordInput(String password) {
        driver.findElement(PASSWORD_INPUT_LOCATOR).sendKeys(password);
        return this;
    }

    public InboxFolderPage getAuthorizationIntoMailBox() {
        driver.findElement(LOGIN_BUTTON_LOCATOR).click();
        return new InboxFolderPage(driver);
    }

    public boolean getAuthenticationFailMessage() {
        driver.findElement(LOGIN_BUTTON_LOCATOR).click();
        waitForElementVisible(AUTHENTICATION_FAIL_MESSAGE_LOCATOR);
        driver.findElement(AUTHENTICATION_FAIL_MESSAGE_LOCATOR).isDisplayed();
        return true;
    }

    public LoginPage clearLoginInput() {
        driver.findElement(LOGIN_INPUT_LOCATOR).clear();
        return this;
    }

    public LoginPage clearPasswordInput() {
        driver.findElement(PASSWORD_INPUT_LOCATOR).clear();
        return this;
    }
}