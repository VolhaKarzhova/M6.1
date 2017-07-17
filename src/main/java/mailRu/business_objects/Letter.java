package mailRu.business_objects;

public class Letter {

    private final String addressee;
    private final String subject;
    private final String body;

    private Letter(LetterBuilder builder) {
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

    public static class LetterBuilder {
        private final String addressee;
        private String subject;
        private String body;

        public LetterBuilder (String addressee) {
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

    @Override
    public String toString() {
        return "Addressee: " + getAddressee() + ", Subject: " + getSubject() + ", Body: " + getBody();
    }
}