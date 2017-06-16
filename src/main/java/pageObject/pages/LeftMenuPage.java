package pageObject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageObject.config.GlobalParameters;

public class LeftMenuPage extends AbstractPage {

    private static final By SENT_MAIL_FOLDER_LOCATOR = By.xpath("//a[contains(@href,'sent')]");
    private static final By DRAFT_MAIL_FOLDER_LOCATOR = By.cssSelector("a[href$='drafts/']>span");
    private static final By INBOX_MAIL_FOLDER_LOCATOR = By.xpath("//a[@href='/messages/inbox/' and @class='b-nav__link']");

    public LeftMenuPage(WebDriver driver) {
        super(driver);
    }

    public MailListPage openInboxFolder() {
        waitForElementEnabled(INBOX_MAIL_FOLDER_LOCATOR);
        driver.findElement(INBOX_MAIL_FOLDER_LOCATOR).click();
        waitForElementPresent(By.xpath(GlobalParameters.MAIL_SUBJECT_PATTERN));
        return new MailListPage(driver);
    }

    public MailListPage openSentFolder() {
        waitForElementEnabled(SENT_MAIL_FOLDER_LOCATOR);
        driver.findElement(SENT_MAIL_FOLDER_LOCATOR).click();
        return new MailListPage(driver);
    }

    public MailListPage openDraftFolder() {
        driver.findElement(DRAFT_MAIL_FOLDER_LOCATOR).click();
        return new MailListPage(driver);
    }
}