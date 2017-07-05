package MailRu.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
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
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", FileUtils.getTempDirectoryPath());

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);

        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(ChromeOptions.CAPABILITY, options);

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver(cap);
        /*WebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL("http://127.0.0.1:4445/wd/hub"), cap);
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