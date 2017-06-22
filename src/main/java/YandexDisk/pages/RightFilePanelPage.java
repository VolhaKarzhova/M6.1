package YandexDisk.pages;

import YandexDisk.config.GlobalParameters;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class RightFilePanelPage extends AbstractPage {

    private static final By DOWNLOAD_BUTTON_LOCATOR = By.xpath("//button[@data-click-action='resource.download']");
    private static final By RESTORE_BUTTON_LOCATOR = By.xpath("//button[@data-click-action='resource.restore']");
    public static final By RIGHT_FILE_PANEL_LOCATOR = By.xpath("//div[contains(@data-key, 'view=aside')]");
    public static final String URL_TO_DOWNLOAD_FILE_FROM = "https://downloader.disk.yandex.ru";

    public RightFilePanelPage downloadFile(String fileName) throws IOException {
        //waitForElementVisible(DOWNLOAD_BUTTON_LOCATOR);
        //driver.findElement(DOWNLOAD_BUTTON_LOCATOR).click();
        URL website = new URL(URL_TO_DOWNLOAD_FILE_FROM);
        FileUtils.copyURLToFile(website, new File(GlobalParameters.FILE_PATH_TO_DOWNLOAD));
        return this;
    }

    public FooterNotificationsPage clickRestoreFileButton() {
        driver.findElement(RESTORE_BUTTON_LOCATOR).click();
        waitForElementVisible(FooterNotificationsPage.NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR);
        return new FooterNotificationsPage();
    }
}