package mailRu.pages;

import mailRu.reporting.Logger;
import org.openqa.selenium.By;

public class LeftMenuPage extends AbstractPage {

    private static final By SENT_MAIL_FOLDER_LOCATOR = By.xpath("//div[@data-id='500000']");
    private static final By DRAFT_MAIL_FOLDER_LOCATOR = By.xpath("//div[@data-id='500001']");
    private static final By INBOX_MAIL_FOLDER_LOCATOR = By.xpath("//div[@data-id='0']");
    private static final By SPAM_MAIL_FOLDER_LOCATOR = By.xpath("//div[@data-id='950']");
    private static final By DELETED_MAIL_FOLDER_LOCATOR = By.xpath("//div[@data-id='500002']");
    private static final By CLEAR_FOLDER_LOCATOR = By.xpath("//button[@data-name='clearFolder']");

    public MailListPage openInboxFolder() {
        Logger.info("Opening Inbox Folder");
        waitForElementEnabled(INBOX_MAIL_FOLDER_LOCATOR);
        click(INBOX_MAIL_FOLDER_LOCATOR);
        waitForElementPresent(MailListPage.LETTER_BLOCK_LOCATOR);
        return new MailListPage();
    }

    public MailListPage openSentFolder() {
        Logger.info("Opening Sent Folder");
        waitForElementEnabled(SENT_MAIL_FOLDER_LOCATOR);
        click(SENT_MAIL_FOLDER_LOCATOR);
        waitForElementPresent(MailListPage.LETTER_BLOCK_LOCATOR);
        return new MailListPage();
    }

    public MailListPage openDraftFolder() {
        Logger.info("Opening Draft Folder");
        waitForElementEnabled(DRAFT_MAIL_FOLDER_LOCATOR);
        click(DRAFT_MAIL_FOLDER_LOCATOR);
        waitForElementPresent(MailListPage.LETTER_BLOCK_LOCATOR);
        return new MailListPage();
    }

    public MailListPage openDeletedFolder() {
        Logger.info("Opening Trash Folder");
        waitForElementEnabled(DELETED_MAIL_FOLDER_LOCATOR);
        click(DELETED_MAIL_FOLDER_LOCATOR);
        waitForElementPresent(MailListPage.LETTER_BLOCK_LOCATOR);
        return new MailListPage();
    }

    public MailListPage openSpamFolder() {
        refreshPage();
        Logger.info("Opening Spam Folder");
        waitForElementEnabled(SPAM_MAIL_FOLDER_LOCATOR);
        click(SPAM_MAIL_FOLDER_LOCATOR);
        waitForElementVisible(CLEAR_FOLDER_LOCATOR);
        return new MailListPage();
    }
}