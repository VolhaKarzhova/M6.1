package YandexDisk.tests;

import YandexDisk.config.GlobalParameters;
import YandexDisk.pages.FileListPage;
import YandexDisk.pages.LoginPage;
import YandexDisk.pages.TrashFolder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TrashRestoreTest extends BaseTest {

    private String fileName;
    private static final String FILE_WAS_PERMANENTLY_DELETED_NOTIFICATION_MESSAGE = "Файл «" + GlobalParameters.FILE_NAME + "» был удален";
    private static final String FILE_WAS_DELETED_NOTIFICATION_MESSAGE = "Файл «%s» удален в Корзину";
    private static final String FILE_WAS_RESTORED_NOTIFICATION_MESSAGE = "Файл «%s» восстановлен";

    @Test(description = "Check that file is moved to Trash")
    public void deleteFile() {
        FileListPage fileListPage = new LoginPage().login(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD)
                .closePopUpWindow().uploadFile(expectedFileList);
        fileName = fileListPage.setFileNameForFurtherActions(expectedFileList, 0);
        String actualMessage = fileListPage.moveToTrash(fileName)
                .getNotificationMessageAboutMovedFile(fileName);
        System.out.println(actualMessage);
        Assert.assertEquals(actualMessage, String.format(FILE_WAS_DELETED_NOTIFICATION_MESSAGE, fileName),
                "Notification message doesn't match");
    }

    @Test(description = "Check that deleted file is not in the Main Folder anymore", dependsOnMethods = "deleteFile")
    public void isFileRemovedFromMainFolder() {
        boolean isFileVisible = new FileListPage().isFileVisible(fileName);
        Assert.assertFalse(isFileVisible, "Deleted File is still in the Main Folder");
    }

    @Test(description = "Check that deleted file is in the Trash folder", dependsOnMethods = "isFileRemovedFromMainFolder")
    public void isDeletedFileInTrash() {
        boolean isFileVisible = new FileListPage().openTrashPage().isFileVisible(fileName);
        Assert.assertTrue(isFileVisible, "Deleted file is not in the Trash Folder");
    }

    @Test(description = "Restore file from Trash", dependsOnMethods = "isDeletedFileInTrash")
    public void restoreFile() {
        String message = new FileListPage().clickFileToSelect(fileName).clickRestoreFileButton()
                .getNotificationMessageAboutMovedFile(GlobalParameters.FILE_NAME);
        Assert.assertEquals(message, String.format(FILE_WAS_RESTORED_NOTIFICATION_MESSAGE, fileName),
                "Notification message doesn't match");
    }

    @Test(description = "Check that restored file is not in Trash Folder anymore", dependsOnMethods = "restoreFile")
    public void isRestoredFileRemovedFromTrash() {
        boolean isFileVisible = new FileListPage().isFileVisible(fileName);
        Assert.assertFalse(isFileVisible, "Restored file is still in the Trash Folder");
    }

    @Test(description = "Check that restored is in the Main Folder", dependsOnMethods = "isRestoredFileRemovedFromTrash")
    public void isRestoredFileInMainFolder() {
        FileListPage fileListPage = new TrashFolder().goToMainPage();
        boolean isFileVisible = fileListPage.isFileVisible(fileName);
        Assert.assertTrue(isFileVisible, "Restored file is not in the Main Folder");
    }

    @Test(description = "Check several files are removed to Trash", dependsOnMethods = "isRestoredFileInMainFolder")
    public void deleteSeveralFilesAtOnce() {
        boolean isNotificationVisible = new FileListPage().selectFiles(expectedFileList)
                .moveToTrash(GlobalParameters.FILE_NAME).isNotificationVisible();
        Assert.assertTrue(isNotificationVisible, "Notification about deleted files doesn't appear");
    }

    /*@Test(dataProvider = "filesDataProvider", description = "Check that deleted file are in Trash", dependsOnMethods = "deleteSeveralFilesAtOnce")
    @Parameters("fileName")
    public void areDeletedFilesInMainFolder(String fileName) {
        boolean getUploadedFilesList = new FileListPage().getUploadedFilesList(fileName);
        Assert.assertFalse(getUploadedFilesList, "Deleted file " + fileName + " is still in the Main Folder");
    }

    @Test(dataProvider = "filesDataProvider", description = "Check that deleted file are in Trash", dependsOnMethods = "areDeletedFilesInMainFolder")
    @Parameters("fileName")
    public void areDeletedFilesInTrash(String fileName) {
        boolean getUploadedFilesList = new FileListPage().openTrashPage().getUploadedFilesList(fileName);
        Assert.assertTrue(getUploadedFilesList, "Deleted file " + fileName + " is not in the Trash");
    }

    @Test(description = "Check permanent delete of file from Trash", dependsOnMethods = "areDeletedFilesInTrash")
    public void deleteFilePermanentlyFromTrash() {
        String actualMessage = new FileListPage().clickFileToSelect(GlobalParameters.FILE_NAME).clickDeleteButton()
                .getNotificationMessageAboutMovedFile(GlobalParameters.FILE_NAME);
        Assert.assertEquals(actualMessage, FILE_WAS_PERMANENTLY_DELETED_NOTIFICATION_MESSAGE, "Notification message doesn't match");
    }

    @Test(description = "Check that deleted file is not in the Trash Folder", dependsOnMethods = "deleteFilePermanentlyFromTrash")
    public void isDeletedFileInTrashFolder() {
        boolean getUploadedFilesList = new FileListPage().getUploadedFilesList(GlobalParameters.FILE_NAME);
        Assert.assertFalse(getUploadedFilesList, "Deleted File is still in Trash Folder");
    }

    @DataProvider(name = "filesDataProvider")
    public Object[][] filesDataProvider() {
        return new Object[][]{
                {GlobalParameters.FILE_NAME},
                {GlobalParameters.ADDITIONAL_FILE_NAME}
        };
    }*/

    /*@AfterClass
    public void clearTrash() {
        new TrashFolder().clearTrashFolder();
    }*/
}