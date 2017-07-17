package yandexDisk.tests;

import yandexDisk.services.AuthorizationService;
import yandexDisk.services.FileService;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class DownloadUploadTest extends BaseTest {

    private AuthorizationService authorizationService = new AuthorizationService();
    private FileService fileService = new FileService();

    @Test(description = "Check successful doLogin")
    public void login() {
        authorizationService.doLogin(user);
        boolean doesUserLoginMatchExpected = authorizationService.isUserLoginAfterAuthorizationExpected(user);
        Assert.assertTrue(doesUserLoginMatchExpected, "UserLogin doesn't match");
    }

    @Test(description = "Check if file upload was successful", dependsOnMethods = "login")
    public void uploadFiles() {
        fileService.uploadFiles(expectedFileList);
        boolean areAllFilesUploaded = fileService.isFileVisibleInFolder(expectedFileList);
        Assert.assertTrue(areAllFilesUploaded, "Not all the files were uploaded.");
    }

    @Test(description = "Check that downloaded file exists", dependsOnMethods = "uploadFiles")
    public void downloadFile() throws IOException {
        fileService.downloadFiles(oneFileSelectedList);
        boolean isFileDownloaded = fileService.isFileDownloaded(fileActuallyDownloaded);
        Assert.assertTrue(isFileDownloaded, "File " + fileActuallyDownloaded.getName()
                + " is not downloaded to the directory: " + FileUtils.getTempDirectoryPath());
    }

    @Test(description = "Check the content of the downloaded file", dependsOnMethods = "downloadFile")
    public void checkContentOfDownloadedFile() throws IOException {
        boolean doesDownloadedFileMatchExpected = fileService.isDownloadedFileExpected(fileToBeDownloaded, fileActuallyDownloaded);
        Assert.assertTrue(doesDownloadedFileMatchExpected, "The content of file "
                + fileActuallyDownloaded.getName() + " doesn't match the content of previously uploaded file.");
    }
}