package hranj.marijan.springbootapp.util;

import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

public final class ErrorMessageFormer {

    private ErrorMessageFormer() {}

    public static String formErrorMessage(ObjectError error) {
        return error.getDefaultMessage();
    }

    public static String formErrorMessage(List<ObjectError> errors) {
        return errors.stream()
                .map(ErrorMessageFormer::formErrorMessage)
                .collect(Collectors.joining(";"));
    }

}
