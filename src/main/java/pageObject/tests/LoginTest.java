package pageObject.tests;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.pages.GlobalParametersPage;
import pageObject.pages.HeaderMenuPage;
import pageObject.pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void loginWithValidCredentials() {
        new LoginPage(driver).login(GlobalParametersPage.USER_LOGIN, GlobalParametersPage.USER_PASSWORD);
        String authorization = new HeaderMenuPage(driver).getUserLogin();
        Assert.assertTrue(authorization.startsWith(GlobalParametersPage.USER_LOGIN), "Login wasn't successful");
    }

    @Test(dataProvider = "credentialsDataProvider", priority = 1)
    @Parameters({"login", "password", "expectedErrorMessage"})
    public void loginWithInvalidLogin(String login, String password, String expectedErrorMessage) {
        new HeaderMenuPage(driver).logout();
        new LoginPage(driver).login(login, password);
        String errorMessage = new LoginPage(driver).getErrorMessage();
        Assert.assertEquals(errorMessage, expectedErrorMessage, "Error message doesn't match");
    }

    @DataProvider(name = "credentialsDataProvider")
    public Object[][] credentialsDataProvider() {
        return new Object[][]{
                {"volha", GlobalParametersPage.USER_PASSWORD, LoginPage.INVALID_CREDENTIALS_ERROR_MESSAGE},
                {GlobalParametersPage.USER_LOGIN, "12345", LoginPage.INVALID_CREDENTIALS_ERROR_MESSAGE},
                {"", "", LoginPage.BLANK_INPUTS_ERROR_MESSAGE},
                {GlobalParametersPage.USER_LOGIN, "", LoginPage.BLANK_PASSWORD_ERROR_MESSAGE},
                {"", GlobalParametersPage.USER_PASSWORD, LoginPage.BLANK_LOGIN_ERROR_MESSAGE},
        };
    }
}