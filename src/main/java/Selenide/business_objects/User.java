package Selenide.business_objects;

public class User {

    private String login;
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLoginPart() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}