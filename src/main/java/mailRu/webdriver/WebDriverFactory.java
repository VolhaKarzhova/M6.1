package mailRu.webdriver;

import mailRu.config.GlobalOptions;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverFactory {

    protected static WebDriverCreator webDriverCreator;
    public static WebDriver driver;

    public static WebDriver init() {
        switch (GlobalOptions.instance().getBrowserType()) {
            case FIREFOX:
                webDriverCreator = new FirefoxDriverCreator();
                driver = webDriverCreator.createWebDriver();
                break;
            case CHROME:
                webDriverCreator = new ChromeDriverCreator();
                driver = webDriverCreator.createWebDriver();
                break;
            default:
                throw new RuntimeException("Browser type unsupported");
        }
        driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }
}