package YandexDisk.tests;

import YandexDisk.config.GlobalParameters;
import YandexDisk.pages.MainPage;
import YandexDisk.pages.TrashFolderPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TrashRestoreTest extends BaseTest {

    private static final String FILE_WAS_DELETED_NOTIFICATION_MESSAGE = "Файл «" + GlobalParameters.FILE_NAME + "» удален в Корзину";
    private static final String FILE_WAS_RESTORED_NOTIFICATION_MESSAGE = "Файл «" + GlobalParameters.FILE_NAME + "» восстановлен";

    @Test(description = "Check successful login")
    public void login() throws InterruptedException {
        MainPage mainPage = loginPage.login(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD);
        String actualUserLogin = mainPage.getUserLogin();
        Assert.assertEquals(actualUserLogin, GlobalParameters.USER_LOGIN, "UserLogin doesn't match");
    }

    @Test(description = "Check if file upload was successful", dependsOnMethods = "login")
    public void uploadFile() throws InterruptedException {
        MainPage mainPage = new MainPage().uploadFile();
        boolean isFileUploaded = mainPage.isFileVisible(GlobalParameters.FILE_NAME);
        Assert.assertTrue(isFileUploaded, "File doesn't present among recently added files");
    }

    @Test(description = "Check that file is moved to Trash", dependsOnMethods = "uploadFile")
    public void deleteFile() throws InterruptedException {
        String actualMessage = new MainPage().moveToTrash(GlobalParameters.FILE_NAME)
                .getNotificationMessageAboutDeletedFile(GlobalParameters.FILE_NAME);
        Assert.assertEquals(actualMessage, FILE_WAS_DELETED_NOTIFICATION_MESSAGE, "Notification message doesn't match");
    }

    @Test(description = "Check that deleted file is not in the Main Folder anymore", dependsOnMethods = "deleteFile")
    public void isFileRemovedFromMainFolder() {
        boolean isFileVisible = new MainPage().isFileVisible(GlobalParameters.FILE_NAME);
        Assert.assertFalse(isFileVisible, "Deleted File is still in the Main Folder");
    }

    @Test(description = "Check that deleted file is in the Trash folder", dependsOnMethods = "isFileRemovedFromMainFolder")
    public void isDeletedFileInTrash() {
        boolean isFileVisible = new MainPage().openTrashPage().isFileVisible(GlobalParameters.FILE_NAME);
        Assert.assertTrue(isFileVisible, "Deleted file is not in the Trash Folder");
    }

    @Test(description = "Restore file from Trash", dependsOnMethods = "isDeletedFileInTrash")
    public void restoreFile() {
        String message = new TrashFolderPage().clickFileToSelect(GlobalParameters.FILE_NAME).clickRestoreFileButton()
                .getNotificationMessageAboutRestoredFile(GlobalParameters.FILE_NAME);
        Assert.assertEquals(message, FILE_WAS_RESTORED_NOTIFICATION_MESSAGE, "Notification message doesn't match");
    }

    @Test(description = "Check that redtored file is not in Trash Folder anymore", dependsOnMethods = "restoreFile")
    public void isRestoredFileRemovedFromTrash () {
        boolean isFileVisible = new TrashFolderPage().isFileVisible(GlobalParameters.FILE_NAME);
        Assert.assertFalse(isFileVisible, "Restored file is still in the Trash Folder");
    }

    @Test(description = "Check that restored is in the Main Folder", dependsOnMethods = "isRestoredFileRemovedFromTrash")
    public void isRestoredFileIsInMainFolder () {
        boolean isFileVisible = new TrashFolderPage().goToMainPage().isFileVisible(GlobalParameters.FILE_NAME);
        Assert.assertTrue(isFileVisible,"Restored file is not in the Main Folder");
    }
}