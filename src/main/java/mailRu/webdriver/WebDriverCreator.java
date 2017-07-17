package mailRu.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class WebDriverCreator {

    protected WebDriver driver;

    public abstract WebDriver createWebDriver(DesiredCapabilities capabilities);
}