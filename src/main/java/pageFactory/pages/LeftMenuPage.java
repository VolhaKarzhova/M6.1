package pageFactory.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LeftMenuPage extends AbstractPage {

    @FindBy(xpath = "//div[@data-id='500000']")
    private WebElement sentFolder;

    @FindBy(xpath = "//div[@data-id='500001']")
    private WebElement draftFolder;

    @FindBy(xpath = "//div[@data-id='0']")
    private WebElement inboxFolder;

    @FindBy(xpath = "//div[@data-id='950']")
    private WebElement spamFolder;

    @FindBy(xpath = "//div[@data-id='500002']")
    private WebElement trashFolder;

    public LeftMenuPage(WebDriver driver) {
        super(driver);
    }

    public MailListPage openInboxFolder() {
        waitForElementEnabled(inboxFolder);
        inboxFolder.click();
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
        refreshPage();
        waitForElementEnabled(spamFolder);
        spamFolder.click();
        return new MailListPage(driver);
    }
}