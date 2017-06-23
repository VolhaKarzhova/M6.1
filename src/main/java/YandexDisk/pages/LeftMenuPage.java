package YandexDisk.pages;


import org.openqa.selenium.By;

public class LeftMenuPage extends AbstractPage {

    private static final By TRASH_FOLDER_LOCATOR = By.xpath("//a[@ href='/client/trash']");

    public FileListPage openTrashFolder() {
        waitForElementVisible(TRASH_FOLDER_LOCATOR);
        driver.findElement(TRASH_FOLDER_LOCATOR).click();
        return new FileListPage();
    }
}