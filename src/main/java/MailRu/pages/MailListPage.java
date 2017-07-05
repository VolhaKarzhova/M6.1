package MailRu.pages;


import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class MailListPage extends AbstractPage {

    public static final By LETTER_BLOCK_LOCATOR = By.xpath("//div[@data-mnemo='letters']");
    private static final By SPAM_BUTTON_LOCATOR = By.xpath("//div[@data-name='spam']");
    private static final By NO_SPAM_BUTTON_LOCATOR = By.xpath("//div[@data-cache-key='950_undefined_false']//div[@data-name='noSpam']/span");
    private static final By MAIL_WITH_BLANK_SUBJECT_LOCATOR = By.xpath("(//a[@class='js-href b-datalist__item__link'][not(@data-subject)])[1]");
    private static final String MAIL_BY_SUBJECT_LOCATOR = "//a[@data-subject='%s']";
    private static final String LETTER_CHECKBOX_LOCATOR = "//*[@data-subject='%s']//div[@class='b-checkbox__box']";
    private static final By CONFIRM_SPAM_BUTTON_LOCATOR = By.xpath("//div[@class='is-confirmSpam_in']//button[contains(@class,'confirm-cancel')]");
    private static final By DELETE_OPTION_IN_CONTEXT_MENU_LOCATOR = By.xpath("//a[@data-name='remove']");

    public ContentLetterPage openLetterBySubject(String subject) {
        waitForElementEnabled(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
        driver.findElement(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject))).click();
        waitForElementVisible(ContentLetterPage.ADDRESSEE_MAIL_LOCATOR);
        return new ContentLetterPage();
    }

    public ContentLetterPage openLetterWithoutSubject() {
        waitForElementEnabled(MAIL_WITH_BLANK_SUBJECT_LOCATOR);
        driver.findElement(MAIL_WITH_BLANK_SUBJECT_LOCATOR).click();
        waitForElementVisible(ContentLetterPage.ADDRESSEE_MAIL_LOCATOR);
        return new ContentLetterPage();
    }

    public boolean isLetterVisible(String subject) {
        boolean i = false;
        try {
            waitForElementVisible(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
            driver.findElement(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject))).isDisplayed();
            i = true;
        } catch (TimeoutException exception) {
        } catch (NoSuchElementException exception) {
        }
        return i;
    }

    public MailListPage clickLetterCheckbox(String subject) {
        waitForElementVisible(By.xpath(String.format(LETTER_CHECKBOX_LOCATOR, subject)));
        driver.findElement(By.xpath(String.format(LETTER_CHECKBOX_LOCATOR, subject))).click();
        return new MailListPage();
    }

    public MailListPage deleteLetter(String subject) {
        WebElement letter = driver.findElement(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
        new Actions(driver).contextClick(letter).build().perform();
        driver.findElement(DELETE_OPTION_IN_CONTEXT_MENU_LOCATOR).click();
        waitForElementDisappear(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
        return new MailListPage();
    }

    public MailListPage markLetterAsNoSpam(String subject) {
        driver.findElement(NO_SPAM_BUTTON_LOCATOR).click();
        waitForElementDisappear(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
        return new MailListPage();
    }

    public MailListPage markLetterAsSpam(String subject) {
        driver.findElement(SPAM_BUTTON_LOCATOR).click();
        waitForElementEnabled(CONFIRM_SPAM_BUTTON_LOCATOR);
        driver.findElement(CONFIRM_SPAM_BUTTON_LOCATOR).click();
        waitForElementDisappear(By.xpath(String.format(MAIL_BY_SUBJECT_LOCATOR, subject)));
        return new MailListPage();
    }
}