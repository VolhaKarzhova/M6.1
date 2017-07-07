package Selenide.tests;

import Selenide.business_objects.User;
import Selenide.config.GlobalParameters;
import Selenide.pages.HeaderMenuPage;
import Selenide.pages.LoginPage;
import Selenide.utils.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.page;

public class LoginTest extends BaseTest {

    private static final String BLANK_INPUTS_ERROR_MESSAGE = "Введите имя ящика";
    private static final String INVALID_CREDENTIALS_ERROR_MESSAGE = "Неверное имя или пароль";
    private static final String BLANK_LOGIN_ERROR_MESSAGE = "Введите имя ящика";
    private static final String BLANK_PASSWORD_ERROR_MESSAGE = "Введите пароль";
    private static final User VALID_USER_ACCOUNT = new User(GlobalParameters.USER_LOGIN, GlobalParameters.USER_PASSWORD);

    @Test(description = "Check displayed username for logged user USER")
    public void loginWithValidCredentials() {
        LoginPage loginPage = page(LoginPage.class);
        HeaderMenuPage headerMenuPage = page(HeaderMenuPage.class);
        loginPage.login(VALID_USER_ACCOUNT.getLoginPart(), VALID_USER_ACCOUNT.getPassword());
        Assert.assertEquals(headerMenuPage.getUserLogin(), VALID_USER_ACCOUNT.getLoginPart() + GlobalParameters.USER_DOMAIN, "Login wasn't successful");
    }

    @Test(dataProvider = "credentialsDataProvider", priority = 1, description = "Check error messages match entered invalid credentials")
    @Parameters({"doLogin", "password", "expectedErrorMessage"})
    public void loginWithInvalidLogin(String login, String password, String expectedErrorMessage) {
        User user = new User(login, password);
        LoginPage loginPage = page(LoginPage.class);
        loginPage.login(user.getLoginPart(), user.getPassword());
        Assert.assertEquals(loginPage.getErrorMessage(expectedErrorMessage), expectedErrorMessage, "Error message doesn't match: " + expectedErrorMessage);
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
        HeaderMenuPage headerMenuPage = page(HeaderMenuPage.class);
        if (headerMenuPage.isLogOutButtonVisible()) {
            headerMenuPage.logout();
        }
    }
}