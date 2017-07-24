package mailRu.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import mailRu.business_objects.letter.Letter;
import mailRu.business_objects.letter.LetterBuilder;
import mailRu.config.GlobalParameters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private final static String baseFile = "letterList.json";

    public static void toJSON(List<Letter> letterList) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(baseFile), letterList);
        System.out.println("json created!");
    }

//    public static Letter toJavaObject() throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.readValue(new File(baseFile), Letter.class);
//    }

    public static void main(String[] args) throws IOException {
        Letter letterWithAllFieldsFilled = new LetterBuilder(GlobalParameters.USER_LOGIN+GlobalParameters.USER_DOMAIN)
                .setSubject(RandomUtils.getLetterSubject()).setBody(RandomUtils.getLetterBody()).build();
        Letter blankLetter = new LetterBuilder(GlobalParameters.USER_LOGIN+GlobalParameters.USER_DOMAIN)
                .setSubject(GlobalParameters.EMPTY_STRING).setBody(GlobalParameters.EMPTY_STRING).build();
        Letter letterWithInvalidAddressee = new LetterBuilder(RandomUtils.getInvalidAddressee())
                .setSubject(RandomUtils.getLetterSubject()).setBody(RandomUtils.getLetterBody()).build();
        List<Letter> letters = new ArrayList<>();
            letters.add(letterWithAllFieldsFilled);
            letters.add(blankLetter);
            letters.add(letterWithInvalidAddressee);

        JsonUtils.toJSON(letters);
    }
}

