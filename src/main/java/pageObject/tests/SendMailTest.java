package pageObject.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.pages.*;

public class SendMailTest extends BaseTest {

    @Test
    public void sendNewMailWithAllFilledInputs() {
        new LoginPage(driver).login(GlobalParametersPage.USER_LOGIN, GlobalParametersPage.USER_PASSWORD);
        new HeaderMenuPage(driver).clickNewLetterButton();
        new NewLetterPage(driver).fillAddresseeInput(GlobalParametersPage.ADDRESSEE).fillSubjectInput(GlobalParametersPage.MAIL_SUBJECT)
                .fillMailBodyInput(GlobalParametersPage.MAIL_BODY);
        new HeaderMenuPage(driver).sendMail();
        new LeftMenuPage(driver).openSentFolder();
        System.out.println("123");
        StringBuilder message = new StringBuilder();
        if (!driver.findElement(By.xpath(GlobalParametersPage.MAIL_SUBLECT_PATTERN)).isDisplayed()) {
            message.append("Mail is not in the Sent Folder");
        }
        new LeftMenuPage(driver).openInboxFolder();
        System.out.println("856");
        if (!driver.findElement(By.xpath(GlobalParametersPage.MAIL_SUBLECT_PATTERN)).isDisplayed()) {
            message.append("Mail is not in the Inbox Folder");
        }
        Assert.assertTrue(message.toString().equals(""), message.toString());
    }
}