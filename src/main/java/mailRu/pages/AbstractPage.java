package mailRu.pages;

import mailRu.reporting.Logger;
import mailRu.webdriver.WebDriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractPage {

    private static final int WAIT_FOR_ELEMENT_TIMEOUT_SECONDS = 40;
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

    protected void refreshPage() {
        driver.navigate().refresh();
    }

    public void highlightElement(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='5px solid green'", driver.findElement(locator));
    }

    public void unHighlightElement(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='0px'", driver.findElement(locator));
    }

    public void click(final By locator) {
        waitForElementVisible(locator);
        Logger.debug("Clicking element located: " + locator);
        highlightElement(locator);
        unHighlightElement(locator);
        driver.findElement(locator).click();
    }

    public void type(final By locator, String text) {
        waitForElementVisible(locator);
        highlightElement(locator);
        Logger.debug("Typing text '" + text + "' to input form located: " + locator);
        driver.findElement(locator).sendKeys(text);
        unHighlightElement(locator);
    }

    public void clear(final By locator) {
        waitForElementVisible(locator);
        Logger.debug("Clearing the input located: " + locator);
        highlightElement(locator);
        unHighlightElement(locator);
        driver.findElement(locator).clear();
    }

    public String getElementText(final By locator) {
        waitForElementVisible(locator);
        boolean succeed = !driver.findElement(locator).getText().isEmpty();
        if (succeed) {
            highlightElement(locator);
            Logger.debug("Actual result: " + driver.findElement(locator).getText());
            unHighlightElement(locator);
        } else Logger.error("Can't get text from the element by locator " + locator);
        return driver.findElement(locator).getText();
    }

    public void open(String url) {
        Logger.info("Going to URL: " + url);
        driver.get(url);
    }
}