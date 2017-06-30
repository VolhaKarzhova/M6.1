package YandexDisk.utils;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


public class WebDriverSingleton {

    private static WebDriver instance;

    private WebDriverSingleton() {
    }

    public static WebDriver getWebDriverInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = init();
    }

    private static WebDriver init() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        /*WebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL("http://127.0.0.1:4445/wd/hub"), DesiredCapabilities.chrome());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
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