package Selenide.tests;

import Selenide.config.GlobalParameters;
import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeClass;


import static com.codeborne.selenide.Selenide.open;

public class BaseTest {

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        Configuration.browser = "chrome";
        open(GlobalParameters.URL);
    }
}