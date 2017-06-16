package pageObject.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.config.GlobalParameters;
import pageObject.objects.Letter;
import pageObject.pages.LeftMenuPage;
import pageObject.pages.LoginPage;
import pageObject.pages.NewLetterPage;

public class SendMailTest extends BaseTest {

    @Test(description = "Check the possibility to create new letter and send it")
    public void sendNewMailWithAllFilledInputs() {
        NewLetterPage newLetterPage = new LoginPage(driver).login(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD)
                .clickNewLetterButton();
        newLetterPage.fillAddresseeInput(Letter.ADDRESSEE).fillSubjectInput(Letter.MAIL_SUBJECT)
                .fillMailBodyInput(Letter.MAIL_BODY).sendMail();
        LeftMenuPage leftMenuPage = new LeftMenuPage(driver);
        Letter letter1 = leftMenuPage.openSentFolder().clickToOpenMailLetter().createLetterObject();
        Letter letter2 = leftMenuPage.openInboxFolder().clickToOpenMailLetter().createLetterObject();
        Assert.assertTrue(letter.checkEqualLetters(letter1) && letter.checkEqualLetters(letter2),
                "At least one letter in the folder doesn't match sent letter");
    }

    @Test(description = "Check that sending mail with no subject and body is successful",priority = 1)
    public void sendMailWithBlankSubjectAndBodyInputs () {

    }

    @Test(description = "Check error message while sending mail with invalid addressee",priority = 2)
    public void sendMailWithInvalidAddresse () {
        //NewLetterPage newLetterPage = new LoginPage(driver).login(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD)
                //.clickNewLetterButton();
       // newLetterPage.fillAddresseeInput(Letter.INVALID_ADDRESSEE).
    }


}