package test;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class MailRuTest extends BaseTest {

    private static final String URL = "https://mail.ru";
    private static final String USER_LOGIN = "volhakarzhova";
    private static final String USER_PASSWORD = "1584624Qwe";
    private static final String ADDRESSEE = "volhakarzhova@mail.ru";
    private static final String MAIL_SUBJECT = "mail number: " + new Random().nextInt(5000);
    private static final String MAIL_BODY = "This is a new email " + new Random().nextInt(5000);
    private String mailSubjectPattern = String.format("//*[@data-subject='%s']", MAIL_SUBJECT);

    @Test
    public void logIn() {
        driver.get(URL);
        driver.findElement(By.id("mailbox__login")).sendKeys(USER_LOGIN);
        driver.findElement(By.cssSelector("#mailbox__password")).sendKeys(USER_PASSWORD);
        driver.findElement(By.className("mailbox__auth__button")).click();
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#PH_user-email")));
        String element = driver.findElement(By.cssSelector("#PH_user-email")).getText();
        Assert.assertTrue(element.startsWith(USER_LOGIN));
    }

    @Test(dependsOnMethods = {"logIn"})
    public void newMailCreation() {
        driver.findElement(By.cssSelector("a[data-name='compose']")).click();
        driver.findElement(By.xpath("//*[@data-original-name='To']")).sendKeys(ADDRESSEE);
        driver.findElement(By.name("Subject")).sendKeys(MAIL_SUBJECT);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@id, 'composeEditor')]")));
        driver.findElement(By.cssSelector("#tinymce")).sendKeys(MAIL_BODY);
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//div[contains(@data-name, 'saveDraft')]")).click();
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[@class='b-toolbar__message']/a"))));
        driver.findElement(By.xpath("//div[@class='b-toolbar__message']/a")).click();
        Assert.assertTrue(driver.findElement(By.xpath(mailSubjectPattern)).isDisplayed());
    }

    @Test(dependsOnMethods = {"newMailCreation"})
    public void verifyDraftMailContent() {
        driver.findElement(By.xpath(mailSubjectPattern)).click();
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-blockid='compose_to']/div")));
        StringBuilder message = new StringBuilder();
        if (!ADDRESSEE.equals(driver.findElement(By.xpath("//div[@data-blockid='compose_to']/div")).getText())) {
            message.append("Addressee of the mail doesn't match");
        }
        if (!MAIL_SUBJECT.equals(driver.findElement(By.xpath("//*[@name='Subject']")).getAttribute("value"))) {
            message.append("Mail subject doesn't match");
        }
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@id, 'composeEditor')]")));
        String mailContent = driver.findElement(By.cssSelector("div[id$='_BODY']")).getText();
        if (!mailContent.contains(MAIL_BODY)) {
            message.append("Body of the mail doesn't match");
        }
        Assert.assertTrue(message.toString().equals(""), message.toString());
    }

    @Test(dependsOnMethods = {"verifyDraftMailContent"}, expectedExceptions = NoSuchElementException.class)
    public void sendMailFromDraftFolder() {
        driver.switchTo().defaultContent();
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-name='send']")));
        driver.findElement(By.xpath("//div[@data-name='send']")).click();
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='message-sent__title']")));
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href$='drafts/']>span")));
        driver.findElement(By.cssSelector("a[href$='drafts/']>span")).click();
        Assert.assertFalse(driver.findElement(By.xpath(mailSubjectPattern)).isDisplayed());
    }

    @Test(dependsOnMethods = {"sendMailFromDraftFolder"})
    public void verifyMailIsInTheSentFolder() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href,'sent')]")));
        driver.findElement(By.xpath("//a[contains(@href,'sent')]")).click();
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(By.xpath(mailSubjectPattern)));
        Assert.assertTrue(driver.findElement(By.xpath(mailSubjectPattern)).isDisplayed());
    }

    @Test(dependsOnMethods = {"verifyMailIsInTheSentFolder"})
    public void logOut() {
        driver.findElement(By.cssSelector("#PH_logoutLink")).click();
        Assert.assertTrue(driver.getCurrentUrl().endsWith("logout"));
    }
}