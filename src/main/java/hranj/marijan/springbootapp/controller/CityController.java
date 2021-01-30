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
@RequestMapping("/v1/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/all")
    private ResponseEntity<Response> getAll() {
        Response response = new Response(HttpStatus.OK, cityService.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sorted")
    private ResponseEntity<Response> getAllSorted() {
        Response response = new Response(HttpStatus.OK, cityService.findAllSortedByTimeAdded());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/popular")
    private ResponseEntity<Response> getMostPopularCities() {
        Response response = new Response(HttpStatus.OK, cityService.findMostPopularCities());
        return ResponseEntity.ok(response);
    }

}
