package pageFactory.config;


import org.apache.commons.lang3.StringUtils;

public class GlobalParameters {

    public static final String URL = "https://mail.ru";
    private static final String USER_LOGIN = "volhakarzhova";
    private static final String USER_DOMAIN = "@mail.ru";
    private static final String USER_PASSWORD = "1584624Qwe";
    public static final String INVALID_USER_LOGIN = "iuytr";
    public static final String INVALID_USER_PASSWORD = "12345";
    public static final String EMPTY_STRING = StringUtils.EMPTY;
    public static final String BLANK_LETTER_SUBJECT_STRING = "<Без темы>";

    public String getUserLogin() {
        return USER_LOGIN;
    }

    public String getUserPassword() {
        return USER_PASSWORD;
    }

    public String getUserDomain() {
        return USER_DOMAIN;
    }
}