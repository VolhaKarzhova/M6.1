package pageObject.utils;

import pageObject.config.GlobalParameters;

public class Utils {

    public static final String getAddressee() {
        return GlobalParameters.USER_LOGIN + GlobalParameters.USER_DOMAIN;
    }
}