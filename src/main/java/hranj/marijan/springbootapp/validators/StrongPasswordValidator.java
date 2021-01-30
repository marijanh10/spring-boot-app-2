package hranj.marijan.springbootapp.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    private static final Pattern STRONG_PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$");

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return STRONG_PASSWORD_PATTERN.matcher(string).matches();
    }

}
