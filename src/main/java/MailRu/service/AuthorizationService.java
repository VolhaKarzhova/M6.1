package MailRu.service;


import MailRu.business_objects.User;
import MailRu.config.GlobalParameters;
import MailRu.pages.AbstractPage;
import MailRu.pages.HeaderMenuPage;
import MailRu.pages.LoginPage;

public class AuthorizationService extends AbstractPage {

    public void doLogin(User user) {
        new LoginPage().login(user.getLoginPart(), user.getPassword());
    }

    public boolean checkUserLoginAfterAuthorization(User user) {
        doLogin(user);
        String userLogin = new HeaderMenuPage().getUserLogin();
        return (userLogin.equalsIgnoreCase(user.getLoginPart() + GlobalParameters.USER_DOMAIN));
    }

    public boolean checkErrorMessageWhileLoginWithInvalidCredentials(User user, String expectedErrorMessage) {
        refreshPage();
        doLogin(user);
        String errorMessage = new LoginPage().getErrorMessage();
        return (errorMessage.equalsIgnoreCase(expectedErrorMessage));
    }

    public void doLogout() {
        HeaderMenuPage headerMenuPage = new HeaderMenuPage();
        if (headerMenuPage.isLogOutButtonVisible()) {
            headerMenuPage.logout();
        }
    }
}