package mailRu.business_objects.letter;

public class Letter {

    private final String addressee;
    private final String subject;
    private final String body;

    public Letter(LetterBuilder builder) {
        this.addressee = builder.addressee;
        this.subject = builder.subject;
        this.body = builder.body;
    }

    public String getAddressee() {
        return addressee;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Addressee: " + getAddressee() + ", Subject: " + getSubject() + ", Body: " + getBody();
    }
}