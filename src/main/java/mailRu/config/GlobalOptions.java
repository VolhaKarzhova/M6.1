package mailRu.config;

import com.beust.jcommander.Parameter;
import mailRu.webdriver.BrowserType;

public class GlobalOptions {

    private static final String SUITE_PATH = "./src/main/resources/suites/smoke.xml";
    private static final String CHROME_DRIVER_PATH = "./src/main/resources/drivers/chromedriver.exe";
    private static final String GECKO_DRIVER_PATH = "./src/main/resources/drivers/geckodriver.exe";
    private static GlobalOptions instance;

    @Parameter(names = {"--host", "-h"}, description = "Host")
    private String host = "192.168.100.7";

    @Parameter(names = {"--port", "-p"}, description = "Port")
    private String port = "4444";

    @Parameter(names = {"--browser", "-b"}, description = "BrowserType")
    private BrowserType browserType = BrowserType.CHROME;

    @Parameter(names = {"--suitePath", "-sp"}, description = "Path to Suite")
    private String suitePath = SUITE_PATH;

    @Parameter(names = {"--chromePath", "-ch"}, description = "Path to chrome driver")
    private String chromeDriverPath = CHROME_DRIVER_PATH;

    @Parameter(names = {"--fireFoxPath", "-ff"}, description = "Path to geckoDriver")
    private String geckoDriverPath = GECKO_DRIVER_PATH;

    private GlobalOptions() {
    }

    public static synchronized GlobalOptions instance() {
        if (instance == null) {
            instance = new GlobalOptions();
        }
        return instance;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getSuitePath() {
        return suitePath;
    }

    public String getChromeDriverPath() {
        return chromeDriverPath;
    }

    public String getGeckoDriverPath() {
        return geckoDriverPath;
    }

    public BrowserType getBrowserType() {
        return browserType;
    }
}