package YandexDisk.tests;


import YandexDisk.pages.LoginPage;
import YandexDisk.utils.WebDriverSingleton;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    public LoginPage loginPage;

    @BeforeClass
    public void setUp() {
        loginPage = new LoginPage().open();
    }

    @AfterClass
    public void sutDown() {
        WebDriverSingleton.kill();
    }
}