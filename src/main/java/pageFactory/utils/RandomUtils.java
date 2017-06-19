package pageFactory.utils;


import pageObject.config.GlobalParameters;

import java.util.Random;

public class RandomUtils {

    private GlobalParameters globalParameters = new GlobalParameters();

    public String setAddressee() {
        return globalParameters.getUserLogin() + globalParameters.getUserDomain();
    }

    public String setLetterSubject() {
        return "mail number: " + new Random().nextInt(5000);
    }

    public String setLetterBody() {
        return "This is a new email " + new Random().nextInt(5000);
    }

    public String setInvalidAddressee() {
        return "qwerty";
    }
}