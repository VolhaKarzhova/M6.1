package mailRu.services;


import mailRu.business_objects.user.User;
import mailRu.config.GlobalParameters;
import mailRu.pages.AbstractPage;
import mailRu.pages.HeaderMenuPage;
import mailRu.pages.LoginPage;

public class AuthorizationService extends AbstractPage {

    public void doLogin(User user) {
        new LoginPage().login(user.getLoginPart(), user.getPassword());
    }

    public boolean isUserLoginAfterAuthorizationExpected(User user) {
        return (new HeaderMenuPage().getUserLogin().equalsIgnoreCase(user.getLoginPart() + GlobalParameters.USER_DOMAIN));
    }

    public boolean isInvalidCredentialsErrorMessageExpected(String expectedErrorMessage) {
        return (new LoginPage().getErrorMessage().equalsIgnoreCase(expectedErrorMessage));
    }

    public void doLogout() {
        HeaderMenuPage headerMenuPage = new HeaderMenuPage();
        if (headerMenuPage.isLogOutButtonVisible()) {
            headerMenuPage.logout();
        }
    }
}