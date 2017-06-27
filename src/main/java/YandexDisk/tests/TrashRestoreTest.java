package YandexDisk.tests;

import YandexDisk.config.GlobalParameters;
import YandexDisk.pages.FileListPage;
import YandexDisk.pages.LoginPage;
import YandexDisk.pages.TrashFolder;
import YandexDisk.utils.FilesUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class TrashRestoreTest extends BaseTest {

    private List<File> oneFileSelectedList;
    private static String fileName;
    private static final String FILE_WAS_PERMANENTLY_DELETED_NOTIFICATION_MESSAGE = "Файл «%s» был удален";
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
        boolean isNotificationVisible = new FileListPage()
                .moveFilesToTrash(expectedFileList).isNotificationVisible();
        Assert.assertTrue(isNotificationVisible, "Notification about deleted files doesn't appear");
    }

    @Test(description = "Check that deleted file are in Trash", dependsOnMethods = "deleteSeveralFilesAtOnce")
    public void checkDeletedFilesInMainFolder() {
        String filesVisibility = new FileListPage().checkFilesVisibility(expectedFileList);
        Assert.assertTrue(filesVisibility.contains(String.format(EXPECTED_MESSAGE, fileName)), "Some deleted file are still in the Main Folder");
    }

    @Test(description = "Check that deleted file are in Trash", dependsOnMethods = "checkDeletedFilesInMainFolder")
    public void checkDeletedFilesInTrash() {
        String filesVisibility = new FileListPage().openTrashPage().checkFilesVisibility(expectedFileList);
        Assert.assertEquals(filesVisibility, GlobalParameters.EMPTY_STRING, "Deleted files are not in the Trash");
    }

    @Test(description = "Check permanent delete of file from Trash", dependsOnMethods = "checkDeletedFilesInTrash")
    public void deleteFilePermanentlyFromTrash() {
        String actualMessage = new TrashFolder().selectFiles(oneFileSelectedList)
                .rightFilePanelPage.clickDeleteButton().getNotificationMessageAboutMovedFile();
        Assert.assertEquals(actualMessage, String.format(FILE_WAS_PERMANENTLY_DELETED_NOTIFICATION_MESSAGE, fileName), "Notification message doesn't match");
    }

    @Test(description = "Check that deleted file is not in the Trash Folder", dependsOnMethods = "deleteFilePermanentlyFromTrash")
    public void checkDeletedFileInTrashFolder() {
        String fileVisibility = new FileListPage().checkFilesVisibility(oneFileSelectedList);
        Assert.assertEquals(fileVisibility, String.format(EXPECTED_MESSAGE, fileName), "Deleted File is still in Trash Folder");
    }

    @AfterClass
    public void clearTrash() {
        new TrashFolder().clearTrashFolder();
    }

    @BeforeClass
    public void setFileLists() {
        oneFileSelectedList = new FilesUtils().getFileListForOperations(expectedFileList, 1);
        fileName = new FilesUtils().getFileName(oneFileSelectedList, 0);
    }
}