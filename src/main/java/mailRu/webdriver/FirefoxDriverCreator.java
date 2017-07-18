package mailRu.webdriver;

import mailRu.config.GlobalOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverCreator extends WebDriverCreator {

    @Override
    public WebDriver createWebDriver() {
        System.setProperty("webdriver.gecko.driver", GlobalOptions.instance().getGeckoDriverPath());
        driver = new FirefoxDriver(WebDriverSettings.getFirefoxProfile());
        return driver;
    }
}