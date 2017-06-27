package YandexDisk.tests;


import YandexDisk.pages.LoginPage;
import YandexDisk.utils.FilesUtils;
import YandexDisk.utils.WebDriverSingleton;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BaseTest {

    public LoginPage loginPage;
    public List<File> expectedFileList;

    @BeforeClass
    public void setUp() throws IOException {
        expectedFileList = new FilesUtils().createFiles(2);
        loginPage = new LoginPage().open();
    }

    @AfterClass
    public void shutDown() throws IOException {
        WebDriverSingleton.kill();
    }
}