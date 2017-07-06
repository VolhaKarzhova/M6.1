package MailRu.tests;

import MailRu.business_objects.Letter;
import MailRu.config.GlobalParameters;
import MailRu.pages.NewLetterPage;
import MailRu.service.AuthorizationService;
import MailRu.service.MailService;
import MailRu.utils.RandomUtils;
import MailRu.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SendMailWithSpamTest extends BaseTest {

    public static final String ALERT_EMPTY_BODY_MESSAGE = "Вы уверены, что хотите отправить пустое письмо?";
    public static final String ALERT_INVALID_ADDRESSEE_MESSAGE = "В поле «Кому» указан некорректный адрес получателя.\n" +
            "Исправьте ошибку и отправьте письмо ещё раз.";
    private static Letter receivedBlankLetter = new Letter(Utils.getAddressee(), GlobalParameters.BLANK_LETTER_SUBJECT_STRING, GlobalParameters.EMPTY_STRING);
    private static Letter letterWithOnlyAddressee = new Letter(Utils.getAddressee(), GlobalParameters.EMPTY_STRING, GlobalParameters.EMPTY_STRING);
    public static Letter letterWithAllFieldsFilled = new Letter(Utils.getAddressee(), RandomUtils.getLetterSubject(), RandomUtils.getLetterBody());
    private static Letter letterWithInvalidAddressee = new Letter(RandomUtils.getInvalidAddressee(), RandomUtils.getLetterSubject(),
            RandomUtils.getLetterBody());
    private MailService mailService = new MailService();
    private AuthorizationService authorizationService = new AuthorizationService();

    @Test(description = "Check the possibility to create new letter and send it")
    public void sendNewMailWithAllFilledInputs() {
        authorizationService.doLogin(LoginTest.VALID_USER_ACCOUNT);
        boolean doesAddresseeMatch = mailService.checkAddresseeFromSuccessfulSendLetterMessage(letterWithAllFieldsFilled);
        Assert.assertTrue(doesAddresseeMatch, "Addressee of the sent letter doesn't match");
    }

    @Test(description = "Check that letter presents in the Sent Folder", dependsOnMethods = "sendNewMailWithAllFilledInputs")
    public void checkSentLetterInSentFolder() {
        Letter actualLetter = mailService.getSentLetter(letterWithAllFieldsFilled);
        Assert.assertEquals(actualLetter.toString(), letterWithAllFieldsFilled.toString(), "Letter is not in the Sent Folder");
    }

    @Test(description = "Check that letter presents in the Inbox Folder", dependsOnMethods = "checkSentLetterInSentFolder")
    public void checkLetterInInboxFolder() {
        Letter actualLetter = mailService.getReceivedLetter(letterWithAllFieldsFilled);
        Assert.assertEquals(actualLetter.toString(), letterWithAllFieldsFilled.toString(), "Letter is not in the Inbox folder");
    }

    @Test(description = "Check spam letter is removed from Inbox folder", dependsOnMethods = "checkLetterInInboxFolder")
    public void markLetterAsSpam() {
        boolean isLetterPresent = mailService.moveLetterToSpam(letterWithAllFieldsFilled);
        Assert.assertFalse(isLetterPresent, "Letter is still in the Inbox Folder");
    }

    @Test(description = "Check spam letter is in Spam folder", dependsOnMethods = "markLetterAsSpam")
    public void isLetterInSpamFolder() {
        boolean isLetterPresent = mailService.checkIsLetterPresentInSpamFolder(letterWithAllFieldsFilled);
        Assert.assertTrue(isLetterPresent, "Letter is not in the Spam Folder");
    }

    @Test(description = "Check letter disappeared from Spam Folder", dependsOnMethods = "isLetterInSpamFolder")
    public void markLetterAsNoSpam() {
        boolean isLetterPresent = mailService.moveLetterFromSpam(letterWithAllFieldsFilled);
        Assert.assertFalse(isLetterPresent, "Letter is still in the Spam Folder");
    }

    @Test(description = "Check letter disappeared from Spam Folder", dependsOnMethods = "markLetterAsNoSpam")
    public void isNoSpamLetterReturnedToInbox() {
        boolean isLetterPresent = mailService.checkIsLetterPresentInInboxFolder(letterWithAllFieldsFilled);
        Assert.assertTrue(isLetterPresent, "Letter is not in the Inbox Folder");
    }

    @Test(description = "Check alert message while sending letter without subject and body", dependsOnMethods = "isNoSpamLetterReturnedToInbox")
    public void isAlertVisibleWhenSendingLetterWithOnlyAddresseeFilled() {
        boolean isAlertContentExpected = mailService.checkAlertMessageWhileSendingLetterWithBlankSubject(letterWithOnlyAddressee);
        Assert.assertTrue(isAlertContentExpected,  "Alert message doesn't match");
    }

    @Test(description = "Check that sending mail with no subject and body was successful", dependsOnMethods = "isAlertVisibleWhenSendingLetterWithOnlyAddresseeFilled")
    public void sendMailWithBlankSubjectAndBodyInputs() {
        boolean doesAddresseeMatch = mailService.checkAddresseeAfterSendingLetterWithBlankSubject(letterWithOnlyAddressee);
        Assert.assertTrue(doesAddresseeMatch, "Addressee of the sent letter doesn't match");
    }

    @Test(description = "Check that letter without Subject and Body presents in the Sent Folder", dependsOnMethods = "sendMailWithBlankSubjectAndBodyInputs")
    public void checkLetterWithOnlyAddresseeFilledInSentFolder() {
        Letter actualLetter = mailService.getSentLetterWithBlankSubject();
        Assert.assertEquals(actualLetter.toString(), receivedBlankLetter.toString(), "Letter is not in the Sent Folder");
    }

    @Test(description = "Check that letter without Subject and Body presents in the Inbox Folder", dependsOnMethods = "checkLetterWithOnlyAddresseeFilledInSentFolder")
    public void isLetterWithOnlyAddresseeFilledInInboxFolder() {
        Letter actualLetter = mailService.getReceivedLetterWithBlankSubject();
        Assert.assertEquals(actualLetter.toString(), receivedBlankLetter.toString(), "Letter is not in the Inbox Folder");
    }

    @Test(description = "Check invalid Addressee alert message", dependsOnMethods = "isLetterWithOnlyAddresseeFilledInInboxFolder")
    public void sendMailWithInvalidAddressee() {
        boolean doesAlertMessageMatch = mailService.checkAlertMessageWhileSendingLetterWithInvalidAddressee(letterWithInvalidAddressee);
        Assert.assertTrue(doesAlertMessageMatch, "Text of alert doesn't match");
    }
}