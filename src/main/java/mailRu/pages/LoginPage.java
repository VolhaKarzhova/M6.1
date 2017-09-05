package mailRu.pages;

import mailRu.config.GlobalParameters;
import mailRu.reporting.Logger;
import org.openqa.selenium.By;

public class LoginPage extends AbstractPage {

    private static final By LOGIN_INPUT_LOCATOR = By.id("mailbox__login");
    private static final By PASSWORD_INPUT_LOCATOR = By.cssSelector("#mailbox__password");
    private static final By AUTHENTICATION_ERROR_MESSAGE_LOCATOR = By.xpath("//*[@id='mailbox:authfail']");
    public static final By LOGIN_BUTTON_LOCATOR = By.className("mailbox__auth__button");

    public LoginPage open() {
        open(GlobalParameters.URL);
        return this;
    }

    public HeaderMenuPage login(String login, String password) {
        Logger.info("Login started");
        refreshPage();
        clear(LOGIN_INPUT_LOCATOR);
        type(LOGIN_INPUT_LOCATOR, login);
        clear(PASSWORD_INPUT_LOCATOR);
        type(PASSWORD_INPUT_LOCATOR, password);
        click(LOGIN_BUTTON_LOCATOR);
        Logger.info("Login finished");
        return new HeaderMenuPage();
    }

    public String getErrorMessage() {
        Logger.info("Getting authentication error message");
        return getElementText(AUTHENTICATION_ERROR_MESSAGE_LOCATOR);
    }
}