package pageObject.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class MailListPage extends AbstractPage {

    public static final By LETTER_BLOCK_LOCATOR = By.xpath("//div[@data-mnemo='letters']");
    private static final String DELETE_BUTTON_LOCATOR = "(//div[@data-name='remove'])[%d]/span";
    private static final By SPAM_BUTTON_LOCATOR = By.xpath("//div[@data-name='spam']");
    private static final By NO_SPAM_BUTTON_LOCATOR = By.xpath("//div[@data-cache-key='950_undefined_false']//div[@data-name='noSpam']/span");
    private static final By MAIL_WITH_BLANK_SUBJECT_LOCATOR = By.xpath("(//a[@class='js-href b-datalist__item__link'][not(@data-subject)])[1]");
    private static final String MAIL_BY_SUBJECT_LOCATOR = "//a[@data-subject='%s']";
    private static final String LETTER_CHECKBOX_LOCATOR = "(//*[@data-subject='%s']//div[@class='b-checkbox__box'])[%d]";
    private static final By CONFIRM_SPAM_BUTTON_LOCATOR = By.xpath("//div[@class='is-confirmSpam_in']//button[contains(@class,'confirm-cancel')]");


    public MailListPage(WebDriver driver) {
        super(driver);
    }

    public ContentLetterPage openLetterBySubject(String subject) {
        waitForElementEnabled(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
        driver.findElement(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject))).click();
        waitForElementVisible(ContentLetterPage.ADDRESSEE_MAIL_LOCATOR);
        return new ContentLetterPage(driver);
    }

    public ContentLetterPage openLetterWithoutSubject() {
        waitForElementEnabled(MAIL_WITH_BLANK_SUBJECT_LOCATOR);
        driver.findElement(MAIL_WITH_BLANK_SUBJECT_LOCATOR).click();
        waitForElementVisible(ContentLetterPage.ADDRESSEE_MAIL_LOCATOR);
        return new ContentLetterPage(driver);
    }

    public boolean isLetterVisible(String subject) {
        boolean i = false;
        try {
            waitForElementVisible(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
            driver.findElement(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject))).isDisplayed();
            i = true;
        } catch (TimeoutException exception) {
            return i;
        } catch (NoSuchElementException exception) {
        }  return i;
    }

    public MailListPage clickLetterCheckbox(String subject, int position) {
        waitForElementVisible(By.xpath(String.format(LETTER_CHECKBOX_LOCATOR, subject, position)));
        driver.findElement(By.xpath(String.format(LETTER_CHECKBOX_LOCATOR, subject, position))).click();
        return new MailListPage(driver);
    }

    public MailListPage deleteLetter(String subject, int position) {
        driver.findElement(By.xpath(String.format(DELETE_BUTTON_LOCATOR, position))).click();
        waitForElementDisappear(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
        return new MailListPage(driver);
    }

    public MailListPage markLetterAsNoSpam(String subject) {
        driver.findElement(NO_SPAM_BUTTON_LOCATOR).click();
        waitForElementDisappear(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
        return new MailListPage(driver);
    }

    public MailListPage markLetterAsSpam(String subject) {
        driver.findElement(SPAM_BUTTON_LOCATOR).click();
        waitForElementEnabled(CONFIRM_SPAM_BUTTON_LOCATOR);
        driver.findElement(CONFIRM_SPAM_BUTTON_LOCATOR).click();
        waitForElementDisappear(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
        return new MailListPage(driver);
    }
}