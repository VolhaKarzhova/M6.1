package pageObject.pages;

import java.util.Random;

public class GlobalParametersPage {

    public static final String URL = "https://mail.ru";
    public static final String USER_LOGIN = "volhakarzhova";
    public static final String USER_PASSWORD = "1584624Qwe";
    public static final String ADDRESSEE = "volhakarzhova@mail.ru";
    public static final String MAIL_SUBJECT = "mail number: " + new Random().nextInt(5000);
    public static final String MAIL_BODY = "This is a new email " + new Random().nextInt(5000);
    public static final String MAIL_SUBLECT_PATTERN = String.format("//*[@data-subject='%s']", MAIL_SUBJECT);
}
