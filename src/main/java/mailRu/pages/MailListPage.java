package mailRu.pages;


import mailRu.reporting.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MailListPage extends AbstractPage {

    public static final By LETTER_BLOCK_LOCATOR = By.xpath("//div[@data-mnemo='letters']");
    private static final By SPAM_BUTTON_LOCATOR = By.xpath("//div[@data-name='spam']");
    private static final By NO_SPAM_BUTTON_LOCATOR = By.xpath("//div[@data-cache-key='950_undefined_false']//div[@data-name='noSpam']/span");
    private static final By MAIL_WITH_BLANK_SUBJECT_LOCATOR = By.xpath("(//div[@data-cache-key and not(@style)]//a[contains(@class,'item__link') and not(@data-setSubject)])[1]");
    private static final String MAIL_BY_SUBJECT_LOCATOR = "//a[@data-subject='%s']";
    private static final String LETTER_CHECKBOX_LOCATOR = "//*[@data-subject='%s']//div[@class='b-checkbox__box']";
    private static final By CONFIRM_SPAM_BUTTON_LOCATOR = By.xpath("//div[@class='is-confirmSpam_in']//button[contains(@class,'confirm-cancel')]");
    private static final By DELETE_OPTION_IN_CONTEXT_MENU_LOCATOR = By.xpath("//a[@data-name='remove']");

    public ContentLetterPage openLetterBySubject(String subject) {
        Logger.info("Opening letter by the subject " + subject);
        waitForElementEnabled(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
        click(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
        waitForElementVisible(ContentLetterPage.ADDRESSEE_MAIL_LOCATOR);
        return new ContentLetterPage();
    }

    public ContentLetterPage openLetterWithoutSubject() {
        Logger.info("Opening letter without subject");
        waitForElementVisible(MAIL_WITH_BLANK_SUBJECT_LOCATOR);
        click(MAIL_WITH_BLANK_SUBJECT_LOCATOR);
        waitForElementVisible(ContentLetterPage.ADDRESSEE_MAIL_LOCATOR);
        return new ContentLetterPage();
    }

    public boolean isLetterVisible(String subject) {
        Logger.info("Check if letter with subject " + subject + " is visible in current folder");
        try {
            waitForElementVisible(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
            driver.findElement(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject))).isDisplayed();
            return true;
        } catch (TimeoutException exception) {
            Logger.warn("Timeout exception has appeared. It might be expected behavior");
        } catch (NoSuchElementException exception) {
            Logger.warn("NoSuchElement exception has appeared.It might be expected behavior");
        }
        return false;
    }

    public MailListPage clickLetterCheckbox(String subject) {
        Logger.info("Check the check-box of the letter with subject: " + subject);
        waitForElementVisible(By.xpath(String.format(LETTER_CHECKBOX_LOCATOR, subject)));
        click(By.xpath(String.format(LETTER_CHECKBOX_LOCATOR, subject)));
        return new MailListPage();
    }

    public MailListPage deleteLetter(String subject) {
        Logger.info("Deleting the letter with the subject: " + subject);
        WebElement letter = driver.findElement(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
        new Actions(driver).contextClick(letter).build().perform();
        click(DELETE_OPTION_IN_CONTEXT_MENU_LOCATOR);
        waitForElementDisappear(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
        return new MailListPage();
    }

    public MailListPage clickNoSpamButton(String subject) {
        Logger.info("Clicking No spam Button");
        click(NO_SPAM_BUTTON_LOCATOR);
        waitForElementDisappear(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
        return new MailListPage();
    }

    public MailListPage clickSpamButton(String subject) {
        Logger.info("Clicking SPAM button together and further confirmation button");
        click(SPAM_BUTTON_LOCATOR);
        waitForElementEnabled(CONFIRM_SPAM_BUTTON_LOCATOR);
        click(CONFIRM_SPAM_BUTTON_LOCATOR);
        waitForElementDisappear(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
        return new MailListPage();
    }
}