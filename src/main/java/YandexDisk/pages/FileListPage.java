package YandexDisk.pages;

import YandexDisk.config.GlobalParameters;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

public class FileListPage extends AbstractPage {

    private static final By HEADER_USERNAME_LOCATOR = By.xpath("//span[@class='header__username']");
    private static final By UPLOAD_POPUP_CLOSE_BUTTON_LOCATOR = By.xpath("//a[@data-click-action='dialog.close']");
    private static final By UPLOAD_INPUT_LOCATOR = By.xpath("//input[@type='file']");
    private static final By UPLOAD_DONE_ICON_LOCATOR = By.xpath("//div[contains(@class,'upload__icon_done')]");
    private static final By TRASH_ICON_LOCATOR = By.xpath("//div[contains(@class,'trash')]");
    private static final By CRUMBS_CURRENT_LOCATOR = By.xpath("//span[@class='crumbs__current']");
    private static final By POPUP_CLOSE_BUTTON_LOCATOR = By.xpath("//a[contains(@class,'popup-close')]");
    private static final By CRUMBS_MAIN_DISK_LOCATOR = By.xpath("(//a[@id='/disk'])[2]");
    private static final By CLEAR_TRASH_BUTTON_LOCATOR = By.xpath("//div[@class='toolset__buttons']//button");
    private static final By HIDE_RECENT_FILES_BUTTON_LOCATOR = By.xpath("//div[contains(@class,'recent-files__hide')]");
    private static final By ACCEPT_BUTTON_LOCATOR = By.xpath("//button[contains(@class,'confirmation-accept')]");
    private static final String FILE_LOCATOR = "//div[@title='%s']";

    public List<By> setFileList() {
        List<By> locators = new ArrayList<By>();
        locators.add(By.xpath(String.format(FILE_LOCATOR, GlobalParameters.FILE_NAME)));
        locators.add(By.xpath(String.format(FILE_LOCATOR, GlobalParameters.ADDITIONAL_FILE_NAME)));
        return locators;
    }

    public FileListPage closePopUpWindow() {
        try {
            driver.findElement(POPUP_CLOSE_BUTTON_LOCATOR).click();
        } catch (Exception e) {
            System.out.println("There was no pop-up window after login");
        }
        return this;
    }

    public String getUserLogin() {
        waitForElementVisible(HEADER_USERNAME_LOCATOR);
        return driver.findElement(HEADER_USERNAME_LOCATOR).getText();
    }

    public FileListPage uploadFile(String filePath) {
        WebElement fileInput = driver.findElement(UPLOAD_INPUT_LOCATOR);
        fileInput.sendKeys(filePath);
        waitForElementVisible(UPLOAD_DONE_ICON_LOCATOR);
        driver.findElement(UPLOAD_POPUP_CLOSE_BUTTON_LOCATOR).click();
        return this;
    }

    public FileListPage closeRecentFilesPanel() {
        try {
            driver.findElement(HIDE_RECENT_FILES_BUTTON_LOCATOR).click();
        } catch (NoSuchElementException e) {
        }
        return this;
    }

    public boolean isFileVisible(String fileName) {
        try {
            driver.findElement(By.xpath(String.format(FILE_LOCATOR, fileName)));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public FooterNotificationsPage moveToTrash(String fileName) {
        waitForElementVisible(TRASH_ICON_LOCATOR);
        waitForElementVisible(By.xpath(String.format(FILE_LOCATOR, fileName)));
        WebElement file = driver.findElement(By.xpath(String.format(FILE_LOCATOR, fileName)));
        WebElement trash = driver.findElement(TRASH_ICON_LOCATOR);
        new Actions(driver).dragAndDrop(file, trash).build().perform();
        waitForElementVisible(FooterNotificationsPage.NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR);
        return new FooterNotificationsPage();
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

    public FileListPage goToMainPage() {
        JavascriptExecutor script = (JavascriptExecutor) driver;
        script.executeScript("document.getElementById('/disk').click()");
        waitForElementVisible(FileListPage.TRASH_ICON_LOCATOR);
        return this;
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

    public void clearTrashFolder() {
        try {
            driver.findElement(CLEAR_TRASH_BUTTON_LOCATOR).click();
            driver.findElement(ACCEPT_BUTTON_LOCATOR).click();
        } catch (NoSuchElementException e) {
            System.out.println("Trash is already clear");
        }
    }
}