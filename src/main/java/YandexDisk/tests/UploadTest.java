package YandexDisk.tests;

import YandexDisk.config.GlobalParameters;
import YandexDisk.pages.FileListPage;
import YandexDisk.pages.RightFilePanelPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class UploadTest extends BaseTest {

    @Test(description = "Check successful login")
    public void login() {
        FileListPage fileListPage = loginPage.login(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD);
        String actualUserLogin = fileListPage.getUserLogin();
        Assert.assertEquals(actualUserLogin, GlobalParameters.USER_LOGIN, "UserLogin doesn't match");
    }

    @Test(description = "Check if file upload was successful", dependsOnMethods = "login")
    public void uploadFiles() {
        FileListPage fileListPage = new FileListPage().closePopUpWindow().closeRecentFilesPanel().uploadFiles(expectedFileList);
        String checkFileUpload = fileListPage.checkFilesVisibility(expectedFileList);
        Assert.assertEquals(checkFileUpload, GlobalParameters.EMPTY_STRING, "Not all the files were uploaded");
    }

    @Test(description = "Check downloaded file", dependsOnMethods = "uploadFiles")
    public void downloadFile() throws IOException {
        new RightFilePanelPage().downloadFiles(oneFileSelectedList);
    }
}