package pageObject.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static pageObject.objects.Letter.*;

public class MailListPage extends AbstractPage {

    private static final String MAIL_SUBJECT_PATTERN = String.format("//*[@data-subject='%s']", MAIL_SUBJECT);

    public MailListPage(WebDriver driver) {
        super(driver);
    }

    public FinishedLetterPage clickToOpenMailLetter () {
        waitForElementEnabled(By.xpath(MAIL_SUBJECT_PATTERN));
        driver.findElement(By.xpath(MAIL_SUBJECT_PATTERN)).click();
        return new FinishedLetterPage(driver);
    }

}