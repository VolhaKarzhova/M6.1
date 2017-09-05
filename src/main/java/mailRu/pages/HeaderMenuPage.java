package mailRu.pages;

import mailRu.reporting.Logger;
import org.openqa.selenium.By;

public class HeaderMenuPage extends AbstractPage {

    private static final By NEW_LETTER_BUTTON_LOCATOR = By.cssSelector("a[data-name='compose']");
    private static final By AUTHORIZATION_USER_LOCATOR = By.xpath("//i[@id='PH_user-email']");
    private static final By LOGOUT_LINK_LOCATOR = By.xpath("//*[@id='PH_logoutLink']");

    public String getUserLogin() {
        Logger.info("Getting user login after authorization");
        return getElementText(AUTHORIZATION_USER_LOCATOR);
    }

    public LoginPage logout() {
        Logger.info("Logging out of mailbox");
        click(LOGOUT_LINK_LOCATOR);
        waitForElementVisible(LoginPage.LOGIN_BUTTON_LOCATOR);
        return new LoginPage();
    }

    public NewLetterPage clickNewLetterButton() {
        Logger.info("Creating the new letter has started");
        click(NEW_LETTER_BUTTON_LOCATOR);
        return new NewLetterPage();
    }

    public boolean isLogOutButtonVisible() {
        Logger.info("Check if LogOut Button is visible on the page");
        return driver.findElement(LOGOUT_LINK_LOCATOR).isDisplayed();
    }
}