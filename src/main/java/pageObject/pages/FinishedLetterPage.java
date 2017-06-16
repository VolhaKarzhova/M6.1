package pageObject.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageObject.objects.Letter;

public class FinishedLetterPage extends AbstractPage {

    private static final By ADDRESSEE_MAIL_LOCATOR = By.xpath("//span[@class='b-letter__head__addrs__value']/span");
    private static final By SUBLECT_MAIL_LOCATOR = By.xpath("//div[@class='b-letter__head__subj__text']");
    private static final By BODY_MAIL_LOCATOR = By.xpath("//div[contains(@id, 'BODY')]");

    public FinishedLetterPage(WebDriver driver) {
        super(driver);
    }

    public Letter createLetterObject() {
        String addresse = driver.findElement(ADDRESSEE_MAIL_LOCATOR).getAttribute("data-contact-informer-email");
        String subject = driver.findElement(SUBLECT_MAIL_LOCATOR).getText();
        String body = driver.findElement(BODY_MAIL_LOCATOR).getText();
        return new Letter();

    }


}
