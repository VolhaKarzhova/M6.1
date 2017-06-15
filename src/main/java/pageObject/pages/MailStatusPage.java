package pageObject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MailStatusPage extends AbstractPage {

    public static final By SENT_MAIL_MESSAGE_LOCATOR = By.xpath("//div[@class='message-sent__title']");

    public MailStatusPage(WebDriver driver) {
        super(driver);
    }
}