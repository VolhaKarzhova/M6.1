package mailRu.business_objects.user;

public class User extends AbstractUser {

    private String login;
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String getLoginPart() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }
}