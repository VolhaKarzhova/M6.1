package MailRu.services;


import MailRu.business_objects.User;
import MailRu.config.GlobalParameters;
import MailRu.pages.AbstractPage;
import MailRu.pages.HeaderMenuPage;
import MailRu.pages.LoginPage;

public class AuthorizationService extends AbstractPage {

    public void doLogin(User user) {
        new LoginPage().login(user.getLoginPart(), user.getPassword());
    }

    public boolean doesUserLoginAfterAuthorizationMatchExpected(User user) {
        return (new HeaderMenuPage().getUserLogin().equalsIgnoreCase(user.getLoginPart() + GlobalParameters.USER_DOMAIN));
    }

    public boolean doesInvalidCredentialsErrorMessageMatchExpected(String expectedErrorMessage) {
        return (new LoginPage().getErrorMessage().equalsIgnoreCase(expectedErrorMessage));
    }

    public void doLogout() {
        HeaderMenuPage headerMenuPage = new HeaderMenuPage();
        if (headerMenuPage.isLogOutButtonVisible()) {
            headerMenuPage.logout();
        }
    }
}