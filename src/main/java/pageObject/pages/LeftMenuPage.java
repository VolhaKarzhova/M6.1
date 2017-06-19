package pageObject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LeftMenuPage extends AbstractPage {

    private static final By SENT_MAIL_FOLDER_LOCATOR = By.xpath("//a[@href='/messages/sent/']");
    private static final By DRAFT_MAIL_FOLDER_LOCATOR = By.cssSelector("a[href$='drafts/']>span");
    private static final By INBOX_MAIL_FOLDER_LOCATOR = By.xpath("//a[@href='/messages/inbox/' and @class='b-nav__link']");
    private static final By SPAM_MAIL_FOLDER_LOCATOR = By.xpath("//a[@href='/messages/spam/']/span[contains(@class,'text')]");
    private static final By DELETED_MAIL_FOLDER_LOCATOR = By.xpath("//a[@href='/messages/trash/']/span[contains(@class,'text')]");
    private static final By MAILS_BUTTON_LOCATOR = By.xpath("//*[@href='/messages/inbox/'][@role='button']");

    public LeftMenuPage(WebDriver driver) {
        super(driver);
    }

    public MailListPage openInboxFolder() {
        waitForElementEnabled(INBOX_MAIL_FOLDER_LOCATOR);
        driver.findElement(INBOX_MAIL_FOLDER_LOCATOR).click();
        waitForElementPresent(MailListPage.LETTER_BLOCK_LOCATOR);
        return new MailListPage(driver);
    }

    public MailListPage openInboxFromSpamFolder() {
        driver.findElement(MAILS_BUTTON_LOCATOR).click();
        waitForElementPresent(MailListPage.LETTER_BLOCK_LOCATOR);
        return new MailListPage(driver);
    }

    public MailListPage openSentFolder() {
        waitForElementEnabled(SENT_MAIL_FOLDER_LOCATOR);
        driver.findElement(SENT_MAIL_FOLDER_LOCATOR).click();
        return new MailListPage(driver);
    }

    public MailListPage openDraftFolder() {
        waitForElementEnabled(DRAFT_MAIL_FOLDER_LOCATOR);
        driver.findElement(DRAFT_MAIL_FOLDER_LOCATOR).click();
        return new MailListPage(driver);
    }

    public MailListPage openDeletedFolder() {
        waitForElementEnabled(DELETED_MAIL_FOLDER_LOCATOR);
        driver.findElement(DELETED_MAIL_FOLDER_LOCATOR).click();
        waitForElementPresent(MailListPage.LETTER_BLOCK_LOCATOR);
        return new MailListPage(driver);
    }

    public MailListPage openSpamFolder() {
        waitForElementEnabled(SPAM_MAIL_FOLDER_LOCATOR);
        driver.findElement(SPAM_MAIL_FOLDER_LOCATOR).click();
        waitForElementPresent(MailListPage.LETTER_BLOCK_LOCATOR);
        return new MailListPage(driver);
    }
}