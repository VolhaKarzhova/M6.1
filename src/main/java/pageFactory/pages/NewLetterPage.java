package pageFactory.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewLetterPage extends AbstractPage {

    public LeftMenuPage leftMenuPage = new LeftMenuPage(driver);

    @FindBy(xpath = "//*[@data-original-name='To']")
    private WebElement addresseeInput;

    @FindBy(name = "Subject")
    private WebElement subjectInput;

    @FindBy(css = "#tinymce")
    private WebElement mailBodyInput;

    @FindBy(xpath = "//div[@data-name='send']")
    private WebElement sendButton;

    @FindBy(xpath = "//iframe[contains(@id, 'composeEditor')]")
    private WebElement frameName;

    @FindBy(xpath = "//div[contains(@data-name, 'saveDraft')]")
    private WebElement saveDraftButton;

    @FindBy(xpath = "//div[@class='is-compose-empty_in']//div[@class='popup__desc']")
    private WebElement emptyMailBodyAlert;

    @FindBy(xpath = "//div[@class='is-compose-empty_in']//button[contains(@class, 'confirm-ok')]")
    private WebElement alertConfirmButton;

    @FindBy(xpath = "//div[@class='b-toolbar__message']/a")
    private WebElement savedAsDraftMessage;

    public NewLetterPage(WebDriver driver) {
        super(driver);
    }

    public NewLetterPage fillAllLetterInputs(String addressee, String subject, String body) {
        addresseeInput.sendKeys(addressee);
        subjectInput.sendKeys(subject);
        driver.switchTo().frame(frameName);
        mailBodyInput.sendKeys(body);
        driver.switchTo().defaultContent();
        return this;
    }

    public NewLetterPage saveDraftMail() {
        saveDraftButton.click();
        waitForElementEnabled(savedAsDraftMessage);
        return this;
    }

    public MailStatusPage sendMail() {
        sendButton.click();
        return new MailStatusPage(driver);
    }

    public String getEmptyLetterBodyAlertMessage() {
        return emptyMailBodyAlert.getText();
    }

    public MailStatusPage confirmSendingLetterOnAlert() {
        waitForElementEnabled(alertConfirmButton);
        alertConfirmButton.click();
        waitForElementVisible(addresseeInput);
        return new MailStatusPage(driver);
    }

    public String getInvalidAddresseeAlertMessage() {
        waitForAlertDisplayed();
        return driver.switchTo().alert().getText();
    }
}