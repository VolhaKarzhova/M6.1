package MailRu.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import MailRu.business_objects.Letter;

public class ContentLetterPage extends AbstractPage {

    public static final By ADDRESSEE_MAIL_LOCATOR = By.xpath("//span[@class='b-letter__head__addrs__value']/span");
    private static final By SUBJECT_MAIL_LOCATOR = By.xpath("//div[@class='b-letter__head__subj__text']");
    private static final By BODY_MAIL_LOCATOR = By.xpath("//div[contains(@id, 'BODY')]");

    public Letter getLetter() {
        String addressee = driver.findElement(ADDRESSEE_MAIL_LOCATOR).getText();
        String subject = driver.findElement(SUBJECT_MAIL_LOCATOR).getText();
        String body = driver.findElement(BODY_MAIL_LOCATOR).getText();
        return new Letter(addressee, subject, body);
    }
}