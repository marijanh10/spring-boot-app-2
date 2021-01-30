package hranj.marijan.springbootapp.dto;

import hranj.marijan.springbootapp.validators.StrongPassword;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UserDto {

    @Size(min = 5, message = "The email must be at least 5 characters long")
    private String email;

    @StrongPassword(message = "The password must have at least 6 characters, 1 number and 1 special character")
    private String password;

}
