package YandexDisk.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtils {

    public static final String getFileDirectory() {
        return "test_"+new RandomStringUtils().randomAlphabetic(8);
    }

    public static final String getFileName() {
        return new RandomStringUtils().randomAlphanumeric(6);
    }

    public static final String getFileContent() {
        return new RandomStringUtils().randomAlphanumeric(30);
    }
}