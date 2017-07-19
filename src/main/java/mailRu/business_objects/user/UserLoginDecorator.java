package mailRu.business_objects.user;

import mailRu.config.GlobalParameters;

public class UserLoginDecorator extends UserDecorator {

    public UserLoginDecorator(AbstractUser userToBeDecorated) {
        super(userToBeDecorated);
    }

    @Override
    public String getLoginPart() {
        return super.getLoginPart() + getDomainPart();
    }

    private String getDomainPart() {
        return GlobalParameters.USER_DOMAIN;
    }
}