package YandexDisk.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class TrashFolderPage extends AbstractPage {

    public static final By CRUMBS_CURRENT_LOCATOR = By.xpath("//span[@class='crumbs__current']");
    public static final By CRUMBS_MAIN_DISK_LOCATOR = By.xpath("(//a[@id='/disk'])[2]");
    public static final String DELETED_FILE_LOCATOR = "//div[@title='%s']";

    public boolean isFileVisible(String fileName) {
        try {
            driver.findElement(By.xpath(String.format(DELETED_FILE_LOCATOR, fileName)));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
    public RightFilePanelPage clickFileToSelect (String fileName) {
        driver.findElement(By.xpath(String.format(DELETED_FILE_LOCATOR, fileName))).click();
        return new RightFilePanelPage();
    }

    public MainPage goToMainPage () {
        driver.findElement(CRUMBS_MAIN_DISK_LOCATOR).click();
        waitForElementVisible(MainPage.TRASH_ICON_LOCATOR);
        return new MainPage();
    }
}