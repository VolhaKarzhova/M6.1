package YandexDisk.pages;

import org.openqa.selenium.By;

public class FooterNotificationsPage extends AbstractPage {

    public static final By NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR = By.xpath("//div[contains(@class,'notifications__text')]");

    public String getNotificationMessageAboutDeletedFile(String fileName) {
        return driver.findElement(NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR).getText();
    }

    public String getNotificationMessageAboutRestoredFile(String fileName) {
        return driver.findElement(NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR).getText();
    }
}


