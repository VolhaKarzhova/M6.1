package yandexDisk.services;

import yandexDisk.business_objects.User;
import yandexDisk.pages.AbstractPage;
import yandexDisk.pages.FileListPage;
import yandexDisk.pages.LoginPage;

public class AuthorizationService extends AbstractPage {

    public void doLogin(User user) {
        new LoginPage().login(user.getLogin(), user.getPassword());
    }

    public boolean isUserLoginAfterAuthorizationExpected(User user) {
        return (new FileListPage().getUserLogin().equalsIgnoreCase(user.getLogin()));
    }
}