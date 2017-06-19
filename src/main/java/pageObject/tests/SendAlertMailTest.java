package pageObject.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.businessObjects.Letter;
import pageObject.config.GlobalParameters;
import pageObject.pages.HeaderMenuPage;
import pageObject.pages.LeftMenuPage;
import pageObject.pages.LoginPage;
import pageObject.pages.NewLetterPage;
import pageObject.utils.RandomUtil;

public class SendAlertMailTest extends BaseTest {

    private GlobalParameters globalParameters = new GlobalParameters();
    private RandomUtil randomUtil = new RandomUtil();
    private Letter letter = new Letter(randomUtil.setAddressee(), GlobalParameters.BLANK_LETTER_SUBJECT_STRING, GlobalParameters.EMPTY_STRING);

    @Test(description = "Check alert message while sending letter without subject and body")
    public void checkAlertWhenSendingLetterWithOnlyAddresseeFilled() {
        NewLetterPage newLetterPage = new LoginPage(driver).login(globalParameters.getUserLogin(), globalParameters.getUserPassword()).clickNewLetterButton();
        String alert = newLetterPage.fillAllLetterInputs(letter.getAddressee(), GlobalParameters.EMPTY_STRING,
                GlobalParameters.EMPTY_STRING).sendMail().newLetterPage.getEmptyLetterBodyAlertMessage();
        Assert.assertEquals(alert, NewLetterPage.ALERT_EMPTY_BODY_MESSAGE, "Alert message doesn't match");
    }

    @Test(description = "Check that sending mail with no subject and body was successful", dependsOnMethods = "checkAlertWhenSendingLetterWithOnlyAddresseeFilled")
    public void sendMailWithBlankSubjectAndBodyInputs() {
        String addressee = new NewLetterPage(driver).clickConfirmButtonOnAlertMessageToSendLetter().getAddresseFromMessage();
        Assert.assertEquals(addressee, letter.getAddressee(), "Addressee of the sent letter doesn't match");
    }

    @Test(description = "Check that letter without Subject and Body presents in the Sent Folder", dependsOnMethods = "sendMailWithBlankSubjectAndBodyInputs")
    public void checkLetterWithOnlyAddresseeFilledInSentFolder() {
        LeftMenuPage leftMenuPage = new LeftMenuPage(driver);
        Letter letter = leftMenuPage.openSentFolder()
                .openLetterWithoutSubject().getLetterObject();
        Assert.assertEquals(letter.toString(), letter.toString(), "Letter is not in the Sent Folder");
    }

    @Test(description = "Check that letter without Subject and Body presents in the Inbox Folder", dependsOnMethods = "checkLetterWithOnlyAddresseeFilledInSentFolder")
    public void checkLetterWithOnlyAddresseeFilledInInboxFolder() {
        LeftMenuPage leftMenuPage = new LeftMenuPage(driver);
        Letter letter = leftMenuPage.openInboxFolder()
                .openLetterWithoutSubject().getLetterObject();
        Assert.assertEquals(letter.toString(), letter.toString(), "Letter is not in the Inbox Folder");
    }

    @Test(description = "Check invalid Addressee alert message", dependsOnMethods = "checkLetterWithOnlyAddresseeFilledInInboxFolder")
    public void sendMailWithInvalidAddressee() {
        NewLetterPage newLetterPage = new HeaderMenuPage(driver).clickNewLetterButton();
        String alert = newLetterPage.fillAllLetterInputs(randomUtil.setInvalidAddressee(), randomUtil.setLetterSubject(),
                randomUtil.setLetterBody()).sendMail().newLetterPage.getInvalidAddresseeAlertMessage();
        System.out.println(alert);
        Assert.assertEquals(alert, NewLetterPage.ALERT_INVALID_ADDRESSEE_MESSAGE, "Text of alert doesn't match");
    }
}