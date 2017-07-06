package YandexDisk.tests;

import YandexDisk.config.GlobalParameters;
import YandexDisk.pages.FileListPage;
import YandexDisk.pages.RightFilePanelPage;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class DownloadUploadTest extends BaseTest {

    @Test(description = "Check successful doLogin")
    public void login() {
        FileListPage fileListPage = loginPage.login(user.getLogin(), user.getPassword());
        String actualUserLogin = fileListPage.getUserLogin();
        Assert.assertEquals(actualUserLogin, user.getLogin(), "UserLogin doesn't match");
    }

    @Test(description = "Check if file upload was successful", dependsOnMethods = "login")
    public void uploadFiles() {
        FileListPage fileListPage = new FileListPage().closePopUpWindow().closeRecentFilesPanel().uploadFiles(expectedFileList);
        String checkFileUpload = fileListPage.checkFilesVisibility(expectedFileList);
        Assert.assertEquals(checkFileUpload, GlobalParameters.EMPTY_STRING, "Not all the files were uploaded.");
    }

    @Test(description = "Check that downloaded file exists", dependsOnMethods = "uploadFiles")
    public void downloadFile() throws IOException {
        FileListPage fileListPage = new RightFilePanelPage().downloadFiles(oneFileSelectedList);
        Assert.assertTrue(FileUtils.directoryContains(FileUtils.getTempDirectory(), fileActuallyDownloaded),
                "File " + fileActuallyDownloaded.getName() + " is not downloaded to the directory: " + FileUtils.getTempDirectoryPath());
    }

    @Test(description = "Check the content of the downloaded file", dependsOnMethods = "downloadFile")
    public void checkContentOfDownloadedFile() throws IOException {
        Assert.assertTrue(FileUtils.contentEquals(fileToBeDownloaded, fileActuallyDownloaded),
                "The content of file " + fileActuallyDownloaded.getName() + " doesn't match the content of previously uploaded file.");
    }
}