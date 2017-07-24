package mailRu.business_objects.letter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Letter {

    @JsonProperty("Addressee")
    private String addressee;
    @JsonProperty("Subject")
    private String subject;
    @JsonProperty("Body")
    private String body;

    public Letter(){}

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