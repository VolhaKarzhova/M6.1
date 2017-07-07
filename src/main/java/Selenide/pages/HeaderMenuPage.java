package Selenide.pages;


import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class HeaderMenuPage extends AbstractPage {

    private static final String AUTHORIZATION_USER_LOCATOR = "//i[@id='PH_user-email']";
    private static final String LOGOUT_LINK_LOCATOR = "//*[@id='PH_logoutLink']";

    public String getUserLogin() {
        waitUntilElementIsVisible(AUTHORIZATION_USER_LOCATOR);
        return $x(AUTHORIZATION_USER_LOCATOR).getText();
    }

    public LoginPage logout() {
        waitUntilElementIsVisible(LOGOUT_LINK_LOCATOR);
        $x(LOGOUT_LINK_LOCATOR).click();
        return page(LoginPage.class);
    }

    public boolean isLogOutButtonVisible() {
        return $x(LOGOUT_LINK_LOCATOR).isDisplayed();
    }
}