package pageObject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LeftMenuPage extends AbstractPage {

    private static final By SENT_MAIL_FOLDER_LOCATOR = By.xpath("//a[contains(@href,'sent')]");
    private static final By DRAFT_MAIL_FOLDER_LOCATOR = By.cssSelector("a[href$='drafts/']>span");
    private static final By INBOX_MAIL_FOLDER_LOCATOR = By.xpath("//a[@href='/messages/inbox/' and @class='b-nav__link']");

    public LeftMenuPage(WebDriver driver) {
        super(driver);
    }

    public InboxFolderPage openInboxFolder() {
        waitForElementEnabled(INBOX_MAIL_FOLDER_LOCATOR);
        driver.findElement(INBOX_MAIL_FOLDER_LOCATOR).click();
        waitForElementPresent(By.xpath(GlobalParametersPage.MAIL_SUBLECT_PATTERN));
        return new InboxFolderPage(driver);
    }

    public SentFolderPage openSentFolder() {
        waitForElementEnabled(SENT_MAIL_FOLDER_LOCATOR);
        driver.findElement(SENT_MAIL_FOLDER_LOCATOR).click();
        return new SentFolderPage(driver);
    }

    public DraftFolderPage openDraftFolder() {
        driver.findElement(DRAFT_MAIL_FOLDER_LOCATOR).click();
        return new DraftFolderPage(driver);
    }
}