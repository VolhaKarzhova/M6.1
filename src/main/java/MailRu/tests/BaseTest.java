package MailRu.tests;

import MailRu.pages.LoginPage;
import MailRu.utils.WebDriverSingleton;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    public LoginPage loginPage = new LoginPage();

    @BeforeClass
    public void setUp() {
        loginPage.open();
    }

    @AfterClass
    public void shutDown() {
        WebDriverSingleton.kill();
    }
}