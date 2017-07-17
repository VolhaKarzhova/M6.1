package mailRu.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class FirefoxDriverCreator extends WebDriverCreator {

    @Override
    public WebDriver createWebDriver(DesiredCapabilities capabilities) {
        driver = new FirefoxDriver(capabilities);
        return driver;
    }
}