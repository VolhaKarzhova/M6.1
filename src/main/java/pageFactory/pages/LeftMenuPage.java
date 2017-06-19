package pageFactory.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LeftMenuPage extends AbstractPage {

    @FindBy(xpath = "//a[@href='/messages/sent/']")
    private WebElement sentFolder;

    @FindBy(css = "a[href$='drafts/']>span")
    private WebElement draftFolder;

    @FindBy(xpath = "//a[@href='/messages/inbox/' and @class='b-nav__link']")
    private WebElement inboxFolder;

    @FindBy(xpath = "//a[@href='/messages/spam/']/span[contains(@class,'text')]")
    private WebElement spamFolder;

    @FindBy(xpath = "//a[@href='/messages/trash/']/span[contains(@class,'text')]")
    private WebElement trashFolder;

    @FindBy(xpath = "//*[@href='/messages/inbox/'][@role='button']")
    private WebElement mailsButton;

    public LeftMenuPage(WebDriver driver) {
        super(driver);
    }

    public MailListPage openInboxFolder() {
        waitForElementEnabled(inboxFolder);
        inboxFolder.click();
        waitForElementVisible(MailListPage.letterBlock);
        return new MailListPage(driver);
    }

    public MailListPage openInboxFromSpamFolder() {
        waitForElementEnabled(mailsButton);
        mailsButton.click();
        return new MailListPage(driver);
    }

    public MailListPage openSentFolder() {
        waitForElementEnabled(sentFolder);
        sentFolder.click();
        return new MailListPage(driver);
    }

    public MailListPage openDraftFolder() {
        waitForElementEnabled(draftFolder);
        draftFolder.click();
        return new MailListPage(driver);
    }

    public MailListPage openDeletedFolder() {
        waitForElementEnabled(trashFolder);
        trashFolder.click();
        return new MailListPage(driver);
    }

    public MailListPage openSpamFolder() {
        waitForElementEnabled(spamFolder);
        spamFolder.click();
        return new MailListPage(driver);
    }
}