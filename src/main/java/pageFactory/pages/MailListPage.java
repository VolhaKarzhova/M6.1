package pageFactory.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MailListPage extends AbstractPage {

    private ContentLetterPage contentLetterPage = new ContentLetterPage(driver);

    @FindBy(xpath = "//div[@data-mnemo='letters']")
    public static WebElement letterBlock;

    @FindBy(xpath = "//div[@data-cache-key='500001_undefined_false']//div[@data-name='remove']/span")
    private WebElement deleteDraftLetterButton;

    @FindBy(xpath = "//div[@data-cache-key='500002_undefined_false']//div[@data-name='remove']/span")
    private WebElement deleteLetterFromTrashButton;

    @FindBy(xpath = "//div[@data-cache-key='950_undefined_false']//div[@data-name='noSpam']/span")
    private WebElement noSpamButton;

    @FindBy(xpath = "(//a[@class='js-href b-datalist__item__link'][not(@data-subject)])[1]")
    private static WebElement mailWithBlankSubject;

    public static final String MAIL_WITH_DEFINED_SUBJECT_LOCATOR = "//*[@data-subject='%s']";
    private static final String LETTER_CHECKBOX_LOCATOR = "//*[@data-subject='%s']//div[@class='b-checkbox__box']";
    private static final String SPAM_LETTER_BY_SUBJECT_LOCATOR = "//div[@data-cache-key='950_undefined_false']//a[@data-subject='%s']";
    private static final String CHECKBOX_NOSPAM_LOCATOR = "//div[@data-cache-key='950_undefined_false']//a[@data-subject='%s']//div[@class='b-checkbox__box']";

    public MailListPage(WebDriver driver) {
        super(driver);
    }

    public ContentLetterPage openLetterBySubject(String subject) {
        driver.findElement(By.xpath(String.format(MAIL_WITH_DEFINED_SUBJECT_LOCATOR, subject))).click();
        waitForElementVisible(contentLetterPage.mailAddressee);
        return new ContentLetterPage(driver);
    }

    public ContentLetterPage openLetterWithoutSubject() {
        waitForElementEnabled(mailWithBlankSubject);
        mailWithBlankSubject.click();
        waitForElementVisible(contentLetterPage.mailAddressee);
        return new ContentLetterPage(driver);
    }

    public boolean checkLetterBySubjectIsDisplayed(String subject) {
        if (driver.findElement(By.xpath(String.format(MAIL_WITH_DEFINED_SUBJECT_LOCATOR, subject))).isDisplayed()) {
            return true;
        } else return false;
    }

    public boolean checkLetterPresentInSpamFolder(String subject) {
        if (driver.findElement(By.xpath(String.format(SPAM_LETTER_BY_SUBJECT_LOCATOR, subject))).isDisplayed()) {
            return true;
        } else return false;
    }

    public MailListPage clickLetterCheckbox(String subject) {
        driver.findElement(By.xpath(String.format(LETTER_CHECKBOX_LOCATOR, subject))).click();
        return new MailListPage(driver);
    }

    public MailListPage clickLetterCheckboxInSpamFolder(String subject) {
        driver.findElement(By.xpath(String.format(CHECKBOX_NOSPAM_LOCATOR, subject))).click();
        return new MailListPage(driver);
    }

    public MailListPage deleteDraftLetter(String subject) {
        deleteDraftLetterButton.click();
        waitForElementDisappear(By.xpath(String.format(MAIL_WITH_DEFINED_SUBJECT_LOCATOR, subject)));
        return new MailListPage(driver);
    }

    public MailListPage deleteLetterFromTrash(String subject) {
        deleteLetterFromTrashButton.click();
        waitForElementDisappear(By.xpath(String.format(MAIL_WITH_DEFINED_SUBJECT_LOCATOR, subject)));
        return new MailListPage(driver);
    }

    public MailListPage markLetterAsNoSpam(String subject) {
        noSpamButton.click();
        waitForElementDisappear(By.xpath(String.format(SPAM_LETTER_BY_SUBJECT_LOCATOR, subject)));
        return new MailListPage(driver);
    }
}