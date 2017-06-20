package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageFactory.businessObjects.Letter;

public class ContentLetterPage extends AbstractPage {

    @FindBy(xpath = "//span[@class='b-letter__head__addrs__value']/span")
    public WebElement mailAddressee;

    @FindBy(xpath = "//div[@class='b-letter__head__subj__text']")
    private WebElement mailSubject;

    @FindBy(xpath = "//div[contains(@id, 'BODY')]")
    private WebElement mailBody;

    public ContentLetterPage(WebDriver driver) {
        super(driver);
    }

    public Letter getLetter() {
        String addressee = mailAddressee.getText();
        String subject = mailSubject.getText();
        String body = mailBody.getText();
        return new Letter(addressee, subject, body);
    }
}