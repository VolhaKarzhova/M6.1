package mailRu.webdriver;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverSingleton {

    private static WebDriverCreator webDriverCreator;

    private static WebDriver instance;

    private WebDriverSingleton() {
    }

    public static WebDriver getWebDriverInstance(BrowserType browserType) {
        if (instance != null) {
            return instance;
        }
        return instance = init(browserType);
    }

    public static WebDriver init(BrowserType browserType) {
        WebDriver driver;
        switch (browserType) {
            case FIREFOX:
                webDriverCreator = new FirefoxDriverCreator();
                driver = webDriverCreator.createWebDriver(WebDriverSettings.getFirefoxProfile());
                break;
            case CHROME:
                webDriverCreator = new ChromeDriverCreator();
                driver = webDriverCreator.createWebDriver(WebDriverSettings.getChromeProfile());
                break;
            default:
                throw new RuntimeException("Browser type unsupported");
        }
        driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    public static void kill() {
        if (instance != null) {
            try {
                instance.quit();
            } catch (Exception e) {
            } finally {
                instance = null;
            }
        }
    }
}