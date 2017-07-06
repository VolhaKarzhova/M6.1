package MailRu.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class RandomUtils {

    private final static int bound = 100000;
    private final static int count = 10;

    public static final String getLetterSubject() {
        return "mail number: " + new Random().nextInt(bound);
    }

    public static final String getLetterBody() {
        return "This is a new email " + new Random().nextInt(bound);
    }

    public static final String getInvalidAddressee() {
        return new RandomStringUtils().randomAlphabetic(count);
    }

    public static final String getInvalidPassword() {
        return new RandomStringUtils().randomAlphabetic(count);
    }
}