package YandexDisk.tests;

import YandexDisk.config.GlobalParameters;
import YandexDisk.pages.MainPage;
import YandexDisk.pages.RightFilePanelPage;
import com.sun.jna.platform.win32.GL;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class UploadTest extends BaseTest {

    @Test(description = "Check successful login")
    public void login() throws InterruptedException {
        MainPage mainPage = loginPage.login(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD);
        String actualUserLogin = mainPage.getUserLogin();
        Assert.assertEquals(actualUserLogin, GlobalParameters.USER_LOGIN, "UserLogin doesn't match");
    }

    @Test(description = "Check if file upload was successful",dependsOnMethods = "login")
    public void uploadFile() throws InterruptedException {
        MainPage mainPage = new MainPage().uploadFile();
        boolean isFileUploaded = mainPage.isFileVisible(GlobalParameters.FILE_NAME);
        Assert.assertTrue(isFileUploaded, "File doesn't present among recently added files");
    }

    /*@Test(description="Check if file download was successful", dependsOnMethods = "uploadFile")
    public void downloadFile() throws IOException, InterruptedException {
        RightFilePanelPage rightFilePanelPage = new MainPage().markFileCheckbox(GlobalParameters.FILE_NAME);
        Thread.sleep(8000);
        rightFilePanelPage.downloadFile(GlobalParameters.FILE_NAME);
    }*/




}
