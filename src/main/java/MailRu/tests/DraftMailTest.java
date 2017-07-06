package MailRu.tests;

import MailRu.business_objects.Letter;
import MailRu.config.GlobalParameters;
import MailRu.pages.LoginPage;
import MailRu.service.AuthorizationService;
import MailRu.service.MailService;
import MailRu.utils.RandomUtils;
import MailRu.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DraftMailTest extends BaseTest {

    private Letter draftLetter = new Letter(Utils.getAddressee(), RandomUtils.getLetterSubject(), RandomUtils.getLetterBody());
    private MailService mailService = new MailService();
    private AuthorizationService authorizationService = new AuthorizationService();

    @Test(description = "Check letter is saved in Draft Folder")
    public void saveDraftLetter() {
        authorizationService.doLogin(LoginTest.VALID_USER_ACCOUNT);
        boolean isLetterVisible = mailService.checkDraftMailIsSavedInDraftFolder(draftLetter);
        Assert.assertTrue(isLetterVisible, "Letter is not in the Draft Folder");
    }

    @Test(description = "Check that deleted letter is not in the Draft Folder", dependsOnMethods = "saveDraftLetter")
    public void deleteDraftLetter() {
        boolean isLetterVisible = mailService.checkDeletedLetterInFolder(draftLetter);
        Assert.assertFalse(isLetterVisible, "Deleted letter is still in the Draft Folder");
    }

    @Test(description = "Check deleted letter presents in Trash", dependsOnMethods = "deleteDraftLetter")
    public void isLetterInDeletedFolder() {
        boolean isLetterVisible = mailService.checkIsLetterPresentInTrashFolder(draftLetter);
        Assert.assertTrue(isLetterVisible, "Deleted letter is not in the Trash Folder");
    }

    @Test(description = "Delete letter permanently", dependsOnMethods = "isLetterInDeletedFolder")
    public void deleteLetterFromDeletedFolder() {
        boolean isLetterVisible = mailService.checkDeletedLetterInFolder(draftLetter);
        Assert.assertFalse(isLetterVisible, "Deleted letter is still i the Trash Folder");
    }
}