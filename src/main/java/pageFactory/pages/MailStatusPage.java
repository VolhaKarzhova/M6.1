package pageFactory.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MailStatusPage extends AbstractPage {

    @FindBy(xpath = "//span[@class='message-sent__info']")
    public WebElement mailAddresseeLocator;

    public MailStatusPage(WebDriver driver) {
        super(driver);
    }

    public String getAddresseeFromSuccessfulSendLetterMessage() {
        waitForElementVisible(mailAddresseeLocator);
        return mailAddresseeLocator.getText();
    }
}