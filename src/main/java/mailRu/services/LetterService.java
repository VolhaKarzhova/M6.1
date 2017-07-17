package mailRu.services;

import mailRu.business_objects.Letter;
import mailRu.config.GlobalParameters;
import mailRu.utils.RandomUtils;
import mailRu.utils.Utils;

public class LetterService {

    public Letter createLetterWithAllFilledInputs() {
        return new Letter.LetterBuilder(Utils.getAddressee())
                .setSubject(RandomUtils.getLetterSubject()).setBody(RandomUtils.getLetterBody()).build();
    }

    public Letter createLetterWithOnlyAddressee() {
        return new Letter.LetterBuilder(Utils.getAddressee()).build();
    }

    public Letter createLetterWithInvalidAddresse() {
        return new Letter.LetterBuilder(RandomUtils.getInvalidAddressee())
                .setSubject(RandomUtils.getLetterSubject()).setBody(RandomUtils.getLetterBody()).build();
    }

    public Letter createReceviedBlankLetter() {
        return new Letter.LetterBuilder(Utils.getAddressee()).setSubject(GlobalParameters.BLANK_LETTER_SUBJECT_STRING)
                .setBody(GlobalParameters.EMPTY_STRING).build();
    }
}