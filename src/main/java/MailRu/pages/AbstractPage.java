package MailRu.pages;

import YandexDisk.utils.WebDriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractPage {

    private static final int WAIT_FOR_ELEMENT_TIMEOUT_SECONDS = 30;
    protected WebDriver driver;

    protected AbstractPage() {
        this.driver = WebDriverSingleton.getWebDriverInstance();
    }

    protected void waitForElementPresent(By locator) {
        new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT_SECONDS).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected void waitForElementVisible(By locator) {
        new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT_SECONDS).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForElementEnabled(By locator) {
        new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT_SECONDS).until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForElementDisappear(By locator) {
        new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT_SECONDS).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void waitForAlertDisplayed() {
        new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT_SECONDS).until(ExpectedConditions.alertIsPresent());
    }
    protected void refreshPage () {
        driver.navigate().refresh();
    }

    protected boolean isElementPresent (By locator) {
        return driver.findElement(locator).isDisplayed();
    }
}