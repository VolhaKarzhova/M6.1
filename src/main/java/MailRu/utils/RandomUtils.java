package MailRu.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class RandomUtils {

    public static final String getLetterSubject() {
        return "mail number: " + new Random().nextInt(5000);
    }

    public static final String getLetterBody() {
        return "This is a new email " + new Random().nextInt(5000);
    }

    public static final String getInvalidAddressee() {
        return new RandomStringUtils().randomAlphabetic(10);
    }
}