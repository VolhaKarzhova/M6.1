package pageObject.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageObject.businessObjects.Letter;

public class ContentLetterPage extends AbstractPage {

    public LeftMenuPage leftMenuPage = new LeftMenuPage(driver);

    public static final By ADDRESSEE_MAIL_LOCATOR = By.xpath("//span[@class='b-letter__head__addrs__value']/span");
    private static final By SUBJECT_MAIL_LOCATOR = By.xpath("//div[@class='b-letter__head__subj__text']");
    private static final By BODY_MAIL_LOCATOR = By.xpath("//div[contains(@id, 'BODY')]");
    private static final By SPAM_BUTTON_LOCATOR = By.xpath("//div[not(@*)]//div[@data-name='spam']/span");
    private static final By CONFIRM_SPAM_BUTTON_LOCATOR = By.xpath("//div[@class='is-confirmSpam_in']//button[contains(@class,'confirm-cancel')]");

    public ContentLetterPage(WebDriver driver) {
        super(driver);
    }

    public Letter getLetterObject() {
        String addressee = driver.findElement(ADDRESSEE_MAIL_LOCATOR).getText();
        String subject = driver.findElement(SUBJECT_MAIL_LOCATOR).getText();
        String body = driver.findElement(BODY_MAIL_LOCATOR).getText();
        return new Letter(addressee, subject, body);
    }

    public ContentLetterPage markLetterAsSpam() {
        driver.findElement(SPAM_BUTTON_LOCATOR).click();
        waitForElementEnabled(CONFIRM_SPAM_BUTTON_LOCATOR);
        driver.findElement(CONFIRM_SPAM_BUTTON_LOCATOR).click();
        waitForElementPresent(ADDRESSEE_MAIL_LOCATOR);
        return new ContentLetterPage(driver);
    }
}