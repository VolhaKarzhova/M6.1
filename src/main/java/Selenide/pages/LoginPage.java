package Selenide.pages;


import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage extends AbstractPage {

    private static final String LOGIN_INPUT_LOCATOR = "mailbox__login";
    private static final String PASSWORD_INPUT_LOCATOR = "#mailbox__password";
    public static final String AUTHENTICATION_ERROR_MESSAGE_LOCATOR = "//*[@id='mailbox:authfail']";
    public static final String LOGIN_BUTTON_LOCATOR = "mailbox__auth__button";


    public HeaderMenuPage login(String login, String password) {
        $(By.id(LOGIN_INPUT_LOCATOR)).setValue(login);
        $(PASSWORD_INPUT_LOCATOR).setValue(password);
        $(By.className(LOGIN_BUTTON_LOCATOR)).pressEnter();
        return page(HeaderMenuPage.class);
    }

    public String getErrorMessage(String errorMessage) {
        $x(AUTHENTICATION_ERROR_MESSAGE_LOCATOR).shouldHave(text(errorMessage));
        return $x(AUTHENTICATION_ERROR_MESSAGE_LOCATOR).getText();
    }
}