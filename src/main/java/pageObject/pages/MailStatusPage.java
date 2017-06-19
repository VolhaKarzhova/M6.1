package pageObject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MailStatusPage extends AbstractPage {

    public NewLetterPage newLetterPage = new NewLetterPage(driver);

    public static final By MAIL_ADDRESSEE_LOCATOR = By.xpath("//span[@class='message-sent__info']");

    public MailStatusPage(WebDriver driver) {
        super(driver);
    }

    public String getAddresseFromMessage() {
        return driver.findElement(MAIL_ADDRESSEE_LOCATOR).getText();
    }
}