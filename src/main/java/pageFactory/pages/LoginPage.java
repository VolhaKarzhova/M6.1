package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {

    @FindBy(id = "mailbox__login")
    private WebElement loginInput;

    @FindBy(css = "#mailbox__password")
    private WebElement passwordInput;

    @FindBy(className = "mailbox__auth__button")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@id='mailbox:authfail']")
    private WebElement authenticationErrorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public HeaderMenuPage login(String login, String password) {
        waitForElementVisible(loginInput);
        loginInput.clear();
        loginInput.sendKeys(login);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        loginButton.click();
        return new HeaderMenuPage(driver);
    }

    public String getErrorMessage() {
        waitForElementVisible(authenticationErrorMessage);
        return authenticationErrorMessage.getText();
    }
}