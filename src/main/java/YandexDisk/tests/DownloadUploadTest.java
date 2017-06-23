package YandexDisk.tests;

import YandexDisk.config.GlobalParameters;
import YandexDisk.pages.FileListPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class DownloadUploadTest extends BaseTest {

    @Test(description = "Check successful login")
    public void login() {
        FileListPage fileListPage = loginPage.login(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD);
        String actualUserLogin = fileListPage.closePopUpWindow().getUserLogin();
        Assert.assertEquals(actualUserLogin, GlobalParameters.USER_LOGIN, "UserLogin doesn't match");
    }

    @Test(dataProvider = "filesDataProvider", description = "Check if file upload was successful", dependsOnMethods = "login")
    @Parameters({"filePath", "fileName"})
    public void uploadFiles(String filePath, String fileName) {
        FileListPage fileListPage = new FileListPage().closeRecentFilesPanel().uploadFile(filePath);
        boolean isFileUploaded = fileListPage.isFileVisible(fileName);
        Assert.assertTrue(isFileUploaded, "File doesn't present in the main folder");
    }

    @Test(description = "Check that uploaded files are in the main Folder", dependsOnMethods = "uploadFiles")
    public void downloadFile() {

    }

    @DataProvider(name = "filesDataProvider")
    public Object[][] filesDataProvider() {
        return new Object[][]{
                {GlobalParameters.FILE_PATH_TO_UPLOAD, GlobalParameters.FILE_NAME},
                {GlobalParameters.ADDITIONAL_FILE_PATH_TO_UPLOAD, GlobalParameters.ADDITIONAL_FILE_NAME}
        };
    }
}