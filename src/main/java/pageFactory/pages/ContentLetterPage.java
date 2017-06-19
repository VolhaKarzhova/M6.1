package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageFactory.businessObjects.Letter;

public class ContentLetterPage extends AbstractPage {

    public LeftMenuPage leftMenuPage = new LeftMenuPage(driver);

    @FindBy(xpath = "//span[@class='b-letter__head__addrs__value']/span")
    public WebElement mailAddressee;

    @FindBy(xpath = "//div[@class='b-letter__head__subj__text']")
    private WebElement mailSubject;

    @FindBy(xpath = "//div[contains(@id, 'BODY')]")
    private WebElement mailBody;

    @FindBy(xpath = "//div[not(@*)]//div[@data-name='spam']/span")
    private WebElement spamButton;

    @FindBy(xpath = "//div[@class='is-confirmSpam_in']//button[contains(@class,'confirm-cancel')]")
    private WebElement confirmSpamButton;

    public ContentLetterPage(WebDriver driver) {
        super(driver);
    }

    public Letter getLetterObject() {
        String addressee = mailAddressee.getText();
        String subject = mailSubject.getText();
        String body = mailBody.getText();
        return new Letter(addressee, subject, body);
    }

    public ContentLetterPage markLetterAsSpam() {
        spamButton.click();
        waitForElementEnabled(confirmSpamButton);
        confirmSpamButton.click();
        waitForElementVisible(mailAddressee);
        return new ContentLetterPage(driver);
    }
}