package YandexDisk.pages;

import YandexDisk.utils.FilesUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.util.List;

public class FileListPage extends AbstractPage {

    public RightPanelPage rightFilePanelPage = new RightPanelPage();

    private static final By HEADER_USERNAME_LOCATOR = By.xpath("//span[@class='header__username']");
    private static final By UPLOAD_POPUP_CLOSE_BUTTON_LOCATOR = By.xpath("//a[@data-click-action='dialog.close']");
    private static final By UPLOAD_INPUT_LOCATOR = By.xpath("//input[@type='file']");
    public static final By TRASH_ICON_LOCATOR = By.xpath("//div[contains(@class,'trash')]");
    private static final By CRUMBS_CURRENT_LOCATOR = By.xpath("//span[@class='crumbs__current']");
    private static final By POPUP_CLOSE_BUTTON_LOCATOR = By.xpath("//a[contains(@class,'popup-close')]");
    private static final By HIDE_RECENT_FILES_BUTTON_LOCATOR = By.xpath("//div[contains(@class,'recent-files__hide')]");
    private static final String FILE_LOCATOR = "//div[@title='%s']";
    private static final String UPLOAD_DONE_LOCATOR = "//div[contains(@data-key, '%s')]//div[contains(@class,'done')]";
    public static final By NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR = By.xpath("//div[contains(@class,'notifications__text')]");

    public String getUserLogin() {
        waitForElementVisible(HEADER_USERNAME_LOCATOR);
        return driver.findElement(HEADER_USERNAME_LOCATOR).getText();
    }

    public FileListPage closePopUpWindow() {
        if (isElementVisible(POPUP_CLOSE_BUTTON_LOCATOR)) {
            driver.findElement(POPUP_CLOSE_BUTTON_LOCATOR).click();
        }
        return this;
    }

    public FileListPage uploadFiles(List<File> fileList) {
        WebElement fileInput = driver.findElement(UPLOAD_INPUT_LOCATOR);
        for (File file : fileList) {
            fileInput.sendKeys(file.getAbsolutePath());
            String fileName = file.getName();
            waitForElementVisible(By.xpath(String.format(UPLOAD_DONE_LOCATOR, fileName)));
        }
        driver.findElement(UPLOAD_POPUP_CLOSE_BUTTON_LOCATOR).click();
        return this;
    }

    public FileListPage closeRecentFilesPanel() {
        if (isElementVisible(HIDE_RECENT_FILES_BUTTON_LOCATOR)) {
            driver.findElement(HIDE_RECENT_FILES_BUTTON_LOCATOR).click();
        }
        return this;
    }

    public String checkFilesVisibility(List<File> fileList) {
        pageReload();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < fileList.size(); i++) {
            String fileName = new FilesUtils().getFileName(fileList, i);
            if (!isElementVisible(By.xpath(String.format(FILE_LOCATOR, fileName)))) {
                stringBuilder.append("File " + fileName + " is not visible in Folder.");
            }
        }
        return stringBuilder.toString();
    }

    public FileListPage moveFilesToTrash(List<File> fileList) {
        Actions action = new Actions(driver);
        String fileName = new FilesUtils().getFileName(fileList, 0);
        WebElement file = driver.findElement(By.xpath(String.format(FILE_LOCATOR, fileName)));
        WebElement trash = driver.findElement(TRASH_ICON_LOCATOR);
        for (int i = 0; i < 5; i++) {
            try {
                action.dragAndDrop(file, trash).build().perform();
            } catch (StaleElementReferenceException e) {
            }
            waitForElementVisible(NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR);
        }
        return this;
    }

    public FileListPage openTrashPage() {
        waitForElementVisible(TRASH_ICON_LOCATOR);
        new Actions(driver).doubleClick(driver.findElement(TRASH_ICON_LOCATOR)).build().perform();
        waitForElementVisible(CRUMBS_CURRENT_LOCATOR);
        return this;
    }

    public FileListPage selectFiles(List<File> fileList) {
        closeRecentFilesPanel();
        Actions action = new Actions(driver);
        action.keyDown(Keys.CONTROL);
        WebElement element;
        for (int i = 0; i < fileList.size(); i++) {
            String fileName = new FilesUtils().getFileName(fileList, i);
            waitForElementVisible(By.xpath(String.format(FILE_LOCATOR, fileName)));
            highlightElement(By.xpath(String.format(FILE_LOCATOR, fileName)));
            element = driver.findElement(By.xpath(String.format(FILE_LOCATOR, fileName)));
            action.click(element);
            unHighlightElement(By.xpath(String.format(FILE_LOCATOR, fileName)));
        }
        action.keyUp(Keys.CONTROL).build().perform();
        return this;
    }

    public String getNotificationMessageAboutMovedFile() {
        return driver.findElement(NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR).getText();
    }

    public boolean isNotificationVisible() {
        return isElementVisible(NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR);
    }
}