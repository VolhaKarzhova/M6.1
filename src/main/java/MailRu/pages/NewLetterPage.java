package MailRu.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewLetterPage extends AbstractPage {

    public LeftMenuPage leftMenuPage = new LeftMenuPage(driver);

    private static final By ADDRESSEE_INPUT_LOCATOR = By.xpath("//*[@data-original-name='To']");
    private static final By SUBJECT_INPUT_LOCATOR = By.name("Subject");
    private static final By MAIL_BODY_INPUT_LOCATOR = By.cssSelector("#tinymce");
    private static final By FRAME_NAME = By.xpath("//iframe[contains(@id, 'composeEditor')]");
    private static final By SEND_BUTTON_LOCATOR = By.xpath("//div[@data-name='send']");
    private static final By SAVE_DRAFT_BUTTON_LOCATOR = By.xpath("//div[contains(@data-name, 'saveDraft')]");
    private static final By ALERT_EMPTY_BODY_LOCATOR = By.xpath("//div[contains(@class,'empty')]//div[@class='popup__desc']");
    private static final By ALERT_CONFIRM_BUTTON_LOCATOR = By.xpath("//div[@class='is-compose-empty_in']//button[contains(@class, 'confirm-ok')]");
    private static final By SAVED_AS_DRAFT_MESSAGE_LOCATOR = By.xpath("//div[@class='b-toolbar__message']/a");

    public NewLetterPage(WebDriver driver) {
        super(driver);
    }

    public NewLetterPage fillAllLetterInputs(String addressee, String subject, String body) {
        driver.findElement(ADDRESSEE_INPUT_LOCATOR).sendKeys(addressee);
        driver.findElement(SUBJECT_INPUT_LOCATOR).sendKeys(subject);
        driver.switchTo().frame(driver.findElement(FRAME_NAME));
        driver.findElement(MAIL_BODY_INPUT_LOCATOR).sendKeys(body);
        driver.switchTo().defaultContent();
        return this;
    }

    public NewLetterPage saveDraftMail() {
        driver.findElement(SAVE_DRAFT_BUTTON_LOCATOR).click();
        waitForElementEnabled(SAVED_AS_DRAFT_MESSAGE_LOCATOR);
        return this;
    }

    public MailStatusPage sendMail() {
        driver.findElement(SEND_BUTTON_LOCATOR).click();
        return new MailStatusPage(driver);
    }

    public String getEmptyLetterBodyAlertMessage() {
        return driver.findElement(ALERT_EMPTY_BODY_LOCATOR).getText();
    }

    public MailStatusPage confirmSendingLetterOnAlert() {
        waitForElementEnabled(ALERT_CONFIRM_BUTTON_LOCATOR);
        driver.findElement(ALERT_CONFIRM_BUTTON_LOCATOR).click();
        waitForElementVisible(MailStatusPage.MAIL_ADDRESSEE_LOCATOR);
        return new MailStatusPage(driver);
    }

    public String getInvalidAddresseeAlertMessage() {
        waitForAlertDisplayed();
        return driver.switchTo().alert().getText();
    }
}