package mailRu.tests;

import mailRu.business_objects.letter.Letter;
import mailRu.business_objects.letter.LetterBuilder;
import mailRu.config.GlobalParameters;
import mailRu.services.AuthorizationService;
import mailRu.services.MailService;
import mailRu.utils.RandomUtils;
import mailRu.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CommonMailTest extends BaseTest {

    private MailService mailService = new MailService();
    private AuthorizationService authorizationService = new AuthorizationService();
    private Letter receivedBlankLetter = new LetterBuilder(Utils.getAddressee()).setSubject(GlobalParameters.BLANK_LETTER_SUBJECT_STRING)
            .setBody(GlobalParameters.EMPTY_STRING).build();
    private Letter letterWithOnlyAddressee = new LetterBuilder(Utils.getAddressee()).build();
    private Letter letterWithAllFieldsFilled = new LetterBuilder(Utils.getAddressee())
            .setSubject(RandomUtils.getLetterSubject()).setBody(RandomUtils.getLetterBody()).build();
    private Letter letterWithInvalidAddressee = new LetterBuilder(RandomUtils.getInvalidAddressee())
            .setSubject(RandomUtils.getLetterSubject()).setBody(RandomUtils.getLetterBody()).build();
    public static final String ALERT_EMPTY_BODY_MESSAGE = "Вы уверены, что хотите отправить пустое письмо?";
    public static final String ALERT_INVALID_ADDRESSEE_MESSAGE = "В поле «Кому» указан некорректный адрес получателя.\n" +
            "Исправьте ошибку и отправьте письмо ещё раз.";

    @Test(description = "Check the possibility to create new letter and send it")
    public void sendNewMailWithAllFilledInputs() {
        authorizationService.doLogin(LoginTest.VALID_USER_ACCOUNT);
        mailService.sendLetterWithAllFilledInputs(letterWithAllFieldsFilled);
        boolean isAddresseeExpected = mailService.isAddresseeInSuccessfulSendLetterMessageExpected(letterWithAllFieldsFilled);
        Assert.assertTrue(isAddresseeExpected, "Addressee of the sent letter doesn't match");
    }

    @Test(description = "Check that letter presents in the Sent Folder", dependsOnMethods = "sendNewMailWithAllFilledInputs")
    public void checkSentLetterInSentFolder() {
        Letter actualLetter = mailService.getSentLetter(letterWithAllFieldsFilled);
        Assert.assertEquals(actualLetter.toString(), letterWithAllFieldsFilled.toString(), "letter is not in the Sent Folder");
    }

    @Test(description = "Check that letter presents in the Inbox Folder", dependsOnMethods = "checkSentLetterInSentFolder")
    public void checkLetterInInboxFolder() {
        Letter actualLetter = mailService.getReceivedLetter(letterWithAllFieldsFilled);
        Assert.assertEquals(actualLetter.toString(), letterWithAllFieldsFilled.toString(), "letter is not in the Inbox folder");
    }

    @Test(description = "Check spam letter is removed from Inbox folder", dependsOnMethods = "checkLetterInInboxFolder")
    public void markLetterAsSpam() {
        mailService.moveLetterToSpam(letterWithAllFieldsFilled);
        boolean isLetterVisible = mailService.isLetterVisibleInInboxFolder(letterWithAllFieldsFilled);
        Assert.assertFalse(isLetterVisible, "letter is still in the Inbox Folder");
    }

    @Test(description = "Check spam letter is in Spam folder", dependsOnMethods = "markLetterAsSpam")
    public void isLetterInSpamFolder() {
        boolean isLetterVisible = mailService.isLetterVisibleInSpamFolder(letterWithAllFieldsFilled);
        Assert.assertTrue(isLetterVisible, "letter is not in the Spam Folder");
    }

    @Test(description = "Check letter disappeared from Spam Folder", dependsOnMethods = "isLetterInSpamFolder")
    public void markLetterAsNoSpam() {
        mailService.moveLetterFromSpam(letterWithAllFieldsFilled);
        boolean isLetterVisible = mailService.isLetterVisibleInSpamFolder(letterWithAllFieldsFilled);
        Assert.assertFalse(isLetterVisible, "letter is still in the Spam Folder");
    }

    @Test(description = "Check letter disappeared from Spam Folder", dependsOnMethods = "markLetterAsNoSpam")
    public void isNoSpamLetterReturnedToInbox() {
        boolean isLetterVisible = mailService.isLetterVisibleInInboxFolder(letterWithAllFieldsFilled);
        Assert.assertTrue(isLetterVisible, "letter is not in the Inbox Folder");
    }

    @Test(description = "Check alert message while sending letter without setSubject and setBody", dependsOnMethods = "isNoSpamLetterReturnedToInbox")
    public void isExpectedAlertVisibleWhileSendingLetterWithOnlyAddresseeFilled() {
        mailService.sendLetterWithOnlyAddressee(letterWithOnlyAddressee);
        boolean isAlertContentExpected = mailService.isAlertMessageExpectedWhileSendingLetterWithBlankSubject();
        Assert.assertTrue(isAlertContentExpected, "Alert message doesn't match");
    }

    @Test(description = "Check that sending mail with no setSubject and setBody was successful", dependsOnMethods = "isExpectedAlertVisibleWhileSendingLetterWithOnlyAddresseeFilled")
    public void sendMailWithBlankSubjectAndBodyInputs() {
        mailService.confirmSendingLetterOnAlert();
        boolean isAddresseeExpected = mailService.isAddresseeInSuccessfulSendLetterMessageExpected(letterWithOnlyAddressee);
        Assert.assertTrue(isAddresseeExpected, "Addressee of the sent letter doesn't match");
    }

    @Test(description = "Check that letter without Subject and Body presents in the Sent Folder", dependsOnMethods = "sendMailWithBlankSubjectAndBodyInputs")
    public void checkLetterWithOnlyAddresseeFilledInSentFolder() {
        Letter actualLetter = mailService.getSentLetterWithBlankSubject();
        Assert.assertEquals(actualLetter.toString(), receivedBlankLetter.toString(), "letter is not in the Sent Folder");
    }

    @Test(description = "Check that letter without Subject and Body presents in the Inbox Folder", dependsOnMethods = "checkLetterWithOnlyAddresseeFilledInSentFolder")
    public void checkLetterWithOnlyAddresseeFilledInInboxFolder() {
        Letter actualLetter = mailService.getReceivedLetterWithBlankSubject();
        Assert.assertEquals(actualLetter.toString(), receivedBlankLetter.toString(), "letter is not in the Inbox Folder");
    }

    @Test(description = "Check invalid Addressee alert message", dependsOnMethods = "checkLetterWithOnlyAddresseeFilledInInboxFolder")
    public void sendMailWithInvalidAddressee() {
        mailService.sendLetterWithAllFilledInputs(letterWithInvalidAddressee);
        boolean isAlertMessageExpected = mailService.isAlertMessageExpectedWhileSendingLetterWithInvalidAddressee();
        Assert.assertTrue(isAlertMessageExpected, "Text of alert doesn't match");
    }
}