package MailRu.service;


import MailRu.business_objects.User;
import MailRu.pages.AbstractPage;
import MailRu.pages.HeaderMenuPage;
import MailRu.pages.LoginPage;

public class LoginService extends AbstractPage {

    private LoginPage loginPage = new LoginPage();

    public String login(User user) {
        loginPage.login(user.getLoginPart(), user.getPassword());
        if (driver.findElement(HeaderMenuPage.AUTHORIZATION_USER_LOCATOR).isDisplayed()) {
            return HeaderMenuPage.AUTHORIZATION_USER_LOCATOR.findElement(driver).getText();
        }
        else return driver.findElement(loginPage.AUTHENTICATION_ERROR_MESSAGE_LOCATOR).getText();
    }
}
