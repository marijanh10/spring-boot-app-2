package hranj.marijan.springbootapp.controller;

import hranj.marijan.springbootapp.bean.Response;
import hranj.marijan.springbootapp.dto.UserDto;
import hranj.marijan.springbootapp.services.UserService;
import hranj.marijan.springbootapp.util.DtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    private ResponseEntity<Response> addUser(@Valid @RequestBody(required = false) UserDto userDto, Errors errors) {
        DtoValidator.validateDto(userDto, errors);
        userService.addNewUser(userDto);
        Response response = new Response(HttpStatus.OK, userDto.getEmail() + " was successfully created");
        return ResponseEntity.ok(response);
    }

}
