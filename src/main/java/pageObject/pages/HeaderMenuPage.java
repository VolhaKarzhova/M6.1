package pageObject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderMenuPage extends AbstractPage {

    private static final By NEW_LETTER_BUTTON_LOCATOR = By.cssSelector("a[data-name='compose']");
    private static final By AUTHORIZATION_USER_LOCATOR = By.cssSelector("#PH_user-email");
    private static final By LOGOUT_LINK_LOCATOR = By.xpath("//*[@id='PH_logoutLink']");
    private static final By SEND_BUTTON_LOCATOR = By.xpath("//div[@data-name='send']");
    private static final By SAVE_DRAFT_BUTTON_LOCATOR = By.xpath("//div[contains(@data-name, 'saveDraft')]");

    public HeaderMenuPage(WebDriver driver) {
        super(driver);
    }

    public String getUserLogin() {
        waitForElementVisible(AUTHORIZATION_USER_LOCATOR);
        return AUTHORIZATION_USER_LOCATOR.findElement(driver).getText();
    }

    public LoginPage logout() {
        driver.findElement(LOGOUT_LINK_LOCATOR).click();
        waitForElementVisible(LoginPage.LOGIN_BUTTON_LOCATOR);
        return new LoginPage(driver);
    }

    public NewLetterPage clickNewLetterButton() {
        waitForElementEnabled(NEW_LETTER_BUTTON_LOCATOR);
        driver.findElement(NEW_LETTER_BUTTON_LOCATOR).click();
        return new NewLetterPage(driver);
    }

    public NewLetterPage saveDraftMail() {
        driver.findElement(SAVE_DRAFT_BUTTON_LOCATOR).click();
        return new NewLetterPage(driver);
    }

    public MailStatusPage sendMail() {
        driver.findElement(SEND_BUTTON_LOCATOR).click();
        waitForElementVisible(MailStatusPage.SENT_MAIL_MESSAGE_LOCATOR);
        return new MailStatusPage(driver);
    }
}