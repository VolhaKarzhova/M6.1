package MailRu.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import MailRu.businessObjects.Letter;
import MailRu.config.GlobalParameters;
import MailRu.pages.LeftMenuPage;
import MailRu.pages.LoginPage;
import MailRu.pages.MailListPage;
import MailRu.pages.NewLetterPage;
import MailRu.utils.RandomUtils;
import MailRu.utils.Utils;

public class DraftMailTest extends BaseTest {

    private Letter expectedLetter = new Letter(Utils.getAddressee(), RandomUtils.getLetterSubject(), RandomUtils.getLetterBody());

    @Test(description = "Check letter is saved in Draft Folder")
    public void saveDraftLetter() {
        NewLetterPage newLetterPage = new LoginPage(driver).login(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD)
                .clickNewLetterButton();
        MailListPage mailListPage = newLetterPage.fillAllLetterInputs(expectedLetter.getAddressee(), expectedLetter.getSubject(), expectedLetter.getBody())
                .saveDraftMail().leftMenuPage.openDraftFolder();
        Assert.assertTrue(mailListPage.isLetterVisible(expectedLetter.getSubject()), "Letter is not in the Draft Folder");
    }

    @Test(description = "Check that deleted letter is not in the Draft Folder", dependsOnMethods = "saveDraftLetter")
    public void deleteDraftLetter() {
        boolean isLetterVisible = new MailListPage(driver)
                .deleteLetter(expectedLetter.getSubject()).isLetterVisible(expectedLetter.getSubject());
        Assert.assertFalse(isLetterVisible, "Deleted letter is still in the Draft Folder");
    }

    @Test(description = "Check deleted letter presents in Trash", dependsOnMethods = "deleteDraftLetter")
    public void isLetterInDeletedFolder() {
        boolean isLetterVisible = new LeftMenuPage(driver).openDeletedFolder()
                .isLetterVisible(expectedLetter.getSubject());
        Assert.assertTrue(isLetterVisible, "Deleted letter is not in the Trash Folder");
    }

    @Test(description = "Delete letter permanently", dependsOnMethods = "isLetterInDeletedFolder")
    public void deleteLetterFromDeletedFolder() {
        boolean isLetterVisible = new MailListPage(driver)
                .deleteLetter(expectedLetter.getSubject()).isLetterVisible(expectedLetter.getSubject());
        Assert.assertFalse(isLetterVisible, "Deleted letter is still i the Trash Folder");
    }
}