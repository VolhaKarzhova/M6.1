package MailRu.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import MailRu.config.GlobalParameters;
import MailRu.pages.HeaderMenuPage;
import MailRu.pages.LoginPage;


public class LoginTest extends BaseTest {

    private static final String BLANK_INPUTS_ERROR_MESSAGE = "Введите имя ящика";
    private static final String INVALID_CREDENTIALS_ERROR_MESSAGE = "Неверное имя или пароль";
    private static final String BLANK_LOGIN_ERROR_MESSAGE = "Введите имя ящика";
    private static final String BLANK_PASSWORD_ERROR_MESSAGE = "Введите пароль";

    @Test(description = "Check displayed username for logged user account")
    public void loginWithValidCredentials() {
        HeaderMenuPage headerMenuPage = new LoginPage(driver)
                .login(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD);
        String userLogin = headerMenuPage.getUserLogin();
        Assert.assertTrue(userLogin
                        .equalsIgnoreCase(GlobalParameters.USER_LOGIN + GlobalParameters.USER_DOMAIN),
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
                {GlobalParameters.EMPTY_STRING, GlobalParameters.EMPTY_STRING, BLANK_INPUTS_ERROR_MESSAGE},
                {GlobalParameters.USER_LOGIN, GlobalParameters.EMPTY_STRING, BLANK_PASSWORD_ERROR_MESSAGE},
                {GlobalParameters.USER_LOGIN, GlobalParameters.INVALID_USER_PASSWORD, INVALID_CREDENTIALS_ERROR_MESSAGE},
                {GlobalParameters.INVALID_USER_LOGIN, GlobalParameters.USER_PASSWORD, INVALID_CREDENTIALS_ERROR_MESSAGE},
                {GlobalParameters.EMPTY_STRING, GlobalParameters.USER_PASSWORD, BLANK_LOGIN_ERROR_MESSAGE}
        };
    }

    @AfterMethod
    public void logout() {
        HeaderMenuPage headerMenuPage = new HeaderMenuPage(driver);
        if (headerMenuPage.isLogOutButtonVisible()) {
            headerMenuPage.logout();
        }
    }
}