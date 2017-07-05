package MailRu.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderMenuPage extends AbstractPage {

    private static final By NEW_LETTER_BUTTON_LOCATOR = By.cssSelector("a[data-name='compose']");
    private static final By AUTHORIZATION_USER_LOCATOR = By.cssSelector("#PH_user-email");
    public static final By LOGOUT_LINK_LOCATOR = By.xpath("//*[@id='PH_logoutLink']");

    public String getUserLogin() {
        waitForElementVisible(AUTHORIZATION_USER_LOCATOR);
        return AUTHORIZATION_USER_LOCATOR.findElement(driver).getText();
    }

    public LoginPage logout() {
        driver.findElement(LOGOUT_LINK_LOCATOR).click();
        waitForElementVisible(LoginPage.LOGIN_BUTTON_LOCATOR);
        return new LoginPage();
    }

    public NewLetterPage clickNewLetterButton() {
        waitForElementEnabled(NEW_LETTER_BUTTON_LOCATOR);
        driver.findElement(NEW_LETTER_BUTTON_LOCATOR).click();
        return new NewLetterPage();
    }

    public boolean isLogOutButtonVisible() {
        return driver.findElement(LOGOUT_LINK_LOCATOR).isDisplayed();
    }
}