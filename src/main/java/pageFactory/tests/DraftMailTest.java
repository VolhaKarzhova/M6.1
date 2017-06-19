package pageFactory.tests;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageFactory.businessObjects.Letter;
import pageFactory.config.GlobalParameters;
import pageFactory.pages.*;
import pageFactory.utils.RandomUtils;

public class DraftMailTest extends BaseTest {

    private GlobalParameters globalParameters = new GlobalParameters();
    private RandomUtils randomUtils = new RandomUtils();
    private Letter expectedLetter = new Letter(randomUtils.setAddressee(), randomUtils.setLetterSubject(), randomUtils.setLetterBody());

    @Test(description = "Check letter is saved in Draft Folder")
    public void saveDraftLetter() {
        NewLetterPage newLetterPage = new LoginPage(driver).login(globalParameters.getUserLogin(), globalParameters.getUserPassword())
                .clickNewLetterButton();
        MailListPage mailListPage = newLetterPage.fillAllLetterInputs(expectedLetter.getAddressee(), expectedLetter.getSubject(), expectedLetter.getBody())
                .saveDraftMail().leftMenuPage.openDraftFolder();
        Assert.assertTrue(mailListPage.checkLetterBySubjectIsDisplayed(expectedLetter.getSubject()));
    }

    @Test(description = "Check that deleted letter is not in the Draft Folder", dependsOnMethods = "saveDraftLetter", expectedExceptions = NoSuchElementException.class)
    public void deleteDraftLetter() {
        boolean letterPresent = new MailListPage(driver).clickLetterCheckbox(expectedLetter.getSubject())
                .deleteDraftLetter(expectedLetter.getSubject()).checkLetterBySubjectIsDisplayed(expectedLetter.getSubject());
        Assert.assertFalse(letterPresent);
    }

    @Test(description = "Check deleted letter presents in Trash", dependsOnMethods = "deleteDraftLetter")
    public void checkLetterInDeletedFolder() {
        boolean letterPresent = new LeftMenuPage(driver).openDeletedFolder()
                .checkLetterBySubjectIsDisplayed(expectedLetter.getSubject());
        Assert.assertTrue(letterPresent);
    }

    @Test(description = "Delete letter permanently", dependsOnMethods = "checkLetterInDeletedFolder", expectedExceptions = NoSuchElementException.class)
    public void deleteLetterFromDeletedFolder() {
        boolean letterPresent = new MailListPage(driver).clickLetterCheckbox(expectedLetter.getSubject())
                .deleteLetterFromTrash(expectedLetter.getSubject()).checkLetterBySubjectIsDisplayed(expectedLetter.getSubject());
        Assert.assertFalse(letterPresent);
    }
}