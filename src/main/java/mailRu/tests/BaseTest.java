package mailRu.tests;

import mailRu.pages.LoginPage;
import mailRu.webdriver.WebDriverSingleton;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

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