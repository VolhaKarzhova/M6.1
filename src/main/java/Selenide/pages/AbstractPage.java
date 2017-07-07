package Selenide.pages;


import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class AbstractPage {

    protected static void waitUntilElementIsVisible(String locator) {
        $x(locator).waitUntil(visible, 2000);
    }
}