package YandexDisk.tests;

import YandexDisk.config.GlobalParameters;
import YandexDisk.pages.FileListPage;
import YandexDisk.pages.LoginPage;
import YandexDisk.pages.TrashFolder;
import YandexDisk.utils.FilesUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class TrashRestoreTest extends BaseTest {

    private List<File> oneFileSelectedList;
    private static String fileName;

    //private static final String FILE_WAS_PERMANENTLY_DELETED_NOTIFICATION_MESSAGE = "Файл «" + GlobalParameters.FILE_NAME + "» был удален";
    private static final String FILE_WAS_DELETED_NOTIFICATION_MESSAGE = "Файл «%s» удален в Корзину";
    private static final String FILE_WAS_RESTORED_NOTIFICATION_MESSAGE = "Файл «%s» восстановлен";
    private static final String EXPECTED_MESSAGE = "File %s is not visible in Folder.";

    @Test(description = "Check that file is moved to Trash")
    public void deleteOneFile() {
        FileListPage fileListPage = new LoginPage().login(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD)
                .closePopUpWindow().uploadFiles(expectedFileList);
        String actualMessage = fileListPage.moveFilesToTrash(oneFileSelectedList)
                .getNotificationMessageAboutMovedFile();
        Assert.assertEquals(actualMessage, String.format(FILE_WAS_DELETED_NOTIFICATION_MESSAGE, fileName),
                "Notification message doesn't match");
    }

    @Test(description = "Check that deleted file is not in the Main Folder anymore", dependsOnMethods = "deleteOneFile")
    public void checkFileRemovedFromMainFolder() {
        String fileVisibility = new FileListPage().checkFilesVisibility(oneFileSelectedList);
        Assert.assertEquals(fileVisibility, String.format(EXPECTED_MESSAGE, fileName), "Deleted File is still in the Main Folder");
    }

    @Test(description = "Check that deleted file is in the Trash folder", dependsOnMethods = "checkFileRemovedFromMainFolder")
    public void checkDeletedFileInTrash() {
        String fileVisibility = new FileListPage().openTrashPage().checkFilesVisibility(oneFileSelectedList);
        Assert.assertEquals(fileVisibility, GlobalParameters.EMPTY_STRING, "Deleted file is not in the Trash Folder");
    }

    @Test(description = "Restore file from Trash", dependsOnMethods = "checkDeletedFileInTrash")
    public void restoreFile() {
        FileListPage fileListPage = new FileListPage().selectFiles(oneFileSelectedList)
                .rightFilePanelPage.clickRestoreFileButton();
        String message = fileListPage.getNotificationMessageAboutMovedFile();
        Assert.assertEquals(message, String.format(FILE_WAS_RESTORED_NOTIFICATION_MESSAGE, fileName),
                "Notification message doesn't match");
    }

    @Test(description = "Check that restored file is not in Trash Folder anymore", dependsOnMethods = "restoreFile")
    public void checkRestoredFileRemovedFromTrash() {
        String fileVisibility = new FileListPage().checkFilesVisibility(oneFileSelectedList);
        Assert.assertEquals(fileVisibility, String.format(EXPECTED_MESSAGE, fileName), "Restored file is still in the Trash Folder");
    }

    @Test(description = "Check that restored is in the Main Folder", dependsOnMethods = "checkRestoredFileRemovedFromTrash")
    public void checkRestoredFileInMainFolder() {
        FileListPage fileListPage = new TrashFolder().goToMainPage();
        String fileVisibility = fileListPage.checkFilesVisibility(oneFileSelectedList);
        Assert.assertEquals(fileVisibility, GlobalParameters.EMPTY_STRING, "Restored file is not in the Main Folder");
    }

    @Test(description = "Check several files are removed to Trash", dependsOnMethods = "checkRestoredFileInMainFolder")
    public void deleteSeveralFilesAtOnce() {
        boolean isNotificationVisible = new FileListPage().selectFiles(expectedFileList)
                .moveFilesToTrash(expectedFileList).isNotificationVisible();
        Assert.assertTrue(isNotificationVisible, "Notification about deleted files doesn't appear");
    }

    /*@Test(dataProvider = "filesDataProvider", description = "Check that deleted file are in Trash", dependsOnMethods = "deleteSeveralFilesAtOnce")
    @Parameters("fileName")
    public void areDeletedFilesInMainFolder(String fileName) {
        boolean checkFilesVisibility = new FileListPage().checkFilesVisibility(fileName);
        Assert.assertFalse(checkFilesVisibility, "Deleted file " + fileName + " is still in the Main Folder");
    }

    @Test(dataProvider = "filesDataProvider", description = "Check that deleted file are in Trash", dependsOnMethods = "areDeletedFilesInMainFolder")
    @Parameters("fileName")
    public void areDeletedFilesInTrash(String fileName) {
        boolean checkFilesVisibility = new FileListPage().openTrashPage().checkFilesVisibility(fileName);
        Assert.assertTrue(checkFilesVisibility, "Deleted file " + fileName + " is not in the Trash");
    }

    @Test(description = "Check permanent delete of file from Trash", dependsOnMethods = "areDeletedFilesInTrash")
    public void deleteFilePermanentlyFromTrash() {
        String actualMessage = new FileListPage().clickFileToSelect(GlobalParameters.FILE_NAME).clickDeleteButton()
                .getNotificationMessageAboutMovedFile(GlobalParameters.FILE_NAME);
        Assert.assertEquals(actualMessage, FILE_WAS_PERMANENTLY_DELETED_NOTIFICATION_MESSAGE, "Notification message doesn't match");
    }

    @Test(description = "Check that deleted file is not in the Trash Folder", dependsOnMethods = "deleteFilePermanentlyFromTrash")
    public void isDeletedFileInTrashFolder() {
        boolean checkFilesVisibility = new FileListPage().checkFilesVisibility(GlobalParameters.FILE_NAME);
        Assert.assertFalse(checkFilesVisibility, "Deleted File is still in Trash Folder");
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
    @BeforeClass
    public void setFileLists() {
        oneFileSelectedList = new FilesUtils().getFileListForOperations(expectedFileList, 1);
        fileName = new FilesUtils().getFileName(oneFileSelectedList, 0);
    }
}
