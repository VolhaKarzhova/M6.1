package mailRu.tests;

import mailRu.business_objects.Letter;
import mailRu.services.AuthorizationService;
import mailRu.services.LetterService;
import mailRu.services.MailService;
import mailRu.utils.RandomUtils;
import mailRu.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DraftMailTest extends BaseTest {

    private MailService mailService = new MailService();
    private AuthorizationService authorizationService = new AuthorizationService();
    private LetterService letterService = new LetterService();
    private Letter draftLetter = letterService.createLetterWithAllFilledInputs();

    @Test(description = "Check letter is saved in Draft Folder")
    public void saveDraftLetter() {
        authorizationService.doLogin(LoginTest.VALID_USER_ACCOUNT);
        mailService.saveDraftLetter(draftLetter);
        boolean isLetterVisible = mailService.isLetterVisibleInDraftFolder(draftLetter);
        Assert.assertTrue(isLetterVisible, "Letter is not in the Draft Folder");
    }

    @Test(description = "Check that deleted letter is not in the Draft Folder", dependsOnMethods = "saveDraftLetter")
    public void deleteDraftLetter() {
        mailService.deleteLetter(draftLetter);
        boolean isLetterVisible = mailService.isLetterVisibleInDraftFolder(draftLetter);
        Assert.assertFalse(isLetterVisible, "Deleted letter is still in the Draft Folder");
    }

    @Test(description = "Check deleted letter presents in Trash", dependsOnMethods = "deleteDraftLetter")
    public void isLetterInDeletedFolder() {
        boolean isLetterVisible = mailService.isLetterVisibleInTrashFolder(draftLetter);
        Assert.assertTrue(isLetterVisible, "Deleted letter is not in the Trash Folder");
    }

    @Test(description = "Delete letter permanently", dependsOnMethods = "isLetterInDeletedFolder")
    public void deleteLetterFromDeletedFolder() {
        mailService.deleteLetter(draftLetter);
        boolean isLetterVisible = mailService.isLetterVisibleInTrashFolder(draftLetter);
        Assert.assertFalse(isLetterVisible, "Deleted letter is still i the Trash Folder");
    }
}