package mailRu.webdriver;

import mailRu.config.GlobalOptions;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;

public class WebDriverSettings {

    public static DesiredCapabilities getChromeProfile() {
        System.setProperty("webdriver.chrome.driver", GlobalOptions.instance().getChromeDriverPath());
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", FileUtils.getTempDirectoryPath());
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return capabilities;
    }

    public static DesiredCapabilities getFirefoxProfile() {
        System.setProperty("webdriver.gecko.driver", GlobalOptions.instance().getGeckoDriverPath());
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
        return capabilities;
    }
}