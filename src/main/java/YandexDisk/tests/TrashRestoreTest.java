package YandexDisk.tests;

import YandexDisk.services.AuthorizationService;
import YandexDisk.services.FileService;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TrashRestoreTest extends BaseTest {

    private static String fileName;
    private static final String FILE_WAS_PERMANENTLY_DELETED_NOTIFICATION_MESSAGE = "Файл «%s» был удален";
    private static final String FILE_WAS_DELETED_NOTIFICATION_MESSAGE = "Файл «%s» удален в Корзину";
    private static final String FILE_WAS_RESTORED_NOTIFICATION_MESSAGE = "Файл «%s» восстановлен";
    public static final String EXPECTED_MESSAGE = "File %s is not visible in Folder.";
    private AuthorizationService authorizationService = new AuthorizationService();
    private FileService fileService = new FileService();

    @Test(description = "Check that file is moved to Trash")
    public void deleteOneFile() {
        authorizationService.doLogin(user);
        fileService.uploadFiles(expectedFileList);
        fileService.moveFilesToTrash(oneFileSelectedList);
        boolean doesMessageMatchExpected = fileService.doesNotificationMessageMatchExpected(FILE_WAS_DELETED_NOTIFICATION_MESSAGE, fileName);
        Assert.assertTrue(doesMessageMatchExpected, "Notification message doesn't match");
    }

    @Test(description = "Check that deleted file is not in the Main Folder anymore", dependsOnMethods = "deleteOneFile")
    public void checkFileRemovedFromMainFolder() {
        boolean isFileVisible = fileService.areFilesVisibleInFolder(oneFileSelectedList);
        Assert.assertFalse(isFileVisible, "Deleted File is still in the Main Folder");
    }

    @Test(description = "Check that deleted file is in the Trash folder", dependsOnMethods = "checkFileRemovedFromMainFolder")
    public void checkDeletedFileInTrash() {
        boolean isFileVisible = fileService.areFilesVisibleInTrashFolder(oneFileSelectedList);
        Assert.assertTrue(isFileVisible, "Deleted file is not in the Trash Folder");
    }

    @Test(description = "Restore file from Trash", dependsOnMethods = "checkDeletedFileInTrash")
    public void restoreFile() {
        fileService.restoreFile(oneFileSelectedList);
        boolean doesMessageMatchExpected = fileService.doesNotificationMessageMatchExpected(FILE_WAS_RESTORED_NOTIFICATION_MESSAGE, fileName);
        Assert.assertTrue(doesMessageMatchExpected, "Notification message doesn't match");
    }

    @Test(description = "Check that restored file is not in Trash Folder anymore", dependsOnMethods = "restoreFile")
    public void checkRestoredFileRemovedFromTrash() {
        boolean isFileVisible = fileService.areFilesVisibleInFolder(oneFileSelectedList);
        Assert.assertFalse(isFileVisible, "Restored file is still in the Trash Folder");
    }

    @Test(description = "Check that restored is in the Main Folder", dependsOnMethods = "checkRestoredFileRemovedFromTrash")
    public void checkRestoredFileInMainFolder() {
        boolean isFileVisible = fileService.areFilesVisibleInMainFolder(oneFileSelectedList);
        Assert.assertTrue(isFileVisible, "Restored file is not in the Main Folder");
    }

    @Test(description = "Check several files are removed to Trash", dependsOnMethods = "checkRestoredFileInMainFolder")
    public void deleteSeveralFilesAtOnce() {
        fileService.moveFilesToTrash(expectedFileList);
        boolean isNotificationVisible = fileService.doesNotificationAppear();
        Assert.assertTrue(isNotificationVisible, "Notification about deleted files doesn't appear");
    }

    @Test(description = "Check that deleted file are in Trash", dependsOnMethods = "deleteSeveralFilesAtOnce")
    public void checkDeletedFilesInMainFolder() {
        boolean areFilesVisible = fileService.areFilesVisibleInFolder(expectedFileList);
        Assert.assertFalse(areFilesVisible, "Some deleted file are still in the Main Folder");
    }

    @Test(description = "Check that deleted file are in Trash", dependsOnMethods = "checkDeletedFilesInMainFolder")
    public void checkDeletedFilesInTrash() {
        boolean areFilesVisible = fileService.areFilesVisibleInTrashFolder(expectedFileList);
        Assert.assertTrue(areFilesVisible, "Deleted files are not in the Trash");
    }

    @Test(description = "Check permanent delete of file from Trash", dependsOnMethods = "checkDeletedFilesInTrash")
    public void deleteFilePermanentlyFromTrash() {
        fileService.deleteFilesFromTrash(oneFileSelectedList);
        boolean doesNotificationMessageMatchExpected = fileService.doesNotificationMessageMatchExpected(FILE_WAS_PERMANENTLY_DELETED_NOTIFICATION_MESSAGE, fileName);
        Assert.assertTrue(doesNotificationMessageMatchExpected, "Notification message doesn't match");
    }

    @Test(description = "Check that deleted file is not in the Trash Folder", dependsOnMethods = "deleteFilePermanentlyFromTrash")
    public void checkDeletedFileInTrashFolder() {
        boolean isFileVisible = fileService.areFilesVisibleInFolder(oneFileSelectedList);
        Assert.assertFalse(isFileVisible, "Deleted File is still in Trash Folder");
    }

    @AfterClass
    public void clearTrash() {
        fileService.clearTrashFolder();
    }

    @BeforeClass
    public void setFileName() {
        fileName = fileService.getFileName(oneFileSelectedList);
    }
}