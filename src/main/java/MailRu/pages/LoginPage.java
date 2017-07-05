package MailRu.pages;

import MailRu.config.GlobalParameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractPage {

    private static final By LOGIN_INPUT_LOCATOR = By.id("mailbox__login");
    private static final By PASSWORD_INPUT_LOCATOR = By.cssSelector("#mailbox__password");
    private static final By AUTHENTICATION_ERROR_MESSAGE_LOCATOR = By.xpath("//*[@id='mailbox:authfail']");
    public static final By LOGIN_BUTTON_LOCATOR = By.className("mailbox__auth__button");

    public LoginPage open() {
        driver.get(GlobalParameters.URL);
        return this;
    }

    public HeaderMenuPage login(String login, String password) {
        waitForElementVisible(LOGIN_INPUT_LOCATOR);
        driver.findElement(LOGIN_INPUT_LOCATOR).clear();
        driver.findElement(LOGIN_INPUT_LOCATOR).sendKeys(login);
        driver.findElement(PASSWORD_INPUT_LOCATOR).clear();
        driver.findElement(PASSWORD_INPUT_LOCATOR).sendKeys(password);
        driver.findElement(LOGIN_BUTTON_LOCATOR).click();
        return new HeaderMenuPage();
    }

    public String getErrorMessage() {
        waitForElementVisible(AUTHENTICATION_ERROR_MESSAGE_LOCATOR);
        return driver.findElement(AUTHENTICATION_ERROR_MESSAGE_LOCATOR).getText();
    }
}