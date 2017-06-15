package pageObject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Random;

public class NewLetterPage extends AbstractPage {

    private static final By ADDRESSEE_INPUT_LOCATOR = By.xpath("//*[@data-original-name='To']");
    private static final By SUBJECT_INPUT_LOCATOR = By.name("Subject");
    private static final By MAIL_BODY_INPUT_LOCATOR = By.cssSelector("#tinymce");
    private static final By SAVE_DRAFT_BUTTON_LOCATOR = By.xpath("//div[contains(@data-name, 'saveDraft')]");
    private static final By SEND_BUTTON_LOCATOR = By.xpath("//div[@data-name='send']");
    private static final By FRAME_NAME = By.xpath("//iframe[contains(@id, 'composeEditor')]");
    private static final String ADDRESSEE = "volhakarzhova@mail.ru";
    private static final String MAIL_SUBJECT = "mail number: " + new Random().nextInt(5000);
    private static final String MAIL_BODY = "This is a new email " + new Random().nextInt(5000);
    private String mailSubjectPattern = String.format("//*[@data-subject='%s']", MAIL_SUBJECT);

    public NewLetterPage(WebDriver driver) {
        super(driver);
    }

    public NewLetterPage fillAddresseeInput(String addressee) {
        driver.findElement(ADDRESSEE_INPUT_LOCATOR).sendKeys(ADDRESSEE);
        return this;
    }

    public NewLetterPage fillSubjectInput(String subject) {
        driver.findElement(SUBJECT_INPUT_LOCATOR).sendKeys(MAIL_SUBJECT);
        return this;
    }

    public NewLetterPage fillMailBodyInput(String body) {
        driver.switchTo().frame(driver.findElement(FRAME_NAME));
        driver.findElement(MAIL_BODY_INPUT_LOCATOR).sendKeys(MAIL_BODY);
        driver.switchTo().defaultContent();
        return this;
    }

    public NewLetterPage saveDraftMail() {
        driver.findElement(SAVE_DRAFT_BUTTON_LOCATOR).click();
        return this;
    }

    public NewLetterPage sendMail() {
        driver.findElement(SEND_BUTTON_LOCATOR).click();
        return this;
    }
}