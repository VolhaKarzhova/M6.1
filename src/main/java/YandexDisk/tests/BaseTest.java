package YandexDisk.tests;


import YandexDisk.business_objects.User;
import YandexDisk.pages.LoginPage;
import YandexDisk.services.FileService;
import YandexDisk.utils.WebDriverSingleton;
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
    private FileService fileService;


    @BeforeClass
    public void setUp() throws IOException {
        fileService = new FileService();
        expectedFileList = fileService.createFileList(2);
        oneFileSelectedList = fileService.getFileListForActions(expectedFileList, 1);
        fileToBeDownloaded = fileService.getFileForDownload(expectedFileList);
        fileActuallyDownloaded = fileService.getDownloadedFile(expectedFileList);
        loginPage = new LoginPage().open();
        user = new User();
    }

    @AfterClass
    public void shutDown() throws IOException {
        WebDriverSingleton.kill();
        fileService.deleteTempFolderWithTempFiles();
    }
}