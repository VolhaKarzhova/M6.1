package MailRu.tests;

import MailRu.business_objects.User;
import MailRu.service.LoginService;
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
    private static final User VALID_USER_ACCOUNT = new User(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD);
    private static final LoginService loginService = new LoginService();

    @Test(description = "Check displayed username for logged user USER")
    public void loginWithValidCredentials() {
        loginService.login(VALID_USER_ACCOUNT);
        String userLogin = new HeaderMenuPage().getUserLogin();
        Assert.assertTrue(userLogin.equalsIgnoreCase(VALID_USER_ACCOUNT.getLoginPart() + GlobalParameters.USER_DOMAIN),
                "Login wasn't successful");
    }

    @Test(dataProvider = "credentialsDataProvider", priority = 1, description = "Check error messages match entered invalid credentials")
    @Parameters({"login", "password", "expectedErrorMessage"})
    public void loginWithInvalidLogin(String login, String password, String expectedErrorMessage) {
        User user = new User(login, password);
        loginService.login(user);
        String errorMessage = loginPage.getErrorMessage();
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
        HeaderMenuPage headerMenuPage = new HeaderMenuPage();
        if (headerMenuPage.isLogOutButtonVisible()) {
            headerMenuPage.logout();
        }
    }
}