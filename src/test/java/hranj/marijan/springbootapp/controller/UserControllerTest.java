package hranj.marijan.springbootapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hranj.marijan.springbootapp.SpringBootAppApplicationTests;
import hranj.marijan.springbootapp.dto.UserDto;
import hranj.marijan.springbootapp.model.User;
import hranj.marijan.springbootapp.services.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static hranj.marijan.springbootapp.constants.JwtConstants.SIGN_IN_URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.text.CharSequenceLength.hasLength;

@AutoConfigureMockMvc
class UserControllerTest extends SpringBootAppApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Given no user in body, status code bad request should be retrieved")
    public void testBadRequestWhenNoUserInBody() throws Exception {
        mvc.perform(post("/v1/user/signup")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Given user in body with weak password, status code bad request should be retrieved")
    public void testBadRequestWhenUserHasWeakPassword() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(new UserDto("user", "user"));
        mvc.perform(post("/v1/user/signup")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Given valid user in body, status code ok should be retrieved and user should be created in database")
    public void testOkWhenValidUserProvided() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto newUser = new UserDto("user@user.com", "useR!23");
        String userJson = objectMapper.writeValueAsString(newUser);
        mvc.perform(post("/v1/user/signup")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        User user = userService.findUser(newUser.getEmail());
        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals(newUser.getEmail(), user.getEmail()),
                () -> assertTrue(passwordEncoder.matches(newUser.getPassword(), user.getPassword()))
        );
    }

    @Test
    @DisplayName("Given not provided required parameters, status code bad request should be retrieved")
    public void testBadRequestWhenNoParametersProvided() throws Exception {
        mvc.perform(post(SIGN_IN_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Given provided invalid credentials, status code unauthorized should be retrieved")
    public void testUnauthorizedWhenUserSignsIn() throws Exception {
        mvc.perform(post(SIGN_IN_URL)
                .param("email", "admin")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Given provided valid credentials, status code ok should be retrieved and a jwt token should be provided")
    public void testOkWhenUserSignsIn() throws Exception {
        mvc.perform(post(SIGN_IN_URL)
                .param("email", "admin")
                .param("password", "admin")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response", hasLength(167)));
    }

}