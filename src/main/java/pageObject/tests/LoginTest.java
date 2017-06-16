package pageObject.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.config.GlobalParameters;
import pageObject.pages.HeaderMenuPage;
import pageObject.pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(description = "Check displayed username for logged user account")
    public void loginWithValidCredentials() throws InterruptedException {
        HeaderMenuPage headerMenuPage = new LoginPage(driver)
                .login(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD);
        String userLogin = headerMenuPage.getUserLogin();
        Assert.assertTrue(userLogin
                        .equalsIgnoreCase(GlobalParameters.USER_LOGIN + GlobalParameters.USER_DOMAIN),
                "Login wasn't successful");
        Thread.sleep(2000);
    }

    @Test(dataProvider = "credentialsDataProvider", priority = 1, description = "Check error messages match entered invalid credentials")
    @Parameters({"login", "password", "expectedErrorMessage"})
    public void loginWithInvalidLogin(String login, String password, String expectedErrorMessage) {
        new LoginPage(driver).login(login, password);
        String errorMessage = new LoginPage(driver).getErrorMessage();
        Assert.assertEquals(errorMessage, expectedErrorMessage, "Error message doesn't match");
    }

    @DataProvider(name = "credentialsDataProvider")
    public Object[][] credentialsDataProvider() {
        return new Object[][]{
                {GlobalParameters.EMPTY_STRING, GlobalParameters.EMPTY_STRING, LoginPage.BLANK_INPUTS_ERROR_MESSAGE},
                {GlobalParameters.EMPTY_STRING, GlobalParameters.USER_PASSWORD, LoginPage.BLANK_LOGIN_ERROR_MESSAGE},
                {GlobalParameters.USER_LOGIN, GlobalParameters.EMPTY_STRING, LoginPage.BLANK_PASSWORD_ERROR_MESSAGE},
                {GlobalParameters.USER_LOGIN, GlobalParameters.INVALID_USER_PASSWORD, LoginPage.INVALID_CREDENTIALS_ERROR_MESSAGE},
                {GlobalParameters.INVALID_USER_LOGIN, GlobalParameters.USER_PASSWORD, LoginPage.INVALID_CREDENTIALS_ERROR_MESSAGE}
        };
    }

    @AfterMethod
    public void logout() {
        HeaderMenuPage headerMenuPage = new HeaderMenuPage(driver);
        if (driver.findElement(headerMenuPage.LOGOUT_LINK_LOCATOR).isDisplayed()) {
            headerMenuPage.logout();
        }
    }
}