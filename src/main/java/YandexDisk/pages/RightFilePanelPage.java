package YandexDisk.pages;

import YandexDisk.utils.FilesUtils;
import YandexDisk.utils.RandomUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RightFilePanelPage extends AbstractPage {

    private static final By DOWNLOAD_BUTTON_LOCATOR = By.xpath("//button[@data-click-action='resource.download']");
    private static final By RESTORE_BUTTON_LOCATOR = By.xpath("//button[@data-click-action='resource.restore']");
    private static final By DELETE_BUTTON_LOCATOR = By.xpath("//button[@data-click-action='resource.delete']");

    public FileListPage clickRestoreFileButton() {
        driver.findElement(RESTORE_BUTTON_LOCATOR).click();
        waitForElementVisible(FileListPage.NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR);
        return new FileListPage();
    }

    public FileListPage clickDeleteButton() {
        waitForElementVisible(DELETE_BUTTON_LOCATOR);
        driver.findElement(DELETE_BUTTON_LOCATOR).click();
        waitForElementVisible(FileListPage.NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR);
        return new FileListPage();
    }

    public FilesUtils downloadFiles(List<File> fileList) {
        new FileListPage().selectFiles(fileList);
        waitForElementVisible(DOWNLOAD_BUTTON_LOCATOR);
        driver.findElement(DOWNLOAD_BUTTON_LOCATOR).click();
        File filePath = new File("C:/Users/Volha_Karzhova/Downloads");
        for (File file : fileList) {
            file = new File(filePath, file.getName());
            waitForFileDownload(file);
            return new FilesUtils();
        }
        return new FilesUtils();
    }
}