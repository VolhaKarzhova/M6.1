package mailRu.pages;

import mailRu.reporting.Logger;
import org.openqa.selenium.By;

public class NewLetterPage extends AbstractPage {

    private static final By ADDRESSEE_INPUT_LOCATOR = By.xpath("//*[@data-original-name='To']");
    private static final By SUBJECT_INPUT_LOCATOR = By.name("Subject");
    private static final By MAIL_BODY_INPUT_LOCATOR = By.cssSelector("#tinymce");
    private static final By FRAME_NAME = By.xpath("//iframe[contains(@id, 'composeEditor')]");
    private static final By SEND_BUTTON_LOCATOR = By.xpath("//div[@data-name='send']");
    private static final By SAVE_DRAFT_BUTTON_LOCATOR = By.xpath("//div[contains(@data-name, 'saveDraft')]");
    private static final By ALERT_EMPTY_BODY_LOCATOR = By.xpath("//div[contains(@class,'empty')]//div[@class='popup__desc']");
    private static final By ALERT_CONFIRM_BUTTON_LOCATOR = By.xpath("//div[@class='is-compose-empty_in']//button[contains(@class, 'confirm-ok')]");
    private static final By SAVED_AS_DRAFT_MESSAGE_LOCATOR = By.xpath("//div[@class='b-toolbar__message']/a");

    public NewLetterPage fillAllLetterInputs(String addressee, String subject, String body) {
        Logger.info("Filling all letter inputs started");
        fillAddresseeInput(addressee);
        type(SUBJECT_INPUT_LOCATOR, subject);
        driver.switchTo().frame(driver.findElement(FRAME_NAME));
        type(MAIL_BODY_INPUT_LOCATOR, body);
        driver.switchTo().defaultContent();
        return this;
    }

    public NewLetterPage fillAddresseeInput(String addressee) {
        type(ADDRESSEE_INPUT_LOCATOR, addressee);
        return this;
    }

    public NewLetterPage saveDraftMail() {
        Logger.info("Saving letter as a draft by clicking Draft button");
        click(SAVE_DRAFT_BUTTON_LOCATOR);
        waitForElementEnabled(SAVED_AS_DRAFT_MESSAGE_LOCATOR);
        return this;
    }

    public MailStatusPage sendMail() {
        Logger.info("Sending letter by clicking Send button");
        click(SEND_BUTTON_LOCATOR);
        return new MailStatusPage();
    }

    public String getEmptyLetterBodyAlertMessage() {
        Logger.info("Getting alert message as letter has empty body");
        return getElementText(ALERT_EMPTY_BODY_LOCATOR);
    }

    public MailStatusPage confirmSendingLetterOnAlert() {
        Logger.info("Confirming the letter to be send");
        waitForElementEnabled(ALERT_CONFIRM_BUTTON_LOCATOR);
        click(ALERT_CONFIRM_BUTTON_LOCATOR);
        waitForElementVisible(MailStatusPage.MAIL_ADDRESSEE_LOCATOR);
        return new MailStatusPage();
    }

    public String getInvalidAddresseeAlertMessage() {
        Logger.info("Getting the alert message as the addressee is invalid");
        waitForAlertDisplayed();
        return driver.switchTo().alert().getText();
    }
}