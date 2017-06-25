package YandexDisk.tests;

import YandexDisk.config.GlobalParameters;
import YandexDisk.config.WorkWithFile;
import YandexDisk.pages.FileListPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class DownloadUploadTest extends BaseTest {

    private static List<File> expectedFileList;
    private static List<Boolean> actualFileList;

    @Test(description = "Check successful login")
    public void login() {
        FileListPage fileListPage = loginPage.login(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD);
        String actualUserLogin = fileListPage.closePopUpWindow().getUserLogin();
        Assert.assertEquals(actualUserLogin, GlobalParameters.USER_LOGIN, "UserLogin doesn't match");
    }

    @Test(description = "Check if file upload was successful", dependsOnMethods = "login")
    public void uploadFiles() {
        FileListPage fileListPage = new FileListPage().closeRecentFilesPanel().uploadFile(expectedFileList);
        actualFileList = fileListPage.areUploadedFilesVisible(expectedFileList);
        Assert.assertFalse(actualFileList.contains(false), "Not every file was uploaded");
    }

    @Test(description = "Check that uploaded files are in the main Folder", dependsOnMethods = "uploadFiles")
    public void downloadFile() {
    }

    @BeforeClass
    public void filesCreation() throws IOException {
        expectedFileList = new WorkWithFile().createFiles(3);
    }
}