package mailRu.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverCreator extends WebDriverCreator {

    @Override
    public WebDriver createWebDriver() {
        driver = new FirefoxDriver(WebDriverSettings.getFirefoxProfile());
        return driver;
    }
}