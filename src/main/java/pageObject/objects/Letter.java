package pageObject.objects;

import java.util.Random;

import static pageObject.config.GlobalParameters.*;

public class Letter {

    private String addressee;
    private String subject;
    private String body;

    public static final String ADDRESSEE = USER_LOGIN + USER_DOMAIN;
    public static final String MAIL_SUBJECT = "mail number: " + new Random().nextInt(5000);
    public static final String MAIL_BODY = "This is a new email " + new Random().nextInt(5000);
    public static final String INVALID_ADDRESSEE = "qwerty";

    public Letter() {
        this.addressee = ADDRESSEE;
        this.subject = MAIL_SUBJECT;
        this.body = MAIL_BODY;
    }
    public Letter(String addressee, String subject, String body) {
        this.addressee = addressee;
        this.subject = subject;
        this.body = body;
    }

    public boolean checkEqualLetters(Object obj) {
        Letter letter1 = (Letter) obj;
        if (letter1.addressee == this.addressee && letter1.subject == this.subject && letter1.body == this.body) {
            return true;
        } else return false;
    }
}