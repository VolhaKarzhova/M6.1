package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderMenuPage extends AbstactPage {

    @FindBy(css = "#PH_user-email")
    private WebElement logedInUserMail;

    @FindBy(css = "a[data-name='compose']")
    private WebElement newLetterButton;

    @FindBy(xpath = "//*[@id='PH_logoutLink']")
    public WebElement logOutButton;

    public HeaderMenuPage(WebDriver driver) {
        super(driver);
    }

    public String getUserLogin() {
        waitForElementVisible(logedInUserMail);
        return logedInUserMail.getText();
    }

    public LoginPage logout() {
        logOutButton.click();
        //waitForElementVisible(Loginpage.loginInput)
        return new LoginPage(driver);
    }

    public NewLetterPage clickNewLetterButton() {
        waitForElementEnabled(newLetterButton);
        newLetterButton.click();
        return new NewLetterPage(driver);
    }
}