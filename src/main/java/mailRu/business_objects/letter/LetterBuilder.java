package mailRu.business_objects.letter;

public class LetterBuilder {

    public final String addressee;
    public String subject;
    public String body;

    public LetterBuilder(String addressee) {
        this.addressee = addressee;
    }

    public LetterBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public LetterBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public Letter build() {
        return new Letter(this);
    }
}