package mailRu.pages;

import org.openqa.selenium.By;

public class LeftMenuPage extends AbstractPage {

    private static final By SENT_MAIL_FOLDER_LOCATOR = By.xpath("//div[@data-id='500000']");
    private static final By DRAFT_MAIL_FOLDER_LOCATOR = By.xpath("//div[@data-id='500001']");
    private static final By INBOX_MAIL_FOLDER_LOCATOR = By.xpath("//div[@data-id='0']");
    private static final By SPAM_MAIL_FOLDER_LOCATOR = By.xpath("//div[@data-id='950']");
    private static final By DELETED_MAIL_FOLDER_LOCATOR = By.xpath("//div[@data-id='500002']");
    private static final By CLEAR_FOLDER_LOCATOR = By.xpath("//button[@data-name='clearFolder']");

    public MailListPage openInboxFolder() {
        waitForElementEnabled(INBOX_MAIL_FOLDER_LOCATOR);
        driver.findElement(INBOX_MAIL_FOLDER_LOCATOR).click();
        waitForElementPresent(MailListPage.LETTER_BLOCK_LOCATOR);
        return new MailListPage();
    }

    public MailListPage openSentFolder() {
        waitForElementEnabled(SENT_MAIL_FOLDER_LOCATOR);
        driver.findElement(SENT_MAIL_FOLDER_LOCATOR).click();
        waitForElementPresent(MailListPage.LETTER_BLOCK_LOCATOR);
        return new MailListPage();
    }

    public MailListPage openDraftFolder() {
        waitForElementEnabled(DRAFT_MAIL_FOLDER_LOCATOR);
        driver.findElement(DRAFT_MAIL_FOLDER_LOCATOR).click();
        waitForElementPresent(MailListPage.LETTER_BLOCK_LOCATOR);
        return new MailListPage();
    }

    public MailListPage openDeletedFolder() {
        waitForElementEnabled(DELETED_MAIL_FOLDER_LOCATOR);
        driver.findElement(DELETED_MAIL_FOLDER_LOCATOR).click();
        waitForElementPresent(MailListPage.LETTER_BLOCK_LOCATOR);
        return new MailListPage();
    }

    public MailListPage openSpamFolder() {
        refreshPage();
        waitForElementEnabled(SPAM_MAIL_FOLDER_LOCATOR);
        driver.findElement(SPAM_MAIL_FOLDER_LOCATOR).click();
        waitForElementVisible(CLEAR_FOLDER_LOCATOR);
        return new MailListPage();
    }
}