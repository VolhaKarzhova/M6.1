package YandexDisk.pages;

import org.openqa.selenium.By;

public class RightFilePanelPage extends AbstractPage {

    private static final By DOWNLOAD_BUTTON_LOCATOR = By.xpath("//button[@data-click-action='resource.download']");
    private static final By RESTORE_BUTTON_LOCATOR = By.xpath("//button[@data-click-action='resource.restore']");
    private static final By DELETE_BUTTON_LOCATOR = By.xpath("//button[@data-click-action='resource.delete']");
    public static final By RIGHT_FILE_PANEL_LOCATOR = By.xpath("//div[contains(@data-key, 'view=aside')]");
    private static final String URL_TO_DOWNLOAD_FILE_FROM = "https://downloader.disk.yandex.ru";

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
}