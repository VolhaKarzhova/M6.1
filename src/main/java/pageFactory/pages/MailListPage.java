package pageFactory.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class MailListPage extends AbstractPage {

    private ContentLetterPage contentLetterPage = new ContentLetterPage(driver);

    @FindBy(xpath = "//div[@data-mnemo='letters']")
    public static WebElement letterBlock;

    @FindBy(xpath = "//div[@data-name='spam']")
    private WebElement spamButton;

    @FindBy(xpath = "//div[@class='is-confirmSpam_in']//button[contains(@class,'confirm-cancel')]")
    private WebElement confirmSpamButton;

    @FindBy(xpath = "//div[@data-cache-key='950_undefined_false']//div[@data-name='noSpam']/span")
    private WebElement noSpamButton;

    @FindBy(xpath = "(//a[@class='js-href b-datalist__item__link'][not(@data-subject)])[1]")
    private static WebElement mailWithBlankSubject;

    private static final String DELETE_BUTTON_LOCATOR = "(//div[@data-name='remove'])[%d]/span";
    private static final String MAIL_WITH_DEFINED_SUBJECT_LOCATOR = "//*[@data-subject='%s']";
    private static final String LETTER_CHECKBOX_LOCATOR = "(//*[@data-subject='%s']//div[@class='b-checkbox__box'])[%d]";

    public MailListPage(WebDriver driver) {
        super(driver);
    }

    public ContentLetterPage openLetterBySubject(String subject) {
        waitForElementVisible(By.xpath(String.format(MAIL_WITH_DEFINED_SUBJECT_LOCATOR, subject)));
        driver.findElement(By.xpath(String.format(MAIL_WITH_DEFINED_SUBJECT_LOCATOR, subject))).click();
        waitForElementVisible(contentLetterPage.mailAddressee);
        return new ContentLetterPage(driver);
    }

    public ContentLetterPage openLetterWithoutSubject() {
        mailWithBlankSubject.click();
        waitForElementVisible(contentLetterPage.mailAddressee);
        return new ContentLetterPage(driver);
    }

    public boolean isLetterVisible(String subject) {
        boolean i = false;
        try {
            waitForElementVisible(By.xpath(String.format(MAIL_WITH_DEFINED_SUBJECT_LOCATOR, subject)));
            driver.findElement(By.xpath(String.format(MAIL_WITH_DEFINED_SUBJECT_LOCATOR, subject))).isDisplayed();
            i = true;
        } catch (TimeoutException exception) {
            return i;
        } catch (NoSuchElementException exception) {
        }
        return i;
    }

    public MailListPage clickLetterCheckbox(String subject, int position) {
        driver.findElement(By.xpath(String.format(LETTER_CHECKBOX_LOCATOR, subject, position))).click();
        return new MailListPage(driver);
    }

    public MailListPage deleteLetter(String subject, int position) {
        driver.findElement(By.xpath(String.format(DELETE_BUTTON_LOCATOR, position))).click();
        waitForElementDisappear(By.xpath(String.format(MAIL_WITH_DEFINED_SUBJECT_LOCATOR, subject)));
        return new MailListPage(driver);
    }

    public MailListPage markLetterAsNoSpam(String subject) {
        noSpamButton.click();
        waitForElementDisappear(By.xpath(String.format(MAIL_WITH_DEFINED_SUBJECT_LOCATOR, subject)));
        return new MailListPage(driver);
    }

    public MailListPage markLetterAsSpam(String subject) {
        spamButton.click();
        waitForElementEnabled(confirmSpamButton);
        confirmSpamButton.click();
        waitForElementDisappear(By.xpath(String.format(MAIL_WITH_DEFINED_SUBJECT_LOCATOR, subject)));
        return new MailListPage(driver);
    }
}