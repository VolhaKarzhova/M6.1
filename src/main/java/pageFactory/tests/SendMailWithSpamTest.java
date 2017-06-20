package pageFactory.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageFactory.businessObjects.Letter;
import pageFactory.config.GlobalParameters;
import pageFactory.pages.*;
import pageFactory.utils.RandomUtils;
import pageFactory.utils.Utils;

public class SendMailWithSpamTest extends BaseTest {

    private static final String ALERT_EMPTY_BODY_MESSAGE = "Вы уверены, что хотите отправить пустое письмо?";
    private static final String ALERT_INVALID_ADDRESSEE_MESSAGE = "В поле «Кому» указан некорректный адрес получателя.\n" +
            "Исправьте ошибку и отправьте письмо ещё раз.";
    private Letter blankLetter = new Letter(Utils.getAddressee(), GlobalParameters.BLANK_LETTER_SUBJECT_STRING, GlobalParameters.EMPTY_STRING);
    private Letter expectedLetter = new Letter(Utils.getAddressee(), RandomUtils.getLetterSubject(), RandomUtils.getLetterBody());

    @Test(description = "Check the possibility to create new letter and send it")
    public void sendNewMailWithAllFilledInputs() {
        NewLetterPage newLetterPage = new LoginPage(driver).login(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD)
                .clickNewLetterButton();
        String addressee = newLetterPage.fillAllLetterInputs(expectedLetter.getAddressee(), expectedLetter.getSubject(),
                expectedLetter.getBody()).sendMail().getAddresseeFromSuccessfulSendLetterMessage();
        Assert.assertEquals(addressee, expectedLetter.getAddressee(), "Addressee of the sent letter doesn't match");
    }

    @Test(description = "Check that letter presents in the Sent Folder", dependsOnMethods = "sendNewMailWithAllFilledInputs")
    public void isLetterInSentFolder() {
        LeftMenuPage leftMenuPage = new LeftMenuPage(driver);
        Letter actualLetter = leftMenuPage.openSentFolder().openLetterBySubject(expectedLetter.getSubject()).getLetter();
        Assert.assertEquals(actualLetter.toString(), expectedLetter.toString(), "Letter is not in the Sent Folder");
    }

    @Test(description = "Check that letter presents in the Inbox Folder", dependsOnMethods = "isLetterInSentFolder")
    public void isLetterInInboxFolder() {
        LeftMenuPage leftMenuPage = new LeftMenuPage(driver);
        Letter actualLetter = leftMenuPage.openInboxFolder().openLetterBySubject(expectedLetter.getSubject()).getLetter();
        Assert.assertEquals(actualLetter.getAddressee(), expectedLetter.getAddressee(), "Letter is not in the Inbox folder");
    }

    @Test(description = "Check spam letter is removed from Inbox folder", dependsOnMethods = "isLetterInInboxFolder")
    public void markLetterAsSpam() {
        MailListPage mailListPage = new LeftMenuPage(driver).openInboxFolder().clickLetterCheckbox(expectedLetter.getSubject(), 1)
                .markLetterAsSpam(expectedLetter.getSubject());
        boolean isLetterPresent = mailListPage.isLetterVisible(expectedLetter.getSubject());
        Assert.assertFalse(isLetterPresent, "Letter is still in the Inbox Folder");
    }

    @Test(description = "Check spam letter is in Spam folder", dependsOnMethods = "markLetterAsSpam")
    public void isLetterInSpamFolder() {
        boolean isLetterPresent = new LeftMenuPage(driver).openSpamFolder()
                .isLetterVisible(expectedLetter.getSubject());
        Assert.assertTrue(isLetterPresent, "Letter is not in the Spam Folder");
    }

    @Test(description = "Check letter disappeared from Spam Folder", dependsOnMethods = "isLetterInSpamFolder")
    public void markLetterAsNoSpam() {
        boolean isLetterPresent = new MailListPage(driver).clickLetterCheckbox(expectedLetter.getSubject(), 1)
                .markLetterAsNoSpam(expectedLetter.getSubject()).isLetterVisible(expectedLetter.getSubject());
        Assert.assertFalse(isLetterPresent, "Letter is still in the Spam Folder");
    }

    @Test(description = "Check letter disappeared from Spam Folder", dependsOnMethods = "markLetterAsNoSpam")
    public void isNoSpamLetterReturnedToInbox() {
        boolean isLetterPresent = new LeftMenuPage(driver).openInboxFolder()
                .isLetterVisible(expectedLetter.getSubject());
        Assert.assertTrue(isLetterPresent, "Letter is not in the Inbox Folder");
    }

    @Test(description = "Check alert message while sending letter without subject and body", dependsOnMethods = "isNoSpamLetterReturnedToInbox")
    public void isAlertVisibleWhenSendingLetterWithOnlyAddresseeFilled() {
        NewLetterPage newLetterPage = new HeaderMenuPage(driver).clickNewLetterButton();
        newLetterPage.fillAllLetterInputs(blankLetter.getAddressee(), GlobalParameters.EMPTY_STRING,
                GlobalParameters.EMPTY_STRING).sendMail();
        String alert = newLetterPage.getEmptyLetterBodyAlertMessage();
        Assert.assertEquals(alert, ALERT_EMPTY_BODY_MESSAGE, "Alert message doesn't match");
    }

    @Test(description = "Check that sending mail with no subject and body was successful", dependsOnMethods = "isAlertVisibleWhenSendingLetterWithOnlyAddresseeFilled")
    public void sendMailWithBlankSubjectAndBodyInputs() {
        String addressee = new NewLetterPage(driver).confirmSendingLetterOnAlert().getAddresseeFromSuccessfulSendLetterMessage();
        Assert.assertEquals(addressee, blankLetter.getAddressee(), "Addressee of the sent letter doesn't match");
    }

    @Test(description = "Check that letter without Subject and Body presents in the Sent Folder", dependsOnMethods = "sendMailWithBlankSubjectAndBodyInputs")
    public void isLetterWithOnlyAddresseeFilledInSentFolder() {
        //System.out.println(blankLetter.toString());
        Letter actualLetter = new LeftMenuPage(driver).openSentFolder()
                .openLetterWithoutSubject().getLetter();
        //System.out.println(actualLetter.toString());
        Assert.assertEquals(actualLetter.toString(), blankLetter.toString(), "Letter is not in the Sent Folder");
    }

    @Test(description = "Check that letter without Subject and Body presents in the Inbox Folder", dependsOnMethods = "isLetterWithOnlyAddresseeFilledInSentFolder")
    public void isLetterWithOnlyAddresseeFilledInInboxFolder() {
        Letter actualLetter = new LeftMenuPage(driver).openInboxFolder()
                .openLetterWithoutSubject().getLetter();
        Assert.assertEquals(actualLetter.toString(), blankLetter.toString(), "Letter is not in the Inbox Folder");
    }

    @Test(description = "Check invalid Addressee alert message", dependsOnMethods = "isLetterWithOnlyAddresseeFilledInInboxFolder")
    public void sendMailWithInvalidAddressee() {
        NewLetterPage newLetterPage = new HeaderMenuPage(driver).clickNewLetterButton();
        newLetterPage.fillAllLetterInputs(RandomUtils.getInvalidAddressee(), RandomUtils.getLetterSubject(),
                RandomUtils.getLetterBody()).sendMail();
        String alert = newLetterPage.getInvalidAddresseeAlertMessage();
        Assert.assertEquals(alert, ALERT_INVALID_ADDRESSEE_MESSAGE, "Text of alert doesn't match");
    }
}