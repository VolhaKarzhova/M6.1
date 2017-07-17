package mailRu.tests;

import mailRu.business_objects.User;
import mailRu.config.GlobalParameters;
import mailRu.services.AuthorizationService;
import mailRu.utils.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class LoginTest extends BaseTest {

    private static final String BLANK_INPUTS_ERROR_MESSAGE = "Введите имя ящика";
    private static final String INVALID_CREDENTIALS_ERROR_MESSAGE = "Неверное имя или пароль";
    private static final String BLANK_LOGIN_ERROR_MESSAGE = "Введите имя ящика";
    private static final String BLANK_PASSWORD_ERROR_MESSAGE = "Введите пароль";
    public static final User VALID_USER_ACCOUNT = new User(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD);
    private AuthorizationService authorizationService = new AuthorizationService();

    @Test(description = "Check displayed username for logged user USER")
    public void loginWithValidCredentials() {
        authorizationService.doLogin(VALID_USER_ACCOUNT);
        boolean doesUserLoginMatch = authorizationService.isUserLoginAfterAuthorizationExpected(VALID_USER_ACCOUNT);
        Assert.assertTrue(doesUserLoginMatch, "Login wasn't successful");
    }

    @Test(dataProvider = "credentialsDataProvider", priority = 1, description = "Check error messages match entered invalid credentials")
    @Parameters({"doLogin", "password", "expectedErrorMessage"})
    public void loginWithInvalidLogin(String login, String password, String expectedErrorMessage) {
        authorizationService.doLogin(new User(login, password));
        boolean doesErrorMessageMatch = authorizationService.isInvalidCredentialsErrorMessageExpected(expectedErrorMessage);
        Assert.assertTrue(doesErrorMessageMatch, "Error message doesn't match: " + expectedErrorMessage);
    }

    @DataProvider(name = "credentialsDataProvider")
    public Object[][] credentialsDataProvider() {
        return new Object[][]{
                {GlobalParameters.EMPTY_STRING, GlobalParameters.EMPTY_STRING, BLANK_INPUTS_ERROR_MESSAGE},
                {GlobalParameters.USER_LOGIN, GlobalParameters.EMPTY_STRING, BLANK_PASSWORD_ERROR_MESSAGE},
                {GlobalParameters.USER_LOGIN, RandomUtils.getInvalidPassword(), INVALID_CREDENTIALS_ERROR_MESSAGE},
                {RandomUtils.getInvalidAddressee(), GlobalParameters.USER_PASSWORD, INVALID_CREDENTIALS_ERROR_MESSAGE},
                {GlobalParameters.EMPTY_STRING, GlobalParameters.USER_PASSWORD, BLANK_LOGIN_ERROR_MESSAGE}
        };
    }

    @AfterMethod
    public void logout() {
        authorizationService.doLogout();
    }
}