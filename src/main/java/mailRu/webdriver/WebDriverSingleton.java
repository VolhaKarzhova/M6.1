package mailRu.webdriver;

import mailRu.reporting.Logger;
import org.openqa.selenium.WebDriver;

public class WebDriverSingleton extends WebDriverFactory {

    private static WebDriver instance;

    private WebDriverSingleton() {
    }

    public static WebDriver getWebDriverInstance() {
        Logger.trace("Getting WebDriver instance");
        if (instance != null) {
            return instance;
        }
        return instance = init();
    }

    public static void kill() {
        if (instance != null) {
            try {
                instance.quit();
            } catch (Exception e) {
            } finally {
                Logger.trace("WebDriver quits");
                instance = null;
            }
        }
    }
}