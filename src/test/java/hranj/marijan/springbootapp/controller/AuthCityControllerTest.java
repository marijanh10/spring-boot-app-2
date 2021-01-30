package hranj.marijan.springbootapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import hranj.marijan.springbootapp.SpringBootAppApplicationTests;
import hranj.marijan.springbootapp.dto.CityDto;
import hranj.marijan.springbootapp.model.User;
import hranj.marijan.springbootapp.services.CityService;
import hranj.marijan.springbootapp.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static hranj.marijan.springbootapp.constants.JwtConstants.JWT_TOKEN_HEADER_KEY;
import static hranj.marijan.springbootapp.constants.JwtConstants.SIGN_IN_URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
class AuthCityControllerTest extends SpringBootAppApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Given no city in body, status code bad request should be retrieved")
    public void testBadRequestWhenNoUserInBody() throws Exception {
        mvc.perform(post("/v1/auth/city/new")
                .header(JWT_TOKEN_HEADER_KEY, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Given invalid city in body, status code bad request should be retrieved")
    public void testBadRequestWhenInvalidCityProvided() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String cityJson = objectMapper.writeValueAsString(new CityDto("", "", 0L));
        mvc.perform(post("/v1/auth/city/new")
                .header(JWT_TOKEN_HEADER_KEY, getJwtToken())
                .content(cityJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Given valid city in body, status code ok should be retrieved and city should be created in database")
    public void testOkWhenValidCityProvided() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CityDto newCity = new CityDto("Vienna", "Austrian city", 1000000L);
        String cityJson = objectMapper.writeValueAsString(newCity);
        mvc.perform(post("/v1/auth/city/new")
                .header(JWT_TOKEN_HEADER_KEY, getJwtToken())
                .content(cityJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        cityService.findById(11).ifPresentOrElse(city -> assertAll(
                () -> assertNotNull(city),
                () -> assertEquals(city.getName(), newCity.getName())
        ), Assertions::fail);
    }

    @Test
    @DisplayName("Given valid city id, the corresponding city should be added in the user favourite cities list")
    public void testCityHasBeenAddedToUserFavoritesList() throws Exception {
        mvc.perform(post("/v1/auth/city/9/addToFavourite")
                .header(JWT_TOKEN_HEADER_KEY, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        User user = userService.findUser("admin");
        cityService.findById(9).ifPresentOrElse(city -> assertAll(
                () -> assertNotNull(city),
                () -> assertTrue(city.getUsersWhoMarkedAsFavourite().contains(user))
        ), Assertions::fail);
    }

    @Test
    @DisplayName("Given valid city id, the corresponding city should be removed from the user favourite cities list")
    public void testCityHasBeenRemovedFromUserFavoritesList() throws Exception {
        mvc.perform(delete("/v1/auth/city/9/removeFromFavourites")
                .header(JWT_TOKEN_HEADER_KEY, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        User user = userService.findUser("admin");
        cityService.findById(9).ifPresentOrElse(city -> assertAll(
                () -> assertNotNull(city),
                () -> assertFalse(city.getUsersWhoMarkedAsFavourite().contains(user))
        ), Assertions::fail);
    }

    private String getJwtToken() throws Exception {
        MvcResult mvcResult = mvc.perform(post(SIGN_IN_URL)
                .param("email", "admin")
                .param("password", "admin")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        return JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.response");
    }

}