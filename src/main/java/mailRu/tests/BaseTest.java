package mailRu.tests;

import mailRu.pages.LoginPage;
import mailRu.reporting.ScreenshotExecutor;
import mailRu.webdriver.WebDriverSingleton;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

@Listeners(ScreenshotExecutor.class)
public class BaseTest {

    private LoginPage loginPage = new LoginPage();

    @BeforeSuite
    public void setUp() {
        loginPage.open();
    }

    @AfterSuite
    public void shutDown() {
        WebDriverSingleton.kill();
    }
}