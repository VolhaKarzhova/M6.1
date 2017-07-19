package mailRu.services;


import mailRu.business_objects.user.AbstractUser;
import mailRu.business_objects.user.User;
import mailRu.business_objects.user.UserLoginDecorator;
import mailRu.pages.AbstractPage;
import mailRu.pages.HeaderMenuPage;
import mailRu.pages.LoginPage;

public class AuthorizationService extends AbstractPage {

    public void doLogin(User user) {
        try {
            new LoginPage().login(user.getLoginPart(), user.getPassword());
        } catch (Exception e) {
        }
    }

    public boolean isUserLoginAfterAuthorizationExpected(User user) {
        AbstractUser decoratedUser = new UserLoginDecorator(user);
        return (new HeaderMenuPage().getUserLogin().equalsIgnoreCase(decoratedUser.getLoginPart()));
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