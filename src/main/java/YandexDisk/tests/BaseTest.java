package YandexDisk.tests;


import YandexDisk.config.WorkWithFile;
import YandexDisk.pages.LoginPage;
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
    public void setUp() {
        loginPage = new LoginPage().open();
    } @BeforeClass
    public void filesCreation() throws IOException {
        expectedFileList = new WorkWithFile().createFiles(3);
    }

    @AfterClass
    public void sutDown() {
        WebDriverSingleton.kill();
    }
}