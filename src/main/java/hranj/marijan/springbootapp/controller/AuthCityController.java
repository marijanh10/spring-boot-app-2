package hranj.marijan.springbootapp.controller;

import hranj.marijan.springbootapp.bean.Response;
import hranj.marijan.springbootapp.dto.CityDto;
import hranj.marijan.springbootapp.model.City;
import hranj.marijan.springbootapp.model.User;
import hranj.marijan.springbootapp.services.CityService;
import hranj.marijan.springbootapp.services.UserService;
import hranj.marijan.springbootapp.util.DtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static hranj.marijan.springbootapp.constants.JwtConstants.JWT_TOKEN_HEADER_KEY;

@RestController
@RequestMapping("/v1/auth/city")
public class AuthCityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @PostMapping("/new")
    private ResponseEntity<Response> addCity(@RequestHeader(value = JWT_TOKEN_HEADER_KEY, required = false) String jwtToken,
                                             @Valid @RequestBody(required = false) CityDto cityDto, Errors errors) {
        checkJwtTokenHeader(jwtToken);
        DtoValidator.validateDto(cityDto, errors);
        cityService.addNewCity(cityDto);
        Response response = new Response(HttpStatus.OK, cityDto.getName() + " was successfully created");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{cityId}/addToFavourite")
    private ResponseEntity<Response> addToFavourites(@RequestHeader(value = JWT_TOKEN_HEADER_KEY, required = false) String jwtToken,
                                                     @PathVariable int cityId, Authentication authentication) {
        checkJwtTokenHeader(jwtToken);
        cityService.findById(cityId)
                .ifPresentOrElse(city -> addCityToUser(city, authentication.getName()),
                        () -> { throw new IllegalArgumentException("City with id: " + cityId + " not found!"); });
        Response response = new Response(HttpStatus.OK, "City was successfully added to favourites");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cityId}/removeFromFavourites")
    private ResponseEntity<Response> removeFromFavourites(@RequestHeader(value = JWT_TOKEN_HEADER_KEY, required = false) String jwtToken,
                                                     @PathVariable int cityId, Authentication authentication) {
        checkJwtTokenHeader(jwtToken);
        cityService.findById(cityId)
                .ifPresentOrElse(city -> removeCityFromUser(city, authentication.getName()),
                        () -> { throw new IllegalArgumentException("City with id: " + cityId + " not found!"); });
        Response response = new Response(HttpStatus.OK, "City was successfully removed from favourites");
        return ResponseEntity.ok(response);
    }

    private void addCityToUser(City city, String email) {
        User user = userService.findUser(email);
        if (user != null) {
            user.addFavouriteCity(city);
            userService.saveUsers(user);
        }
    }

    private void removeCityFromUser(City city, String email) {
        User user = userService.findUser(email);
        if (user != null) {
            if (user.removeFavouriteCity(city)) {
                userService.saveUsers(user);
            } else {
                throw new IllegalArgumentException("User " + user.getEmail() +
                        " didn't mark the city: " + city.getName() + " as favourite");
            }
        }
    }

    private void checkJwtTokenHeader(@RequestHeader(value = JWT_TOKEN_HEADER_KEY, required = false) String jwtToken) {
        if (jwtToken == null) {
            throw new IllegalArgumentException("Please provide the header: " + JWT_TOKEN_HEADER_KEY);
        }
    }

}
