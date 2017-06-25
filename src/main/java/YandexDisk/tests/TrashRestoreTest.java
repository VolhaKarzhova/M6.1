package YandexDisk.tests;

import YandexDisk.config.GlobalParameters;
import YandexDisk.pages.FileListPage;
import YandexDisk.pages.LoginPage;
import YandexDisk.pages.TrashFolder;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class TrashRestoreTest extends BaseTest {

    private static final String FILE_WAS_PERMANENTLY_DELETED_NOTIFICATION_MESSAGE = "Файл «" + GlobalParameters.FILE_NAME + "» был удален";
    private static final String FILE_WAS_DELETED_NOTIFICATION_MESSAGE = "Файл «" + GlobalParameters.FILE_NAME + "» удален в Корзину";
    private static final String FILE_WAS_RESTORED_NOTIFICATION_MESSAGE = "Файл «" + GlobalParameters.FILE_NAME + "» восстановлен";

    @Test(description = "Check that file is moved to Trash")
    public void deleteFile() {
        FileListPage fileListPage = new LoginPage().login(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD)
                .closePopUpWindow().closeRecentFilesPanel();
        String actualMessage = fileListPage.moveToTrash(GlobalParameters.FILE_NAME)
                .getNotificationMessageAboutMovedFile(GlobalParameters.FILE_NAME);
        Assert.assertEquals(actualMessage, FILE_WAS_DELETED_NOTIFICATION_MESSAGE, "Notification message doesn't match");
    }

    /*@Test(description = "Check that deleted file is not in the Main Folder anymore", dependsOnMethods = "deleteFile")
    public void isFileRemovedFromMainFolder() {
        boolean areUploadedFilesVisible = new FileListPage().areUploadedFilesVisible(GlobalParameters.FILE_NAME);
        Assert.assertFalse(areUploadedFilesVisible, "Deleted File is still in the Main Folder");
    }

    @Test(description = "Check that deleted file is in the Trash folder", dependsOnMethods = "isFileRemovedFromMainFolder")
    public void isDeletedFileInTrash() {
        boolean areUploadedFilesVisible = new FileListPage().openTrashPage().areUploadedFilesVisible(GlobalParameters.FILE_NAME);
        Assert.assertTrue(areUploadedFilesVisible, "Deleted file is not in the Trash Folder");
    }

    @Test(description = "Restore file from Trash", dependsOnMethods = "isDeletedFileInTrash")
    public void restoreFile() {
        String message = new FileListPage().clickFileToSelect(GlobalParameters.FILE_NAME).clickRestoreFileButton()
                .getNotificationMessageAboutMovedFile(GlobalParameters.FILE_NAME);
        Assert.assertEquals(message, FILE_WAS_RESTORED_NOTIFICATION_MESSAGE, "Notification message doesn't match");
    }

    @Test(description = "Check that restored file is not in Trash Folder anymore", dependsOnMethods = "restoreFile")
    public void isRestoredFileRemovedFromTrash() {
        boolean areUploadedFilesVisible = new FileListPage().areUploadedFilesVisible(GlobalParameters.FILE_NAME);
        Assert.assertFalse(areUploadedFilesVisible, "Restored file is still in the Trash Folder");
    }

    @Test(description = "Check that restored is in the Main Folder", dependsOnMethods = "isRestoredFileRemovedFromTrash")
    public void isRestoredFileInMainFolder() {
        boolean areUploadedFilesVisible = new FileListPage().goToMainPage().areUploadedFilesVisible(GlobalParameters.FILE_NAME);
        Assert.assertTrue(areUploadedFilesVisible, "Restored file is not in the Main Folder");
    }

    @Test(description = "Check several files are removed to Trash", dependsOnMethods = "isRestoredFileInMainFolder")
    public void deleteSeveralFilesAtOnce() {
        List<By> fileList = new FileListPage().setFileList();
        boolean isNotificationVisible = new FileListPage().selectSeveralFiles(fileList)
                .moveToTrash(GlobalParameters.FILE_NAME).isNotificationVisible();
        Assert.assertTrue(isNotificationVisible, "Notification about deleted files doesn't appear");
    }

    @Test(dataProvider = "filesDataProvider", description = "Check that deleted file are in Trash", dependsOnMethods = "deleteSeveralFilesAtOnce")
    @Parameters("fileName")
    public void areDeletedFilesInMainFolder(String fileName) {
        boolean areUploadedFilesVisible = new FileListPage().areUploadedFilesVisible(fileName);
        Assert.assertFalse(areUploadedFilesVisible, "Deleted file " + fileName + " is still in the Main Folder");
    }

    @Test(dataProvider = "filesDataProvider", description = "Check that deleted file are in Trash", dependsOnMethods = "areDeletedFilesInMainFolder")
    @Parameters("fileName")
    public void areDeletedFilesInTrash(String fileName) {
        boolean areUploadedFilesVisible = new FileListPage().openTrashPage().areUploadedFilesVisible(fileName);
        Assert.assertTrue(areUploadedFilesVisible, "Deleted file " + fileName + " is not in the Trash");
    }

    @Test(description = "Check permanent delete of file from Trash", dependsOnMethods = "areDeletedFilesInTrash")
    public void deleteFilePermanentlyFromTrash() {
        String actualMessage = new FileListPage().clickFileToSelect(GlobalParameters.FILE_NAME).clickDeleteButton()
                .getNotificationMessageAboutMovedFile(GlobalParameters.FILE_NAME);
        Assert.assertEquals(actualMessage, FILE_WAS_PERMANENTLY_DELETED_NOTIFICATION_MESSAGE, "Notification message doesn't match");
    }

    @Test(description = "Check that deleted file is not in the Trash Folder", dependsOnMethods = "deleteFilePermanentlyFromTrash")
    public void isDeletedFileInTrashFolder() {
        boolean areUploadedFilesVisible = new FileListPage().areUploadedFilesVisible(GlobalParameters.FILE_NAME);
        Assert.assertFalse(areUploadedFilesVisible, "Deleted File is still in Trash Folder");
    }

    @DataProvider(name = "filesDataProvider")
    public Object[][] filesDataProvider() {
        return new Object[][]{
                {GlobalParameters.FILE_NAME},
                {GlobalParameters.ADDITIONAL_FILE_NAME}
        };
    }*/

    @AfterClass
    public void clearTrash() {
        new TrashFolder().clearTrashFolder();
    }
}