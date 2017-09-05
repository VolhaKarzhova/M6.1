package mailRu.pages;

import mailRu.reporting.Logger;
import org.openqa.selenium.By;

public class MailStatusPage extends AbstractPage {

    public static final By MAIL_ADDRESSEE_LOCATOR = By.xpath("//span[@class='message-sent__info']");

    public String getAddresseeFromSuccessfulSendLetterMessage() {
        Logger.info("Checking the addressee of the sent letter");
        return getElementText(MAIL_ADDRESSEE_LOCATOR);
    }
}