package mailRu.business_objects.user;

public abstract class UserDecorator extends AbstractUser {

    private AbstractUser userToBeDecorated;

    public UserDecorator(AbstractUser userToBeDecorated) {
        this.userToBeDecorated = userToBeDecorated;
    }

    public String getLoginPart() {
        return userToBeDecorated.getLoginPart();
    }

    public String getPassword() {
        return userToBeDecorated.getPassword();
    }
}