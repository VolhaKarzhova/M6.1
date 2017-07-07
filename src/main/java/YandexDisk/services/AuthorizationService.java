package YandexDisk.services;

import YandexDisk.business_objects.User;
import YandexDisk.pages.AbstractPage;
import YandexDisk.pages.FileListPage;
import YandexDisk.pages.LoginPage;

public class AuthorizationService extends AbstractPage {

    public void doLogin(User user) {
        new LoginPage().login(user.getLogin(), user.getPassword());
    }

    public boolean doesUserLoginAfterAuthorizationMatchExpected(User user) {
        return (new FileListPage().getUserLogin().equalsIgnoreCase(user.getLogin()));
    }
}