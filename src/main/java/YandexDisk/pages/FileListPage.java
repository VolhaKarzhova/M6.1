package YandexDisk.pages;

import YandexDisk.config.GlobalParameters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListPage extends AbstractPage {

    private static final By HEADER_USERNAME_LOCATOR = By.xpath("//span[@class='header__username']");
    private static final By UPLOAD_POPUP_CLOSE_BUTTON_LOCATOR = By.xpath("//a[@data-click-action='dialog.close']");
    private static final By UPLOAD_INPUT_LOCATOR = By.xpath("//input[@type='file']");
    private static final By UPLOAD_DONE_ICON_LOCATOR = By.xpath("//div[contains(@class,'upload__icon_done')]");
    public static final By TRASH_ICON_LOCATOR = By.xpath("//div[contains(@class,'trash')]");
    private static final By CRUMBS_CURRENT_LOCATOR = By.xpath("//span[@class='crumbs__current']");
    private static final By POPUP_CLOSE_BUTTON_LOCATOR = By.xpath("//a[contains(@class,'popup-close')]");
    private static final By HIDE_RECENT_FILES_BUTTON_LOCATOR = By.xpath("//div[contains(@class,'recent-files__hide')]");
    private static final String FILE_LOCATOR = "//div[@title='%s']";
    private static final String UPLOAD_DONE_LOCATOR = "//div[contains(@data-key, '%s')]//div[contains(@class,'done')]";
    public static final By NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR = By.xpath("//div[contains(@class,'notifications__text')]");

    public List<By> setFileList() {
        List<By> locators = new ArrayList<By>();
        locators.add(By.xpath(String.format(FILE_LOCATOR, GlobalParameters.FILE_NAME)));
        locators.add(By.xpath(String.format(FILE_LOCATOR, GlobalParameters.ADDITIONAL_FILE_NAME)));
        return locators;
    }

    //использование только блока if не позволяет отлавливать постоянный exception, пришлось вернуться к блоку try/catch
    public FileListPage closePopUpWindow() {
        try {
            driver.findElement(POPUP_CLOSE_BUTTON_LOCATOR).click();
        } catch (Exception e) {
        }
        return this;
    }

    public String getUserLogin() {
        waitForElementVisible(HEADER_USERNAME_LOCATOR);
        return driver.findElement(HEADER_USERNAME_LOCATOR).getText();
    }

    public FileListPage uploadFile(List<File> fileList) {
        WebElement fileInput = driver.findElement(UPLOAD_INPUT_LOCATOR);
        for (File filePath : fileList) {
            fileInput.sendKeys(filePath.getAbsolutePath());
            //waitForElementVisible(UPLOAD_DONE_ICON_LOCATOR);
            String fileName = filePath.getName();
            waitForElementVisible(By.xpath(String.format(UPLOAD_DONE_LOCATOR, fileName)));
        }
        driver.findElement(UPLOAD_POPUP_CLOSE_BUTTON_LOCATOR).click();
        return this;
    }

    public FileListPage closeRecentFilesPanel() {
        try {
            driver.findElement(HIDE_RECENT_FILES_BUTTON_LOCATOR).click();
        } catch (Exception e) {
        }
        return this;
    }

    public FileListPage moveToTrash(String fileName) {
        waitForElementVisible(TRASH_ICON_LOCATOR);
        waitForElementVisible(By.xpath(String.format(FILE_LOCATOR, fileName)));
        WebElement file = driver.findElement(By.xpath(String.format(FILE_LOCATOR, fileName)));
        WebElement trash = driver.findElement(TRASH_ICON_LOCATOR);
        new Actions(driver).dragAndDrop(file, trash).build().perform();
        waitForElementVisible(NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR);
        return this;
    }

    public FileListPage openTrashPage() {
        waitForElementVisible(TRASH_ICON_LOCATOR);
        new Actions(driver).doubleClick(driver.findElement(TRASH_ICON_LOCATOR)).build().perform();
        waitForElementVisible(CRUMBS_CURRENT_LOCATOR);
        return this;
    }

    public RightFilePanelPage clickFileToSelect(String fileName) {
        driver.findElement(By.xpath(String.format(FILE_LOCATOR, fileName))).click();
        return new RightFilePanelPage();
    }

    public FileListPage selectSeveralFiles(List<By> locators) {
        Actions action = new Actions(driver);
        action.keyDown(Keys.CONTROL);
        WebElement element;
        for (By locator : locators) {
            waitForElementVisible(locator);
            highlightElement(locator);
            element = driver.findElement(locator);
            action.click(element);
            unHighlightElement(locator);
        }
        action.keyUp(Keys.CONTROL).build().perform();
        return this;
    }

    /* public List<File> areUploadedFilesVisible(List<File> fileList) {
         List<File> uploadedListFile = new ArrayList<File>();
         System.out.println("list"+uploadedListFile);
         for (int i = 0; i < fileList.size(); i++) {
             File file = fileList.get(i);
                if (driver.findElement(By.xpath(String.format(FILE_LOCATOR, file.getName()))).isDisplayed()) {
                 uploadedListFile.add(file);
             }
         }
         return uploadedListFile;
     }*/
    public List<Boolean> areUploadedFilesVisible(List<File> fileList) {
        List<Boolean> uploadedListFile = new ArrayList<Boolean>();
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            if (driver.findElement(By.xpath(String.format(FILE_LOCATOR, file.getName()))).isDisplayed()) {
                uploadedListFile.add(true);
            } else uploadedListFile.add(false);
        }
        return uploadedListFile;
    }

    public String getNotificationMessageAboutMovedFile(String fileName) {
        return driver.findElement(NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR).getText();
    }

    public boolean isNotificationVisible() {
        return driver.findElement(NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR).isDisplayed();
    }
}