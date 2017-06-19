package pageFactory.tests;


import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageFactory.config.GlobalParameters;
import pageFactory.pages.*;


public class LoginTest extends BaseTest {

    private GlobalParameters globalParameters = new GlobalParameters();

    @Test(description = "Check displayed username for logged user account")
    public void loginWithValidCredentials() {
        HeaderMenuPage headerMenuPage = new LoginPage(driver)
                .login(globalParameters.getUserLogin(), globalParameters.getUserPassword());
        String userLogin = headerMenuPage.getUserLogin();
        Assert.assertTrue(userLogin
                        .equalsIgnoreCase(globalParameters.getUserLogin() + globalParameters.getUserDomain()),
                "Login wasn't successful");
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
                {GlobalParameters.EMPTY_STRING, globalParameters.getUserPassword(), LoginPage.BLANK_LOGIN_ERROR_MESSAGE},
                {GlobalParameters.EMPTY_STRING, GlobalParameters.EMPTY_STRING, LoginPage.BLANK_INPUTS_ERROR_MESSAGE},
                {globalParameters.getUserLogin(), GlobalParameters.EMPTY_STRING, LoginPage.BLANK_PASSWORD_ERROR_MESSAGE},
                {globalParameters.getUserLogin(), GlobalParameters.INVALID_USER_PASSWORD, LoginPage.INVALID_CREDENTIALS_ERROR_MESSAGE},
                {GlobalParameters.INVALID_USER_LOGIN, globalParameters.getUserPassword(), LoginPage.INVALID_CREDENTIALS_ERROR_MESSAGE}
        };
    }

    @AfterMethod
    public void logout() {
        HeaderMenuPage headerMenuPage = new HeaderMenuPage(driver);
        if (headerMenuPage.logOutButton.isDisplayed()) {
            headerMenuPage.logout();
        }
    }
}