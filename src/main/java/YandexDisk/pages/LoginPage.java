package YandexDisk.pages;


import YandexDisk.config.GlobalParameters;
import org.openqa.selenium.By;

public class LoginPage extends AbstractPage {

    private static final By LOGIN_INPUT_LOCATOR = By.xpath("//input[@id='nb-22']");
    private static final By PASSWORD_INPUT_LOCATOR = By.xpath("//input[@id='nb-23']");
    private static final By LOGIN_BUTTON_LOCATOR = By.xpath("//button[@id='nb-21']");

    public LoginPage open() {
        driver.get(GlobalParameters.URL);
        return this;
    }

    public FileListPage login(String login, String password) {
        driver.findElement(LOGIN_INPUT_LOCATOR).sendKeys(login);
        driver.findElement(PASSWORD_INPUT_LOCATOR).sendKeys(password);
        driver.findElement(LOGIN_BUTTON_LOCATOR).click();
        return new FileListPage();
    }
}