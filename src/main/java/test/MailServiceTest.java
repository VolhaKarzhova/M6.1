package test;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MailServiceTest extends BaseTest {

    @Test
    public void logIn() {
        driver.get(URL);
        driver.findElement(By.id("mailbox__login")).sendKeys(LOGIN);
        driver.findElement(By.cssSelector("#mailbox__password")).sendKeys(PASSWORD);
        driver.findElement(By.className("mailbox__auth__button")).click();
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.titleContains("Входящие"));
        String element = driver.findElement(By.cssSelector("#PH_user-email")).getText();
        Assert.assertEquals(element, "volhakarzhova@mail.ru");
    }

    @Test(dependsOnMethods = {"logIn"})
    public void newMailCreation() {
        driver.findElement(By.cssSelector("a[data-name='compose']")).click();
        driver.findElement(By.xpath("//*[@data-original-name='To']")).sendKeys(ADDRESSEE);
        driver.findElement(By.name("Subject")).sendKeys(SUBJECT);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@id, 'composeEditor')]")));
        driver.findElement(By.cssSelector("#tinymce")).sendKeys(MAILBODY);
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//div[contains(@data-name, 'saveDraft')]")).click();
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[@class='b-toolbar__message']/a"))));
        driver.findElement(By.xpath("//div[@class='b-toolbar__message']/a")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//*[@data-subject='" + SUBJECT + "']")).isDisplayed());
    }

    @Test(dependsOnMethods = {"newMailCreation"})
    public void verifyDraftMailContent() {
        driver.findElement(By.xpath("//*[@data-subject='" + SUBJECT + "']")).click();
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-blockid='compose_to']/div")));
        String addressee = driver.findElement(By.xpath("//div[@data-blockid='compose_to']/div")).getText();
        Assert.assertEquals(addressee, ADDRESSEE);
        String subject = driver.findElement(By.xpath("//*[@name='Subject']")).getAttribute("value");
        Assert.assertEquals(subject, SUBJECT);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@id, 'composeEditor')]")));
        String mailContent = driver.findElement(By.cssSelector("div[id$='_BODY']")).getText();
        Assert.assertTrue(mailContent.contains(MAILBODY));
    }

    @Test(dependsOnMethods = {"verifyDraftMailContent"})
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
        Assert.assertFalse(driver.findElement(By.xpath("//*[@data-subject='" + SUBJECT + "']")).isDisplayed());
    }

    @Test(dependsOnMethods = {"sendMailFromDraftFolder"})
    public void verifyMailIsInTheSentFolder() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href,'sent')]")));
        driver.findElement(By.xpath("//a[contains(@href,'sent')]")).click();
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-subject='" + SUBJECT + "']")));
        Assert.assertTrue(driver.findElement(By.xpath("//*[@data-subject='" + SUBJECT + "']")).isDisplayed());
    }

    @Test(dependsOnMethods = {"verifyMailIsInTheSentFolder"})
    public void logOut() {
        driver.findElement(By.cssSelector("#PH_logoutLink")).click();
        Assert.assertTrue(driver.getCurrentUrl().endsWith("logout"));
    }
}