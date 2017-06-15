package pageObject.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.pages.InboxFolderPage;
import pageObject.pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void loginWithValidCredentials() {
        new LoginPage(driver).open().fillLoginInput(LoginPage.USER_LOGIN)
                .fillPasswordInput(LoginPage.USER_PASSWORD).getAuthorizationIntoMailBox();
        String authorization = new InboxFolderPage(driver).getAuthorizationProof();
        Assert.assertTrue(authorization.startsWith(LoginPage.USER_LOGIN), "Login wasn't successful");
    }

    @Test(priority = 1)
    public void loginWithInvalidLogin() {
        new InboxFolderPage(driver).logout();
        boolean failMessage = new LoginPage(driver).clearLoginInput().fillLoginInput("karzhova")
                .fillPasswordInput(LoginPage.USER_PASSWORD).getAuthenticationFailMessage();
        Assert.assertTrue(failMessage, "There is no login fail message on the page");
    }

    @Test(priority = 2)
    public void loginWithInvalidPassword() {
        boolean failMessage = new LoginPage(driver).clearLoginInput().clearPasswordInput()
                .fillLoginInput(LoginPage.USER_LOGIN).fillPasswordInput("123456").getAuthenticationFailMessage();
        Assert.assertTrue(failMessage, "There is no login fail message on the page");
    }

    @Test(priority = 3)
    public void loginWithEmptyInputs() {
        boolean failMessage = new LoginPage(driver).clearLoginInput().clearPasswordInput().getAuthenticationFailMessage();
        Assert.assertTrue(failMessage, "There is no login fail message on the page");
    }
}