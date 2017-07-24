package mailRu.cucumber.steps_definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import mailRu.business_objects.user.User;
import mailRu.pages.LoginPage;
import mailRu.services.AuthorizationService;
import org.testng.Assert;

import static mailRu.tests.LoginTest.VALID_USER_ACCOUNT;

public class LoginSteps {

    private AuthorizationService authorizationService = new AuthorizationService();
    private LoginPage loginPage = new LoginPage();

    @Given("^User navigates to MailRu Home Page$")
    public void user_navigates_to_malRu_home_page() {
        loginPage.open();
    }

    @When("^User enters \"([^\"]*)\" and \"([^\"]*)\" and click submit button$")
    public void do_login_to_mailbox(String login, String password) {
        authorizationService.doLogin(new User(login, password));
    }

    @Then("^Check user is authorized successfully$")
    public void is_authorization_successful() {
        boolean isUserLoginExpected = authorizationService.isUserLoginAfterAuthorizationExpected(VALID_USER_ACCOUNT);
        Assert.assertTrue(isUserLoginExpected, "Login wasn't successful");
    }

    @When("^User logOut from the mailbox$")
    public void do_logOut() {
        authorizationService.doLogout();
    }

    @Then("^MailRu home page is displayed$")
    public void is_logout_successful() {
        Assert.assertTrue(loginPage.isLoginInputPresent(), "User didn't logOut from MailBox");
    }

    @Then("^\"([^\"]*)\" message is displayed$")
    public void is_alert_message_expected(String alert) {
        authorizationService.isInvalidCredentialsErrorMessageExpected(alert);
    }
}