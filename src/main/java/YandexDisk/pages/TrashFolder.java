package YandexDisk.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TrashFolder extends FileListPage {

    private static final By CLEAR_TRASH_BUTTON_LOCATOR = By.xpath("//div[@class='toolset__buttons']//button");
    private static final By ACCEPT_BUTTON_LOCATOR = By.xpath("//button[contains(@class,'confirmation-accept')]");

    public FileListPage goToMainPage() {
        clickDiscCrumbs();
        waitForElementVisible(FileListPage.TRASH_ICON_LOCATOR);
        return new FileListPage();
    }

    public TrashFolder clearTrashFolder() {
        WebElement clearButton = driver.findElement(CLEAR_TRASH_BUTTON_LOCATOR);
        if (isElementVisible(CLEAR_TRASH_BUTTON_LOCATOR)) {
            clearButton.click();
            driver.findElement(ACCEPT_BUTTON_LOCATOR).click();
        }
        return this;
    }
}