package yandexDisk.services;


import yandexDisk.config.GlobalParameters;
import yandexDisk.pages.FileListPage;
import yandexDisk.pages.RightPanelPage;
import yandexDisk.pages.TrashFolder;
import yandexDisk.utils.FilesUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileService {

    public void moveFilesToTrash(List<File> fileList) {
        new FileListPage().selectFiles(fileList).moveFilesToTrash(fileList);
    }

    public boolean isNotificationMessageExpected(String message, String fileName) {
        return new FileListPage().getNotificationMessageAboutMovedFile()
                .equalsIgnoreCase(String.format(message, fileName));
    }

    public void uploadFiles(List<File> fileList) {
        new FileListPage().closePopUpWindow().closeRecentFilesPanel().uploadFiles(fileList);
    }

    public boolean isFileVisibleInFolder(List<File> fileList) {
        return new FileListPage().checkFilesVisibility(fileList).equalsIgnoreCase(GlobalParameters.EMPTY_STRING);
    }

    public boolean isFileVisibleInTrashFolder(List<File> fileList) {
        new FileListPage().openTrashPage();
        return isFileVisibleInFolder(fileList);
    }

    public boolean isFileVisibleInMainFolder(List<File> fileList) {
        new TrashFolder().goToMainPage();
        return isFileVisibleInFolder(fileList);
    }

    public void downloadFiles(List<File> fileList) {
        new RightPanelPage().downloadFiles(fileList);
    }

    public void restoreFile(List<File> fileList) {
        new FileListPage().selectFiles(fileList).rightFilePanelPage.clickRestoreFileButton();
    }

    public void deleteFilesFromTrash(List<File> fileList) {
        new TrashFolder().selectFiles(fileList).rightFilePanelPage.clickDeleteButton();
    }

    public boolean isFileDownloaded(File file) throws IOException {
        return FileUtils.directoryContains(FileUtils.getTempDirectory(), file);
    }

    public boolean isNotificationVisible() {
        return new FileListPage().isNotificationVisible();
    }

    public boolean isDownloadedFileExpected(File fileToBeDownloaded, File actuallyDownloaded) throws IOException {
        return FileUtils.contentEquals(fileToBeDownloaded, actuallyDownloaded);
    }

    public void deleteTempFolderWithTempFiles() {
        new FilesUtils().deleteTempFolder(FilesUtils.folder);
    }

    public List<File> createFileList(int filesQuantity) throws IOException {
        return new FilesUtils().createFiles(filesQuantity);
    }

    public List<File> getFileListForActions(List<File> fileList, int fileQuantity) {
        return new FilesUtils().getFileListForOperations(fileList, fileQuantity);
    }

    public File getFileForDownload(List<File> fileList) {
        return new File(FilesUtils.folder, fileList.get(0).getName());
    }

    public File getDownloadedFile(List<File> fileList) {
        return new File(FileUtils.getTempDirectoryPath(), fileList.get(0).getName());
    }

    public void clearTrashFolder() {
        new TrashFolder().clearTrashFolder();
    }

    public String getFileName(List<File> fileList) {
        return new FilesUtils().getFileName(fileList, 0);
    }
}