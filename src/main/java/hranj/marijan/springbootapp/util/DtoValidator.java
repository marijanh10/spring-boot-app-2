package hranj.marijan.springbootapp.util;

import org.springframework.validation.Errors;

public final class DtoValidator {

    private DtoValidator() {}

    public static <T> void validateDto(T dto, Errors errors) throws IllegalArgumentException {
        if (dto == null) {
            throw new IllegalArgumentException("Please provide a valid object in JSON format");
        } else if (errors.hasErrors()) {
            throw new IllegalArgumentException(ErrorMessageFormer.formErrorMessage(errors.getAllErrors()));
        }
    }

}
