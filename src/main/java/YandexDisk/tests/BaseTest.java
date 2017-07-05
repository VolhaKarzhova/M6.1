package YandexDisk.tests;


import YandexDisk.business_objects.User;
import YandexDisk.pages.LoginPage;
import YandexDisk.utils.FilesUtils;
import YandexDisk.utils.WebDriverSingleton;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BaseTest {

    public LoginPage loginPage;
    public List<File> expectedFileList;
    public List<File> oneFileSelectedList;
    public File fileToBeDownloaded;
    public File fileActuallyDownloaded;
    public User user;


    @BeforeClass
    public void setUp() throws IOException {
        expectedFileList = new FilesUtils().createFiles(2);
        oneFileSelectedList = new FilesUtils().getFileListForOperations(expectedFileList, 1);
        fileToBeDownloaded = new File(FilesUtils.folder, expectedFileList.get(0).getName());
        fileActuallyDownloaded = new File(FileUtils.getTempDirectoryPath(),expectedFileList.get(0).getName());
        loginPage = new LoginPage().open();
        user = new User();
    }

    @AfterClass
    public void shutDown() throws IOException {
        WebDriverSingleton.kill();
        new FilesUtils().deleteTempFolder(FilesUtils.folder);
    }
}