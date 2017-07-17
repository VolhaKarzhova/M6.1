package mailRu.config;

import com.beust.jcommander.Parameter;
import mailRu.webdriver.BrowserType;

public class GlobalOptions {
    public static final String SUITE_PATH = "./src/main/resources/suites/smoke.xml";
    private static GlobalOptions instance;

    @Parameter(names = {"--host", "-h"}, description = "Host")
    private String host = "192.168.100.7";

    @Parameter(names = {"--port", "-p"}, description = "Port")
    private String port = "4444";

    @Parameter(names = {"--browser", "-b"}, description = "BrowserType")
    private BrowserType browserType = BrowserType.CHROME;

    @Parameter(names = {"--suitePath", "-sp"}, description = "Path to Suite")
    //private String suitePath = "./suites/smoke.xml";
    private String suitePath = "./src/main/resources/suites/smoke.xml";

    @Parameter(names = {"--chromePath", "-ch"}, description = "Path to chrome driver")
    //private String chromeDriverPath = "./drivers/chromedriver.exe";
    private String chromeDriverPath = "./src/main/resources/drivers/chromedriver.exe";

    @Parameter(names = {"--fireFoxPath", "-ff"}, description = "Path to geckoDriver")
    //private String geckoDriverPath = "./drivers/geckodriver.exe";
    private String geckoDriverPath = "./src/main/resources/drivers/geckodriver.exe";


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
}