package hranj.marijan.springbootapp.exeptions;

public class UserAlreadyExistsException extends IllegalArgumentException {

    public UserAlreadyExistsException(String explanation) {
        super(explanation);
    }

}
