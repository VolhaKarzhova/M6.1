package pageFactory.tests;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageFactory.businessObjects.Letter;
import pageFactory.config.GlobalParameters;
import pageFactory.pages.*;
import pageFactory.utils.RandomUtils;


public class SpamMailTest extends BaseTest {

    private GlobalParameters globalParameters = new GlobalParameters();
    private RandomUtils randomUtils = new RandomUtils();
    private Letter expectedLetter = new Letter(randomUtils.setAddressee(), randomUtils.setLetterSubject(), randomUtils.setLetterBody());

    @Test(description = "Check the possibility to create new letter and send it")
    public void sendNewMailWithAllFilledInputs() {
        NewLetterPage newLetterPage = new LoginPage(driver).login(globalParameters.getUserLogin(), globalParameters.getUserPassword())
                .clickNewLetterButton();
        String addressee = newLetterPage.fillAllLetterInputs(expectedLetter.getAddressee(), expectedLetter.getSubject(),
                expectedLetter.getBody()).sendMail().getAddresseeFromMessage();
        Assert.assertEquals(addressee, expectedLetter.getAddressee(), "Addressee of the sent letter doesn't match");
    }

    @Test(description = "Check that letter presents in the Sent Folder", dependsOnMethods = "sendNewMailWithAllFilledInputs")
    public void checkLetterContentInSentFolder() {
        LeftMenuPage leftMenuPage = new LeftMenuPage(driver);
        Letter letter1 = leftMenuPage.openSentFolder().openLetterBySubject(expectedLetter.getSubject()).getLetterObject();
        Assert.assertEquals(expectedLetter.toString(), letter1.toString(), "Letter is not in the Sent Folder");
    }

    @Test(description = "Check that letter presents in the Inbox Folder", dependsOnMethods = "checkLetterContentInSentFolder")
    public void checkLetterContentInInboxFolder() {
        LeftMenuPage leftMenuPage = new LeftMenuPage(driver);
        Letter letter2 = leftMenuPage.openInboxFolder().openLetterBySubject(expectedLetter.getSubject()).getLetterObject();
        Assert.assertEquals(expectedLetter.toString(), letter2.toString(), "Letter is not in the Inbox folder");
    }

    @Test(description = "Check spam letter is removed from Inbox folder", dependsOnMethods = "checkLetterContentInInboxFolder")
    public void markLetterAsSpam() {
        boolean letterPresent = new ContentLetterPage(driver).markLetterAsSpam().leftMenuPage.openInboxFromSpamFolder()
                .checkLetterBySubjectIsDisplayed(expectedLetter.getSubject());
        Assert.assertFalse(letterPresent);
    }

    @Test(description = "Check spam letter is in Spam folder", dependsOnMethods = "markLetterAsSpam")
    public void checkLetterInSpamFolder() {
        boolean letterPresent = new LeftMenuPage(driver).openSpamFolder()
                .checkLetterPresentInSpamFolder(expectedLetter.getSubject());
        Assert.assertTrue(letterPresent);
    }

    @Test(description = "Check letter disappeared from Spam Folder", dependsOnMethods = "checkLetterInSpamFolder", expectedExceptions = NoSuchElementException.class)
    public void markLetterAsNoSpam() {
        boolean letterPresent = new MailListPage(driver).clickLetterCheckboxInSpamFolder(expectedLetter.getSubject())
                .markLetterAsNoSpam(expectedLetter.getSubject()).checkLetterPresentInSpamFolder(expectedLetter.getSubject());
        Assert.assertFalse(letterPresent);
    }

    @Test(description = "Check letter disappeared from Spam Folder", dependsOnMethods = "markLetterAsNoSpam")
    public void checkNoSpamLetterReturnedToInbox() {
        boolean letterPresent = new LeftMenuPage(driver).openInboxFolder()
                .checkLetterBySubjectIsDisplayed(expectedLetter.getSubject());
        Assert.assertTrue(letterPresent);
    }
}