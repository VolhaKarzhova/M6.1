package YandexDisk.pages;

import YandexDisk.config.GlobalParameters;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MainPage extends AbstractPage {

    private static final By HEADER_USERNAME_LOCATOR = By.xpath("//span[@class='header__username']");

    private static final By UPLOAD_POPUP_CLOSE_BUTTON_LOCATOR = By.xpath("//a[@data-click-action='dialog.close']");
    private static final By UPLOAD_INPUT_LOCATOR = By.xpath("//input[@type='file']");
    private static final String UPLOADED_FILE_ICON_LOCATOR = "//div[contains(@class,'resources')]//div[@title='%s']";
    private static final String FILE_CHECKBOX_LOCATOR = "(//div[@title='%s'])[1]//div[contains(@class,'image_document')]//div[contains(@class,'checkbox')]";
    private static final By UPLOAD_DONE_ICON_LOCATOR = By.xpath("//div[contains(@class,'upload__icon_done')]");
    private static final By POPUP_CLOSE_BUTTON_LOCATOR = By.xpath("//a[contains(@class,'popup-close')]");
    public static final By TRASH_ICON_LOCATOR = By.xpath("//div[contains(@class,'trash')]");

    public MainPage closePopUpWindow() {
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

    public MainPage uploadFile() throws InterruptedException {
        WebElement fileInput = driver.findElement(UPLOAD_INPUT_LOCATOR);
        fileInput.sendKeys(GlobalParameters.FILE_PATH_TO_UPLOAD);
        waitForElementVisible(UPLOAD_DONE_ICON_LOCATOR);
        driver.findElement(UPLOAD_POPUP_CLOSE_BUTTON_LOCATOR).click();
        return this;
    }

    public boolean isFileVisible(String fileName) {
        try {
            driver.findElement(By.xpath(String.format(UPLOADED_FILE_ICON_LOCATOR, fileName)));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public FooterNotificationsPage moveToTrash(String fileName) throws InterruptedException {
        waitForElementVisible(TRASH_ICON_LOCATOR);
        waitForElementVisible(By.xpath(String.format(UPLOADED_FILE_ICON_LOCATOR, fileName)));
        WebElement file = driver.findElement(By.xpath(String.format(UPLOADED_FILE_ICON_LOCATOR, fileName)));
        WebElement trash = driver.findElement(TRASH_ICON_LOCATOR);
        new Actions(driver).dragAndDrop(file, trash).build().perform();
        waitForElementVisible(FooterNotificationsPage.NOTIFICATION_ABOUT_FILE_MOVED_LOCATOR);
        return new FooterNotificationsPage();
    }

       public TrashFolderPage openTrashPage() {
        waitForElementVisible(TRASH_ICON_LOCATOR);
        new Actions(driver).doubleClick(driver.findElement(TRASH_ICON_LOCATOR)).build().perform();
        waitForElementVisible(TrashFolderPage.CRUMBS_CURRENT_LOCATOR);
        return new TrashFolderPage();
    }
}



    /*public RightFilePanelPage markFileCheckbox(String fileName) {
        waitForElementPresent(By.xpath(String.format(FILE_CHECKBOX_LOCATOR, fileName)));
        WebElement fileIcon = driver.findElement(By.xpath(String.format(UPLOADED_FILE_ICON_LOCATOR, fileName)));
        new Actions(driver).moveToElement(fileIcon).build().perform();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById");
        //driver.findElement(By.xpath(String.format(FILE_CHECKBOX_LOCATOR, fileName))).click();
        waitForElementVisible(RightFilePanelPage.RIGHT_FILE_PANEL_LOCATOR);
        return new RightFilePanelPage();
    }*/
